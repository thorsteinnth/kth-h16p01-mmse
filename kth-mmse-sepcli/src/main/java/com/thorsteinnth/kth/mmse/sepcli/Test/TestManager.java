package com.thorsteinnth.kth.mmse.sepcli.Test;

import com.thorsteinnth.kth.mmse.sepcli.AppData;
import com.thorsteinnth.kth.mmse.sepcli.Domain.EventRequest;

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

        if (!UserTest.testAddGetUserByEmail())
        {
            System.out.println("UserTest.testAddGetUserByEmail() FAILED");
            success = false;
        }

        AppData.clear();

        if (!UserTest.testAddDeleteUser())
        {
            System.out.println("UserTest.testAddDeleteUser() FAILED");
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

        if(!UserTest.testGetAllUsers())
        {
            System.out.println("UserTest.testGetAllUsers() FAILED");
            success = false;
        }

        AppData.clear();

        if(!UserTest.testEditUser())
        {
            System.out.println("UserTest.testEditUser() FAILED");
            success = false;
        }

        AppData.clear();

        // Client tests

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

        AppData.clear();

        if (!ClientTest.testGetAllClients())
        {
            System.out.println("ClientTest.testGetAllClients() FAILED");
            success = false;
        }

        AppData.clear();

        // Event request test

        if (!EventRequestTest.testCreateEventRequest())
        {
            System.out.println("EventRequestTest.testCreateEventRequest() FAILED");
            success = false;
        }

        AppData.clear();

        if (!EventRequestTest.testAddEventRequest())
        {
            System.out.println("EventRequestTest.testAddEventRequest() FAILED");
            success = false;
        }

        AppData.clear();

        // Task request test

        if(!TaskRequestTest.testCreateTaskRequest())
        {
            System.out.println("TaskRequestTest.testCreateTaskRequest() FAILED");
            success = false;
        }

        // Access control test

        if (!AccessTest.testHasAccess())
        {
            System.out.println("AccessTest.testHasAccess() FAILED");
            success = false;
        }

        AppData.clear();

        if (!AccessTest.testAccessDenied())
        {
            System.out.println("AccessTest.testAccessDenied() FAILED");
            success = false;
        }

        AppData.clear();

        if (success)
            System.out.println("TestManager - SUCCESS");
        else
            System.out.println("TestManager - FAILURE");

        return success;
    }
}
