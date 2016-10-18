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

        // Options to work with request

        ArrayList<UIOperation> operations = new ArrayList<>();
        int operationCount = 0;

        // Add comments
        // Check if the current request type supports comments and if the current user has authority to add them
        if (requestTypeSupportsComments(request) && userHasAddCommentRightsForRequest(request))
        {
            UIOperation.Command addComment = () -> addCommentToRequest(request, requestEnvelope);
            operations.add(new UIOperation(++operationCount, "Add comment", addComment));
        }

        // Update status
        if (userHasUpdateStatusRightsForRequest(request))
        {
            UIOperation.Command updateStatus = () -> updateRequestStatus(request);
            operations.add(new UIOperation(++operationCount, "Update request status", updateStatus));
        }

        // Mark as resolved
        // All users are allowed to mark the requests they receive as resolved
        // We trust that the user will not mark a request as resolved prematurely
        // (e.g. if he should continue an event request workflow)
        UIOperation.Command markAsResolved = () -> markAsResolved(requestEnvelope);
        operations.add(new UIOperation(++operationCount, "Mark as resolved", markAsResolved));

        //region Event request workflow specific actions

        if (request instanceof EventRequest)
        {
            if (AppData.loggedInUser.role == User.Role.SeniorCustomerServiceOfficer)
            {
                EventRequest eventRequest = (EventRequest) request;

                if (eventRequest.getStatus() == EventRequest.Status.Pending)
                {
                    // Senior customer service officer should forward the request to financial manager
                    // (if she does not reject it)
                    UIOperation.Command forwardEventRequestToFinancialManager =
                            () -> forwardEventRequestToFinancialManager(eventRequest, requestEnvelope);
                    operations.add(new UIOperation(
                            ++operationCount,
                            "Forward event request to financial manager",
                            forwardEventRequestToFinancialManager)
                    );
                }
                else if (eventRequest.getStatus() == EventRequest.Status.Approved)
                {
                    // Event request has been approved by admin dept manager
                    // Should send to production managers and staff managers
                    UIOperation.Command forwardApprovedEventRequestToStaffManagers =
                            () -> forwardApprovedEventRequestToStaffManagers(eventRequest, requestEnvelope);
                    operations.add(new UIOperation(
                            ++operationCount,
                            "Forward event request to staff managers",
                            forwardApprovedEventRequestToStaffManagers)
                    );
                }
                else
                {
                    // Do nothing
                }
            }
            else if (AppData.loggedInUser.role == User.Role.FinancialManager)
            {
                // The financial manager should write his feedback (add comment)
                // and then forward the request to the administration department manager
                UIOperation.Command addCommentAndForwardToAdministrationDeptManager =
                        () -> addCommentToEventRequestAndForwardToAdministrationDeptManager(
                                (EventRequest)request, requestEnvelope
                        );
                operations.add(new UIOperation(
                        ++operationCount,
                        "Add comment and forward to administration department manager",
                        addCommentAndForwardToAdministrationDeptManager)
                );
            }
            else if (AppData.loggedInUser.role == User.Role.AdministrationDepartmentManager)
            {
                // The administration department manager should accept or reject and then forward
                // the request back to the senior customer service officer
                UIOperation.Command approveOrRejectAndForwardToSCSO =
                        () -> approveOrRejectEventRequestAndForwardToSeniorCustomerServiceOfficer(
                                (EventRequest)request, requestEnvelope);
                operations.add(new UIOperation(
                        ++operationCount,
                        "Approve or reject and forward to senior customer service officer",
                        approveOrRejectAndForwardToSCSO)
                );
            }
            else if (AppData.loggedInUser.role == User.Role.ProductionManager
                    || AppData.loggedInUser.role == User.Role.ServiceDepartmentManager)
            {
                // Staff managers (production manager and servicedepartmentmanager have the
                // chance to update the event request's workflow status
                UIOperation.Command updateEventRequestWorkflowStatus =
                        () -> updateEventRequestWorkflowStatus((EventRequest)request);
                operations.add(new UIOperation(
                        ++operationCount,
                        "Update workflow status",
                        updateEventRequestWorkflowStatus)
                );
            }
            else
            {
                // Do nothing
            }
        }

        //endregion

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

            // Send task request automatically back to the user that created the task request
            // i.e. the staff manager

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

    //region Update request status

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

    //endregion

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

    //region Event request workflow specific actions

    /**
     * Send event request to some user with the selected role.
     * @param request
     * @param recipientRole
     */
    private void sendEventRequest(EventRequest request, User.Role recipientRole)
    {
        CliHelper.newLine();
        CliHelper.write("Send event request");
        CliHelper.newLine();

        UserService userService = new UserService(new UserRepository());

        if (userService.getAllUsersByRole(recipientRole).isEmpty())
        {
            CliHelper.write("ERROR: No users with role " + User.getRoleDisplayString(recipientRole) + " in system.");
            return;
        }
        else
        {
            ArrayList<String> validInputs = new ArrayList<>();
            ArrayList<String> emailList = new ArrayList<>();

            int i = 1;
            for (User user : userService.getAllUsersByRole(recipientRole))
            {
                CliHelper.write(
                        Integer.toString(i)
                                + ".\t"
                                + user.toDisplayString()
                );
                validInputs.add(Integer.toString(i));
                emailList.add(user.email);
                i++;
            }

            CliHelper.newLine();
            String selectedNumber = CliHelper.getInput(
                    "Select a user to send the request to:",
                    validInputs);

            String selectedEmail = emailList.get(Integer.parseInt(selectedNumber)-1);
            User recipient = userService.getUserByEmail(selectedEmail);

            if (recipient == null)
            {
                CliHelper.write("ERROR: Could not find user with email: " + selectedEmail);
                return;
            }

            this.requestMailService.sendRequest(request, recipient);

            CliHelper.newLine();
            CliHelper.write("Request sent to: " + recipient.toDisplayString());
        }
    }

    /**
     * Senior customer service officer forwards an event request to the financial manager
     * @param request
     * @param envelope
     */
    private void forwardEventRequestToFinancialManager(EventRequest request, RequestEnvelope envelope)
    {
        sendEventRequest(request, User.Role.FinancialManager);
        markAsResolved(envelope);
    }

    /**
     * Financial manager adds a comment to the event request and then forwards it to the admin dept manager
     * @param request
     * @param envelope
     */
    private void addCommentToEventRequestAndForwardToAdministrationDeptManager(EventRequest request, RequestEnvelope envelope)
    {
        addCommentToRequest(request, envelope);
        sendEventRequest(request, User.Role.AdministrationDepartmentManager);
        markAsResolved(envelope);
    }

    /**
     * Administration department manager approves or rejects the event request and then sends it to the senior
     * customer service officer
     * @param request
     * @param envelope
     */
    private void approveOrRejectEventRequestAndForwardToSeniorCustomerServiceOfficer(EventRequest request, RequestEnvelope envelope)
    {
        approveOrRejectEventRequest(request);
        sendEventRequest(request, User.Role.SeniorCustomerServiceOfficer);
        markAsResolved(envelope);
    }

    /**
     * Administration department manager approves or rejects an event request
     * @param request
     */
    private void approveOrRejectEventRequest(EventRequest request)
    {
        EventRequestService service = new EventRequestService(new EventRequestRepository());

        CliHelper.newLine();

        ArrayList<String> validInputs = new ArrayList<>();
        validInputs.add("A");
        validInputs.add("R");

        String decision = CliHelper.getInput("Approve (A) or reject (R) event request:", validInputs);

        if (decision.equals("A"))
        {
            boolean success = service.updateEventRequestStatus(request, EventRequest.Status.Approved);
            if (success)
                CliHelper.write("Event request status updated. New status: " + request.getStatus().toString());
            else
                CliHelper.write("Could not update event request status");
        }
        else if (decision.equals("R"))
        {
            boolean success = service.updateEventRequestStatus(request, EventRequest.Status.Rejected);
            if (success)
                CliHelper.write("Event request status updated. New status: " + request.getStatus().toString());
            else
                CliHelper.write("Could not update event request status");
        }
        else
        {
            System.out.println("ERROR: RequestManagementController.approveOrRejectEventRequest() - invalid decision");
        }
    }

    /**
     * Senior customer service officer sends the approved event request to the staff managers
     * (production manager and service department manager)
     * @param request
     * @param envelope
     */
    private void forwardApprovedEventRequestToStaffManagers(EventRequest request, RequestEnvelope envelope)
    {
        sendEventRequest(request, User.Role.ProductionManager);
        sendEventRequest(request, User.Role.ServiceDepartmentManager);
        markAsResolved(envelope);
    }

    /**
     * Staff managers (production manager & service department managers) can update the event request's
     * workflow status
     * @param request
     */
    private void updateEventRequestWorkflowStatus(EventRequest request)
    {
        CliHelper.newLine();
        CliHelper.write("Update workflow status. Current status: " + request.getWorkflowStatus());

        ArrayList<String> validInputs = new ArrayList<>();
        ArrayList<EventRequest.WorkflowStatus> wfStatuses = new ArrayList<>();

        int i = 1;
        for (EventRequest.WorkflowStatus wfStatus : EventRequest.WorkflowStatus.values())
        {
            wfStatuses.add(wfStatus);
            validInputs.add(Integer.toString(i));
            CliHelper.write(i + ". " + wfStatus);
            i++;
        }

        String input = CliHelper.getInput("Please choose a workflow status:", validInputs);

        EventRequest.WorkflowStatus selectedStatus = wfStatuses.get(Integer.parseInt(input)-1);

        EventRequestService service = new EventRequestService(new EventRequestRepository());
        boolean success = service.updateWorkflowStatus(request, selectedStatus);
        if (success)
        {
            CliHelper.newLine();
            CliHelper.write("Workflow status updated");
            CliHelper.newLine();
            CliHelper.write(request.toDisplayString());
        }
        else
        {
            CliHelper.write("Unable to update workflow status");
        }
    }

    //endregion

    //region Access control helpers

    /**
     * Check if the user has add comment rights to request
     * @param request
     * @return true if he has add comment rights, false otherwise
     */
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

    /**
     * Check if the user has either approve or reject rights to the request, for UI purposes
     * When the user then attempts to update the status we check if he can update the status to that value
     * he is selecting
     * NOTE:
     * For event requests we only ever let the user approve or reject, other status
     * updates are done automatically
     * @param request
     * @return true if he has some update rights, false if not
     */
    private boolean userHasUpdateStatusRightsForRequest(Request request)
    {
        if (request instanceof EventRequest)
        {
            if (AccessControlService.hasAccess(AccessFunction.approveEventRequest)
                    || AccessControlService.hasAccess(AccessFunction.rejectEventRequest))
                return true;
            else
                return false;
        }
        else if (request instanceof TaskRequest)
        {
            if (AccessControlService.hasAccess(AccessFunction.approveTaskRequest)
                    || AccessControlService.hasAccess(AccessFunction.rejectTaskRequest))
                return true;
            else
                return false;
        }
        else if (request instanceof FinancialRequest)
        {
            if (AccessControlService.hasAccess(AccessFunction.approveFinancialRequest)
                    || AccessControlService.hasAccess(AccessFunction.rejectFinancialRequest))
                return true;
            else
                return false;
        }
        else if (request instanceof RecruitmentRequest)
        {
            if (AccessControlService.hasAccess(AccessFunction.approveRecruitmentRequest)
                    || AccessControlService.hasAccess(AccessFunction.rejectRecruitmentRequest))
                return true;
            else
                return false;
        }
        else
        {
            return false;
        }
    }

    //endregion
}
