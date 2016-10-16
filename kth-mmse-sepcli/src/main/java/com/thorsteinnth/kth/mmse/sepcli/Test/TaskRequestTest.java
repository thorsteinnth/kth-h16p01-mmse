package com.thorsteinnth.kth.mmse.sepcli.Test;

import com.sun.javafx.tk.Toolkit;
import com.thorsteinnth.kth.mmse.sepcli.AppData;
import com.thorsteinnth.kth.mmse.sepcli.Domain.*;
import com.thorsteinnth.kth.mmse.sepcli.Repository.ClientRepository;
import com.thorsteinnth.kth.mmse.sepcli.Repository.EventRequestRepository;
import com.thorsteinnth.kth.mmse.sepcli.Repository.TaskRequestRepository;
import com.thorsteinnth.kth.mmse.sepcli.Repository.UserRepository;
import com.thorsteinnth.kth.mmse.sepcli.Service.ClientService;
import com.thorsteinnth.kth.mmse.sepcli.Service.EventRequestService;
import com.thorsteinnth.kth.mmse.sepcli.Service.TaskRequestService;
import com.thorsteinnth.kth.mmse.sepcli.Service.UserService;

import java.math.BigDecimal;
import java.util.GregorianCalendar;

public class TaskRequestTest
{
    public static TaskRequestService getService()
    {
        return new TaskRequestService(new TaskRequestRepository());
    }

    public static boolean testCreateTaskRequest()
    {
        TaskRequestService service = getService();

        UserService userService = new UserService(new UserRepository());
        userService.addInitialUsers();
        userService.login("jack@sep.se", "jack123");

        String testTitle = "Test task request title";
        String testDescription = "Test task request description";
        TaskRequest.Priority testPriority = TaskRequest.Priority.High;
        EventRequest testEr = getTestEventRequest();
        User testAssignee = new User("magy@sep.se", "magy123", User.Role.ProductionDepartmentSubTeamEmployee);

        TaskRequest tr = service.createTaskRequest(
                testTitle,
                testDescription,
                testPriority,
                testEr,
                testAssignee
        );

        try
        {
            assert tr.getTitle().equals(testTitle)
                    && tr.getDescription().equals(testDescription)
                    && tr.getPriority().equals(testPriority)
                    && tr.getEventRequest().equals(testEr)
                    && tr.getCreatedByUser().equals(AppData.loggedInUser)
                    && tr.getAssignee().equals(testAssignee);

            return true;
        }
        catch (AssertionError ae)
        {
            System.out.println("testCreateTaskRequest() - failed");
            return false;
        }
    }

    public static boolean testAddGetCommentToTaskRequest()
    {
        TaskRequest testTaskRequest = getTestTaskRequest();

        RequestComment testComment1 = new RequestComment(AppData.loggedInUser, "test task request comment 1");
        testTaskRequest.addComment(testComment1);
        RequestComment testComment2 = new RequestComment(AppData.loggedInUser, "test task request comment 2");
        testTaskRequest.addComment(testComment2);

        try
        {
            assert testTaskRequest.getComments().size() == 2;
            assert testTaskRequest.getComments().get(0).equals(testComment1);
            assert testTaskRequest.getComments().get(1).equals(testComment2);
            return true;
        }
        catch (AssertionError ae)
        {
            System.out.println("testAddCommentToTaskRequest - failed");
            return false;
        }
    }

    private static TaskRequest getTestTaskRequest()
    {
        TaskRequestService service = getService();

        UserService userService = new UserService(new UserRepository());
        userService.addInitialUsers();
        userService.login("jack@sep.se", "jack123");

        String testTitle = "Test task request title";
        String testDescription = "Test task request description";
        TaskRequest.Priority testPriority = TaskRequest.Priority.High;
        EventRequest testEr = getTestEventRequest();
        User testAssignee = new User("magy@sep.se", "magy123", User.Role.ProductionDepartmentSubTeamEmployee);

        TaskRequest tr = service.createTaskRequest(
                testTitle,
                testDescription,
                testPriority,
                testEr,
                testAssignee
        );

        return tr;
    }

    private static EventRequest getTestEventRequest()
    {
        EventRequestService srv = new EventRequestService(new EventRequestRepository());
        ClientService clientService = new ClientService(new ClientRepository());
        UserService userService = new UserService(new UserRepository());
        userService.addInitialUsers();
        userService.login("charlie@sep.se","charlie123");

        String title = "Test event request title";
        String description = "Test event request description";
        GregorianCalendar startDateTime = new GregorianCalendar(2016, 10, 15, 18, 0);
        GregorianCalendar endDateTime = new GregorianCalendar(2016, 10, 15, 21, 0);
        int numberOfAttendees = 100;
        String preferenceDesc = "These are the preferences";
        BigDecimal expectedBudget = new BigDecimal(99999);
        Client client = clientService.createClient("Fannar", "KTH", "email@web.com", "5556666");

        EventRequest er = srv.createEventRequest(
                title,
                description,
                startDateTime,
                endDateTime,
                numberOfAttendees,
                preferenceDesc,
                expectedBudget,
                client
        );

        return er;
    }
}
