package com.thorsteinnth.kth.mmse.sepcli.Controllers;

import com.thorsteinnth.kth.mmse.sepcli.CliHelper;

import java.util.ArrayList;

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
    private void createEventRequest()
    {
        CliHelper.write("Create event request");
        String name = CliHelper.getInput("Name:");

        //TODO Add all neccessary fields

        displayPage();
    }

    private void back()
    {
        this.previousController.displayPage();
    }
}
