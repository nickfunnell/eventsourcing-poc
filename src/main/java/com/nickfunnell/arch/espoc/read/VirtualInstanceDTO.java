package com.nickfunnell.arch.espoc.read;

import com.nickfunnell.arch.espoc.domain.InstanceStatus;
import com.nickfunnell.arch.espoc.domain.InstanceType;

import java.util.UUID;

/**
 *
 * This is a truly anaemic view of a VirtualInstance, used to hold the VIs in an in-memory read map. Normally, the read
 * layer would consist of storage formats that are convenient for reading, and DTOs purely to transfer the data to the
 * view
 * Created by nick on 02/01/2016.
 */
public class VirtualInstanceDTO {
    private UUID id;
    private String ownerEmail;
    private InstanceType type;
    private InstanceStatus status;
    private int version;

    public void setOwnerEmail(String ownerEmail) {
        this.ownerEmail = ownerEmail;
    }

    public void setType(InstanceType type) {
        this.type = type;
    }

    public void setStatus(InstanceStatus status) {
        this.status = status;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public VirtualInstanceDTO(UUID id, String ownerEmail, InstanceType type, InstanceStatus status, int version) {
        this.id = id;
        this.ownerEmail = ownerEmail;
        this.type = type;
        this.status = status;
        this.version = version;
    }

    public UUID getId() {
        return id;
    }

    public String getOwnerEmail() {
        return ownerEmail;
    }

    public InstanceType getType() {
        return type;
    }

    public InstanceStatus getStatus() {
        return status;
    }

    public int getVersion() {
        return version;
    }
}
