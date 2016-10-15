package com.thorsteinnth.kth.mmse.sepcli.Controllers;

import com.thorsteinnth.kth.mmse.sepcli.AppData;
import com.thorsteinnth.kth.mmse.sepcli.CliHelper;

import java.util.ArrayList;

public class HomePageController extends BaseController
{
    public void displayPage()
    {
        CliHelper.newLine();
        CliHelper.write("This is the homepage for user: " + AppData.loggedInUser.email);
        CliHelper.write("Please select one of the following operations:");
        CliHelper.write("1. Client management");
        CliHelper.write("2. Create event request");
        CliHelper.write("9. Logout");
        CliHelper.write("0. Quit");
        ArrayList<String> validInputs = new ArrayList<String>();
        validInputs.add("1");
        validInputs.add("2");
        validInputs.add("9");
        validInputs.add("0");

        String input = CliHelper.getInput("Select an operation (0-9)", validInputs);

        if (input.equals("1"))
            clientManagement();
        else if(input.equals("2"))
            createEventRequest();
        else if (input.equals("9"))
            logout();
        else if (input.equals("0"))
            closeApplication();
        else
            System.out.println("ERROR: Unknown command");
    }

    private void clientManagement()
    {
        new ClientController(this).displayPage();
    }

    private void createEventRequest()
    {
        new EventRequestController(this).displayPage();
    }

    private void logout()
    {
        new LoginController().logout();
    }

    private void closeApplication()
    {
        // Do nothing, process will terminate
        CliHelper.write("Closing application");
    }
}
