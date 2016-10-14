package com.thorsteinnth.kth.mmse.sepcli.Controllers;

import com.thorsteinnth.kth.mmse.sepcli.AppData;
import com.thorsteinnth.kth.mmse.sepcli.CliHandler;

import java.util.ArrayList;

public class HomePageController extends BaseController
{
    public void displayPage()
    {
        CliHandler.newLine();
        CliHandler.write("This is the homepage for user: " + AppData.loggedInUser.email);
        CliHandler.write("Please select one of the following operations:");
        CliHandler.write("1. Client management");
        CliHandler.write("2. Logout");
        CliHandler.write("3. Quit");
        ArrayList<String> validInputs = new ArrayList<String>();
        validInputs.add("1");
        validInputs.add("2");
        validInputs.add("3");

        String input = CliHandler.getInput("Select an operation (1-3)", validInputs);

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
        CliHandler.write("Closing application");
    }
}
