package com.thorsteinnth.kth.mmse.sepcli.Service;

import com.thorsteinnth.kth.mmse.sepcli.Domain.User;

import java.util.ArrayList;

public class UserService
{
    public User login(String email, String password)
    {
        // TODO Get from repository

        return new User(email, password, User.Role.GeneralUser);
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
