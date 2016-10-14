package com.thorsteinnth.kth.mmse.sep.cli.Service;

import com.thorsteinnth.kth.mmse.sep.cli.Domain.User;

public class UserService
{
    public User Login(String email, String password)
    {
        // TODO Get from repository

        return new User(email, password);
    }
}
