package com.thorsteinnth.kth.mmse.sepcli.Controllers;

import com.thorsteinnth.kth.mmse.sepcli.AppData;
import com.thorsteinnth.kth.mmse.sepcli.CliHandler;
import com.thorsteinnth.kth.mmse.sepcli.Domain.User;
import com.thorsteinnth.kth.mmse.sepcli.Service.UserService;

public class LoginController
{
    public Boolean login()
    {
        CliHandler.write("Welcome to the SEP client. Please enter your login information.");

        String email = CliHandler.getInput("Email:");
        String password = CliHandler.getInput("Password:");

        User user = new UserService().login(email, password);
        AppData.loggedInUser = user;
        CliHandler.write("User successfully logged in");

        return true;
    }
}
