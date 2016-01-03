package com.nickfunnell.arch.espoc.domain.command;

import com.nickfunnell.arch.espoc.domain.InstanceType;

import java.util.UUID;

/**
 * Created by nick on 20/12/2015.
 */
public class CreateVirtualInstanceCommand implements Command {
    public final UUID id;
    public final InstanceType type;
    public final String ownerEmail;
    public final String instanceName;

    public CreateVirtualInstanceCommand(UUID id, InstanceType type, String ownerEmail) {
        this.id = id;
        this.type = type;
        this.ownerEmail = ownerEmail;
        this.instanceName = id + "-" + type;
    }

    public UUID aggregateId() {
        return id;
    }
}
