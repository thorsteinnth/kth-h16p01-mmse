package com.thorsteinnth.kth.mmse.sepcli.Test;

import com.thorsteinnth.kth.mmse.sepcli.AppData;
import com.thorsteinnth.kth.mmse.sepcli.Domain.Client;
import com.thorsteinnth.kth.mmse.sepcli.Domain.EventRequest;
import com.thorsteinnth.kth.mmse.sepcli.Domain.RequestComment;
import com.thorsteinnth.kth.mmse.sepcli.Repository.ClientRepository;
import com.thorsteinnth.kth.mmse.sepcli.Repository.EventRequestRepository;
import com.thorsteinnth.kth.mmse.sepcli.Repository.UserRepository;
import com.thorsteinnth.kth.mmse.sepcli.Service.ClientService;
import com.thorsteinnth.kth.mmse.sepcli.Service.EventRequestService;
import com.thorsteinnth.kth.mmse.sepcli.Service.UserService;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.GregorianCalendar;

public class EventRequestTest
{
    private static EventRequestService getService()
    {
        return new EventRequestService(new EventRequestRepository());
    }

    private static EventRequest getTestEventRequest()
    {
        EventRequestService srv = getService();
        ClientService clientService = new ClientService(new ClientRepository());
        UserService userService = new UserService(new UserRepository());
        userService.addInitialUsers();
        userService.login("charlie@sep.se","charlie123");

        String title = "Test event request title";
        String description = "Test event request description";
        GregorianCalendar startDateTime = new GregorianCalendar(2016, 10, 15, 18, 0);
        GregorianCalendar endDateTime = new GregorianCalendar(2016, 10, 15, 21, 0);
        int numberOfAttendees = 100;
        String preferenceDesc = "These are the preferences";
        BigDecimal expectedBudget = new BigDecimal(99999);
        Client client = clientService.createClient("Fannar", "KTH", "email@web.com", "5556666");

        EventRequest er = srv.createEventRequest(
                title,
                description,
                startDateTime,
                endDateTime,
                numberOfAttendees,
                preferenceDesc,
                expectedBudget,
                client
        );

        return er;
    }

    public static boolean testCreateEventRequest()
    {
        EventRequestService srv = getService();
        ClientService clientService = new ClientService(new ClientRepository());
        UserService userService = new UserService(new UserRepository());
        userService.addInitialUsers();
        userService.login("charlie@sep.se","charlie123");

        String testTitle = "Test event request title";
        String testDescription = "Test event request description";
        GregorianCalendar testStartDateTime = new GregorianCalendar(2016, 10, 15, 18, 0);
        GregorianCalendar testEndDateTime = new GregorianCalendar(2016, 10, 15, 21, 0);
        int testNumberOfAttendees = 100;
        String testPreferenceDesc = "These are the preferences";
        BigDecimal testExpectedBudget = new BigDecimal(99999);
        Client testClient = clientService.createClient("Fannar", "KTH", "email@web.com", "5556666");

        EventRequest er = srv.createEventRequest(
                testTitle,
                testDescription,
                testStartDateTime,
                testEndDateTime,
                testNumberOfAttendees,
                testPreferenceDesc,
                testExpectedBudget,
                testClient
        );

        try
        {
            assert er.getTitle().equals(testTitle)
                    && er.getDescription().equals(testDescription)
                    && er.getStartDateTime().equals(testStartDateTime)
                    && er.getEndDateTime().equals(testEndDateTime)
                    && er.getNumberOfAttendees() == testNumberOfAttendees
                    && er.getPreferenceDescription().equals(testPreferenceDesc)
                    && er.getExpectedBudget().equals(testExpectedBudget)
                    && er.getComments().equals(new ArrayList<RequestComment>())
                    && er.getStatus().equals(EventRequest.Status.Pending)
                    && er.client.equals(testClient)
                    && er.createdBy.equals(AppData.loggedInUser);

            return true;
        }
        catch (AssertionError ae)
        {
            System.out.println("testCreateEventRequest() - failed");
            return false;
        }
    }
}
