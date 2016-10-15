package com.thorsteinnth.kth.mmse.sepcli.Repository;

import com.thorsteinnth.kth.mmse.sepcli.AppData;
import com.thorsteinnth.kth.mmse.sepcli.Domain.Client;

import java.util.ArrayList;

public class ClientRepository implements IClientRepository
{
    public ClientRepository()
    {}

    @Override
    public int getNextId()
    {
        // NOTE: We will never delete any clients in our implementation, so size+1 works
        return getAllClients().size() + 1;
    }

    public void addClient(Client client)
    {
        AppData.clients.add(client);
    }

    public Client getClientById(int id)
    {
        Client client = null;

        for (Client c : AppData.clients)
        {
            if (c.id == id)
            {
                client = c;
                break;
            }
        }

        return client;
    }

    public ArrayList<Client> getAllClients()
    {
        return AppData.clients;
    }
}
