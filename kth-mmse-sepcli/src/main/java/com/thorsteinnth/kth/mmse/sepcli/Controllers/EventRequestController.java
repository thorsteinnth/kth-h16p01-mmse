package com.thorsteinnth.kth.mmse.sepcli.Controllers;

import com.thorsteinnth.kth.mmse.sepcli.CliHelper;
import com.thorsteinnth.kth.mmse.sepcli.Domain.Client;
import com.thorsteinnth.kth.mmse.sepcli.Domain.EventRequest;
import com.thorsteinnth.kth.mmse.sepcli.Repository.ClientRepository;
import com.thorsteinnth.kth.mmse.sepcli.Repository.EventRequestRepository;
import com.thorsteinnth.kth.mmse.sepcli.Service.ClientService;
import com.thorsteinnth.kth.mmse.sepcli.Service.EventRequestService;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.ParsePosition;
import java.util.ArrayList;
import java.util.GregorianCalendar;

public class EventRequestController extends BaseController
{
    private BaseController previousController;

    public EventRequestController(BaseController previousController)
    {
        this.previousController = previousController;
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
    // TODO Input validation
    private void createEventRequest()
    {
        CliHelper.newLine();
        CliHelper.write("Create event request");

        String title = CliHelper.getInput("Title:");
        String description = CliHelper.getInput("Description:");
        String startDateTime = CliHelper.getInput("Start time (YYYY-MM-DD-HH-MM):");
        String endDateTime = CliHelper.getInput("End time (YYYY-MM-DD-HH-MM):");
        String numberOfAttendees = CliHelper.getInput("Number of attendees:");
        String preferenceDescription = CliHelper.getInput("Preference description:");
        String expectedBudget = CliHelper.getInput("Expected budget (SEK):");
        //TODO String client = CliHelper.getInput("Select client:");

        ClientService clientService = new ClientService(new ClientRepository());
        Client client = clientService.createClient("Fannar", "KTH", "email@web.com", "5556666");

        GregorianCalendar calStartDateTime = getGregorianCalendarFromString(startDateTime);
        GregorianCalendar calEndDateTime = getGregorianCalendarFromString(endDateTime);

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

        System.out.println(er);

        displayPage();
    }

    private void createEventRequestSelectClient()
    {

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
