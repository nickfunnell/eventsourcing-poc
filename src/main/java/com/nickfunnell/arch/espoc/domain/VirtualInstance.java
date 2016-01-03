package com.nickfunnell.arch.espoc.domain;

import com.nickfunnell.arch.espoc.domain.event.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * An Aggregate Root representing a virtual machine instance
 * Created by nick on 19/12/2015.
 */
public class VirtualInstance extends AggregateRoot {
    Logger logger = LogManager.getLogger(VirtualInstance.class);
    private UUID id;
    private InstanceStatus status;
    private String ownerEmail;

    @Override
    public UUID getId() {
        return id;
    }

    public InstanceStatus getStatus() {
        return status;
    }

    public String getOwnerEmail() {
        return ownerEmail;
    }

    @Override
    protected void apply(Event event) {
        //TODO Do something more elegant here - possibly using reflection
        if (event instanceof VirtualInstanceCreatedEvent) {
            apply((VirtualInstanceCreatedEvent) event);
        } else if (event instanceof VirtualInstanceDestroyedEvent) {
            apply((VirtualInstanceDestroyedEvent) event);
        } else if (event instanceof VirtualInstanceSpunUpEvent) {
            apply((VirtualInstanceSpunUpEvent) event);
        } else if (event instanceof VirtualInstanceSpunDownEvent) {
            apply((VirtualInstanceSpunDownEvent) event);
        } else {
            throw new RuntimeException("Type " + event + " not recognised.");
        }

    }

    private void apply(VirtualInstanceCreatedEvent e) {
        this.id = e.instanceId;
        this.status = InstanceStatus.stopped;
        this.ownerEmail = e.ownerEmail;
    }

    private void apply(VirtualInstanceDestroyedEvent e) { this.status = InstanceStatus.destroyed; }

    private void apply(VirtualInstanceSpunUpEvent e) {
        this.status = InstanceStatus.started;
    }

    private void apply(VirtualInstanceSpunDownEvent e) {
        this.status = InstanceStatus.stopped;
    }

    public VirtualInstance(UUID id, InstanceType type, String hostname, String ownerEmail) {
        applyChange(new VirtualInstanceCreatedEvent(id, type, hostname, ownerEmail));
    }

    protected VirtualInstance() {

    }


    public void start() {

        applyChange(new VirtualInstanceSpunUpEvent(id));
    }

    public void stop() {
        applyChange(new VirtualInstanceSpunDownEvent(id));
    }

    public void destroy() {
        applyChange(new VirtualInstanceDestroyedEvent(id));
    }
}
