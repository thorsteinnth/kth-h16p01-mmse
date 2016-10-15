package com.thorsteinnth.kth.mmse.sepcli.Test;

import com.thorsteinnth.kth.mmse.sepcli.AppData;
import com.thorsteinnth.kth.mmse.sepcli.Domain.User;
import com.thorsteinnth.kth.mmse.sepcli.Repository.UserRepository;
import com.thorsteinnth.kth.mmse.sepcli.Service.UserService;

public class UserTest
{
    private static UserService getService()
    {
        return new UserService(new UserRepository());
    }

    public static boolean testAddGetUser()
    {
        UserService userService = getService();
        User user1 = new User("email", "password", User.Role.AdministrationDepartmentManager);
        userService.addUser(user1);
        User userGet = userService.getUserByEmail(user1.email);

        try
        {
            assert  userGet.equals(user1);
            return true;
        }
        catch (AssertionError ae)
        {
            System.out.println("testAddGetUser() - userGet not equal to user1");
            return false;
        }
    }

    public static boolean testLoginFailed()
    {
        UserService userService = getService();
        User user1 = new User("testemail", "testpassword", User.Role.AdministrationDepartmentManager);
        userService.addUser(user1);

        boolean loginSuccess = userService.login("testemail", "nonsense");

        try
        {
            assert !loginSuccess;
        }
        catch (AssertionError ae)
        {
            System.out.println("testLoginFailed() - Login success when it shouldn't have been successful");
            return false;
        }

        try
        {
            assert AppData.loggedInUser == null;
        }
        catch (AssertionError ae)
        {
            System.out.println("testLoginFailed() - Non-null logged in user");
            return false;
        }

        return true;
    }
}
