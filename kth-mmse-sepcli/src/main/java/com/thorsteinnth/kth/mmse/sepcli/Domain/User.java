package com.thorsteinnth.kth.mmse.sepcli.Domain;

/**
 * Created by tts on 14/10/2016.
 */
public class User
{
    public String email;
    public String password;
    public Role role;

    public User(String email, String password, Role role)
    {
        this.email = email;
        this.password = password;
        this.role = role;
    }

    public enum Role
    {
        FinancialManager,
        CustomerServiceOfficer,
        SeniorCustomerServiceOfficer,
        AdministrationDepartmentManager,
        HRAssistant,
        HRManager,
        GeneralUser
    }

    @Override
    public String toString() {
        return "User{" +
                "email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", role=" + role +
                '}';
    }
}
