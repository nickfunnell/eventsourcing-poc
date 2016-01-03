package com.nickfunnell.arch.espoc.read.event.handler;

import com.nickfunnell.arch.espoc.domain.InstanceStatus;
import com.nickfunnell.arch.espoc.domain.event.Event;
import com.nickfunnell.arch.espoc.domain.event.VirtualInstanceSpunUpEvent;
import com.nickfunnell.arch.espoc.read.VirtualInstanceDTO;

import java.util.Map;
import java.util.UUID;

/**
 * Created by nick on 03/01/2016.
 */
public class VirtualInstanceSpunUpReadReadHandler implements ReadHandler {

    private Map<UUID,VirtualInstanceDTO> virtualInstanceDTOMap;

    public VirtualInstanceSpunUpReadReadHandler(Map<UUID, VirtualInstanceDTO> virtualInstanceDTOMap) {
        this.virtualInstanceDTOMap = virtualInstanceDTOMap;
    }

    @Override
    public void update(Event event) {
        if(event instanceof VirtualInstanceSpunUpEvent){

            VirtualInstanceDTO dto = virtualInstanceDTOMap.get(((VirtualInstanceSpunUpEvent) event).id);
            dto.setStatus(InstanceStatus.started);
            dto.setVersion(event.getVersion());

        }
    }
}
