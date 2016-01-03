package com.nickfunnell.arch.espoc.read;

import com.nickfunnell.arch.espoc.client.VirtualInstanceManagementAPI;
import com.nickfunnell.arch.espoc.controller.VirtualInstanceCommandHandler;
import com.nickfunnell.arch.espoc.domain.EventStore;
import com.nickfunnell.arch.espoc.domain.InstanceRepository;
import com.nickfunnell.arch.espoc.domain.InstanceStatus;
import com.nickfunnell.arch.espoc.domain.InstanceType;
import com.nickfunnell.arch.espoc.read.VirtualInstanceReadModel;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static junit.framework.TestCase.assertEquals;

/**
 * Created by nick on 29/12/2015.
 */
public class VirtualInstanceReadModelTest {

    private VirtualInstanceManagementAPI controller;
    private VirtualInstanceReadModel readModel;

    @Rule
    public final ExpectedException exception = ExpectedException.none();

    @Before
    public void init() {

        EventStore eventStore = new EventStore();
        InstanceRepository repository = new InstanceRepository(eventStore);
        this.readModel = new VirtualInstanceReadModel(eventStore);
        controller = new VirtualInstanceManagementAPI(new VirtualInstanceCommandHandler(repository),
                this.readModel);
    }

    @Test
    public void getVirtualInstanceDTO() {
        UUID id = UUID.randomUUID();

        String email = "test.user1@vmail.com";
        controller.createNewVirtualInstance(id, InstanceType.small, email);

        VirtualInstanceDTO virtualInstanceDTO = readModel.getVirtualInstanceDTO(id);

        assertEquals(InstanceStatus.stopped,virtualInstanceDTO.getStatus());
        controller.startVirtualInstance(id,virtualInstanceDTO.getVersion());

        virtualInstanceDTO = readModel.getVirtualInstanceDTO(id);

        assertEquals(InstanceStatus.started,virtualInstanceDTO.getStatus());

        controller.destroyVirtualInstance(id,virtualInstanceDTO.getVersion());

        assertEquals(InstanceStatus.destroyed,readModel.getVirtualInstanceDTO(id).getStatus());


    }


}
