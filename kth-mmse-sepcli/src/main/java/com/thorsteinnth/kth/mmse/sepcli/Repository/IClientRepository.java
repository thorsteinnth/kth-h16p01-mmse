package com.thorsteinnth.kth.mmse.sepcli.Repository;

import com.thorsteinnth.kth.mmse.sepcli.Domain.Client;

import java.util.ArrayList;

public interface IClientRepository
{
    void addClient(Client client);
    Client getClientById(String id);
    ArrayList<Client> getAllClients();
}
