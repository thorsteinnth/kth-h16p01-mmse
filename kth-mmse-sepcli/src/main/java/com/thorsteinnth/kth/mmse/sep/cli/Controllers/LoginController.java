package com.thorsteinnth.kth.mmse.sep.cli.Controllers;

import com.thorsteinnth.kth.mmse.sep.cli.AppData;
import com.thorsteinnth.kth.mmse.sep.cli.CliHandler;
import com.thorsteinnth.kth.mmse.sep.cli.Domain.User;
import com.thorsteinnth.kth.mmse.sep.cli.Service.UserService;

public class LoginController
{
    public Boolean login()
    {
        System.out.println("Welcome to the SEP client. Please enter your login information.");

        String email = CliHandler.getInput("Email:");
        String password = CliHandler.getInput("Password:");

        User user = new UserService().login(email, password);
        AppData.LoggedInUser = user;
        CliHandler.write("User successfully logged in");

        return true;
    }
}
