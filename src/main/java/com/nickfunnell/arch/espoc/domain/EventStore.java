package com.nickfunnell.arch.espoc.domain;

import com.nickfunnell.arch.espoc.domain.event.Event;
import com.nickfunnell.arch.espoc.read.event.handler.ReadHandler;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.*;
import java.util.stream.Collectors;

/**
 *
 * A simple event storage solution, used to save all events on a per-aggregate basis. The class also contains a 'dummy'
 * message bus, which records all events that occur.
 * Created by nick on 29/12/2015.
 */
public class EventStore {
    Logger logger = LogManager.getLogger(EventStore.class);

    private Map<UUID, List<EventDescriptor>> current = new HashMap<>();

    //This is a very quick and dirty 'dummy' message bus, to allow the Command side to publish events in one unified
    //sequential stream, and for the Query side to subscribe to the messages and convert them into a queryable format.
    //In normal usage, this would be a decoupled messaging stream (Kafka, for instance), allowing the the two sides
    //to be independently scalable
    //Note this is implemented as a List rather than a queue as we don't want to be more 'publish and subscribe' than
    //'point to point' - essentially, we want the subscriber to pick a place in the event stream, rather than simply
    //removing the head event
    private List eventBus = new ArrayList();
    private Set<ReadHandler> subscribedHandlers = new HashSet<ReadHandler>();


    public void saveEvents(UUID aggregateId, List<Event> events, int expectedVersion) {

        List<EventDescriptor> eventDescriptors;

        if (!current.containsKey(aggregateId)) {
            //First event for the aggregate. No chance of concurrency issues
            eventDescriptors = new ArrayList<>();
            current.put(aggregateId, eventDescriptors);
        } else {
            eventDescriptors = current.get(aggregateId);
            if (eventDescriptors.get(eventDescriptors.size() - 1).version != expectedVersion) {
                throw new ConcurrentModificationException(
                        String.format("Expected version: %d, current version: %d",
                                expectedVersion,
                                eventDescriptors.get(eventDescriptors.size() - 1).version));
            }
        }

        int i = expectedVersion;
        for (Event event : events) {
            i++;
            event.setVersion(i);
            eventDescriptors.add(new EventDescriptor(aggregateId, event, i));

            logger.info("Publishing event %d on dummy 'event bus' for read-side subscribers.", aggregateId);

            //call update on each handler
            subscribedHandlers.forEach(handler -> handler.update(event));
        }

    }

    public void subscribeToEventBus(ReadHandler handler) {
        subscribedHandlers.add(handler);

    }

    // collect all processed events for given aggregate and return them as a list
    // used to build up an aggregate from its history (Domain.LoadsFromHistory)
    public List<Event> getEventsForAggregate(UUID aggregateId) {
        List<EventDescriptor> eventDescriptors;

        if (!current.containsKey(aggregateId)) {
            throw new AggregateNotFoundException(String.format("Aggregate %s does not exist in eventStore", aggregateId));
        }

        eventDescriptors = current.get(aggregateId);
        return eventDescriptors.stream().map(eventDescriptor -> eventDescriptor.eventData).collect(Collectors.toList());

    }

    public class EventDescriptor {

        public final Event eventData;
        public final UUID id;
        public final int version;

        public EventDescriptor(UUID id, Event eventData, int version) {
            this.eventData = eventData;
            this.version = version;
            this.id = id;
        }
    }

    private class AggregateNotFoundException extends RuntimeException {
        public AggregateNotFoundException(String message) {
            super(message);
        }
    }

}
