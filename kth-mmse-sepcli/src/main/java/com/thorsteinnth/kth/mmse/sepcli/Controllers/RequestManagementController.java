package com.thorsteinnth.kth.mmse.sepcli.Controllers;

import com.thorsteinnth.kth.mmse.sepcli.AppData;
import com.thorsteinnth.kth.mmse.sepcli.CliHelper;
import com.thorsteinnth.kth.mmse.sepcli.Domain.*;
import com.thorsteinnth.kth.mmse.sepcli.Repository.*;
import com.thorsteinnth.kth.mmse.sepcli.Service.*;
import com.thorsteinnth.kth.mmse.sepcli.Test.RequestMailTest;
import com.thorsteinnth.kth.mmse.sepcli.UIOperation;

import java.util.ArrayList;

public class RequestManagementController extends BaseController
{
    private BaseController previousController;
    private RequestMailService requestMailService;

    public RequestManagementController(BaseController previousController)
    {
        // TODO Delete when done testing
        //RequestMailTest.uiTestHelperCreateAndSendRequests();

        this.previousController = previousController;
        this.requestMailService = new RequestMailService(new RequestEnvelopeRepository());
    }

    @Override
    public void displayPage()
    {
        CliHelper.newLine();
        CliHelper.write("This is the request management page");

        ArrayList<UIOperation> operations = buildUIOperationList();
        UIOperation.Command onSelectedOperationError = () -> {
            CliHelper.write("ERROR: Selected operation error");
            displayPage();
        };
        displayUIOperations(operations, onSelectedOperationError);
    }

    private ArrayList<UIOperation> buildUIOperationList()
    {
        ArrayList<UIOperation> operations = new ArrayList<>();
        int operationCount = 0;

        // TODO Check access control and other stuff for what operations he should have access to

        // Browse incoming requests
        UIOperation.Command browseIncomingRequests = () -> browseIncomingRequests();
        operations.add(new UIOperation(++operationCount, "Browse incoming requests", browseIncomingRequests));

        // Event requests
        UIOperation.Command eventRequestManagement = () -> eventRequestManagement();
        operations.add(new UIOperation(++operationCount, "Event requests", eventRequestManagement));

        // Task requests
        UIOperation.Command taskRequestManagement = () -> taskRequestManagement();
        operations.add(new UIOperation(++operationCount, "Task requests", taskRequestManagement));

        // Financial requests
        UIOperation.Command financialRequestManagement = () -> financialRequestManagement();
        operations.add(new UIOperation(++operationCount, "Financial requests", financialRequestManagement));

        // Recruitment requests
        UIOperation.Command recruitmentRequestManagement = () -> recruitmentRequestManagement();
        operations.add(new UIOperation(++operationCount, "Recruitment requests", recruitmentRequestManagement));

        // Go back
        UIOperation.Command back = () -> back();
        operations.add(new UIOperation(++operationCount, "Back", back));

        return operations;
    }

    public void eventRequestManagement()
    {
        new EventRequestController(this).displayPage();
    }

    public void taskRequestManagement()
    {
        new TaskRequestController(this).displayPage();
    }

    public void financialRequestManagement()
    {
        new FinancialRequestController(this).displayPage();
    }

    public void recruitmentRequestManagement()
    {
        new RecruitmentRequestController(this).displayPage();
    }

    public void browseIncomingRequests()
    {
        CliHelper.newLine();

        if (!this.requestMailService.userHasIncomingRequests())
        {
            CliHelper.write("You do not have any incoming requests");
            displayPage();
            // NOTE:
            // Have to return here, otherwise this function will continue later, unexpectedly
            // when the new workflow we are starting with displayPage() has finished
            return;
        }

        CliHelper.write("Incoming requests");

        ArrayList<RequestEnvelope> incomingEnvelopes =
                requestMailService.getRequestEnvelopesForUser(AppData.loggedInUser);

        ArrayList<UIOperation> operations = new ArrayList<>();
        int operationCount = 0;

        for (RequestEnvelope re : incomingEnvelopes)
        {
            UIOperation.Command showRequest = () -> viewRequest(re.getRequest());
            operations.add(
                    new UIOperation(
                            ++operationCount,
                            "[" + re.getCreatedDateTime().toString() + "] "
                            + re.getSender().email + ": "
                            + getRequestTypeString(re.getRequest()),
                            showRequest
                    )
            );
        }

        // TODO Add back option

        UIOperation.Command onSelectedOperationError = () -> {
            CliHelper.write("ERROR: Selected operation error");
            displayPage();
        };
        displayUIOperations(operations, onSelectedOperationError);

        displayPage();
    }

    private String getRequestTypeString(Request request)
    {
        if (request instanceof EventRequest)
            return "Event request";
        else if (request instanceof TaskRequest)
            return "Task request";
        else if (request instanceof FinancialRequest)
            return "Financial request";
        else if (request instanceof RecruitmentRequest)
            return "Recruitment request";
        else
            return "Unknown request type";
    }

    private void viewRequest(Request request)
    {
        CliHelper.newLine();
        CliHelper.write(request.toDisplayString());

        // Allow user to select and work with request

        ArrayList<UIOperation> operations = new ArrayList<>();
        int operationCount = 0;

        if (requestTypeSupportsComments(request))
        {
            UIOperation.Command addComment = () -> addCommentToRequest(request);
            operations.add(new UIOperation(++operationCount, "Add comment", addComment));
        }

        UIOperation.Command updateStatus = () -> updateRequestStatus(request);
        operations.add(new UIOperation(++operationCount, "Update request status", updateStatus));

        UIOperation.Command back = () -> { /* Do nothing */ };
        operations.add(new UIOperation(++operationCount, "Back", back));

        UIOperation.Command onSelectedOperationError = () -> CliHelper.write("ERROR: Selected operation error");
        displayUIOperations(operations, onSelectedOperationError);
    }

    private void addCommentToRequest(Request request)
    {
        CliHelper.newLine();
        String comment = CliHelper.getInput("Add comment:");

        if (request instanceof EventRequest)
        {
            EventRequestService service = new EventRequestService(new EventRequestRepository());
            EventRequest eventRequest = (EventRequest)request;
            service.addCommentToEventRequest(eventRequest, comment);
            CliHelper.write("Comment added");
        }
        else if (request instanceof TaskRequest)
        {
            TaskRequestService service = new TaskRequestService(new TaskRequestRepository());
            TaskRequest taskRequest = (TaskRequest)request;
            service.addCommentToTaskRequest(taskRequest, comment);
            CliHelper.write("Comment added");
        }
        else
        {
            System.out.println("ERROR: RequestManagementController.addCommentToRequest() - unknown request type");
        }
    }

    private boolean requestTypeSupportsComments(Request request)
    {
        if (request instanceof EventRequest)
            return true;
        else if (request instanceof TaskRequest)
            return true;
        else
            return false;
    }

    private void updateRequestStatus(Request request)
    {
        if (request instanceof EventRequest)
            updateEventRequestStatus((EventRequest)request);
        else if (request instanceof TaskRequest)
            updateTaskRequestStatus((TaskRequest)request);
        else if (request instanceof FinancialRequest)
            updateFinancialRequestStatus((FinancialRequest)request);
        else if (request instanceof RecruitmentRequest)
            updateRecruitmentRequestStatus((RecruitmentRequest)request);
        else
            System.out.println(
                    "ERROR: RequestManagementController.updateRequestStatus() - unknown request type");
    }

    private void updateEventRequestStatus(EventRequest eventRequest)
    {
        CliHelper.newLine();
        CliHelper.write("Current status is: " + eventRequest.getStatus());
        CliHelper.write("Select new status:");

        ArrayList<UIOperation> operations = new ArrayList<>();
        int operationCount = 0;

        // TODO Don't allow the user to go to just any status - progression rules
        for (EventRequest.Status status : EventRequest.Status.values())
        {
            UIOperation.Command selectStatus = () -> {
                EventRequestService service = new EventRequestService(new EventRequestRepository());
                service.updateEventRequestStatus(eventRequest, status);
                CliHelper.write("Status updated. New status: " + eventRequest.getStatus());
            };
            operations.add(new UIOperation(++operationCount, status.toString(), selectStatus));
        }

        UIOperation.Command onSelectedOperationError = () ->
                CliHelper.write("ERROR: Selected operation error");
        displayUIOperations(operations, onSelectedOperationError);
    }

    private void updateTaskRequestStatus(TaskRequest taskRequest)
    {
        CliHelper.newLine();
        CliHelper.write("Current status is: " + taskRequest.getStatus());
        CliHelper.write("Select new status:");

        ArrayList<UIOperation> operations = new ArrayList<>();
        int operationCount = 0;

        // TODO Don't allow the user to go to just any status - progression rules
        for (TaskRequest.Status status : TaskRequest.Status.values())
        {
            UIOperation.Command selectStatus = () -> {
                TaskRequestService service = new TaskRequestService(new TaskRequestRepository());
                service.updateTaskRequestStatus(taskRequest, status);
                CliHelper.write("Status updated. New status: " + taskRequest.getStatus());
            };
            operations.add(new UIOperation(++operationCount, status.toString(), selectStatus));
        }

        UIOperation.Command onSelectedOperationError = () ->
                CliHelper.write("ERROR: Selected operation error");
        displayUIOperations(operations, onSelectedOperationError);
    }

    private void updateFinancialRequestStatus(FinancialRequest financialRequest)
    {
        CliHelper.newLine();
        CliHelper.write("Current status is: " + financialRequest.getStatus());
        CliHelper.write("Select new status:");

        ArrayList<UIOperation> operations = new ArrayList<>();
        int operationCount = 0;

        // TODO Don't allow the user to go to just any status - progression rules
        for (FinancialRequest.Status status : FinancialRequest.Status.values())
        {
            UIOperation.Command selectStatus = () -> {
                FinancialRequestService service = new FinancialRequestService(new FinancialRequestRepository());
                service.updateFinancialRequestStatus(financialRequest, status);
                CliHelper.write("Status updated. New status: " + financialRequest.getStatus());
            };
            operations.add(new UIOperation(++operationCount, status.toString(), selectStatus));
        }

        UIOperation.Command onSelectedOperationError = () ->
                CliHelper.write("ERROR: Selected operation error");
        displayUIOperations(operations, onSelectedOperationError);
    }

    private void updateRecruitmentRequestStatus(RecruitmentRequest recruitmentRequest)
    {
        CliHelper.newLine();
        CliHelper.write("Current status is: " + recruitmentRequest.getStatus());
        CliHelper.write("Select new status:");

        ArrayList<UIOperation> operations = new ArrayList<>();
        int operationCount = 0;

        // TODO Don't allow the user to go to just any status - progression rules
        for (RecruitmentRequest.Status status : RecruitmentRequest.Status.values())
        {
            UIOperation.Command selectStatus = () -> {
                RecruitmentRequestService service = new RecruitmentRequestService(new RecruitmentRequestRepository());
                service.updateRecruitmentRequestStatus(recruitmentRequest, status);
                CliHelper.write("Status updated. New status: " + recruitmentRequest.getStatus());
            };
            operations.add(new UIOperation(++operationCount, status.toString(), selectStatus));
        }

        UIOperation.Command onSelectedOperationError = () ->
                CliHelper.write("ERROR: Selected operation error");
        displayUIOperations(operations, onSelectedOperationError);
    }

    private void back()
    {
        this.previousController.displayPage();
    }
}
