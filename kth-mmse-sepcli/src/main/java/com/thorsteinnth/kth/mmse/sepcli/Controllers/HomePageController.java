package com.thorsteinnth.kth.mmse.sepcli.Controllers;

import com.thorsteinnth.kth.mmse.sepcli.AppData;
import com.thorsteinnth.kth.mmse.sepcli.CliHelper;
import com.thorsteinnth.kth.mmse.sepcli.Domain.AccessFunction;
import com.thorsteinnth.kth.mmse.sepcli.Repository.RequestEnvelopeRepository;
import com.thorsteinnth.kth.mmse.sepcli.Service.AccessControlService;
import com.thorsteinnth.kth.mmse.sepcli.Service.RequestMailService;
import com.thorsteinnth.kth.mmse.sepcli.UIOperation;

import java.util.ArrayList;

public class HomePageController extends BaseController
{
    private RequestMailService requestMailService;

    public HomePageController()
    {
        this.requestMailService = new RequestMailService(new RequestEnvelopeRepository());
    }

    public void displayPage()
    {
        CliHelper.newLine();
        CliHelper.write("This is the homepage for user: " + AppData.loggedInUser.toDisplayString());

        if (requestMailService.userHasIncomingRequests())
            CliHelper.write("You have incoming requests - go to the request management page to view them");

        ArrayList<UIOperation> operations = buildUIOperationList();
        UIOperation.Command onSelectedOperationError = () -> displayPage();
        displayUIOperations(operations, onSelectedOperationError);
    }

    private ArrayList<UIOperation> buildUIOperationList()
    {
        ArrayList<UIOperation> operations = new ArrayList<>();
        int operationCount = 0;

        if (AccessControlService.hasAccess(AccessFunction.requestManagement))
        {
            UIOperation.Command requestManagement = () -> requestManagement();
            operations.add(new UIOperation(++operationCount, "Request management", requestManagement));
        }

        if (AccessControlService.hasAccess(AccessFunction.clientManagement))
        {
            UIOperation.Command clientManagement = () -> clientManagement();
            operations.add(new UIOperation(++operationCount, "Client management", clientManagement));
        }

        UIOperation.Command logout = () -> logout();
        operations.add(new UIOperation(++operationCount, "Logout", logout));

        UIOperation.Command quit = () -> closeApplication();
        operations.add(new UIOperation(++operationCount, "Quit", quit));

        return operations;
    }

    private void requestManagement()
    {
        new RequestManagementController(this).displayPage();
    }

    private void clientManagement()
    {
        new ClientController(this).displayPage();
    }

    private void logout()
    {
        new LoginController().logout();
    }

    private void closeApplication()
    {
        CliHelper.write("Closing application");
    }
}
