package com.thorsteinnth.kth.mmse.sepcli.Test;

import com.thorsteinnth.kth.mmse.sepcli.Domain.Client;
import com.thorsteinnth.kth.mmse.sepcli.Repository.ClientRepository;
import com.thorsteinnth.kth.mmse.sepcli.Service.ClientService;

public class ClientTest
{
    public static boolean testCreateClient()
    {
        Client client = new Client("1", "TestClient", "Test address", "test@test.com", "12345678");

        //The first client that we create should have Id = 1
        Client createClient = new ClientService(new ClientRepository()).createClient(
                "TestClient",
                "Test address",
                "test@test.com",
                "12345678"
                );

        try
        {
            assert client.equals(createClient);
            return true;
        }
        catch (AssertionError ae)
        {
            System.out.println("testCreateClient() - client not equal to the service created client");
            return false;
        }

    }
}
