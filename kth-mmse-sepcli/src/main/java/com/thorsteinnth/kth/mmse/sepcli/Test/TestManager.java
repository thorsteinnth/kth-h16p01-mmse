package com.thorsteinnth.kth.mmse.sepcli.Test;

public class TestManager
{
    public static boolean runTests()
    {
        boolean success = true;

        if (!UserTest.testAddGetUser())
        {
            System.out.println("UserTest.testAddGetUser() FAILED");
            success = false;
        }

        if (!ClientTest.testCreateClient())
        {
            success = false;
        }

        if (success)
            System.out.println("TestManager - SUCCESS");
        else
            System.out.println("TestManager - FAILURE");

        return success;
    }
}
