package com.nickfunnell.arch.espoc.read.event.handler;

import com.nickfunnell.arch.espoc.domain.event.Event;

/**
 * ReadHandler interface. Implementations will subscribe to the 'dummy' EventBus, and handle specific types of events
 * Created by nick on 02/01/2016.
 */
public interface ReadHandler {
    public void update(Event event);
}
