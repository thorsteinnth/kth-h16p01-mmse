package com.thorsteinnth.kth.mmse.sepcli.Test;

import com.thorsteinnth.kth.mmse.sepcli.AppData;
import com.thorsteinnth.kth.mmse.sepcli.Domain.User;
import com.thorsteinnth.kth.mmse.sepcli.Repository.UserRepository;
import com.thorsteinnth.kth.mmse.sepcli.Service.UserService;

import java.util.ArrayList;

public class UserTest
{
    private static UserService getService()
    {
        return new UserService(new UserRepository());
    }

    public static boolean testCreateUser()
    {
        try
        {
            User user1 = new User("email", "password", User.Role.AdministrationDepartmentManager);
            User user2 = getService().createUser("email", "password", User.Role.AdministrationDepartmentManager);
            assert user1.equals(user2);
            return true;
        }
        catch (AssertionError ae)
        {
            System.out.println("testCreateUser() - user1 not equal to user2");
            return false;
        }
    }

    public static boolean testAddInitialUsers()
    {
        try
        {
            UserService srv = getService();
            srv.addInitialUsers();
            assert srv.getAllUsers().size() == 14;
            return true;
        }
        catch (AssertionError ae)
        {
            System.out.println("testAddInitialUsers() - Number of users not correct");
            return false;
        }
    }

    public static boolean testAddDuplicateUser()
    {
        // Users should be unique by email

        try
        {
            UserService userService = getService();
            User user1 = userService.createUser("email", "password", User.Role.AdministrationDepartmentManager);
            User user2 = userService.createUser("email", "password", User.Role.AdministrationDepartmentManager);

            assert userService.addUser(user1);
            assert !userService.addUser(user2);

            return true;
        }
        catch (AssertionError ae)
        {
            System.out.println("testAddDuplicateUser() - failed");
            return false;
        }
    }

    public static boolean testAddGetUserByEmail()
    {
        UserService userService = getService();
        User user1 = userService.createUser("email", "password", User.Role.AdministrationDepartmentManager);
        userService.addUser(user1);
        User userGet = userService.getUserByEmail(user1.email);

        try
        {
            // NOTE:
            // Since we just have an in memory data store
            // the getUserByEmail() function just returns the same user object - if we find a user they will be equal
            assert userGet.equals(user1);
            return true;
        }
        catch (AssertionError ae)
        {
            System.out.println("testAddGetUserByEmail() - userGet not equal to user1");
            return false;
        }
    }

    public static boolean testAddDeleteUser()
    {
        UserService srv = getService();
        srv.addUser(srv.createUser("maria@sep.se", "maria123", User.Role.HRAssistant));

        // Verify that the user is indeed present
        // (there is another test for this but doing it again here to avoid coupling)
        try
        {
            assert srv.getUserByEmail("maria@sep.se") != null;
        }
        catch (AssertionError ae)
        {
            System.out.println("testAddDeleteUser() - Failed to add user to then delete");
            return false;
        }

        int userCountBeforeDelete = srv.getAllUsers().size();
        User userToDelete = srv.createUser("maria@sep.se", "maria123", User.Role.HRAssistant);
        srv.deleteUser(userToDelete);

        try
        {
            assert srv.getAllUsers().size() == userCountBeforeDelete-1;
        }
        catch (AssertionError ae)
        {
            System.out.println("testAddDeleteUser() - User count same as before delete operation");
            return false;
        }

        try
        {
            assert srv.getUserByEmail("maria@sep.se") == null;
        }
        catch (AssertionError ae)
        {
            System.out.println("testAddDeleteUser() - User still present after delete");
            return false;
        }

        return true;
    }

    public static boolean testLoginFailed()
    {
        UserService userService = getService();
        User user1 = userService.createUser("testemail", "testpassword", User.Role.AdministrationDepartmentManager);
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

    public static boolean testLoginSuccess()
    {
        UserService userService = getService();
        User user1 = userService.createUser("correctemail", "correctpassword", User.Role.AdministrationDepartmentManager);
        userService.addUser(user1);

        boolean loginSuccess = userService.login("correctemail", "correctpassword");

        try
        {
            assert loginSuccess;
        }
        catch (AssertionError ae)
        {
            System.out.println("testLoginSuccess() - Login failure when it should have been successful");
            return false;
        }

        try
        {
            assert AppData.loggedInUser.equals(user1);
        }
        catch (AssertionError ae)
        {
            System.out.println("testLoginSuccess() - AppData.loggedInUser not equal to user that logged in");
            return false;
        }

        return true;
    }

    public static boolean testLogout()
    {
        if (!testLoginSuccess())
        {
            System.out.println("testLogout() - Could not log in user for logout test");
            return false;
        }

        try
        {
            assert AppData.loggedInUser != null;
        }
        catch (AssertionError ae)
        {
            System.out.println("testLogout() - No user logged in to log out");
            return false;
        }

        try
        {
            UserService userService = getService();
            userService.logout();
            assert AppData.loggedInUser == null;
        }
        catch (AssertionError ae)
        {
            System.out.println("testLogout() - Logged in user not null");
            return false;
        }

        return true;
    }

    public static boolean testGetAllUsers()
    {
        UserService userService = getService();

        ArrayList<User> users = new ArrayList<>();

        User user1 = new User("testemail1", "testpassword1", User.Role.AdministrationDepartmentManager);
        userService.addUser(user1);
        users.add(user1);

        User user2 = new User("testemail2", "testpassword2", User.Role.VicePresident);
        userService.addUser(user2);
        users.add(user2);

        User user3 = new User("testemail3", "testpassword3", User.Role.SeniorHRManager);
        userService.addUser(user3);
        users.add(user3);

        ArrayList<User> getAllUsers = userService.getAllUsers();

        try
        {
            assert users.size() == getAllUsers.size();

            int cnt = 0;

            for(User u: users)
            {
                User getUser = getAllUsers.get(cnt);
                assert getUser.equals(u);

                cnt++;
            }

            return true;
        }
        catch (AssertionError ae)
        {
            System.out.println("testGetAllUsers() - users not equal to the getAllUsers");
            return false;
        }
    }

    public static boolean testGetAllUsersByRole()
    {
        UserService userService = getService();

        ArrayList<User> adminDeptManagers = new ArrayList<>();
        ArrayList<User> vicePresidents = new ArrayList<>();
        ArrayList<User> seniorHRManagers = new ArrayList<>();

        User user1 = new User("testemail1", "testpassword1", User.Role.AdministrationDepartmentManager);
        userService.addUser(user1);
        adminDeptManagers.add(user1);

        User user2 = new User("testemail2", "testpassword2", User.Role.VicePresident);
        userService.addUser(user2);
        vicePresidents.add(user2);

        User user3 = new User("testemail3", "testpassword3", User.Role.SeniorHRManager);
        userService.addUser(user3);
        seniorHRManagers.add(user3);

        User user4 = new User("testemail4", "testpassword4", User.Role.SeniorHRManager);
        userService.addUser(user4);
        seniorHRManagers.add(user4);

        ArrayList<User> getAllAdminDeptManagers = userService.getAllUsersByRole(User.Role.AdministrationDepartmentManager);
        ArrayList<User> getAllVPs = userService.getAllUsersByRole(User.Role.VicePresident);
        ArrayList<User> getAllSeniorHRManagers = userService.getAllUsersByRole(User.Role.SeniorHRManager);

        try
        {
            assert adminDeptManagers.size() == getAllAdminDeptManagers.size();
            assert vicePresidents.size() == getAllVPs.size();
            assert seniorHRManagers.size() == getAllSeniorHRManagers.size();

            for (User adminDeptManager : adminDeptManagers)
            {
                assert getAllAdminDeptManagers.contains(adminDeptManager);
            }

            for (User vp : vicePresidents)
            {
                assert getAllVPs.contains(vp);
            }

            for (User seniorHRManager : seniorHRManagers)
            {
                assert getAllSeniorHRManagers.contains(seniorHRManager);
            }

            return true;
        }
        catch (AssertionError ae)
        {
            System.out.println("testGetAllUsersByRole() - failed");
            return false;
        }
    }

    public static boolean testEditUser()
    {
        UserService srv = getService();

        User user = srv.createUser("emailbeforeedit", "passwordbeforeedit", User.Role.GeneralUser);

        String newEmail = "newemail";
        String newPassword = "newpassword";
        User.Role newRole = User.Role.VicePresident;

        User editedUser = srv.updateUser(user, newEmail, newPassword, newRole);

        try
        {
            assert editedUser.email.equals(newEmail)
                    && editedUser.password.equals(newPassword)
                    && editedUser.role.equals(newRole);
            return true;
        }
        catch (AssertionError ae)
        {
            System.out.println("testEditUser() - user fields not correct after edit");
            return false;
        }
    }
}
