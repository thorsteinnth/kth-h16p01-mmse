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

        if (!UserTest.testLoginFailed())
        {
            System.out.println("UserTest.testLoginFailed() FAILED");
            success = false;
        }

        if (!UserTest.testLoginSuccess())
        {
            System.out.println("UserTest.testLoginSuccess() FAILED");
            success = false;
        }

        if (!UserTest.testLogout())
        {
            System.out.println("UserTest.testLogout() FAILED");
            success = false;
        }

        if (!ClientTest.testCreateClient())
        {
            System.out.println("ClientTest.testCreateClient() FAILED");
            success = false;
        }

        if (success)
            System.out.println("TestManager - SUCCESS");
        else
            System.out.println("TestManager - FAILURE");

        return success;
    }
}
