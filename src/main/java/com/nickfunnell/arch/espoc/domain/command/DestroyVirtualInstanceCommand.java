package com.nickfunnell.arch.espoc.domain.command;

import java.util.UUID;

/**
 * Created by nick on 29/12/2015.
 */
public class DestroyVirtualInstanceCommand {
    public final UUID id;
    public final int originalVersion;

    public DestroyVirtualInstanceCommand(UUID id, int originalVersion) {
        this.id = id;
        this.originalVersion = originalVersion;
    }
}
