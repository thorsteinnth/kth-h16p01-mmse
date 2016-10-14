package com.thorsteinnth.kth.mmse.sepcli.Controllers;

import com.thorsteinnth.kth.mmse.sepcli.AppData;
import com.thorsteinnth.kth.mmse.sepcli.CliHandler;

import java.util.ArrayList;

public class HomePageController
{
    public void displayHomePage()
    {
        CliHandler.write("This is the homepage for user: " + AppData.loggedInUser.email);
        CliHandler.write("Please select one of the following operations:");
        CliHandler.write("1. Create client record");
        CliHandler.write("2. Logout");
        ArrayList<String> validInputs = new ArrayList<String>();
        validInputs.add("1");
        validInputs.add("2");

        String input = CliHandler.getInput("Select an operation (1-2)", validInputs);

        if (input.equals("1"))
            createClientRecord();
        else if (input.equals("2"))
            logout();
        else
            System.out.println("ERROR: Unknown command");
    }

    private void createClientRecord()
    {
        new ClientController().createClient();
        displayHomePage();
    }

    private void logout()
    {
        //TODO Do we need to do anything here?
        System.out.println("Logging out");
    }
}
