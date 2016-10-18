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

        int argumentsCount = arguments.length;

        if (argumentsCount > 0)
        {
            String arg1 = arguments[0];

            if(arg1.equals("runUnitTests"))
            {
                TestManager.runTests();
            }
            else if(arg1.equals("runAcceptanceTest1"))
            {
                AcceptanceTestManager.runAcceptanceTest("1");
                runSepCliApplication(true);
            }
            else if (arg1.equals("runAcceptanceTest2"))
            {
                AcceptanceTestManager.runAcceptanceTest("2");
                runSepCliApplication(true);
            }
            else
            {
                runSepCliApplication(false);
            }
        }
        else
        {
            runSepCliApplication(false);
        }
    }

    private static void runSepCliApplication(boolean testMode)
    {
        CliHelper.setIsTestMode(testMode);

        // Add initial data
        new UserService(new UserRepository()).addInitialUsers();
        new ClientService(new ClientRepository()).createInitialClients();

        new LoginController().displayPage();
    }
}
