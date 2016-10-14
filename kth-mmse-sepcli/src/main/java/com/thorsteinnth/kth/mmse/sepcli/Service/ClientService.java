package com.thorsteinnth.kth.mmse.sepcli.Service;

import com.thorsteinnth.kth.mmse.sepcli.Domain.Client;

public class ClientService
{
    public Client createClient(String name, String address, String email, String phoneNumber)
    {
        // TODO Get from repository
        return new Client(name, address, email, phoneNumber);
    }
}
