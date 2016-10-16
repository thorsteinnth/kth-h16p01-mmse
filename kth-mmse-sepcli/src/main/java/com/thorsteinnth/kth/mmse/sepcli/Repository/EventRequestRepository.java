package com.thorsteinnth.kth.mmse.sepcli.Repository;

import com.thorsteinnth.kth.mmse.sepcli.AppData;
import com.thorsteinnth.kth.mmse.sepcli.Domain.EventRequest;

import java.util.ArrayList;

public class EventRequestRepository implements IEventRequestRepository
{
    @Override
    public void createEventRequest(EventRequest eventRequest)
    {
        AppData.eventRequests.add(eventRequest);
    }

    @Override
    public ArrayList<EventRequest> getAllEventRequests()
    {
        return AppData.eventRequests;
    }
}
