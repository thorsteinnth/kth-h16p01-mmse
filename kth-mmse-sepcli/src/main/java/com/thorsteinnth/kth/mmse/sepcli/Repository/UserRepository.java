package com.thorsteinnth.kth.mmse.sepcli.Repository;

import com.thorsteinnth.kth.mmse.sepcli.AppData;
import com.thorsteinnth.kth.mmse.sepcli.Domain.User;

import java.util.ArrayList;

public class UserRepository implements  IUserRepository
{
    public UserRepository()
    {
    }

    public void addUser(User user)
    {
        AppData.users.add(user);
    }

    public void deleteUser(User userToDelete)
    {
        AppData.users.removeIf(u -> u.equals(userToDelete));
    }

    public User getUserByEmail(String email)
    {
        User user = null;

        for (User u : AppData.users)
        {
            if (u.email.equals(email))
            {
                user = u;
                break;
            }
        }

        return user;
    }

    public ArrayList<User> getAllUsers()
    {
        return AppData.users;
    }

    public User updateUser(User user, String newEmail, String newPassword, User.Role newRole)
    {
        // NOTE:
        // Since we just have an in memory data store there is no need to save the user explicitly
        // after updating it
        user.email = newEmail;
        user.password = newPassword;
        user.role = newRole;
        return user;
    }
}
