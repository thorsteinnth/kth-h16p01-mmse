package com.thorsteinnth.kth.mmse.sepcli.Controllers;

import com.thorsteinnth.kth.mmse.sepcli.AppData;
import com.thorsteinnth.kth.mmse.sepcli.CliHelper;
import com.thorsteinnth.kth.mmse.sepcli.Repository.RequestEnvelopeRepository;
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
        CliHelper.write("This is the homepage for user: " + AppData.loggedInUser.email);

        ArrayList<UIOperation> operations = buildUIOperationList();
        ArrayList<String> validInputs = new ArrayList<String>();

        CliHelper.write("Please select one of the following operations:");

        for (UIOperation op : operations)
        {
            CliHelper.write(op.toDisplayString());
            validInputs.add(Integer.toString(op.getNumber()));
        }

        String input = CliHelper.getInput("Select an operation", validInputs);

        UIOperation selectedOperation = UIOperation.getUIOperationWithNumberFromList(
                Integer.parseInt(input),
                operations
        );

        if (selectedOperation == null)
        {
            CliHelper.write("ERROR: Unknown operation selected");
            displayPage();
        }
        else
        {
            selectedOperation.executeCommand();
        }
    }

    private boolean userHasIncomingRequests()
    {
        if (!this.requestMailService.getRequestEnvelopesForUser(AppData.loggedInUser).isEmpty())
            return true;
        else
            return false;
    }

    private ArrayList<UIOperation> buildUIOperationList()
    {
        ArrayList<UIOperation> operations = new ArrayList<>();

        UIOperation.Command clientManagement = () -> clientManagement();
        UIOperation.Command createEventRequest = () -> createEventRequest();
        UIOperation.Command logout = () -> logout();
        UIOperation.Command quit = () -> closeApplication();

        operations.add(new UIOperation(1, "Client management", clientManagement));
        operations.add(new UIOperation(2, "Create event request", createEventRequest));
        operations.add(new UIOperation(3, "Logout", logout));
        operations.add(new UIOperation(4, "Quit", quit));

        return operations;
    }

    private void clientManagement()
    {
        new ClientController(this).displayPage();
    }

    private void createEventRequest()
    {
        new EventRequestController(this).displayPage();
    }

    private void logout()
    {
        new LoginController().logout();
    }

    private void closeApplication()
    {
        // Do nothing, process will terminate
        CliHelper.write("Closing application");
    }
}
