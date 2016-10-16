package com.thorsteinnth.kth.mmse.sepcli.Service;

import com.thorsteinnth.kth.mmse.sepcli.AppData;
import com.thorsteinnth.kth.mmse.sepcli.Domain.Client;
import com.thorsteinnth.kth.mmse.sepcli.Domain.EventRequest;
import com.thorsteinnth.kth.mmse.sepcli.Repository.IEventRequestRepository;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.GregorianCalendar;

public class EventRequestService
{
    private IEventRequestRepository repository;

    // TODO Stop using separate create and add functions in the services

    public EventRequestService(IEventRequestRepository repository)
    {
        this.repository = repository;
    }

    public EventRequest createEventRequest(
            String title,
            String description,
            GregorianCalendar startDateTime,
            GregorianCalendar endDateTime,
            int numberOfAttendees,
            String preferenceDescription,
            BigDecimal expectedBudget,
            Client client
    )
    {
        EventRequest er = new EventRequest(
                title,
                description,
                startDateTime,
                endDateTime,
                numberOfAttendees,
                preferenceDescription,
                expectedBudget,
                client,
                AppData.loggedInUser
        );

        this.repository.addEventRequest(er);
        return er;
    }

    public ArrayList<EventRequest> getAllEventRequests()
    {
        return this.repository.getAllEventRequests();
    }
}
