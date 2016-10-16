package com.thorsteinnth.kth.mmse.sepcli.Test;

import com.sun.javafx.tk.Toolkit;
import com.thorsteinnth.kth.mmse.sepcli.AppData;
import com.thorsteinnth.kth.mmse.sepcli.Domain.Client;
import com.thorsteinnth.kth.mmse.sepcli.Domain.EventRequest;
import com.thorsteinnth.kth.mmse.sepcli.Domain.TaskRequest;
import com.thorsteinnth.kth.mmse.sepcli.Domain.User;
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
        User testAssignee = new User("magy@sep.se", "magy123", User.Role.SubTeamEmployee);

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

        String testComment = "test task request comment";
        testTaskRequest.addComment(testComment);

        try
        {
            assert testTaskRequest.getComment().equals(testComment);
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
        User testAssignee = new User("magy@sep.se", "magy123", User.Role.SubTeamEmployee);

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
