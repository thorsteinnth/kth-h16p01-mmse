package com.thorsteinnth.kth.mmse.sepcli;

import com.thorsteinnth.kth.mmse.sepcli.Domain.User;
import com.thorsteinnth.kth.mmse.sepcli.Domain.Client;

import java.util.ArrayList;

public class AppData
{
    public static User loggedInUser;
    public static ArrayList<Client> clients;

    public AppData()
    {
        clients = new ArrayList<Client>();
    }
}
