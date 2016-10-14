package com.thorsteinnth.kth.mmse.sepcli.Domain;

public class Client
{
    public String name;
    public String address;
    public String email;
    public String phoneNumber;

    public Client(String name, String address, String email, String phoneNumber)
    {
        this.name = name;
        this.address = address;
        this.email = email;
        this.phoneNumber = phoneNumber;
    }

    @Override
    public String toString() {
        return "Client{" +
                "name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", email='" + email + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                '}';
    }
}
