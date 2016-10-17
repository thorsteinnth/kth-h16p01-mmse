package com.thorsteinnth.kth.mmse.sepcli.Repository;

import com.thorsteinnth.kth.mmse.sepcli.Domain.User;

import java.util.ArrayList;

public interface IUserRepository
{
    boolean addUser(User user);
    void deleteUser(User user);
    User getUserByEmail(String email);
    ArrayList<User> getAllUsers();
    ArrayList<User> getAllUsersByRole(User.Role role);
    User updateUser(User user, String newEmail, String newPassword, User.Role newRole);
}
