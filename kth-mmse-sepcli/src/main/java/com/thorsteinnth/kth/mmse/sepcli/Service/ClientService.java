package com.thorsteinnth.kth.mmse.sepcli.Service;

import com.thorsteinnth.kth.mmse.sepcli.Domain.Client;
import com.thorsteinnth.kth.mmse.sepcli.Repository.IClientRepository;

import java.util.ArrayList;

public class ClientService
{
    private IClientRepository repository;

    public ClientService(IClientRepository repository)
    {
        this.repository = repository;
    }

    public Client createClient(String name, String address, String email, String phoneNumber)
    {
        String clientId = Integer.toString(this.repository.getAllClients().size() + 1);
        Client newClient = new Client(clientId, name, address, email, phoneNumber);
        this.repository.addClient(newClient);
        return newClient;
    }

    public Client getClientById(String id)
    {
        return this.repository.getClientById(id);
    }

    public ArrayList<Client> getAllClients()
    {
        return this.repository.getAllClients();
    }
}
