package com.thorsteinnth.kth.mmse.sepcli.Domain;

import java.util.Date;

/**
 * Class used to send requests between users
 */
public class RequestEnvelope
{
    private Request request;
    private User sender;
    private User recipient;
    private Date createdDateTime;

    public RequestEnvelope(Request request, User sender, User recipient)
    {
        this.request = request;
        this.sender = sender;
        this.recipient = recipient;
        this.createdDateTime = new Date();
    }

    public Request getRequest()
    {
        return request;
    }

    public User getSender()
    {
        return sender;
    }

    public User getRecipient()
    {
        return recipient;
    }

    public Date getCreatedDateTime()
    {
        return createdDateTime;
    }

    @Override
    public String toString() {
        return "RequestEnvelope{" +
                "request=" + request +
                ", sender=" + sender +
                ", recipient=" + recipient +
                ", createdDateTime=" + createdDateTime +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        RequestEnvelope that = (RequestEnvelope) o;

        if (request != null ? !request.equals(that.request) : that.request != null) return false;
        if (sender != null ? !sender.equals(that.sender) : that.sender != null) return false;
        if (recipient != null ? !recipient.equals(that.recipient) : that.recipient != null) return false;
        return createdDateTime != null ? createdDateTime.equals(that.createdDateTime) : that.createdDateTime == null;

    }

    @Override
    public int hashCode() {
        int result = request != null ? request.hashCode() : 0;
        result = 31 * result + (sender != null ? sender.hashCode() : 0);
        result = 31 * result + (recipient != null ? recipient.hashCode() : 0);
        result = 31 * result + (createdDateTime != null ? createdDateTime.hashCode() : 0);
        return result;
    }
}
