package com.thorsteinnth.kth.mmse.sepcli.Controllers;

import com.thorsteinnth.kth.mmse.sepcli.CliHelper;
import com.thorsteinnth.kth.mmse.sepcli.Domain.Client;
import com.thorsteinnth.kth.mmse.sepcli.Domain.EventRequest;
import com.thorsteinnth.kth.mmse.sepcli.Repository.ClientRepository;
import com.thorsteinnth.kth.mmse.sepcli.Repository.EventRequestRepository;
import com.thorsteinnth.kth.mmse.sepcli.Service.ClientService;
import com.thorsteinnth.kth.mmse.sepcli.Service.EventRequestService;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.GregorianCalendar;

public class EventRequestController extends BaseController
{
    private BaseController previousController;
    private ClientService clientService;

    public EventRequestController(BaseController previousController)
    {
        this.previousController = previousController;
        this.clientService = new ClientService(new ClientRepository());
    }

    public void displayPage()
    {
        CliHelper.newLine();
        CliHelper.write("This is the create event request page");
        CliHelper.write("Please select one of the following operations:");
        CliHelper.write("1. Create event request");
        CliHelper.write("2. Back");
        ArrayList<String> validInputs = new ArrayList<String>();
        validInputs.add("1");
        validInputs.add("2");

        String input = CliHelper.getInput("Select an operation (1-2)", validInputs);

        if (input.equals("1"))
            createEventRequest();
        else if (input.equals("2"))
            back();
        else
            System.out.println("ERROR: Unknown command");
    }

    // TODO Restrict access to specific roles
    private void createEventRequest()
    {
        CliHelper.newLine();
        CliHelper.write("Create event request");

        if (clientService.getAllClients().isEmpty())
        {
            CliHelper.write("There are no client records in the system. Add some clients in order to add event requests.");
            displayPage();
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

        displayPage();
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
                CliHelper.write(client.toStringShort());
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
        // TODO Finalize formatting

        CliHelper.newLine();

        StringBuilder sb = new StringBuilder();
        sb.append("ID:\t\t\t\t\t\t" + er.getId() + System.getProperty("line.separator"));
        sb.append("Status:\t\t\t\t\t" + er.getStatus() + System.getProperty("line.separator"));
        sb.append("Title:\t\t\t\t\t" + er.getTitle() + System.getProperty("line.separator"));
        sb.append("Description:\t\t\t" + er.getDescription() + System.getProperty("line.separator"));
        sb.append("Start date time:\t\t" + er.getStartDateTimeString() + System.getProperty("line.separator"));
        sb.append("End date time:\t\t\t" + er.getEndDateTimeString() + System.getProperty("line.separator"));
        sb.append("Number of attendees:\t" + er.getNumberOfAttendees() + System.getProperty("line.separator"));
        sb.append("Preference description:\t" + er.getPreferenceDescription() + System.getProperty("line.separator"));
        sb.append("Expected budget:\t\t" + er.getExpectedBudget() + System.getProperty("line.separator"));
        sb.append("Client:\t\t\t\t\t" + er.getClient().name + System.getProperty("line.separator"));
        sb.append("Created by:\t\t\t\t" + er.getCreatedByUser().email + System.getProperty("line.separator"));
        sb.append("Created date time:\t\t" + er.getCreatedDateTime().toString() + System.getProperty("line.separator"));
        sb.append("Comments:\t" + er.getComments().toString() + System.getProperty("line.separator"));

        CliHelper.write(sb.toString());
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
