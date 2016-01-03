package com.nickfunnell.arch.espoc.domain.event;

import java.util.UUID;

/**
 * Created by nick on 19/12/2015.
 */
public class VirtualInstanceDestroyedEvent extends Event {
    public final UUID id;

    public VirtualInstanceDestroyedEvent(UUID id) {
        this.id = id;
    }
}
