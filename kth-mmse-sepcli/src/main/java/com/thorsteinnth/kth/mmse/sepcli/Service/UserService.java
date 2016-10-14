package com.thorsteinnth.kth.mmse.sepcli.Service;

import com.thorsteinnth.kth.mmse.sepcli.AppData;
import com.thorsteinnth.kth.mmse.sepcli.Domain.User;

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
}
