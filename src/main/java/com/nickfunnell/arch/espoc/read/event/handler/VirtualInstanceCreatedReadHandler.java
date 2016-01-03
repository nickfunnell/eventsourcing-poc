package com.nickfunnell.arch.espoc.read.event.handler;

import com.nickfunnell.arch.espoc.domain.InstanceStatus;
import com.nickfunnell.arch.espoc.domain.event.Event;
import com.nickfunnell.arch.espoc.domain.event.VirtualInstanceCreatedEvent;
import com.nickfunnell.arch.espoc.read.VirtualInstanceDTO;

import java.util.Map;
import java.util.UUID;

/**
 * Created by nick on 03/01/2016.
 */
public class VirtualInstanceCreatedReadHandler implements Handler {

    private Map<UUID,VirtualInstanceDTO> virtualInstanceDTOMap;

    public VirtualInstanceCreatedReadHandler(Map<UUID, VirtualInstanceDTO> virtualInstanceDTOMap) {
        this.virtualInstanceDTOMap = virtualInstanceDTOMap;
    }

    @Override
    public void update(Event event) {
        if(event instanceof VirtualInstanceCreatedEvent){
            VirtualInstanceCreatedEvent createdEvent = (VirtualInstanceCreatedEvent) event;
            VirtualInstanceDTO dto = new VirtualInstanceDTO(createdEvent.instanceId,
                    createdEvent.ownerEmail,
                    createdEvent.type,
                    InstanceStatus.stopped,
                    createdEvent.getVersion());
            virtualInstanceDTOMap.put(createdEvent.instanceId,dto);
        }
    }
}
