package com.thorsteinnth.kth.mmse.sepcli.Test.AcceptanceTest;

import java.io.InputStream;

public class AcceptanceTestManager
{
    private static String testNumber;

    //TODO figure out how to assert test result

    public static void runAcceptanceTest(String testToRun)
    {
        testNumber = testToRun;

        if (testNumber.equals("1"))
        {
            EventRequestAcptTest.runTest();
        }
        else
        {
            //Run another acceptance test
        }
    }

    public static InputStream getInputStream()
    {
        if (testNumber.equals("1"))
            return EventRequestAcptTest.getInputStream();
        else
            return EventRequestAcptTest.getInputStream();

        //else return another input stream
    }

    public static void displayResults(String result)
    {
        System.out.println("Acceptance test result: " + result);
    }
}
