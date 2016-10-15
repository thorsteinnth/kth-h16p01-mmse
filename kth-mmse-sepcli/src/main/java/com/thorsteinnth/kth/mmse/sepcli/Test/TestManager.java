package com.thorsteinnth.kth.mmse.sepcli.Test;

import com.thorsteinnth.kth.mmse.sepcli.AppData;

public class TestManager
{
    public static boolean runTests()
    {
        boolean success = true;

        // User tests

        if (!UserTest.testCreateUser())
        {
            System.out.println("UserTest.testCreateUser() FAILED");
            success = false;
        }

        AppData.clear();

        if (!UserTest.testAddGetUser())
        {
            System.out.println("UserTest.testAddGetUser() FAILED");
            success = false;
        }

        AppData.clear();

        if (!UserTest.testLoginFailed())
        {
            System.out.println("UserTest.testLoginFailed() FAILED");
            success = false;
        }

        AppData.clear();

        if (!UserTest.testLoginSuccess())
        {
            System.out.println("UserTest.testLoginSuccess() FAILED");
            success = false;
        }

        AppData.clear();

        if (!UserTest.testLogout())
        {
            System.out.println("UserTest.testLogout() FAILED");
            success = false;
        }

        AppData.clear();

        if (!UserTest.testAddInitialUsers())
        {
            System.out.println("UserTest.testAddInitialUsers() FAILED");
            success = false;
        }

        AppData.clear();

        // Client test

        if (!ClientTest.testCreateClient())
        {
            System.out.println("ClientTest.testCreateClient() FAILED");
            success = false;
        }

        AppData.clear();

        if (!ClientTest.testGetClientById())
        {
            System.out.println("ClientTest.testGetClientById() FAILED");
            success = false;
        }

        if (success)
            System.out.println("TestManager - SUCCESS");
        else
            System.out.println("TestManager - FAILURE");

        // Make sure AppData is cleared, after running the tests
        AppData.clear();

        return success;
    }
}
