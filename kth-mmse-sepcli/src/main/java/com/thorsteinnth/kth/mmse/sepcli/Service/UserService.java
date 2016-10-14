package com.thorsteinnth.kth.mmse.sepcli.Service;

import com.thorsteinnth.kth.mmse.sepcli.Domain.User;

public class UserService
{
    public User login(String email, String password)
    {
        // TODO Get from repository

        return new User(email, password, User.Role.GeneralUser);
    }
}
