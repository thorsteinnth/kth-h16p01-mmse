package com.thorsteinnth.kth.mmse.sepcli.Test;

import com.thorsteinnth.kth.mmse.sepcli.Domain.Client;
import com.thorsteinnth.kth.mmse.sepcli.Repository.ClientRepository;
import com.thorsteinnth.kth.mmse.sepcli.Service.ClientService;

import java.util.ArrayList;

public class ClientTest
{
    public static ClientService getService()
    {
        return new ClientService(new ClientRepository());
    }

    public static boolean testCreateClient()
    {
        Client client = new Client("1", "TestClient", "Test address", "test@test.com", "12345678");

        //The first client that we create should have Id = 1
        Client createClient = getService().createClient(
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
        ClientService service = getService();

        Client client1 = service.createClient(
                "TestClient1",
                "Test address 1",
                "test1@test.com",
                "12345678"
        );

        Client client2 = service.createClient(
                "TestClient2",
                "Test address 2",
                "test2@test.com",
                "22345678"
        );

        Client getClient2 = service.getClientById("2");

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

    public static boolean testGetAllClients()
    {
        ClientService service = getService();

        //Create three clients
        ArrayList<Client> clients = new ArrayList<Client>();

        Client client1 = service.createClient(
                "TestClient1",
                "Test address 1",
                "test1@test.com",
                "12345678"
        );
        clients.add(client1);

        Client client2 = service.createClient(
                "TestClient2",
                "Test address 2",
                "test2@test.com",
                "22345678"
        );
        clients.add(client2);

        Client client3 = service.createClient(
                "TestClient3",
                "Test address 3",
                "test3@test.com",
                "32345678"
        );
        clients.add(client3);

        ArrayList<Client> getAllClients = service.getAllClients();

        try
        {
            assert clients.size() == getAllClients.size();

            int cnt = 0;

            for(Client c: clients)
            {
                Client getClient = getAllClients.get(cnt);
                assert getClient.equals(c);

                cnt++;
            }

            return true;
        }
        catch (AssertionError ae)
        {
            System.out.println("testGetAllClients() - clients not equal to the getAllClients");
            return false;
        }
    }
}
