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
        SeniorHRManager,
        MarketingOfficer,
        MarketingAssistant,
        ProductionManager,
        ServiceDepartmentManager,
        VicePresident,
        SubTeamEmployee,
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

    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;

        if (email != null ? !email.equals(user.email) : user.email != null) return false;
        if (password != null ? !password.equals(user.password) : user.password != null) return false;
        return role == user.role;
    }

    @Override
    public int hashCode() {
        int result = email != null ? email.hashCode() : 0;
        result = 31 * result + (password != null ? password.hashCode() : 0);
        result = 31 * result + role.hashCode();
        return result;
    }
}
