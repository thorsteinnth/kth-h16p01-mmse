package com.thorsteinnth.kth.mmse.sepcli.Service;

import com.thorsteinnth.kth.mmse.sepcli.AppData;
import com.thorsteinnth.kth.mmse.sepcli.Domain.User;

import java.util.ArrayList;

public class UserService
{
    public boolean login(String email, String password)
    {
        // TODO Get from repository
        User user = new User(email, password, User.Role.GeneralUser);
        AppData.loggedInUser = user;
        return true;
    }

    public void logout()
    {
        AppData.loggedInUser = null;
    }

    public User createUser(String email, String password, User.Role role)
    {
        return new User(email, password, role);
    }

    public ArrayList<User> deleteUser(ArrayList<User> userList, User user)
    {
        if(userList.contains(user))
        {
            userList.remove(user);
        }

        return  userList;
    }
}
