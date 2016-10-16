package com.thorsteinnth.kth.mmse.sepcli.Test;

import com.thorsteinnth.kth.mmse.sepcli.Domain.Client;
import com.thorsteinnth.kth.mmse.sepcli.Domain.EventRequest;
import com.thorsteinnth.kth.mmse.sepcli.Domain.RequestEnvelope;
import com.thorsteinnth.kth.mmse.sepcli.Domain.User;
import com.thorsteinnth.kth.mmse.sepcli.Repository.ClientRepository;
import com.thorsteinnth.kth.mmse.sepcli.Repository.EventRequestRepository;
import com.thorsteinnth.kth.mmse.sepcli.Repository.RequestEnvelopeRepository;
import com.thorsteinnth.kth.mmse.sepcli.Repository.UserRepository;
import com.thorsteinnth.kth.mmse.sepcli.Service.ClientService;
import com.thorsteinnth.kth.mmse.sepcli.Service.EventRequestService;
import com.thorsteinnth.kth.mmse.sepcli.Service.RequestMailService;
import com.thorsteinnth.kth.mmse.sepcli.Service.UserService;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.GregorianCalendar;

public class RequestMailTest
{
    private static RequestMailService getService()
    {
        return new RequestMailService(new RequestEnvelopeRepository());
    }

    public static boolean testSendGetRequestEnvelope()
    {
        RequestMailService requestMailService = getService();
        UserService userService = new UserService(new UserRepository());
        userService.addInitialUsers();
        userService.login("charlie@sep.se","charlie123");

        EventRequest request1 = createTestEventRequest();
        EventRequest request2 = createTestEventRequest();
        EventRequest request3 = createTestEventRequest();
        User recipient1 = userService.getUserByEmail("alice@sep.se");
        User recipient2 = userService.getUserByEmail("jack@sep.se");

        try
        {
            requestMailService.sendRequest(request1, recipient1);
            requestMailService.sendRequest(request2, recipient2);
            requestMailService.sendRequest(request3, recipient1);

            assert requestMailService.getAllRequestEnvelopes().size() == 3;

            ArrayList<RequestEnvelope> requestEnvelopesForRecipient1 =
                    requestMailService.getRequestEnvelopesForUser(recipient1);
            ArrayList<RequestEnvelope> requestEnvelopesForRecipient2 =
                    requestMailService.getRequestEnvelopesForUser(recipient2);

            assert requestEnvelopesForRecipient1.size() == 2;
            assert requestEnvelopesForRecipient2.size() == 1;

            for (RequestEnvelope re : requestEnvelopesForRecipient1)
            {
                assert re.getRecipient().equals(recipient1);
            }

            for (RequestEnvelope re : requestEnvelopesForRecipient2)
            {
                assert re.getRecipient().equals(recipient2);
            }

            return true;
        }
        catch (AssertionError ae)
        {
            System.out.println("testSendGetRequestEnvelope() - failed");
            return false;
        }
    }

    public static boolean testRemoveRequestEnvelope()
    {
        RequestMailService requestMailService = getService();
        UserService userService = new UserService(new UserRepository());
        userService.addInitialUsers();
        userService.login("charlie@sep.se","charlie123");

        EventRequest request1 = createTestEventRequest();
        EventRequest request2 = createTestEventRequest();
        EventRequest request3 = createTestEventRequest();
        User recipient1 = userService.getUserByEmail("alice@sep.se");
        User recipient2 = userService.getUserByEmail("jack@sep.se");

        try
        {
            RequestEnvelope re1 = requestMailService.sendRequest(request1, recipient1);
            RequestEnvelope re2 = requestMailService.sendRequest(request2, recipient2);
            RequestEnvelope re3 = requestMailService.sendRequest(request3, recipient1);

            assert requestMailService.getAllRequestEnvelopes().size() == 3;
            assert requestMailService.getRequestEnvelopesForUser(recipient1).size() == 2;
            assert requestMailService.getRequestEnvelopesForUser(recipient2).size() == 1;

            requestMailService.removeRequestEnvelope(re1);
            assert requestMailService.getAllRequestEnvelopes().size() == 2;
            assert requestMailService.getRequestEnvelopesForUser(recipient1).size() == 1;
            assert requestMailService.getRequestEnvelopesForUser(recipient2).size() == 1;

            requestMailService.removeRequestEnvelope(re3);
            assert requestMailService.getAllRequestEnvelopes().size() == 1;
            assert requestMailService.getRequestEnvelopesForUser(recipient1).size() == 0;
            assert requestMailService.getRequestEnvelopesForUser(recipient2).size() == 1;

            requestMailService.removeRequestEnvelope(re2);
            assert requestMailService.getAllRequestEnvelopes().size() == 0;
            assert requestMailService.getRequestEnvelopesForUser(recipient1).size() == 0;
            assert requestMailService.getRequestEnvelopesForUser(recipient2).size() == 0;

            return true;
        }
        catch (AssertionError ae)
        {
            System.out.println("testRemoveRequestEnvelope() - failed");
            return false;
        }
    }

    private static EventRequest createTestEventRequest()
    {
        EventRequestService srv = new EventRequestService(new EventRequestRepository());
        ClientService clientService = new ClientService(new ClientRepository());

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
}
