package com.thorsteinnth.kth.mmse.sepcli.Controllers;

import com.thorsteinnth.kth.mmse.sepcli.AppData;
import com.thorsteinnth.kth.mmse.sepcli.CliHandler;
import com.thorsteinnth.kth.mmse.sepcli.Domain.Client;
import com.thorsteinnth.kth.mmse.sepcli.Service.ClientService;

import java.util.ArrayList;

class ClientController extends BaseController
{
    private BaseController previousController;

    ClientController(BaseController previousController)
    {
        this.previousController = previousController;
    }

    public void displayPage()
    {
        CliHandler.write("This is the client management page");
        CliHandler.write("Please select one of the following operations:");
        CliHandler.write("1. Create client record");
        CliHandler.write("2. View client records");
        CliHandler.write("3. Back");
        ArrayList<String> validInputs = new ArrayList<String>();
        validInputs.add("1");
        validInputs.add("2");
        validInputs.add("3");

        String input = CliHandler.getInput("Select an operation (1-3)", validInputs);

        if (input.equals("1"))
            createClient();
        else if (input.equals("2"))
            viewClientRecords();
        else if (input.equals("3"))
            back();
        else
            System.out.println("ERROR: Unknown command");

    }

    // TODO Restrict access to specific roles
    private void createClient()
    {
        CliHandler.write("Create client");
        String name = CliHandler.getInput("Name:");
        String address = CliHandler.getInput("Address:");
        String email = CliHandler.getInput("Email:");
        String phoneNumber = CliHandler.getInput("Phone number:");

        Client newClient = new ClientService().createClient(name, address, email, phoneNumber);

        AppData.clients.add(newClient);

        CliHandler.write("Client created: " + newClient.toString());
    }

    private void viewClientRecords()
    {
        CliHandler.write("Create client");
    }

    private void back()
    {
        this.previousController.displayPage();
    }
}
