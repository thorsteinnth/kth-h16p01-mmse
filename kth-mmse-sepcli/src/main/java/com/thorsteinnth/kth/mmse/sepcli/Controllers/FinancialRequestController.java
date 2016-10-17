package com.thorsteinnth.kth.mmse.sepcli.Controllers;

import com.thorsteinnth.kth.mmse.sepcli.CliHelper;
import com.thorsteinnth.kth.mmse.sepcli.Domain.AccessFunction;
import com.thorsteinnth.kth.mmse.sepcli.Domain.EventRequest;
import com.thorsteinnth.kth.mmse.sepcli.Domain.FinancialRequest;
import com.thorsteinnth.kth.mmse.sepcli.Domain.User;
import com.thorsteinnth.kth.mmse.sepcli.Repository.EventRequestRepository;
import com.thorsteinnth.kth.mmse.sepcli.Repository.FinancialRequestRepository;
import com.thorsteinnth.kth.mmse.sepcli.Repository.RequestEnvelopeRepository;
import com.thorsteinnth.kth.mmse.sepcli.Repository.UserRepository;
import com.thorsteinnth.kth.mmse.sepcli.Service.*;
import com.thorsteinnth.kth.mmse.sepcli.UIOperation;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class FinancialRequestController extends BaseController
{
    private BaseController previousController;
    private EventRequestService eventRequestService;
    private UserService userService;
    private RequestMailService requestMailService;

    public FinancialRequestController(BaseController previousController)
    {
        this.previousController = previousController;
        this.eventRequestService = new EventRequestService(new EventRequestRepository());
        this.userService = new UserService(new UserRepository());
        this.requestMailService = new RequestMailService(new RequestEnvelopeRepository());
    }

    public void displayPage()
    {
        CliHelper.newLine();
        CliHelper.write("This is the create financial request page");

        ArrayList<UIOperation> operations = buildUIOperationList();
        UIOperation.Command onSelectedOperationError = () -> displayPage();
        displayUIOperations(operations, onSelectedOperationError);
    }

    private ArrayList<UIOperation> buildUIOperationList()
    {
        ArrayList<UIOperation> operations = new ArrayList<>();
        int operationCount = 0;

        if (AccessControlService.hasAccess(AccessFunction.createFinancialRequest))
        {
            UIOperation.Command createFinancialRequest = () -> createFinancialRequest();
            operations.add(new UIOperation(++operationCount, "Create financial request", createFinancialRequest));
        }

        UIOperation.Command back = () -> back();
        operations.add(new UIOperation(++operationCount, "Back", back));

        return operations;
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
        sendRequest(fr);

        displayPage();
    }

    private void sendRequest(FinancialRequest fr)
    {
        CliHelper.newLine();
        CliHelper.write("Send event request");
        CliHelper.newLine();

        if (userService.getAllUsers().isEmpty())
        {
            // NOTE: Should never happen
            CliHelper.write("ERROR: No users in system");
            return;
        }
        else
        {
            ArrayList<String> validInputs = new ArrayList<>();
            ArrayList<String> emailList = new ArrayList<>();

            int i = 1;
            for (User user : userService.getAllUsers())
            {
                if(user.role == User.Role.FinancialManager)
                {
                    CliHelper.write(
                            Integer.toString(i)
                                    + ".\t"
                                    + user.email
                    );
                    validInputs.add(Integer.toString(i));
                    emailList.add(user.email);
                    i++;
                }
            }

            if(emailList.size() == 0)
            {
                // NOTE: should never happen
                CliHelper.write("ERRIR: No financial manager in the system");
                return;
            }

            CliHelper.newLine();
            String selectedNumber = CliHelper.getInput(
                    "Select a user to send the request to:",
                    validInputs);

            String selectedEmail = emailList.get(Integer.parseInt(selectedNumber)-1);
            User recipient = userService.getUserByEmail(selectedEmail);

            if (recipient == null)
            {
                CliHelper.write("ERROR: Could not find user with email: " + selectedEmail);
                return;
            }

            this.requestMailService.sendRequest(fr, recipient);

            CliHelper.newLine();
            CliHelper.write("Request sent to: " + recipient.email);
        }
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
