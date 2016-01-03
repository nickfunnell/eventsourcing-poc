package com.nickfunnell.arch.espoc.controller;

import com.nickfunnell.arch.espoc.domain.InstanceStatus;
import com.nickfunnell.arch.espoc.domain.command.CreateVirtualInstanceCommand;
import com.nickfunnell.arch.espoc.domain.InstanceRepository;
import com.nickfunnell.arch.espoc.domain.VirtualInstance;
import com.nickfunnell.arch.espoc.domain.command.DestroyVirtualInstanceCommand;
import com.nickfunnell.arch.espoc.domain.command.SpinDownVirtualInstanceCommand;
import com.nickfunnell.arch.espoc.domain.command.SpinUpVirtualInstanceCommand;

/**
 * Created by nick on 29/12/2015.
 */
public class VirtualInstanceCommandHandler {
    private InstanceRepository repository;

    private static final int NEW_VERSION = -1;

    public VirtualInstanceCommandHandler(InstanceRepository repository) {
        this.repository = repository;
    }

    public void handle(CreateVirtualInstanceCommand command) {
        VirtualInstance instance = new VirtualInstance(command.id, command.type, command.instanceName, command.ownerEmail);
        repository.save(instance, NEW_VERSION);
    }

    public void handle(SpinUpVirtualInstanceCommand command) {
        VirtualInstance instance = repository.getById(command.id);
        if (!InstanceStatus.stopped.equals(instance.getStatus())) {
            throw new IncorrectStateException("Tried to spin up instance that wasn't in 'stopped' mode.");
        }
        instance.start();
        repository.save(instance, command.originalVersion);
    }

    public void handle(SpinDownVirtualInstanceCommand command) {
        VirtualInstance instance = repository.getById(command.id);
        if (!InstanceStatus.started.equals(instance.getStatus())) {
            throw new IncorrectStateException("Tried to stop instance that wasn't running.");
        }
        instance.stop();
        repository.save(instance, command.originalVersion);
    }

    public void handle(DestroyVirtualInstanceCommand command) {
        VirtualInstance instance = repository.getById(command.id);
        if (InstanceStatus.destroyed.equals(instance.getStatus())) {
            throw new IncorrectStateException("Tried to destroy instance that was already destroyed.");
        }
        instance.destroy();
        repository.save(instance, command.originalVersion);
    }

    public class IncorrectStateException extends RuntimeException {
        public IncorrectStateException(String message) {
            super(message);
        }
    }
}
