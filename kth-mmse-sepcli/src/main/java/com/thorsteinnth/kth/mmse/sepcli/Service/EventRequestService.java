package com.thorsteinnth.kth.mmse.sepcli.Service;

import com.thorsteinnth.kth.mmse.sepcli.AppData;
import com.thorsteinnth.kth.mmse.sepcli.Domain.Client;
import com.thorsteinnth.kth.mmse.sepcli.Domain.EventRequest;
import com.thorsteinnth.kth.mmse.sepcli.Domain.RequestComment;
import com.thorsteinnth.kth.mmse.sepcli.Repository.IEventRequestRepository;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.GregorianCalendar;

public class EventRequestService
{
    private IEventRequestRepository repository;

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

        this.repository.createEventRequest(er);
        return er;
    }

    public ArrayList<EventRequest> getAllEventRequests()
    {
        return this.repository.getAllEventRequests();
    }

    public void addCommentToEventRequest(EventRequest request, String commentText)
    {
        // NOTE:
        // Not saving this to repo since our implementation is only using an
        // in-memory data store
        request.addComment(new RequestComment(AppData.loggedInUser, commentText));
    }
}
