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

        if(eventRequestService.getAllEventRequests().isEmpty())
        {
            CliHelper.write("There are no event requests in the system. Create a event in order to add task request.");
            displayPage();
        }

        EventRequest eventRequest = selectEventRequest();
        String title = CliHelper.getInput("Title:");
        String reason = CliHelper.getInput("Reason:");
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

        if(eventRequestService.getAllEventRequests().isEmpty())
        {
            CliHelper.write("ERROR: No event available");
            return null;
        }
        else
        {
            Map<String, EventRequest> eventRequests = new HashMap<String, EventRequest>();
            ArrayList<String> validInputs = new ArrayList<>();
            int counter = 1;

            for (EventRequest er: eventRequestService.getAllEventRequests())
            {
                if(er.getStatus() == EventRequest.Status.Open ||
                        er.getStatus() == EventRequest.Status.InProgress)
                {
                    String seqNumber = Integer.toString(counter);

                    eventRequests.put(seqNumber, er);
                    validInputs.add(seqNumber);

                    CliHelper.write(seqNumber + ". " + er.getTitle());
                    counter++;
                }
            }

            CliHelper.newLine();
            final String selectedSeqNumber = CliHelper.getInput(
                    "Select event to link to the task request:",
                    validInputs);

            EventRequest eventRequest = eventRequests.get(selectedSeqNumber);

            return eventRequest;
        }
    }

    private void printFinancialRequest(FinancialRequest fr)
    {
        CliHelper.newLine();

        StringBuilder sb = new StringBuilder();
        sb.append("ID:\t\t\t\t\t\t" + fr.getId() + System.getProperty("line.separator"));
        sb.append("Title:\t\t\t\t\t" + fr.getTitle() + System.getProperty("line.separator"));
        sb.append("Status:\t\t\t\t\t" + fr.getStatus() + System.getProperty("line.separator"));
        sb.append("Reason:\t\t\t\t\t" + fr.getReasonForBudgetAdjustment() + System.getProperty("line.separator"));
        sb.append("Required amt:\t\t" + fr.getRequiredAmount().toString() + System.getProperty("line.separator"));
        sb.append("Event request:\t" + fr.getEventRequest().getTitle() + System.getProperty("line.separator"));
        sb.append("Created by:\t\t" + fr.getCreatedByUser().email + System.getProperty("line.separator"));

        CliHelper.write(sb.toString());
    }

    private void back()
    {
        this.previousController.displayPage();
    }
}
