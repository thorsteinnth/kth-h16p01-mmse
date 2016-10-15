package com.thorsteinnth.kth.mmse.sepcli;

import com.thorsteinnth.kth.mmse.sepcli.Domain.EventRequest;
import com.thorsteinnth.kth.mmse.sepcli.Domain.User;
import com.thorsteinnth.kth.mmse.sepcli.Domain.Client;

import java.util.ArrayList;

public class AppData
{
    public static User loggedInUser;
    public static ArrayList<User> users;
    public static ArrayList<Client> clients;

    public static ArrayList<EventRequest> eventRequests;

    public AppData()
    {
        users = new ArrayList<>();
        clients = new ArrayList<>();
        eventRequests = new ArrayList<>();
    }

    public static void clear()
    {
        loggedInUser = null;
        users.clear();
        clients.clear();
        eventRequests.clear();
    }
}
