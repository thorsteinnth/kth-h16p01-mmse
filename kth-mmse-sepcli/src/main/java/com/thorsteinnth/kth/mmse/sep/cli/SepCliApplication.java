package com.thorsteinnth.kth.mmse.sep.cli;

import com.beust.jcommander.*;
import com.thorsteinnth.kth.mmse.sep.cli.Service.UserService;
import com.thorsteinnth.kth.mmse.sep.cli.Domain.User;

public class SepCliApplication
{
    public static void main (String [] arguments)
    {
        System.out.println("Welcome to the SEP client.");

        String email = CliHandler.getInput("Email:");
        String password = CliHandler.getInput("Password:");

        User user = new UserService().Login(email, password);
        System.out.println(user);

        //Settings settings = new Settings();
        //new JCommander(settings, input);

    }
}
