package com.thorsteinnth.kth.mmse.sepcli.Repository;

import com.thorsteinnth.kth.mmse.sepcli.Domain.RequestEnvelope;
import com.thorsteinnth.kth.mmse.sepcli.Domain.User;

import java.util.ArrayList;

public interface IRequestEnvelopeRepository
{
    RequestEnvelope save(RequestEnvelope envelope);
    void delete(RequestEnvelope envelope);
    ArrayList<RequestEnvelope> getAllRequestEnvelopes();
    ArrayList<RequestEnvelope> getRequestEnvelopesForUser(User user);
}
