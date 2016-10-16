package com.thorsteinnth.kth.mmse.sepcli.Repository;

import com.thorsteinnth.kth.mmse.sepcli.Domain.EventRequest;

import java.util.ArrayList;

public interface IEventRequestRepository
{
    void createEventRequest(EventRequest eventRequest);
    ArrayList<EventRequest> getAllEventRequests();
}
