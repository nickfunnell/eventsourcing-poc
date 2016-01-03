package com.nickfunnell.arch.espoc.domain.command;

import java.util.UUID;

/**
 * Created by nick on 20/12/2015.
 */
public interface Command {
    UUID aggregateId();
}
