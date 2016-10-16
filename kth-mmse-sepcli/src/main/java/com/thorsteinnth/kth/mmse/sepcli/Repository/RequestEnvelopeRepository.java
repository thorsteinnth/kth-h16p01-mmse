package com.thorsteinnth.kth.mmse.sepcli.Repository;

import com.thorsteinnth.kth.mmse.sepcli.AppData;
import com.thorsteinnth.kth.mmse.sepcli.Domain.RequestEnvelope;
import com.thorsteinnth.kth.mmse.sepcli.Domain.User;

import java.util.ArrayList;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class RequestEnvelopeRepository implements IRequestEnvelopeRepository
{
    @Override
    public void save(RequestEnvelope envelope)
    {
        AppData.mailQueue.add(envelope);
    }

    @Override
    public ArrayList<RequestEnvelope> getAllRequestEnvelopes()
    {
        return AppData.mailQueue;
    }

    @Override
    public ArrayList<RequestEnvelope> getRequestEnvelopesForUser(User user)
    {
        return AppData.mailQueue.stream()
                .filter(re -> re.getRecipient().equals(user))
                .collect(Collectors.toCollection(ArrayList::new));
    }
}
