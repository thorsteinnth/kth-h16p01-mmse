package com.thorsteinnth.kth.mmse.sepcli.Controllers;

import com.thorsteinnth.kth.mmse.sepcli.AppData;
import com.thorsteinnth.kth.mmse.sepcli.CliHandler;
import com.thorsteinnth.kth.mmse.sepcli.Domain.Client;
import com.thorsteinnth.kth.mmse.sepcli.Service.ClientService;

public class ClientController
{
    // TODO Restrict access to specific roles
    public void createClient()
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
}
