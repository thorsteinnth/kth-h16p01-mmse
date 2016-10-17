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

        // Browse incoming requests
        // Everybody that can access this page should have the ability to browse incoming requests
        // They should not receive requests that they should not have access to anyway
        UIOperation.Command browseIncomingRequests = () -> browseIncomingRequests();
        operations.add(new UIOperation(
                ++operationCount,
                "Browse incoming requests ("
                        + this.requestMailService.getRequestEnvelopesForUser(AppData.loggedInUser).size()
                        + ")",
                browseIncomingRequests)
        );

        // Event requests
        if (AccessControlService.hasAccess(AccessFunction.eventRequestManagement))
        {
            UIOperation.Command eventRequestManagement = () -> eventRequestManagement();
            operations.add(new UIOperation(++operationCount, "Event requests", eventRequestManagement));
        }

        // Task requests
        if (AccessControlService.hasAccess(AccessFunction.taskRequestManagement))
        {
            UIOperation.Command taskRequestManagement = () -> taskRequestManagement();
            operations.add(new UIOperation(++operationCount, "Task requests", taskRequestManagement));
        }

        // Financial requests
        if (AccessControlService.hasAccess(AccessFunction.financialRequestManagement))
        {
            UIOperation.Command financialRequestManagement = () -> financialRequestManagement();
            operations.add(new UIOperation(++operationCount, "Financial requests", financialRequestManagement));
        }

        // Recruitment requests
        if (AccessControlService.hasAccess(AccessFunction.recruitmentRequestManagement))
        {
            UIOperation.Command recruitmentRequestManagement = () -> recruitmentRequestManagement();
            operations.add(new UIOperation(++operationCount, "Recruitment requests", recruitmentRequestManagement));
        }

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
        // NOTE: If a user receives a request, we assume he is allowed to view it.
        // We do not do access rights checks here, only when the user is going to perform
        // some operation on the request

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
            UIOperation.Command showRequest = () -> viewIncomingRequest(re);
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

    private void viewIncomingRequest(RequestEnvelope requestEnvelope)
    {
        // NOTE:
        // Everybody can get in here.
        // While a user should never receive a request that he does not have authority to do anything with
        // we still have to check if he has access privileges for the type of request/operation he is trying
        // to do with his incoming request

        Request request = requestEnvelope.getRequest();

        CliHelper.newLine();
        CliHelper.write(request.toDisplayString());

        // Allow user to select and work with request

        ArrayList<UIOperation> operations = new ArrayList<>();
        int operationCount = 0;

        // Check if the current task request type supports comments and if the current user has authority to add them
        if (requestTypeSupportsComments(request) && userHasAddCommentRightsForRequest(request))
        {
            UIOperation.Command addComment = () -> addCommentToRequest(request, requestEnvelope);
            operations.add(new UIOperation(++operationCount, "Add comment", addComment));
        }

        // We allow all users to attempt to update a status here, the operation will fail if they don't have the right
        UIOperation.Command updateStatus = () -> updateRequestStatus(request);
        operations.add(new UIOperation(++operationCount, "Update request status", updateStatus));

        // All users are allowed to mark the requests they receive as resolved
        UIOperation.Command markAsResolved = () -> markAsResolved(requestEnvelope);
        operations.add(new UIOperation(++operationCount, "Mark as resolved", markAsResolved));

        UIOperation.Command back = () -> { /* Do nothing */ };
        operations.add(new UIOperation(++operationCount, "Back", back));

        UIOperation.Command onSelectedOperationError = () -> CliHelper.write("ERROR: Selected operation error");
        displayUIOperations(operations, onSelectedOperationError);
    }

    private void addCommentToRequest(Request request, RequestEnvelope requestEnvelope)
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

            CliHelper.newLine();

            this.requestMailService.removeRequestEnvelope(requestEnvelope);
            this.requestMailService.sendRequest(taskRequest, taskRequest.getCreatedByUser());

            CliHelper.write("Request sent with comment back to: " + taskRequest.getCreatedByUser().email);
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
            UIOperation.Command selectStatus = () ->
            {
                EventRequestService service = new EventRequestService(new EventRequestRepository());
                boolean success = service.updateEventRequestStatus(eventRequest, status);
                if (success)
                    CliHelper.write("Status updated. New status: " + eventRequest.getStatus());
                else
                    CliHelper.write("Unable to update status");
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
            UIOperation.Command selectStatus = () ->
            {
                TaskRequestService service = new TaskRequestService(new TaskRequestRepository());
                boolean success = service.updateTaskRequestStatus(taskRequest, status);
                if (success)
                    CliHelper.write("Status updated. New status: " + taskRequest.getStatus());
                else
                    CliHelper.write("Unable to update status");
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
            UIOperation.Command selectStatus = () ->
            {
                FinancialRequestService service = new FinancialRequestService(new FinancialRequestRepository());
                boolean success = service.updateFinancialRequestStatus(financialRequest, status);
                if (success)
                    CliHelper.write("Status updated. New status: " + financialRequest.getStatus());
                else
                    CliHelper.write("Unable to update status");
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
            UIOperation.Command selectStatus = () ->
            {
                RecruitmentRequestService service = new RecruitmentRequestService(new RecruitmentRequestRepository());
                boolean success = service.updateRecruitmentRequestStatus(recruitmentRequest, status);
                if (success)
                    CliHelper.write("Status updated. New status: " + recruitmentRequest.getStatus());
                else
                    CliHelper.write("Unable to update status");
            };
            operations.add(new UIOperation(++operationCount, status.toString(), selectStatus));
        }

        UIOperation.Command onSelectedOperationError = () ->
                CliHelper.write("ERROR: Selected operation error");
        displayUIOperations(operations, onSelectedOperationError);
    }

    private void markAsResolved(RequestEnvelope envelope)
    {
        CliHelper.newLine();
        CliHelper.write("Remove request from incoming queue");

        // Are you sure verification
        ArrayList<String> validInputs = new ArrayList<>();
        validInputs.add("Y");
        validInputs.add("N");
        String userIsSure = CliHelper.getInput("Are you sure? (Y/N)", validInputs);

        if (userIsSure.equals("Y"))
        {
            this.requestMailService.removeRequestEnvelope(envelope);
            CliHelper.write("Request removed from incoming queue");
        }
        else if (userIsSure.equals("N"))
        {
            // Do nothing
        }
        else
        {
            System.out.println("ERROR: Received invalid input from user");
        }
    }

    private void back()
    {
        this.previousController.displayPage();
    }

    // Access control helpers

    private boolean userHasAddCommentRightsForRequest(Request request)
    {
        // User should have edit rights for this

        if (request instanceof EventRequest)
        {
            if (AccessControlService.hasAccess(AccessFunction.editEventRequest))
                return true;
            else
                return false;
        }
        else if (request instanceof TaskRequest)
        {
            if (AccessControlService.hasAccess(AccessFunction.editTaskRequest))
                return true;
            else
                return false;
        }
        else
        {
            System.out.println("ERROR: RequestManagementController.userHasAddCommentRightsForRequest() - unknown request type");
            return false;
        }
    }
}
