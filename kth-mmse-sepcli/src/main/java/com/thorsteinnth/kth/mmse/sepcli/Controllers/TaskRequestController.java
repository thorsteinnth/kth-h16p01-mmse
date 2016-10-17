package com.thorsteinnth.kth.mmse.sepcli.Controllers;

import com.thorsteinnth.kth.mmse.sepcli.AppData;
import com.thorsteinnth.kth.mmse.sepcli.CliHelper;
import com.thorsteinnth.kth.mmse.sepcli.Domain.EventRequest;
import com.thorsteinnth.kth.mmse.sepcli.Domain.TaskRequest;
import com.thorsteinnth.kth.mmse.sepcli.Domain.User;
import com.thorsteinnth.kth.mmse.sepcli.Repository.EventRequestRepository;
import com.thorsteinnth.kth.mmse.sepcli.Repository.RequestEnvelopeRepository;
import com.thorsteinnth.kth.mmse.sepcli.Repository.TaskRequestRepository;
import com.thorsteinnth.kth.mmse.sepcli.Repository.UserRepository;
import com.thorsteinnth.kth.mmse.sepcli.Service.EventRequestService;
import com.thorsteinnth.kth.mmse.sepcli.Service.RequestMailService;
import com.thorsteinnth.kth.mmse.sepcli.Service.TaskRequestService;
import com.thorsteinnth.kth.mmse.sepcli.Service.UserService;
import com.thorsteinnth.kth.mmse.sepcli.UIOperation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class TaskRequestController extends BaseController
{
    private BaseController previousController;
    private EventRequestService eventRequestService;
    private UserService userService;
    private RequestMailService requestMailService;

    public TaskRequestController(BaseController previousController)
    {
        this.previousController = previousController;
        this.eventRequestService = new EventRequestService(new EventRequestRepository());
        this.userService = new UserService(new UserRepository());
        this.requestMailService = new RequestMailService(new RequestEnvelopeRepository());
    }

    public void displayPage()
    {
        CliHelper.newLine();
        CliHelper.write("This is the create task request page");

        ArrayList<UIOperation> operations = buildUIOperationList();
        UIOperation.Command onSelectedOperationError = () -> displayPage();
        displayUIOperations(operations, onSelectedOperationError);
    }

    private ArrayList<UIOperation> buildUIOperationList()
    {
        ArrayList<UIOperation> operations = new ArrayList<>();
        int operationCount = 0;

        UIOperation.Command createTaskRequest = () -> createTaskRequest();
        operations.add(new UIOperation(++operationCount, "Create task request", createTaskRequest));

        UIOperation.Command back = () -> back();
        operations.add(new UIOperation(++operationCount, "Back", back));

        return operations;
    }

    // TODO Restrict access to specific roles
    private void createTaskRequest()
    {
        CliHelper.newLine();
        CliHelper.write("Create task request");

        if (eventRequestService.getAllEventRequests().isEmpty())
        {
            CliHelper.write("There are no event requests in the system. Create an event request in order to add task request.");
            displayPage();
            return;
        }

        EventRequest eventRequest = selectEventRequest();
        if (eventRequest == null)
        {
            displayPage();
            return;
        }

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
        sendRequest(tr);

        displayPage();
    }

    private void sendRequest(TaskRequest tr)
    {
        CliHelper.newLine();
        this.requestMailService.sendRequest(tr, tr.getAssignee());

        CliHelper.write("Request sent to assignee: " + tr.getAssignee().email);
    }

    private EventRequest selectEventRequest()
    {
        // Display list of all open or in progress event requests for user to choose from

        CliHelper.newLine();
        CliHelper.write("Link task request to event");

        if (eventRequestService.getAllEventRequests().isEmpty())
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
                if(er.getStatus() == EventRequest.Status.Pending)
                {
                    String seqNumber = Integer.toString(counter);

                    eventRequests.put(seqNumber, er);
                    validInputs.add(seqNumber);

                    CliHelper.write(seqNumber + ". " + er.getTitle());
                    counter++;
                }
            }

            if (eventRequests.size() != 0)
            {
                CliHelper.newLine();
                final String selectedSeqNumber = CliHelper.getInput(
                        "Select event to link to the task request:",
                        validInputs);

                EventRequest eventRequest = eventRequests.get(selectedSeqNumber);
                return eventRequest;
            }
            else
            {
                CliHelper.write("There are no event requests with status pending");
                return null;
            }
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
            // NOTE:
            // Only production managers and service department managers will
            // be allowed in here
            if (AppData.loggedInUser.role == User.Role.ProductionManager)
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
            else if (AppData.loggedInUser.role == User.Role.ServiceDepartmentManager)
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
        CliHelper.write(tr.toDisplayString());
    }

    private void back()
    {
        this.previousController.displayPage();
    }
}
