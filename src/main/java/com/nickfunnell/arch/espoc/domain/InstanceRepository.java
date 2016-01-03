package com.nickfunnell.arch.espoc.domain;

import com.nickfunnell.arch.espoc.domain.event.Event;

import java.util.List;
import java.util.UUID;

/**
 * DDD-style 'repository' - intent is to provide a generic access Class that provides a 'Collection' view/abstraction
 * of any underlying storage persistence-retrieval mechanism.
 * TODO Genericise interface to be able to persist events for any given aggregate
 *
 * Created by nick on 29/12/2015.
 */
public class InstanceRepository {

    private final EventStore eventStore;

    public InstanceRepository(EventStore eventStore){
        this.eventStore = eventStore;
    }

    public void save(AggregateRoot aggregate, int expectedVersion)
    {
        eventStore.saveEvents(aggregate.getId(), aggregate.getUncommittedChanges(), expectedVersion);
    }

    public VirtualInstance getById(UUID id)
    {
        VirtualInstance virtualInstance = new VirtualInstance();
        List<Event> events = eventStore.getEventsForAggregate(id);
        virtualInstance.loadsFromHistory(events);
        return virtualInstance;
    }
}
