package com.thorsteinnth.kth.mmse.sepcli.Test.AcceptanceTest;

import java.io.InputStream;

public class AcceptanceTestManager
{
    private static String testNumber;

    public static void runAcceptanceTest(String testToRun)
    {
        testNumber = testToRun;

        if (testNumber.equals("1"))
        {
            EventRequestAcptTest.runTest();
        }
        else if(testNumber.equals("2"))
        {
            CreateClientRecordAcptTest.runTest();
        }
    }

    public static InputStream getInputStream()
    {
        if (testNumber.equals("1"))
        {
            InputStream is = EventRequestAcptTest.getInputStream();

            if(is == null)
            {
                //The operation queue for the acceptance test is empty but
                //we have not yet received a success message - test failed

                System.out.println();
                System.out.println("Event request acceptance test failed!");
                System.exit(0);

                return null;
            }
            else
            {
                return is;
            }
        }
        else
        {
            InputStream is = CreateClientRecordAcptTest.getInputStream();

            if(is == null)
            {
                //The operation queue for the acceptance test is empty but
                //we have not yet received a success message - test failed

                System.out.println();
                System.out.println("Create client record acceptance test failed!");
                System.exit(0);

                return null;
            }
            else
            {
                return is;
            }
        }
    }

    public static void checkOutput(String output)
    {
        if(testNumber.equals("1"))
        {
            if(output.equals("Event request successfully created!"))
            {
                System.out.println("Event request acceptance test passed!");
                System.exit(0);
            }
        }
        else if(testNumber.equals("2"))
        {
            if(output.equals("Client record successfully created!"))
            {
                System.out.println("Create client record acceptance test passed!");
                System.exit(0);
            }
        }
    }
}
