package com.thorsteinnth.kth.mmse.sepcli.Controllers;

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
        CliHelper.write("Please enter your login information.");

        String email = CliHelper.getInput("Email:");
        String password = CliHelper.getInput("Password:");

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
        new UserService(new UserRepository()).logout();
        login();
    }
}
