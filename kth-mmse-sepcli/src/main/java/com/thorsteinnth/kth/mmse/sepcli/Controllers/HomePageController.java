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
        CliHelper.write("2. Logout");
        CliHelper.write("3. Quit");
        ArrayList<String> validInputs = new ArrayList<String>();
        validInputs.add("1");
        validInputs.add("2");
        validInputs.add("3");

        String input = CliHelper.getInput("Select an operation (1-3)", validInputs);

        if (input.equals("1"))
            clientManagement();
        else if (input.equals("2"))
            logout();
        else if (input.equals("3"))
            closeApplication();
        else
            System.out.println("ERROR: Unknown command");
    }

    private void clientManagement()
    {
        new ClientController(this).displayPage();
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
