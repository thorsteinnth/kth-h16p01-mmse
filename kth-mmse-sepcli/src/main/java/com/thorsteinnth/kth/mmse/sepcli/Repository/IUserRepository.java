package com.thorsteinnth.kth.mmse.sepcli.Repository;

import com.thorsteinnth.kth.mmse.sepcli.Domain.User;

import java.util.ArrayList;

public interface IUserRepository
{
    void addUser(User user);
    User getUserById(String id);
    ArrayList<User> getAllUsers();
}
