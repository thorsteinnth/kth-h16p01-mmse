package com.thorsteinnth.kth.mmse.sepcli.Test;

import com.thorsteinnth.kth.mmse.sepcli.Domain.User;
import com.thorsteinnth.kth.mmse.sepcli.Repository.UserRepository;
import com.thorsteinnth.kth.mmse.sepcli.Service.UserService;

public class UserTest
{
    public static boolean testAddGetUser()
    {
        UserService userService = new UserService(new UserRepository());
        User user1 = new User("email", "password", User.Role.AdministrationDepartmentManager);
        userService.addUser(user1);

        User userGet = userService.getUserByEmail(user1.email);

        return userGet.equals(user1);
    }
}
