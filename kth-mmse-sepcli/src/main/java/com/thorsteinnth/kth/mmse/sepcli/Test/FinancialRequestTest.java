package com.thorsteinnth.kth.mmse.sepcli.Test;

import com.thorsteinnth.kth.mmse.sepcli.AppData;
import com.thorsteinnth.kth.mmse.sepcli.Domain.Client;
import com.thorsteinnth.kth.mmse.sepcli.Domain.EventRequest;
import com.thorsteinnth.kth.mmse.sepcli.Domain.FinancialRequest;
import com.thorsteinnth.kth.mmse.sepcli.Repository.ClientRepository;
import com.thorsteinnth.kth.mmse.sepcli.Repository.EventRequestRepository;
import com.thorsteinnth.kth.mmse.sepcli.Repository.FinancialRequestRepository;
import com.thorsteinnth.kth.mmse.sepcli.Repository.UserRepository;
import com.thorsteinnth.kth.mmse.sepcli.Service.ClientService;
import com.thorsteinnth.kth.mmse.sepcli.Service.EventRequestService;
import com.thorsteinnth.kth.mmse.sepcli.Service.FinancialRequestService;
import com.thorsteinnth.kth.mmse.sepcli.Service.UserService;

import java.math.BigDecimal;
import java.util.GregorianCalendar;

public class FinancialRequestTest
{
    public static FinancialRequestService getService()
    {
        return new FinancialRequestService(new FinancialRequestRepository());
    }

    public static boolean testCreateFinancialRequest()
    {
        FinancialRequestService service = getService();
        EventRequest eventRequest = getTestEventRequest();

        UserService userService = new UserService(new UserRepository());
        userService.addInitialUsers();
        userService.login("jack@sep.se", "jack123");

        String testTitle = "Test financial request";
        String testReason = "Test reason for the financial request";
        BigDecimal testRequiredAmount = new BigDecimal(5000);


        FinancialRequest fr = service.createFinancialRequest(
                testTitle,
                testReason,
                testRequiredAmount,
                eventRequest
        );

        try
        {
            assert fr.getTitle().equals(testTitle)
                    && fr.getReasonForBudgetAdjustment().equals(testReason)
                    && fr.getRequiredAmount().equals(testRequiredAmount)
                    && fr.getRequestingDepartment().equals(FinancialRequest.RequestingDepartment.ProductionDepartment)
                    && fr.getStatus().equals(FinancialRequest.Status.Pending)
                    && fr.getEventRequest().equals(eventRequest)
                    && fr.getCreatedByUser().equals(AppData.loggedInUser);

            return true;
        }
        catch (AssertionError ae)
        {
            System.out.println("testCreateFinancialRequest() - failed");
            return false;
        }

    }

    public static boolean testUpdateFinancialRequestStatus()
    {
        FinancialRequestService service = getService();
        FinancialRequest testFinancialRequest = getTestFinancialRequest();

        // Log in user that has authority to do this
        UserService userService = new UserService(new UserRepository());
        userService.login("alice@sep.se", "alice123");

        try
        {
            // TODO Status progression rules
            assert testFinancialRequest.getStatus().equals(FinancialRequest.Status.Pending);
            service.updateFinancialRequestStatus(testFinancialRequest, FinancialRequest.Status.Approved);
            assert testFinancialRequest.getStatus().equals(FinancialRequest.Status.Approved);
            return true;
        }
        catch (AssertionError ae)
        {
            System.out.println("testUpdateFinancialRequestStatus() - failed");
            return false;
        }
    }

    private static EventRequest getTestEventRequest()
    {
        EventRequestService srv = new EventRequestService(new EventRequestRepository());
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

    private static FinancialRequest getTestFinancialRequest()
    {
        FinancialRequestService service = getService();
        EventRequest eventRequest = getTestEventRequest();

        UserService userService = new UserService(new UserRepository());
        userService.addInitialUsers();
        userService.login("jack@sep.se", "jack123");

        String testTitle = "Test financial request";
        String testReason = "Test reason for the financial request";
        BigDecimal testRequiredAmount = new BigDecimal(5000);

        FinancialRequest fr = service.createFinancialRequest(
                testTitle,
                testReason,
                testRequiredAmount,
                eventRequest
        );

        return fr;
    }
}
