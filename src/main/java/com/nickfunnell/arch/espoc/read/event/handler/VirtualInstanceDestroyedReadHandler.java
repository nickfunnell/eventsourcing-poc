package com.nickfunnell.arch.espoc.read.event.handler;

import com.nickfunnell.arch.espoc.domain.InstanceStatus;
import com.nickfunnell.arch.espoc.domain.event.Event;
import com.nickfunnell.arch.espoc.domain.event.VirtualInstanceDestroyedEvent;
import com.nickfunnell.arch.espoc.domain.event.VirtualInstanceSpunUpEvent;
import com.nickfunnell.arch.espoc.read.VirtualInstanceDTO;

import java.util.Map;
import java.util.UUID;

/**
 * Created by nick on 03/01/2016.
 */
public class VirtualInstanceDestroyedReadHandler implements Handler {

    private Map<UUID,VirtualInstanceDTO> virtualInstanceDTOMap;

    public VirtualInstanceDestroyedReadHandler(Map<UUID, VirtualInstanceDTO> virtualInstanceDTOMap) {
        this.virtualInstanceDTOMap = virtualInstanceDTOMap;
    }

    @Override
    public void update(Event event) {
        if(event instanceof VirtualInstanceDestroyedEvent){

            VirtualInstanceDTO dto = virtualInstanceDTOMap.get(((VirtualInstanceDestroyedEvent) event).id);
            dto.setStatus(InstanceStatus.destroyed);
            dto.setVersion(event.getVersion());

        }
    }
}
