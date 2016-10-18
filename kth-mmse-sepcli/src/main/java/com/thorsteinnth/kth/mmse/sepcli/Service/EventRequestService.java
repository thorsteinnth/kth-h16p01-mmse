package com.thorsteinnth.kth.mmse.sepcli.Service;

import com.thorsteinnth.kth.mmse.sepcli.AppData;
import com.thorsteinnth.kth.mmse.sepcli.Domain.AccessFunction;
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

    public boolean updateEventRequestStatus(EventRequest request, EventRequest.Status newStatus)
    {
        // NOTE:
        // Not saving this to repo since our implementation is only using an
        // in-memory data store

        // TODO Status progression rules

        // Possible statuses
        // Pending, Open, InProgress, Closed, Rejected
        // TODO
        // We only check if the user has authority to approve or reject, for now
        // It is unclear what users will be responsible for giving the request the other statuses
        // TODO Just have the system be able to do that? and the staff managers able to set workflow statuses
        // and then the system updates the actual status based on the workflow status changes?

        if (newStatus == EventRequest.Status.Rejected)
        {
            if (!AccessControlService.hasAccess(AccessFunction.rejectEventRequest))
                return false;
        }

        if (newStatus == EventRequest.Status.Approved)
        {
            if (!AccessControlService.hasAccess(AccessFunction.approveEventRequest))
                return false;
        }

        request.setStatus(newStatus);
        return true;
    }
}
