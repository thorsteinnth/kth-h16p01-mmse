package com.thorsteinnth.kth.mmse.sepcli;

import com.thorsteinnth.kth.mmse.sepcli.Controllers.HomePageController;
import com.thorsteinnth.kth.mmse.sepcli.Controllers.LoginController;
import com.thorsteinnth.kth.mmse.sepcli.Repository.UserRepository;
import com.thorsteinnth.kth.mmse.sepcli.Service.UserService;
import com.thorsteinnth.kth.mmse.sepcli.Test.TestManager;

public class SepCliApplication
{
    public static AppData AppData;

    public static void main (String [] arguments)
    {
        new AppData();

        // Run tests
        // TODO This should be separate from the program
        TestManager.runTests();

        UserService userService = new UserService(new UserRepository());
        userService.addInitialUsers();

        new LoginController().displayPage();
    }
}
