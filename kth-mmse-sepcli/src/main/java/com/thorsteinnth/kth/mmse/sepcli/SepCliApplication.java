package com.thorsteinnth.kth.mmse.sepcli;

import com.thorsteinnth.kth.mmse.sepcli.Controllers.LoginController;
import com.thorsteinnth.kth.mmse.sepcli.Repository.ClientRepository;
import com.thorsteinnth.kth.mmse.sepcli.Repository.UserRepository;
import com.thorsteinnth.kth.mmse.sepcli.Service.ClientService;
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

        // Add initial data
        new UserService(new UserRepository()).addInitialUsers();
        new ClientService(new ClientRepository()).createInitialClients();

        new LoginController().displayPage();
    }
}
