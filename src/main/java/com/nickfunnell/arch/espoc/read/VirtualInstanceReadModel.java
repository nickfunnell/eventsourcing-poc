package com.nickfunnell.arch.espoc.read;

import com.nickfunnell.arch.espoc.domain.EventStore;
import com.nickfunnell.arch.espoc.domain.event.*;
import com.nickfunnell.arch.espoc.read.event.handler.VirtualInstanceCreatedReadHandler;
import com.nickfunnell.arch.espoc.read.event.handler.VirtualInstanceDestroyedReadHandler;
import com.nickfunnell.arch.espoc.read.event.handler.VirtualInstanceSpunDownReadHandler;
import com.nickfunnell.arch.espoc.read.event.handler.VirtualInstanceSpunUpReadHandler;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * In a full Event Sourcing application, a separate 'read' infrastructure would be built which would read events
 * (which, described by tense, have already happened) from some sort of messaging transport and assemble a read view
 * of the data. This class simulates the management of the read side by subscribing a number of event 'ReadHandlers'
 * to the EventStore, which keep a read-optimised representation of the current data up-to-date.
 *
 * This view is 'eventually consistent'. If the client happens to try to read the changed data, and the read request
 * reaches the read model before the event has been read from the EventBus, the view will be inconsistent. At this
 * scale, though (and in this implementation), that won't happen.
 *
 *
 * Created by nick on 29/12/2015.
 */
public class VirtualInstanceReadModel {
    private Map<UUID,VirtualInstanceDTO> inMemoryReadStorage = new HashMap<>();

    public VirtualInstanceReadModel(EventStore eventStore) {
        eventStore.subscribeToEventBus(new VirtualInstanceCreatedReadHandler(inMemoryReadStorage));
        eventStore.subscribeToEventBus(new VirtualInstanceSpunUpReadHandler(inMemoryReadStorage));
        eventStore.subscribeToEventBus(new VirtualInstanceSpunDownReadHandler(inMemoryReadStorage));
        eventStore.subscribeToEventBus(new VirtualInstanceDestroyedReadHandler(inMemoryReadStorage));
    }

    public VirtualInstanceDTO getVirtualInstanceDTO(UUID id){
        return inMemoryReadStorage.get(id);
    }

    private void apply(VirtualInstanceDestroyedEvent event) {

    }
}
