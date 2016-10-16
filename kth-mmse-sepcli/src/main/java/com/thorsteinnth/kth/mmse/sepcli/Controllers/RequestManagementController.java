package com.thorsteinnth.kth.mmse.sepcli.Controllers;

import com.thorsteinnth.kth.mmse.sepcli.AppData;
import com.thorsteinnth.kth.mmse.sepcli.CliHelper;
import com.thorsteinnth.kth.mmse.sepcli.Domain.*;
import com.thorsteinnth.kth.mmse.sepcli.Repository.RequestEnvelopeRepository;
import com.thorsteinnth.kth.mmse.sepcli.Service.RequestMailService;
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

        UIOperation.Command browseIncomingRequests = () -> browseIncomingRequests();
        operations.add(new UIOperation(++operationCount, "Browse incoming requests", browseIncomingRequests));

        UIOperation.Command back = () -> back();
        operations.add(new UIOperation(++operationCount, "Back", back));

        return operations;
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
                            getRequestTypeString(re.getRequest()) + " from " + re.getSender().email,
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

        UIOperation.Command back = () -> { /* Do nothing */ };
        operations.add(new UIOperation(++operationCount, "Back", back));

        UIOperation.Command onSelectedOperationError = () -> CliHelper.write("ERROR: Selected operation error");
        displayUIOperations(operations, onSelectedOperationError);
    }

    private void addCommentToRequest(Request request)
    {
        // NOTE: If we were using a DB we would need to save to DB after adding comments here

        CliHelper.newLine();
        String comment = CliHelper.getInput("Add comment:");
        RequestComment requestComment = new RequestComment(AppData.loggedInUser, comment);

        if (request instanceof EventRequest)
        {
            EventRequest eventRequest = (EventRequest)request;
            eventRequest.addComment(requestComment);
            CliHelper.write("Comment added");
        }
        else if (request instanceof TaskRequest)
        {
            TaskRequest taskRequest = (TaskRequest)request;
            taskRequest.addComment(requestComment);
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

    private void back()
    {
        this.previousController.displayPage();
    }
}
