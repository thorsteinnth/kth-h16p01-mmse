package com.thorsteinnth.kth.mmse.sepcli.Test;

import com.thorsteinnth.kth.mmse.sepcli.Domain.AccessFunction;
import com.thorsteinnth.kth.mmse.sepcli.Domain.User;
import com.thorsteinnth.kth.mmse.sepcli.Service.AccessControlService;

public class AccessTest
{
    public static AccessControlService getService()
    {
        return new AccessControlService();
    }

    public static boolean testHasAccess()
    {
        AccessControlService service = getService();

        User testUser = new User("janet@sep.se", "janet123", User.Role.SeniorCustomerServiceOfficer);

        boolean hasAccess = service.hasAccess(testUser, AccessFunction.createClientRecord);

        try
        {
            assert hasAccess;
            return true;
        }
        catch (AssertionError ae)
        {
            System.out.println("testHasAccess() - client is denied access of a function that he is supposed to have");
            return false;
        }
    }

    public static boolean testAccessDenied()
    {
        AccessControlService service = getService();

        User testUser = new User("emma@sep.se", "emma123", User.Role.MarketingAssistant);

        boolean hasAccess = service.hasAccess(testUser, AccessFunction.createClientRecord);

        try
        {
            assert !hasAccess;
            return true;
        }
        catch (AssertionError ae)
        {
            System.out.println("testAccessDenied() - client has access to a function that he is not supposed to have");
            return false;
        }
    }
}
