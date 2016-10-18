package com.thorsteinnth.kth.mmse.sepcli.Test;

import com.thorsteinnth.kth.mmse.sepcli.AppData;
import com.thorsteinnth.kth.mmse.sepcli.Domain.EventRequest;

public class TestManager
{
    public static boolean runTests()
    {
        boolean success = true;

        //region User tests

        if (!UserTest.testCreateUser())
        {
            System.out.println("UserTest.testCreateUser() FAILED");
            success = false;
        }

        AppData.clear();

        if (!UserTest.testAddDuplicateUser())
        {
            System.out.println("UserTest.testAddDuplicateUser() FAILED");
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

        if(!UserTest.testGetAllUsersByRole())
        {
            System.out.println("UserTest.testGetAllUsersByRole() FAILED");
            success = false;
        }

        AppData.clear();

        if(!UserTest.testEditUser())
        {
            System.out.println("UserTest.testEditUser() FAILED");
            success = false;
        }

        AppData.clear();

        //endregion

        //region Client tests

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

        //endregion

        //region Event request test

        if (!EventRequestTest.testCreateEventRequest())
        {
            System.out.println("EventRequestTest.testCreateEventRequest() FAILED");
            success = false;
        }

        AppData.clear();

        if (!EventRequestTest.testCreateEventRequestDB())
        {
            System.out.println("EventRequestTest.testCreateEventRequestDB() FAILED");
            success = false;
        }

        AppData.clear();

        if (!EventRequestTest.testAddGetCommentToEventRequest())
        {
            System.out.println("EventRequestTest.testAddGetCommentToEventRequest() FAILED");
            success = false;
        }

        AppData.clear();

        if (!EventRequestTest.testUpdateEventRequestStatus())
        {
            System.out.println("EventRequestTest.testUpdateEventRequestStatus FAILED");
            success = false;
        }

        AppData.clear();

        if (!EventRequestTest.testUpdateEventRequestWorkflowStatus())
        {
            System.out.println("EventRequestTest.testUpdateEventRequestWorkflowStatus FAILED");
            success = false;
        }

        AppData.clear();

        //endregion

        //region Task request test

        if(!TaskRequestTest.testCreateTaskRequest())
        {
            System.out.println("TaskRequestTest.testCreateTaskRequest() FAILED");
            success = false;
        }

        AppData.clear();

        if(!TaskRequestTest.testAddGetCommentToTaskRequest())
        {
            System.out.println("TaskRequestTest.testAddGetCommentToTaskRequest() FAILED");
            success = false;
        }

        AppData.clear();

        if(!TaskRequestTest.testUpdateTaskRequestStatus())
        {
            System.out.println("TaskRequestTest.testUpdateTaskRequestStatus() FAILED");
            success = false;
        }

        AppData.clear();

        //endregion

        //region Financial request test

        if(!FinancialRequestTest.testCreateFinancialRequest())
        {
            System.out.println("FinancialRequest.testCreateFinancialRequest() FAILED");
            success = false;
        }

        AppData.clear();

        if(!FinancialRequestTest.testUpdateFinancialRequestStatus())
        {
            System.out.println("FinancialRequest.testUpdateFinancialRequestStatus() FAILED");
            success = false;
        }

        AppData.clear();

        //endregion

        //region Recruitment request test

        if(!RecruitmentRequestTest.testCreateRecruitmentRequest())
        {
            System.out.println("RecruitmentRequestTest.testCreateRecruitmentRequest() FAILED");
            success = false;
        }

        AppData.clear();

        if(!RecruitmentRequestTest.testUpdateRecruitmentRequestStatus())
        {
            System.out.println("RecruitmentRequestTest.testUpdateRecruitmentRequestStatus() FAILED");
            success = false;
        }

        AppData.clear();

        //endregion

        //region Access control test

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

        //endregion

        //region Request mail test

        if (!RequestMailTest.testSendGetRequestEnvelope())
        {
            System.out.println("RequestMailTest.testSendGetRequestEnvelope() FAILED");
            success = false;
        }

        AppData.clear();

        if (!RequestMailTest.testRemoveRequestEnvelope())
        {
            System.out.println("RequestMailTest.testRemoveRequestEnvelope() FAILED");
            success = false;
        }

        AppData.clear();

        //endregion

        // End of tests

        if (success)
            System.out.println("TestManager - SUCCESS");
        else
            System.out.println("TestManager - FAILURE");

        return success;
    }
}
