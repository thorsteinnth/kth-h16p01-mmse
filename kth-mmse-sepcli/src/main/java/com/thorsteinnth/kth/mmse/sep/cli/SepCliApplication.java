package com.thorsteinnth.kth.mmse.sep.cli;

import com.beust.jcommander.*;
import com.thorsteinnth.kth.mmse.sep.cli.Controllers.HomePageController;
import com.thorsteinnth.kth.mmse.sep.cli.Controllers.LoginController;

public class SepCliApplication
{
    public static AppData AppData;

    public static void main (String [] arguments)
    {
        // JCommander tests
        //Settings settings = new Settings();
        //new JCommander(settings, input);

        LoginController loginController = new LoginController();
        loginController.login();

        HomePageController homePageController = new HomePageController();
        homePageController.displayHomePage();
    }
}
