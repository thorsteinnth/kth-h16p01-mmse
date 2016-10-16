package com.thorsteinnth.kth.mmse.sepcli;

import com.thorsteinnth.kth.mmse.sepcli.Domain.*;

import java.util.ArrayList;

public class AppData
{
    public static User loggedInUser;
    public static ArrayList<User> users;
    public static ArrayList<Client> clients;

    public static ArrayList<EventRequest> eventRequests;
    public static ArrayList<TaskRequest> taskRequests;
    public static ArrayList<FinancialRequest> financialRequests;
    public static ArrayList<RecruitmentRequest> recruitmentRequests;

    public AppData()
    {
        users = new ArrayList<>();
        clients = new ArrayList<>();
        eventRequests = new ArrayList<>();
        taskRequests = new ArrayList<>();
        financialRequests = new ArrayList<>();
        recruitmentRequests = new ArrayList<>();
    }

    public static void clear()
    {
        loggedInUser = null;
        users.clear();
        clients.clear();
        eventRequests.clear();
        taskRequests.clear();
        financialRequests.clear();
        recruitmentRequests.clear();
    }
}
