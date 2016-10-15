package com.thorsteinnth.kth.mmse.sepcli.Repository;

import com.thorsteinnth.kth.mmse.sepcli.AppData;
import com.thorsteinnth.kth.mmse.sepcli.Domain.EventRequest;

import java.util.ArrayList;

public class EventRequestRepository implements IEventRequestRepository
{
    public int getNextId()
    {
        // NOTE: We will never delete any requests in our implementation, so size+1 works
        return getAllEventRequests().size() + 1;
    }

    @Override
    public void addEventRequest(EventRequest eventRequest)
    {
        AppData.eventRequests.add(eventRequest);
    }

    @Override
    public ArrayList<EventRequest> getAllEventRequests()
    {
        return AppData.eventRequests;
    }
}
