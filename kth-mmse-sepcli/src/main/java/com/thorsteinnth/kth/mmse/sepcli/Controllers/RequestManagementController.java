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
        //RequestMailTest.uiTestHelperCreateAndSendEventRequests();

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
            UIOperation.Command showRequest = () -> showIncomingRequest(re.getRequest());
            operations.add(
                    new UIOperation(
                            ++operationCount,
                            getRequestTypeString(re.getRequest()) + " from " + re.getSender().email,
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

    private void showIncomingRequest(Request request)
    {
        CliHelper.newLine();
        CliHelper.write(request.toDisplayString());
    }

    private void back()
    {
        this.previousController.displayPage();
    }
}
