package com.nickfunnell.arch.espoc.domain.command;

import java.util.UUID;

/**
 * Created by nick on 22/12/2015.
 */
public class SpinUpVirtualInstanceCommand {
    public final UUID id;
    public final int originalVersion;

    public SpinUpVirtualInstanceCommand(UUID id, int originalVersion) {
        this.id = id;
        this.originalVersion = originalVersion;
    }
}
