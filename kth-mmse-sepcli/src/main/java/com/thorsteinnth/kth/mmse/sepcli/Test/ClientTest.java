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

    public static boolean testGetClientById()
    {
        //Client 1
        Client client1 = new ClientService(new ClientRepository()).createClient(
                "TestClient1",
                "Test address 1",
                "test1@test.com",
                "12345678"
        );

        Client client2 = new ClientService(new ClientRepository()).createClient(
                "TestClient2",
                "Test address 2",
                "test2@test.com",
                "22345678"
        );

        Client getClient2 = new ClientService(new ClientRepository()).getClientById("2");

        try
        {
            assert client2.equals(getClient2);
            return true;
        }
        catch (AssertionError ae)
        {
            System.out.println("testGetClientByID() - client2 not equal to the getClient2");
            return false;
        }
    }
}
