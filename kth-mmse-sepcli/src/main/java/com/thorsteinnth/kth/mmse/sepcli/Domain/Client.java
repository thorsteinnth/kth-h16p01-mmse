package com.thorsteinnth.kth.mmse.sepcli.Domain;

public class Client
{
    public final int id;
    public String name;
    public String address;
    public String email;
    public String phoneNumber;

    public Client(int id, String name, String address, String email, String phoneNumber)
    {
        this.id = id;
        this.name = name;
        this.address = address;
        this.email = email;
        this.phoneNumber = phoneNumber;
    }

    public String toDisplayStringShort()
    {
        return id + ". " + name;
    }

    public String toDisplayStringLong()
    {
        StringBuilder sb = new StringBuilder();
        sb.append("ID:\t\t\t\t" + id + System.getProperty("line.separator"));
        sb.append("Name:\t\t\t" + name + System.getProperty("line.separator"));
        sb.append("Address:\t\t" + address + System.getProperty("line.separator"));
        sb.append("Email:\t\t\t" + email + System.getProperty("line.separator"));
        sb.append("Phone number:\t" + phoneNumber + System.getProperty("line.separator"));
        return sb.toString();
    }

    @Override
    public String toString() {
        return "Client{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", email='" + email + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Client client = (Client) o;

        if (id != client.id) return false;
        if (name != null ? !name.equals(client.name) : client.name != null) return false;
        if (address != null ? !address.equals(client.address) : client.address != null) return false;
        if (email != null ? !email.equals(client.email) : client.email != null) return false;
        return phoneNumber != null ? phoneNumber.equals(client.phoneNumber) : client.phoneNumber == null;

    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (address != null ? address.hashCode() : 0);
        result = 31 * result + (email != null ? email.hashCode() : 0);
        result = 31 * result + (phoneNumber != null ? phoneNumber.hashCode() : 0);
        return result;
    }
}
