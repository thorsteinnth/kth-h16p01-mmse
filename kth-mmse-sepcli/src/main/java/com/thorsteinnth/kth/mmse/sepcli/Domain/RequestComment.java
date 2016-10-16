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

    public String toDisplayString()
    {
        return "[" + createdTime.toString() + "] " + createdByUser.email + ": " + comment;
    }

    @Override
    public String toString() {
        return "RequestComment{" +
                "createdByUser=" + createdByUser +
                ", createdTime=" + createdTime +
                ", comment='" + comment + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        RequestComment that = (RequestComment) o;

        if (createdByUser != null ? !createdByUser.equals(that.createdByUser) : that.createdByUser != null)
            return false;
        if (createdTime != null ? !createdTime.equals(that.createdTime) : that.createdTime != null) return false;
        return comment != null ? comment.equals(that.comment) : that.comment == null;

    }

    @Override
    public int hashCode()
    {
        int result = createdByUser != null ? createdByUser.hashCode() : 0;
        result = 31 * result + (createdTime != null ? createdTime.hashCode() : 0);
        result = 31 * result + (comment != null ? comment.hashCode() : 0);
        return result;
    }
}
