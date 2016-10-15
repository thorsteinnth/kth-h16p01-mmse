package com.thorsteinnth.kth.mmse.sepcli.Controllers;

import com.thorsteinnth.kth.mmse.sepcli.CliHelper;
import com.thorsteinnth.kth.mmse.sepcli.Domain.Client;
import com.thorsteinnth.kth.mmse.sepcli.Repository.ClientRepository;
import com.thorsteinnth.kth.mmse.sepcli.Service.ClientService;

import java.util.ArrayList;

class ClientController extends BaseController
{
    private BaseController previousController;
    private ClientService clientService;

    ClientController(BaseController previousController)
    {
        this.clientService = new ClientService(new ClientRepository());
        this.previousController = previousController;
    }

    public void displayPage()
    {
        CliHelper.newLine();
        CliHelper.write("This is the client management page");
        CliHelper.write("Please select one of the following operations:");
        CliHelper.write("1. Create client record");
        CliHelper.write("2. Browse client records");
        CliHelper.write("3. Back");
        ArrayList<String> validInputs = new ArrayList<String>();
        validInputs.add("1");
        validInputs.add("2");
        validInputs.add("3");

        String input = CliHelper.getInput("Select an operation (1-3)", validInputs);

        if (input.equals("1"))
            createClient();
        else if (input.equals("2"))
            browseClientRecords();
        else if (input.equals("3"))
            back();
        else
            System.out.println("ERROR: Unknown command");

    }

    // TODO Restrict access to specific roles
    private void createClient()
    {
        CliHelper.write("Create client");
        String name = CliHelper.getInput("Name:");
        String address = CliHelper.getInput("Address:");
        String email = CliHelper.getInput("Email:");
        String phoneNumber = CliHelper.getInput("Phone number:");

        Client newClient = new ClientService(new ClientRepository()).createClient(name, address, email, phoneNumber);

        CliHelper.write("Client created: " + newClient.toString());

        displayPage();
    }

    private void browseClientRecords()
    {
        CliHelper.newLine();
        CliHelper.write("Client records");

        if (clientService.getAllClients().isEmpty())
        {
            CliHelper.write("No client records in system");
        }
        else
        {
            for (Client client : clientService.getAllClients())
            {
                CliHelper.write(client.toStringShort());
            }

            viewClientRecord();
        }

        displayPage();
    }

    private void viewClientRecord()
    {
        CliHelper.newLine();

        ArrayList<String> validInputs = new ArrayList<String>();
        validInputs.add("0");

        for (Client client : clientService.getAllClients())
        {
            validInputs.add(Integer.toString(client.id));
        }

        final String selectedId = CliHelper.getInput(
                "Select a client ID to view details, or select 0 to go back",
                validInputs);

        if (selectedId.equals(0))
        {
            displayPage();
        }
        else
        {
            Client client = clientService.getClientById(selectedId);

            if (client == null)
            {
                CliHelper.write("ERROR: Could not find client with ID: " + selectedId);
            }
            else
            {
                printClientRecord(client);
            }
        }
    }

    private void printClientRecord(Client client)
    {
        CliHelper.newLine();

        StringBuilder sb = new StringBuilder();
        sb.append("ID:\t\t\t\t" + client.id + System.getProperty("line.separator"));
        sb.append("Name:\t\t\t" + client.name + System.getProperty("line.separator"));
        sb.append("Address:\t\t" + client.address + System.getProperty("line.separator"));
        sb.append("Email:\t\t\t" + client.email + System.getProperty("line.separator"));
        sb.append("Phone number:\t" + client.phoneNumber + System.getProperty("line.separator"));

        CliHelper.write(sb.toString());
    }

    private void back()
    {
        this.previousController.displayPage();
    }
}
