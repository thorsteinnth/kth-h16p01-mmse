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
        Client newClient = new Client(this.repository.getNextId(), name, address, email, phoneNumber);
        this.repository.addClient(newClient);
        return newClient;
    }

    public Client getClientById(String id)
    {
        return this.repository.getClientById(Integer.parseInt(id));
    }

    public ArrayList<Client> getAllClients()
    {
        return this.repository.getAllClients();
    }

    public void createInitialClients()
    {
        createClient("Fannar", "Bergshamra 99", "fannar@kth.se", "5556666");
        createClient("Gretar", "Norra Djurgardstaden 999", "gretar@kth.se", "5556666");
    }
}
