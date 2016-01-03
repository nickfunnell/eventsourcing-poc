package com.nickfunnell.arch.espoc.domain.event;

import com.nickfunnell.arch.espoc.domain.InstanceType;

import java.util.UUID;

/**
 * VirtualInstanceCreatedEvent
 * Note that this is 'in the past', and is simply immutable data
 * Created by nick on 19/12/2015.
 */
public class VirtualInstanceCreatedEvent extends Event{
    public final UUID instanceId;
    public final String hostname;
    public final String ownerEmail;
    public final InstanceType type;

    public VirtualInstanceCreatedEvent(UUID instanceId, InstanceType type,String hostname, String ownerEmail) {
        this.instanceId = instanceId;
        this.type = type;
        this.hostname = hostname;
        this.ownerEmail = ownerEmail;
    }
}
