package com.thorsteinnth.kth.mmse.sepcli.Controllers;

import com.thorsteinnth.kth.mmse.sepcli.AppData;
import com.thorsteinnth.kth.mmse.sepcli.CliHelper;
import com.thorsteinnth.kth.mmse.sepcli.Domain.EventRequest;
import com.thorsteinnth.kth.mmse.sepcli.Domain.TaskRequest;
import com.thorsteinnth.kth.mmse.sepcli.Domain.User;
import com.thorsteinnth.kth.mmse.sepcli.Repository.EventRequestRepository;
import com.thorsteinnth.kth.mmse.sepcli.Repository.TaskRequestRepository;
import com.thorsteinnth.kth.mmse.sepcli.Repository.UserRepository;
import com.thorsteinnth.kth.mmse.sepcli.Service.EventRequestService;
import com.thorsteinnth.kth.mmse.sepcli.Service.TaskRequestService;
import com.thorsteinnth.kth.mmse.sepcli.Service.UserService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class TaskRequestController extends BaseController
{
    private BaseController previousController;
    private EventRequestService eventRequestService;
    private UserService userService;

    public TaskRequestController(BaseController previousController)
    {
        this.previousController = previousController;
        this.eventRequestService = new EventRequestService(new EventRequestRepository());
        this.userService = new UserService(new UserRepository());
    }

    public void displayPage()
    {
        CliHelper.newLine();
        CliHelper.write("This is the create task request page");
        CliHelper.write("Please select one of the following operations:");
        CliHelper.write("1. Create task request");
        CliHelper.write("2. Back");
        ArrayList<String> validInputs = new ArrayList<String>();
        validInputs.add("1");
        validInputs.add("2");

        String input = CliHelper.getInput("Select an operation (1-2)", validInputs);

        if (input.equals("1"))
            createTaskRequest();
        else if (input.equals("2"))
            back();
        else
            System.out.println("ERROR: Unknown command");
    }

    // TODO Restrict access to specific roles
    private void createTaskRequest()
    {
        CliHelper.newLine();
        CliHelper.write("Create task request");

        if(eventRequestService.getAllEventRequests().isEmpty())
        {
            CliHelper.write("There are no event requests in the system. Create a event in order to add task request.");
            displayPage();
        }

        EventRequest eventRequest = selectEventRequest();
        String title = CliHelper.getInput("Title:");
        String description = CliHelper.getInput("Description:");

        ArrayList<String> validPriorityInput = new ArrayList<>();
        validPriorityInput.add("L");
        validPriorityInput.add("M");
        validPriorityInput.add("H");
        String priority = CliHelper.getInput("Priority (L/M/H):", validPriorityInput);
        User assignee = selectAssignee();

        // NOTE: Input from the user is valid at this point
        TaskRequest.Priority pri;

        if(priority.equals("M"))
            pri = TaskRequest.Priority.Medium;
        else if(priority.equals("H"))
            pri = TaskRequest.Priority.High;
        else
            pri = TaskRequest.Priority.Low;

        TaskRequestService taskRequestService = new TaskRequestService(new TaskRequestRepository());
        TaskRequest tr = taskRequestService.createTaskRequest(
                title,
                description,
                pri,
                eventRequest,
                assignee
        );

        printTaskRequest(tr);

        displayPage();
    }

    private EventRequest selectEventRequest()
    {
        // Display list of all open or in progress event requests for user to choose from

        CliHelper.newLine();
        CliHelper.write("Link task request to event");

        if(eventRequestService.getAllEventRequests().isEmpty())
        {
            CliHelper.write("ERROR: No event available");
            return null;
        }
        else
        {
            Map<String, EventRequest> eventRequests = new HashMap<String, EventRequest>();
            ArrayList<String> validInputs = new ArrayList<>();
            int counter = 1;

            for (EventRequest er: eventRequestService.getAllEventRequests())
            {
                if(er.getStatus() == EventRequest.Status.Open ||
                        er.getStatus() == EventRequest.Status.InProgress)
                {
                    String seqNumber = Integer.toString(counter);

                    eventRequests.put(seqNumber, er);
                    validInputs.add(seqNumber);

                    CliHelper.write(seqNumber + ". " + er.getTitle());
                    counter++;
                }
            }

            CliHelper.newLine();
            final String selectedSeqNumber = CliHelper.getInput(
                    "Select event to link to the task request:",
                    validInputs);

            EventRequest eventRequest = eventRequests.get(selectedSeqNumber);

            return eventRequest;
        }
    }

    private User selectAssignee()
    {
        // Display list of department sub team employees

        CliHelper.newLine();
        CliHelper.write("Assign the task to an employee");

        Map<String, User> users = new HashMap<String, User>();
        ArrayList<String> validInputs = new ArrayList<>();
        int counter = 1;

        for (User u: userService.getAllUsers())
        {
            if(AppData.loggedInUser.role == User.Role.ProductionManager)
            {
                if(u.role == User.Role.ProductionDepartmentSubTeamEmployee)
                {
                    String seqNumber = Integer.toString(counter);

                    users.put(seqNumber, u);
                    validInputs.add(seqNumber);

                    CliHelper.write(seqNumber + ". " + u.email);
                    counter++;
                }
            }
            else if(AppData.loggedInUser.role == User.Role.ServiceDepartmentManager)
            {
                if(u.role == User.Role.ServiceDepartmentSubTeamEmployee)
                {
                    String seqNumber = Integer.toString(counter);

                    users.put(seqNumber, u);
                    validInputs.add(seqNumber);

                    CliHelper.write(seqNumber + ". " + u.email);
                    counter++;
                }
            }
        }

        CliHelper.newLine();
        final String selectedSeqNumber = CliHelper.getInput(
                "Select user to assign to the task request:",
                validInputs);

        User user = users.get(selectedSeqNumber);

        return user;
    }

    private void printTaskRequest(TaskRequest tr)
    {
        CliHelper.newLine();

        StringBuilder sb = new StringBuilder();
        sb.append("Title:\t\t\t\t\t\t" + tr.getTitle() + System.getProperty("line.separator"));
        sb.append("Description:\t\t\t\t" + tr.getDescription() + System.getProperty("line.separator"));
        sb.append("Priority:\t\t\t\t\t" + tr.getPriority().toString() + System.getProperty("line.separator"));
        sb.append("Event request:\t\t\t" + tr.getEventRequest().getTitle() + System.getProperty("line.separator"));
        sb.append("Assignee:\t\t\t\t" + tr.getAssignee().email + System.getProperty("line.separator"));
        sb.append("Created by:\t\t\t\t" + tr.getCreatedByUser().email + System.getProperty("line.separator"));

        CliHelper.write(sb.toString());
    }

    private void back()
    {
        this.previousController.displayPage();
    }
}