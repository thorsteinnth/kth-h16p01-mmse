package com.thorsteinnth.kth.mmse.sepcli.Service;

import com.thorsteinnth.kth.mmse.sepcli.AppData;
import com.thorsteinnth.kth.mmse.sepcli.Domain.Request;
import com.thorsteinnth.kth.mmse.sepcli.Domain.RequestEnvelope;
import com.thorsteinnth.kth.mmse.sepcli.Domain.User;
import com.thorsteinnth.kth.mmse.sepcli.Repository.IRequestEnvelopeRepository;

import java.util.ArrayList;

public class RequestMailService
{
    private IRequestEnvelopeRepository repository;

    public RequestMailService(IRequestEnvelopeRepository repository)
    {
        this.repository = repository;
    }

    public RequestEnvelope sendRequest(Request request, User recipient)
    {
        User sender = AppData.loggedInUser;

        RequestEnvelope envelope = new RequestEnvelope(request, sender, recipient);
        return this.repository.save(envelope);
    }

    public ArrayList<RequestEnvelope> getAllRequestEnvelopes()
    {
        return this.repository.getAllRequestEnvelopes();
    }

    public ArrayList<RequestEnvelope> getRequestEnvelopesForUser(User user)
    {
        return this.repository.getRequestEnvelopesForUser(user);
    }

    public void removeRequestEnvelope(RequestEnvelope envelope)
    {
        this.repository.delete(envelope);
    }
}
