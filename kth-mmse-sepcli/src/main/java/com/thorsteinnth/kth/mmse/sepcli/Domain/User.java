package com.thorsteinnth.kth.mmse.sepcli.Domain;

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
        ProductionDepartmentSubTeamEmployee,
        ServiceDepartmentSubTeamEmployee,
        GeneralUser
    }

    public static String getRoleDisplayString(Role role)
    {
        switch (role)
        {
            case FinancialManager:
                return "Financial manager";
            case CustomerServiceOfficer:
                return "Customer service officer";
            case SeniorCustomerServiceOfficer:
                return "Senior customer service officer";
            case AdministrationDepartmentManager:
                return "Administration department manager";
            case HRAssistant:
                return "HR assistant";
            case SeniorHRManager:
                return "Senior HR manager";
            case MarketingOfficer:
                return "Marketing officer";
            case MarketingAssistant:
                return "Marketing assistant";
            case ProductionManager:
                return "Production manager";
            case ServiceDepartmentManager:
                return "Service department manager";
            case VicePresident:
                return "Vice president";
            case ProductionDepartmentSubTeamEmployee:
                return "Production department subteam employee";
            case ServiceDepartmentSubTeamEmployee:
                return "Service department subteam employee";
            case GeneralUser:
                return "General user";
            default:
                return "Unknown role";
        }
    }

    public String toDisplayString()
    {
        return this.email + " (" + getRoleDisplayString(this.role) + ")";
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
