package com.thorsteinnth.kth.mmse.sepcli.Domain;

public class Client
{
    public String id;
    public String name;
    public String address;
    public String email;
    public String phoneNumber;

    public Client(String id, String name, String address, String email, String phoneNumber)
    {
        this.id = id;
        this.name = name;
        this.address = address;
        this.email = email;
        this.phoneNumber = phoneNumber;
    }

    public String toStringShort()
    {
        return id + ". " + name;
    }

    @Override
    public String toString()
    {
        return "Client{" +
                "id='" + id + '\'' +
                "name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", email='" + email + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                '}';
    }
}
