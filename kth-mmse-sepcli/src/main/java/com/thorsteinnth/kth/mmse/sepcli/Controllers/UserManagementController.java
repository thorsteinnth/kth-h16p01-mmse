package com.thorsteinnth.kth.mmse.sepcli.Controllers;

import com.thorsteinnth.kth.mmse.sepcli.AppData;
import com.thorsteinnth.kth.mmse.sepcli.CliHelper;
import com.thorsteinnth.kth.mmse.sepcli.Domain.AccessFunction;
import com.thorsteinnth.kth.mmse.sepcli.Domain.User;
import com.thorsteinnth.kth.mmse.sepcli.Repository.UserRepository;
import com.thorsteinnth.kth.mmse.sepcli.Service.AccessControlService;
import com.thorsteinnth.kth.mmse.sepcli.Service.UserService;
import com.thorsteinnth.kth.mmse.sepcli.UIOperation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.function.BooleanSupplier;

public class UserManagementController extends BaseController
{
    private  BaseController previousController;
    private UserService userService;

    public UserManagementController(BaseController previousController)
    {
        this.previousController = previousController;
        this.userService = new UserService(new UserRepository());
    }

    public void displayPage()
    {
        CliHelper.newLine();
        CliHelper.write("This is the user management page");

        ArrayList<UIOperation> operations = buildUIOperationList();
        UIOperation.Command onSelectedOperationError = () -> displayPage();
        displayUIOperations(operations, onSelectedOperationError);
    }

    private ArrayList<UIOperation> buildUIOperationList()
    {
        ArrayList<UIOperation> operations = new ArrayList<>();
        int operationCount = 0;

        if (AccessControlService.hasAccess(AccessFunction.createUser))
        {
            UIOperation.Command createUser = () -> createUser();
            operations.add(new UIOperation(++operationCount, "Create new user", createUser));
        }

        if (AccessControlService.hasAccess(AccessFunction.editUser))
        {
            UIOperation.Command editUser = () -> editUser();
            operations.add(new UIOperation(++operationCount, "Edit user", editUser));
        }

        if (AccessControlService.hasAccess(AccessFunction.deleteUser))
        {
            UIOperation.Command deleteUser = () -> deleteUser();
            operations.add(new UIOperation(++operationCount, "Delete user", deleteUser));
        }

        UIOperation.Command back = () -> back();
        operations.add(new UIOperation(++operationCount, "Back", back));

        return operations;
    }

    private void createUser()
    {
        CliHelper.newLine();
        CliHelper.write("Create user");
        String email = CliHelper.getInputEmptyStringBanned("Email:");
        String password = CliHelper.getInputEmptyStringBanned("Password:");
        User.Role role = selectRoleForUser("Select role for the new user");

        User user = new UserService(new UserRepository()).createUser(email, password, role);
        Boolean added = new UserService(new UserRepository()).addUser(user);

        if(added)
        {
            CliHelper.newLine();
            CliHelper.write("User created");
            CliHelper.write(user.toDisplayString());
        }
        else
        {
            CliHelper.newLine();
            CliHelper.write("Could not create user!");
        }

        displayPage();
    }

    private void editUser()
    {
        CliHelper.newLine();
        CliHelper.write("Edit user");

        User user = selectUser("Select user to edit");
        CliHelper.write("Now editing user:" + user.toDisplayString());

        ArrayList<UIOperation> operations = new ArrayList<>();
        int operationCount = 0;

        UIOperation.Command changeEmail = () -> changeEmail(user);
        operations.add(new UIOperation(++operationCount, "Change email", changeEmail));

        UIOperation.Command changePassword = () -> changePassword(user);
        operations.add(new UIOperation(++operationCount, "Change password", changePassword));

        UIOperation.Command changeRole = () -> changeRole(user);
        operations.add(new UIOperation(++operationCount, "Change role", changeRole));

        UIOperation.Command back = () -> displayPage();
        operations.add(new UIOperation(++operationCount, "Back", back));

        UIOperation.Command onSelectedOperationError = () -> displayPage();
        displayUIOperations(operations, onSelectedOperationError);
    }

    private void deleteUser()
    {
        CliHelper.newLine();
        CliHelper.write("Delete user");

        User user = selectUser("Select user to delete");

        ArrayList<String> validInput = new ArrayList<>();
        validInput.add("Y");
        validInput.add("N");
        String input = CliHelper.getInput("Are you sure you want to delete user " + user.toDisplayString() + "?" + " (Y/N)", validInput);

        if(input.equals("Y"))
        {
            this.userService.deleteUser(user);
            CliHelper.write("User has been deleted!");
            displayPage();
        }
        else
        {
            displayPage();
        }
    }

    private User.Role selectRoleForUser(String message)
    {
        CliHelper.newLine();
        CliHelper.write(message);

        Map<String, User.Role> roles = new HashMap<>();
        ArrayList<String> validRoleInputs = new ArrayList<>();
        int counter = 1;

        for(User.Role r: User.Role.values())
        {
            String seqNumber = Integer.toString(counter);

            roles.put(seqNumber, r);
            validRoleInputs.add(seqNumber);

            CliHelper.write(seqNumber + ". " + r.toString());
            counter++;
        }

        final String selectedRole = CliHelper.getInput(
                message + ":",
                validRoleInputs
        );

        User.Role role = roles.get(selectedRole);
        return role;
    }

    private User selectUser(String message)
    {
        CliHelper.newLine();
        CliHelper.write("Select user");

        Map<String, User> users = new HashMap<String, User>();
        ArrayList<String> validInputs = new ArrayList<>();
        int counter = 1;

        for (User u: userService.getAllUsers())
        {
            if(u.role != User.Role.Admin)
            {
                String seqNumber = Integer.toString(counter);

                users.put(seqNumber, u);
                validInputs.add(seqNumber);

                CliHelper.write(seqNumber + ". " + u.toDisplayString());
                counter++;
            }
        }

        CliHelper.newLine();
        final String selectedSeqNumber = CliHelper.getInput(
                message,
                validInputs);

        User user = users.get(selectedSeqNumber);

        return user;
    }

    private void changeEmail(User user)
    {
        CliHelper.newLine();
        CliHelper.write("Change email for " + user.toDisplayString());

        String newEmail = CliHelper.getInputEmptyStringBanned("New email:");

        User updatedUser = this.userService.updateUser(user, newEmail, user.password, user.role);

        CliHelper.write(updatedUser.toDisplayString() + " has been updated!");
        displayPage();
    }

    private void changePassword(User user)
    {
        CliHelper.newLine();
        CliHelper.write("Change password for " + user.toDisplayString());

        String newPassword = CliHelper.getInputEmptyStringBanned("New password:");

        User updatedUser = this.userService.updateUser(user, user.email, newPassword, user.role);

        CliHelper.write(updatedUser.toDisplayString() + " has been updated!");
        displayPage();
    }

    private void changeRole(User user)
    {
        CliHelper.newLine();
        CliHelper.write("Change role for " + user.toDisplayString());

        User.Role newRole = selectRoleForUser("Select new role");

        User updatedUser = this.userService.updateUser(user, user.email, user.password, newRole);

        CliHelper.write(updatedUser.toDisplayString() + " has been updated!");
        displayPage();
    }

    private void back()
    {
        this.previousController.displayPage();
    }
}
