package com.thorsteinnth.kth.mmse.sepcli;

import com.thorsteinnth.kth.mmse.sepcli.Controllers.HomePageController;
import com.thorsteinnth.kth.mmse.sepcli.Controllers.LoginController;
import com.thorsteinnth.kth.mmse.sepcli.Repository.UserRepository;
import com.thorsteinnth.kth.mmse.sepcli.Service.UserService;

public class SepCliApplication
{
    public static AppData AppData;

    public static void main (String [] arguments)
    {
        AppData appData = new AppData();

        UserService userService = new UserService(new UserRepository());
        userService.createUsers();

        new LoginController().displayPage();
    }
}
