package com.thorsteinnth.kth.mmse.sepcli;

import com.thorsteinnth.kth.mmse.sepcli.Controllers.HomePageController;
import com.thorsteinnth.kth.mmse.sepcli.Controllers.LoginController;

public class SepCliApplication
{
    public static AppData AppData;

    public static void main (String [] arguments)
    {
        AppData appData = new AppData();

        LoginController loginController = new LoginController();
        loginController.login();

        HomePageController homePageController = new HomePageController();
        homePageController.displayHomePage();
    }
}
