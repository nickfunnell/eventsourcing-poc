package com.nickfunnell.arch.espoc.read.event.handler;

import com.nickfunnell.arch.espoc.domain.InstanceStatus;
import com.nickfunnell.arch.espoc.domain.event.Event;
import com.nickfunnell.arch.espoc.domain.event.VirtualInstanceSpunDownEvent;
import com.nickfunnell.arch.espoc.read.VirtualInstanceDTO;

import java.util.Map;
import java.util.UUID;

/**
 * Created by nick on 03/01/2016.
 */
public class VirtualInstanceSpunDownReadHandler implements ReadHandler {

    private Map<UUID,VirtualInstanceDTO> virtualInstanceDTOMap;

    public VirtualInstanceSpunDownReadHandler(Map<UUID, VirtualInstanceDTO> virtualInstanceDTOMap) {
        this.virtualInstanceDTOMap = virtualInstanceDTOMap;
    }

    @Override
    public void update(Event event) {
        if(event instanceof VirtualInstanceSpunDownEvent){

            VirtualInstanceDTO dto = virtualInstanceDTOMap.get(((VirtualInstanceSpunDownEvent) event).id);
            dto.setStatus(InstanceStatus.stopped);
            dto.setVersion(event.getVersion());

        }
    }
}
