package com.nickfunnell.arch.espoc.domain.event;

import java.util.UUID;

/**
 * VirtualInstanceCreatedEvent
 * Note that this is 'in the past', and is simply immutable data
 * Created by nick on 19/12/2015.
 */
public class VirtualInstanceSpunDownEvent extends Event {
    public final UUID id;

    public VirtualInstanceSpunDownEvent(UUID id) {
        this.id = id;

    }
}
