package com.thorsteinnth.kth.mmse.sepcli.Controllers;

import com.thorsteinnth.kth.mmse.sepcli.CliHandler;
import com.thorsteinnth.kth.mmse.sepcli.Service.UserService;

public class LoginController extends BaseController
{
    public void displayPage()
    {
        CliHandler.newLine();
        CliHandler.write("Welcome to the SEP client");
        login();
    }

    public void login()
    {
        CliHandler.write("Please enter your login information.");

        String email = CliHandler.getInput("Email:");
        String password = CliHandler.getInput("Password:");

        boolean success = new UserService().login(email, password);

        if (success)
        {
            CliHandler.write("User successfully logged in");
            new HomePageController().displayPage();
        }
        else
        {
            CliHandler.write("Login failed");
            login();
        }
    }

    public void logout()
    {
        new UserService().logout();
        login();
    }
}
