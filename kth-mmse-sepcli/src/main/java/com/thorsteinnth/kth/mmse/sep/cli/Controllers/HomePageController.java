package com.thorsteinnth.kth.mmse.sep.cli.Controllers;

import com.thorsteinnth.kth.mmse.sep.cli.AppData;
import com.thorsteinnth.kth.mmse.sep.cli.CliHandler;

import java.util.ArrayList;

public class HomePageController
{
    public void displayHomePage()
    {
        CliHandler.write("This is the homepage for user: " + AppData.LoggedInUser.Email);
        CliHandler.write("Please select one of the following operations:");
        CliHandler.write("1. Create client record");
        CliHandler.write("2. Logout");
        ArrayList<String> validInputs = new ArrayList<String>();
        validInputs.add("1");
        validInputs.add("2");

        String input = CliHandler.getInput("Select an operation (1-2)", validInputs);

        if (input.equals("1"))
            System.out.println("Should create client record");
        else if (input.equals("2"))
            System.out.println("Should logout");
        else
            System.out.println("Should logout");
    }
}
