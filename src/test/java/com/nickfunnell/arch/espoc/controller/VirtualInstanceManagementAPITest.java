package com.nickfunnell.arch.espoc.controller;

import com.nickfunnell.arch.espoc.client.VirtualInstanceManagementAPI;
import com.nickfunnell.arch.espoc.domain.EventStore;
import com.nickfunnell.arch.espoc.domain.InstanceRepository;
import com.nickfunnell.arch.espoc.domain.InstanceType;
import com.nickfunnell.arch.espoc.read.VirtualInstanceReadModel;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by nick on 29/12/2015.
 */
public class VirtualInstanceManagementAPITest {

    private VirtualInstanceManagementAPI controller;

    @Rule
    public final ExpectedException exception = ExpectedException.none();

    @Before
    public void init() {

        //A very simple 'dummy' message bus. This is purely to allow both the Command side to publish events as they
        //happen, and the Query side to subscribe to the events and convert them into a convenient read format.
        List eventBus = new ArrayList();
        EventStore eventStore = new EventStore();
        InstanceRepository repository = new InstanceRepository(eventStore);
        controller = new VirtualInstanceManagementAPI(new VirtualInstanceCommandHandler(repository),
                new VirtualInstanceReadModel(eventStore));
    }

    @Test
    public void createVMInstance() {
        UUID id = UUID.randomUUID();

        String email = "test.user1@vmail.com";
        controller.createNewVirtualInstance(id, InstanceType.small, email);

        controller.startVirtualInstance(id,0);

    }

    @Test
    public void cannotSpinUpStartedInstance() {


        UUID id = UUID.randomUUID();

        String email = "test.user1@vmail.com";
        controller.createNewVirtualInstance(id, InstanceType.small, email);

        //The first command will work. The second will throw an exception
        exception.expect(VirtualInstanceCommandHandler.IncorrectStateException.class);
        controller.startVirtualInstance(id,0);
        controller.startVirtualInstance(id,1);
    }

    @Test
    public void startAndStopInstance() {


        UUID id = UUID.randomUUID();

        String email = "test.user1@vmail.com";
        controller.createNewVirtualInstance(id, InstanceType.small, email);
        controller.startVirtualInstance(id,0);
        controller.stopVirtualInstance(id,1);
    }


}
