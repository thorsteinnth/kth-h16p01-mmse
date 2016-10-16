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

    public static ArrayList<RequestEnvelope> mailQueue;

    public AppData()
    {
        users = new ArrayList<>();
        clients = new ArrayList<>();
        eventRequests = new ArrayList<>();
        taskRequests = new ArrayList<>();
        mailQueue = new ArrayList<>();
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
        mailQueue.clear();
        financialRequests.clear();
        recruitmentRequests.clear();
    }
}
