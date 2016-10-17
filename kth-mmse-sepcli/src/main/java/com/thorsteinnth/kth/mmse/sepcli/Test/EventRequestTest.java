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

    private static EventRequest createTestEventRequest()
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

    /**
     * Create event request and check that it gets created correctly
     * (also gets saved to DB, not tested here)
     * @return true if successful, false if not
     */
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
                    && er.createdByUser.equals(AppData.loggedInUser);

            return true;
        }
        catch (AssertionError ae)
        {
            System.out.println("testCreateEventRequest() - failed");
            return false;
        }
    }

    /**
     * Create event request and test that it gets saved to DB
     * @return true if successful, false if not
     */
    public static boolean testCreateEventRequestDB()
    {
        EventRequestService srv = getService();
        EventRequest testEventRequest = createTestEventRequest();

        try
        {
            assert srv.getAllEventRequests().size() == 1;
            // NOTE: list.contains uses the equals() method
            assert srv.getAllEventRequests().contains(testEventRequest);
            return true;
        }
        catch (AssertionError ae)
        {
            System.out.println("testAddEventRequest() - failed");
            return false;
        }
    }

    public static boolean testAddGetCommentToEventRequest()
    {
        EventRequestService service = getService();
        EventRequest testEventRequest = createTestEventRequest();

        String testString1 = "test event request comment 1";
        String testString2 = "test event request comment 2";

        service.addCommentToEventRequest(testEventRequest, testString1);
        service.addCommentToEventRequest(testEventRequest, testString2);

        try
        {
            assert testEventRequest.getComments().size() == 2;

            RequestComment testComment1 = new RequestComment(AppData.loggedInUser, testString1);
            RequestComment testComment2 = new RequestComment(AppData.loggedInUser, testString2);
            assert testEventRequest.getComments().get(0).equals(testComment1);
            assert testEventRequest.getComments().get(1).equals(testComment2);

            return true;
        }
        catch (AssertionError ae)
        {
            System.out.println("testAddCommentToEventRequest - failed");
            return false;
        }
    }

    public static boolean testUpdateEventRequestStatus()
    {
        EventRequestService service = getService();
        EventRequest testEventRequest = createTestEventRequest();

        try
        {
            // TODO Status progression rules
            assert testEventRequest.getStatus().equals(EventRequest.Status.Pending);
            service.updateEventRequestStatus(testEventRequest, EventRequest.Status.InProgress);
            assert testEventRequest.getStatus().equals(EventRequest.Status.InProgress);
            return true;
        }
        catch (AssertionError ae)
        {
            System.out.println("testUpdateEventRequestStatus() - failed");
            return false;
        }
    }
}
