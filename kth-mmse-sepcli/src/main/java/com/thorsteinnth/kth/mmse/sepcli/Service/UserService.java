package com.thorsteinnth.kth.mmse.sepcli.Service;

import com.thorsteinnth.kth.mmse.sepcli.AppData;
import com.thorsteinnth.kth.mmse.sepcli.Domain.User;
import com.thorsteinnth.kth.mmse.sepcli.Repository.IUserRepository;

import java.util.ArrayList;

public class UserService
{
    private IUserRepository repository;
    public UserService(IUserRepository repository)
    {
        this.repository = repository;
    }

    public boolean login(String email, String password)
    {
        User user = getUserByEmail(email);

        if(user != null && user.password.equals(password))
        {
            AppData.loggedInUser = user;
            return true;
        }

        return false;
    }

    public void logout()
    {
        AppData.loggedInUser = null;
    }

    /**
     * Adds all the users that need to use the system
     */
    public void addInitialUsers()
    {
        addUser(createUser("sarah@sep.se", "sarah123", User.Role.CustomerServiceOfficer));
        addUser(createUser("janet@sep.se", "janet123", User.Role.SeniorCustomerServiceOfficer));
        addUser(createUser("alice@sep.se", "alice123", User.Role.FinancialManager));
        addUser(createUser("mike@sep.se", "mike123", User.Role.AdministrationDepartmentManager));
        addUser(createUser("maria@sep.se", "maria123", User.Role.HRAssistant));
        addUser(createUser("simon@sep.se", "simon123", User.Role.SeniorHRManager));
        addUser(createUser("david@sep.se", "david123", User.Role.MarketingOfficer));
        addUser(createUser("emma@sep.se", "emma123", User.Role.MarketingAssistant));
        addUser(createUser("jack@sep.se", "jack123", User.Role.ProductionManager));
        addUser(createUser("natalie@sep.se", "natalie123", User.Role.ServiceDepartmentManager));
        addUser(createUser("charlie@sep.se", "charlie123", User.Role.VicePresident));
    }

    public User createUser(String email, String password, User.Role role)
    {
        return new User(email, password, role);
    }

    public void deleteUser(User user)
    {
        this.repository.deleteUser(user);
    }

    public void addUser(User user)
    {
        this.repository.addUser(user);
    }

    public User getUserByEmail(String email)
    {
        return this.repository.getUserByEmail(email);
    }

    public ArrayList<User> getAllUsers()
    {
        return this.repository.getAllUsers();
    }

    public User updateUser(User user, String newEmail, String newPassword, User.Role newRole)
    {
        return this.repository.updateUser(user, newEmail, newPassword, newRole);
    }
}
