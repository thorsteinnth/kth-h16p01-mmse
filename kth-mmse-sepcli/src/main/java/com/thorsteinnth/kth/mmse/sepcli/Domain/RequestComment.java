package com.thorsteinnth.kth.mmse.sepcli.Domain;

import java.util.Date;

public class RequestComment
{
    private User createdByUser;
    private Date createdTime;
    private String comment;

    public RequestComment(User createdByUser, String comment)
    {
        this.createdByUser = createdByUser;
        this.comment = comment;
        this.createdTime = new Date();
    }

    public User getCreatedByUser()
    {
        return this.createdByUser;
    }

    public Date getCreatedTime()
    {
        return this.createdTime;
    }

    public String getComment()
    {
        return this.comment;
    }

    @Override
    public String toString() {
        return "RequestComment{" +
                "createdByUser=" + createdByUser +
                ", createdTime=" + createdTime +
                ", comment='" + comment + '\'' +
                '}';
    }
}
