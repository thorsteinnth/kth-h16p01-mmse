package com.thorsteinnth.kth.mmse.sepcli.Controllers;

import com.thorsteinnth.kth.mmse.sepcli.CliHelper;
import com.thorsteinnth.kth.mmse.sepcli.Domain.AccessFunction;
import com.thorsteinnth.kth.mmse.sepcli.Domain.Client;
import com.thorsteinnth.kth.mmse.sepcli.Domain.EventRequest;
import com.thorsteinnth.kth.mmse.sepcli.Domain.User;
import com.thorsteinnth.kth.mmse.sepcli.Repository.ClientRepository;
import com.thorsteinnth.kth.mmse.sepcli.Repository.EventRequestRepository;
import com.thorsteinnth.kth.mmse.sepcli.Repository.RequestEnvelopeRepository;
import com.thorsteinnth.kth.mmse.sepcli.Repository.UserRepository;
import com.thorsteinnth.kth.mmse.sepcli.Service.*;
import com.thorsteinnth.kth.mmse.sepcli.UIOperation;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.GregorianCalendar;

public class EventRequestController extends BaseController
{
    private BaseController previousController;
    private ClientService clientService;
    private UserService userService;
    private RequestMailService requestMailService;

    public EventRequestController(BaseController previousController)
    {
        this.previousController = previousController;
        this.clientService = new ClientService(new ClientRepository());
        this.userService = new UserService(new UserRepository());
        this.requestMailService = new RequestMailService(new RequestEnvelopeRepository());
    }

    public void displayPage()
    {
        CliHelper.newLine();
        CliHelper.write("This is the event request page");

        ArrayList<UIOperation> operations = buildUIOperationList();
        UIOperation.Command onSelectedOperationError = () -> displayPage();
        displayUIOperations(operations, onSelectedOperationError);
    }

    private ArrayList<UIOperation> buildUIOperationList()
    {
        ArrayList<UIOperation> operations = new ArrayList<>();
        int operationCount = 0;

        if (AccessControlService.hasAccess(AccessFunction.createEventRequest))
        {
            UIOperation.Command createEventRequest = () -> createEventRequest();
            operations.add(new UIOperation(++operationCount, "Create event request", createEventRequest));
        }

        UIOperation.Command back = () -> back();
        operations.add(new UIOperation(++operationCount, "Back", back));

        return operations;
    }

    private void createEventRequest()
    {
        CliHelper.newLine();
        CliHelper.write("Create event request");

        if (clientService.getAllClients().isEmpty())
        {
            CliHelper.write("There are no client records in the system. Add some clients in order to add event requests.");
            displayPage();
            return;
        }

        String title = CliHelper.getInputEmptyStringBanned("Title:");
        String description = CliHelper.getInput("Description:");
        String startDateTime = CliHelper.getInputDate("Start time (YYYY-MM-DD-HH-MM):");
        String endDateTime = CliHelper.getInputDate("End time (YYYY-MM-DD-HH-MM):");
        String numberOfAttendees = CliHelper.getInputNumber("Number of attendees:");
        String preferenceDescription = CliHelper.getInput("Preference description:");
        String expectedBudget = CliHelper.getInputCurrency("Expected budget (SEK):");
        Client client = createEventRequestSelectClient();
        if (client == null)
        {
            CliHelper.write("We need a client for the event request. Aborting.");
            displayPage();
            return;
        }

        // NOTE: Input from user is valid at this point
        GregorianCalendar calStartDateTime = getGregorianCalendarFromString(startDateTime);
        GregorianCalendar calEndDateTime = getGregorianCalendarFromString(endDateTime);

        // NOTE: Input from user is valid at this point
        BigDecimal bdExpectedBudget = new BigDecimal(expectedBudget);

        EventRequestService eventRequestService = new EventRequestService(new EventRequestRepository());
        EventRequest er = eventRequestService.createEventRequest(
                title,
                description,
                calStartDateTime,
                calEndDateTime,
                Integer.parseInt(numberOfAttendees),
                preferenceDescription,
                bdExpectedBudget,
                client
        );

        printEventRequest(er);

        // Send request to the senior customer service officer
        // Note that the only user with the authority to create an event request is a customer service officer
        sendRequest(er, User.Role.SeniorCustomerServiceOfficer);

        displayPage();
    }

    private void sendRequest(EventRequest request, User.Role recipientRole)
    {
        CliHelper.newLine();
        CliHelper.write("Send event request");
        CliHelper.newLine();

        if (userService.getAllUsersByRole(recipientRole).isEmpty())
        {
            CliHelper.write("ERROR: No users with role " + User.getRoleDisplayString(recipientRole) + " in system.");
            return;
        }
        else
        {
            ArrayList<String> validInputs = new ArrayList<>();
            ArrayList<String> emailList = new ArrayList<>();

            int i = 1;
            for (User user : userService.getAllUsersByRole(recipientRole))
            {
                CliHelper.write(
                        Integer.toString(i)
                        + ".\t"
                        + user.toDisplayString()
                );
                validInputs.add(Integer.toString(i));
                emailList.add(user.email);
                i++;
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

            this.requestMailService.sendRequest(request, recipient);

            CliHelper.newLine();
            CliHelper.write("Event request successfully created!");

            CliHelper.newLine();
            CliHelper.write("Request sent to: " + recipient.toDisplayString());
        }
    }

    private Client createEventRequestSelectClient()
    {
        // Display client records for user to choose from

        CliHelper.newLine();
        CliHelper.write("Link event request to client");

        if (clientService.getAllClients().isEmpty())
        {
            CliHelper.write("ERROR: No client records in system");
            return null;
        }
        else
        {
            ArrayList<String> validInputs = new ArrayList<>();

            for (Client client : clientService.getAllClients())
            {
                CliHelper.write(client.toDisplayStringShort());
                validInputs.add(Integer.toString(client.id));
            }

            CliHelper.newLine();
            final String selectedId = CliHelper.getInput(
                    "Select a client to link to the event request:",
                    validInputs);

            Client client = clientService.getClientById(selectedId);

            if (client == null)
            {
                CliHelper.write("ERROR: Could not find client with ID: " + selectedId);
                return null;
            }

            return client;
        }
    }

    private void printEventRequest(EventRequest er)
    {
        CliHelper.newLine();
        CliHelper.write(er.toDisplayString());
    }

    private void back()
    {
        this.previousController.displayPage();
    }

    /**
     * Take string representation of a date and return a GregorianCalendar instance
     * @param dateTime - string of the form YYYY-MM-DD-HH-MM
     * @return GregorianCalendar instance of the date
     */
    private GregorianCalendar getGregorianCalendarFromString(String dateTime)
    {
        String[] dateParts = dateTime.split("-");
        int year = Integer.parseInt(dateParts[0]);
        int month = Integer.parseInt(dateParts[1]);
        int day = Integer.parseInt(dateParts[2]);
        int hour = Integer.parseInt(dateParts[3]);
        int minute = Integer.parseInt(dateParts[4]);

        return new GregorianCalendar(year, month, day, hour, minute);
    }
}
