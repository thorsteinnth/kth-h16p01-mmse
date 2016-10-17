package com.thorsteinnth.kth.mmse.sepcli.Controllers;

import com.thorsteinnth.kth.mmse.sepcli.CliHelper;
import com.thorsteinnth.kth.mmse.sepcli.Domain.EventRequest;
import com.thorsteinnth.kth.mmse.sepcli.Domain.FinancialRequest;
import com.thorsteinnth.kth.mmse.sepcli.Repository.EventRequestRepository;
import com.thorsteinnth.kth.mmse.sepcli.Repository.FinancialRequestRepository;
import com.thorsteinnth.kth.mmse.sepcli.Service.EventRequestService;
import com.thorsteinnth.kth.mmse.sepcli.Service.FinancialRequestService;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class FinancialRequestController extends BaseController
{
    private BaseController previousController;
    private EventRequestService eventRequestService;

    public FinancialRequestController(BaseController previousController)
    {
        this.previousController = previousController;
        this.eventRequestService = new EventRequestService(new EventRequestRepository());
    }

    public void displayPage()
    {
        CliHelper.newLine();
        CliHelper.write("This is the create financial request page");
        CliHelper.write("Please select one of the following operations:");
        CliHelper.write("1. Create financial request");
        CliHelper.write("2. Back");
        ArrayList<String> validInputs = new ArrayList<String>();
        validInputs.add("1");
        validInputs.add("2");

        String input = CliHelper.getInput("Select an operation (1-2)", validInputs);

        if (input.equals("1"))
            createFinancialRequest();
        else if (input.equals("2"))
            back();
        else
            System.out.println("ERROR: Unknown command");
    }

    private void createFinancialRequest()
    {
        CliHelper.newLine();
        CliHelper.write("Create financial request");

        if (eventRequestService.getAllEventRequests().isEmpty())
        {
            CliHelper.write("There are no event requests in the system. Create an event request in order to add financial request.");
            displayPage();
            return;
        }

        EventRequest eventRequest = selectEventRequest();

        if (eventRequest == null)
        {
            displayPage();
            return;
        }

        String title = CliHelper.getInput("Title:");
        String reason = CliHelper.getInput("Reason for budget adjustment:");
        String reqAmount = CliHelper.getInputCurrency("Required amount (SEK):");

        // NOTE: Input from user is valid at this point
        BigDecimal bdReqAmount = new BigDecimal(reqAmount);

        FinancialRequestService financialRequestService = new FinancialRequestService(new FinancialRequestRepository());
        FinancialRequest fr = financialRequestService.createFinancialRequest(
                title,
                reason,
                bdReqAmount,
                eventRequest
        );

        printFinancialRequest(fr);
        //TODO : send request

        displayPage();
    }

    private EventRequest selectEventRequest()
    {
        // Display list of all open or in progress event requests for user to choose from

        CliHelper.newLine();
        CliHelper.write("Link task request to event");

        if (eventRequestService.getAllEventRequests().isEmpty())
        {
            CliHelper.write("There are no event requests in the system");
            return null;
        }
        else
        {
            Map<String, EventRequest> eventRequests = new HashMap<String, EventRequest>();
            ArrayList<String> validInputs = new ArrayList<>();
            int counter = 1;

            for (EventRequest er: eventRequestService.getAllEventRequests())
            {
                if(er.getStatus() == EventRequest.Status.Pending)
                {
                    String seqNumber = Integer.toString(counter);

                    eventRequests.put(seqNumber, er);
                    validInputs.add(seqNumber);

                    CliHelper.write(seqNumber + ". " + er.getTitle());
                    counter++;
                }
            }

            if (eventRequests.size() != 0)
            {
                CliHelper.newLine();
                final String selectedSeqNumber = CliHelper.getInput(
                        "Select event to link to the financial request:",
                        validInputs);

                EventRequest eventRequest = eventRequests.get(selectedSeqNumber);
                return eventRequest;
            }
            else
            {
                CliHelper.write("There are no event requests with status pending");
                return null;
            }
        }
    }

    private void printFinancialRequest(FinancialRequest fr)
    {
        CliHelper.newLine();
        CliHelper.write(fr.toDisplayString());
    }

    private void back()
    {
        this.previousController.displayPage();
    }
}
