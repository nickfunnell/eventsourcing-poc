package com.nickfunnell.arch.espoc.client;

import com.nickfunnell.arch.espoc.controller.VirtualInstanceCommandHandler;
import com.nickfunnell.arch.espoc.read.VirtualInstanceReadModel;
import com.nickfunnell.arch.espoc.domain.VirtualInstance;
import com.nickfunnell.arch.espoc.domain.command.CreateVirtualInstanceCommand;
import com.nickfunnell.arch.espoc.domain.InstanceType;
import com.nickfunnell.arch.espoc.domain.command.DestroyVirtualInstanceCommand;
import com.nickfunnell.arch.espoc.domain.command.SpinDownVirtualInstanceCommand;
import com.nickfunnell.arch.espoc.domain.command.SpinUpVirtualInstanceCommand;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.UUID;

/**
 * Simple 'client' class. This would usually be creating commands and adding them to a transport of some
 * sort (which would be read by one or more CommandHandlers). In this case, it's tightly-coupled to
 * {@link com.nickfunnell.arch.espoc.controller.VirtualInstanceCommandHandler VirtualInstanceCommandHandler} and
 * {@link VirtualInstanceReadModel VirtualInstanceReadModel}
 * <p>
 * TODO Refactor to decouple and add transport
 * TODO Convert this to a REST API that can be called by a client or external test framework
 *
 * Created by nick on 29/12/2015.
 */
public class VirtualInstanceManagementAPI {

    private VirtualInstanceCommandHandler commandHandler;
    private VirtualInstanceReadModel readModel;

    public VirtualInstanceManagementAPI(VirtualInstanceCommandHandler commandHandler, VirtualInstanceReadModel readModel) {
        this.commandHandler = commandHandler;
        this.readModel = readModel;
    }

    public void createNewVirtualInstance(UUID id, InstanceType type, String ownerEmail) {
        CreateVirtualInstanceCommand command = new CreateVirtualInstanceCommand(id, type, ownerEmail);
        commandHandler.handle(command);
    }

    //version is passed in to check for concurrency issues - has another client already updated the VirtualInstance
    //(in which case, the existing version will not match the expected version
    public void startVirtualInstance(UUID id, int version) {
        SpinUpVirtualInstanceCommand command = new SpinUpVirtualInstanceCommand(id, version);
        commandHandler.handle(command);
    }

    public void stopVirtualInstance(UUID id, int version) {
        SpinDownVirtualInstanceCommand command = new SpinDownVirtualInstanceCommand(id, version);
        commandHandler.handle(command);
    }

    public void destroyVirtualInstance(UUID id, int version) {
        DestroyVirtualInstanceCommand command = new DestroyVirtualInstanceCommand(id, version);
        commandHandler.handle(command);
    }

    public VirtualInstance getVirtualInstance(UUID id){

        throw new NotImplementedException();
    }

    public VirtualInstance getInstancesPerOwner(String ownerEmail){

        throw new NotImplementedException();
    }

    public int getInstanceUptime(UUID id){
        //Use left fold? to grab uptimes and sum them
        throw new NotImplementedException();
    }

    public int getInstanceCreationTime(UUID id){
        throw new NotImplementedException();
    }


}
