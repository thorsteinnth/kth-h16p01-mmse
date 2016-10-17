package com.thorsteinnth.kth.mmse.sepcli;

import com.thorsteinnth.kth.mmse.sepcli.Controllers.LoginController;
import com.thorsteinnth.kth.mmse.sepcli.Repository.ClientRepository;
import com.thorsteinnth.kth.mmse.sepcli.Repository.UserRepository;
import com.thorsteinnth.kth.mmse.sepcli.Service.ClientService;
import com.thorsteinnth.kth.mmse.sepcli.Service.UserService;
import com.thorsteinnth.kth.mmse.sepcli.Test.AcceptanceTest.AcceptanceTestManager;
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

        //TODO we could get this from the main arguments list
        CliHelper.setIsTestMode(false);
        //AcceptanceTestManager.runAcceptanceTest("1");

        // Add initial data
        new UserService(new UserRepository()).addInitialUsers();
        new ClientService(new ClientRepository()).createInitialClients();

        new LoginController().displayPage();
    }
}
