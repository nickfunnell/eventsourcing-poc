package com.nickfunnell.arch.espoc.read.event.handler;

import com.nickfunnell.arch.espoc.domain.event.Event;

/**
 * Created by nick on 02/01/2016.
 */
public interface Handler {
    public void update(Event event);
}
