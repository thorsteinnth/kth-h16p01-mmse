package com.thorsteinnth.kth.mmse.sepcli.Controllers;

import com.thorsteinnth.kth.mmse.sepcli.AppData;
import com.thorsteinnth.kth.mmse.sepcli.CliHelper;
import com.thorsteinnth.kth.mmse.sepcli.Repository.UserRepository;
import com.thorsteinnth.kth.mmse.sepcli.Service.UserService;

public class LoginController extends BaseController
{
    public void displayPage()
    {
        CliHelper.newLine();
        CliHelper.write("Welcome to the SEP client");
        login();
    }

    public void login()
    {
        CliHelper.newLine();
        CliHelper.write("Please enter your login information.");

        String email = CliHelper.getInputEmptyStringBanned("Email:");
        String password = CliHelper.getInputEmptyStringBanned("Password:");

        boolean success = new UserService(new UserRepository()).login(email, password);

        if (success)
        {
            CliHelper.write("User successfully logged in");
            new HomePageController().displayPage();
        }
        else
        {
            CliHelper.write("Login failed");
            login();
        }
    }

    public void logout()
    {
        CliHelper.newLine();
        CliHelper.write("Logging out user: " + AppData.loggedInUser.email);
        new UserService(new UserRepository()).logout();
        CliHelper.write("User logged out");
        login();
    }
}
