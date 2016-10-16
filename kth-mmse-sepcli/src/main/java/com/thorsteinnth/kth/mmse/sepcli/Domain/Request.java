package com.thorsteinnth.kth.mmse.sepcli.Domain;

import java.util.UUID;

public abstract class Request
{
    private String id;
    private User createdByUser;

    public Request(User createdByUser)
    {
        this.id = UUID.randomUUID().toString();
        this.createdByUser = createdByUser;
    }

    public String getId()
    {
        return this.id;
    }

    public User getCreatedByUser()
    {
        return this.createdByUser;
    }

    public abstract String toDisplayString();
}
