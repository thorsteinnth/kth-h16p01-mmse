package com.thorsteinnth.kth.mmse.sepcli.Controllers;

import com.thorsteinnth.kth.mmse.sepcli.CliHelper;
import com.thorsteinnth.kth.mmse.sepcli.Domain.FinancialRequest;
import com.thorsteinnth.kth.mmse.sepcli.Domain.RecruitmentRequest;
import com.thorsteinnth.kth.mmse.sepcli.Domain.User;
import com.thorsteinnth.kth.mmse.sepcli.Repository.RecruitmentRequestRepository;
import com.thorsteinnth.kth.mmse.sepcli.Repository.RequestEnvelopeRepository;
import com.thorsteinnth.kth.mmse.sepcli.Repository.UserRepository;
import com.thorsteinnth.kth.mmse.sepcli.Service.RecruitmentRequestService;
import com.thorsteinnth.kth.mmse.sepcli.Service.RequestMailService;
import com.thorsteinnth.kth.mmse.sepcli.Service.UserService;

import java.util.ArrayList;

public class RecruitmentRequestController extends BaseController
{
    private BaseController previousController;
    private UserService userService;
    private RequestMailService requestMailService;

    public RecruitmentRequestController(BaseController previousController)
    {
        this.previousController = previousController;
        this.userService = new UserService(new UserRepository());
        this.requestMailService = new RequestMailService(new RequestEnvelopeRepository());
    }

    public void displayPage()
    {
        CliHelper.newLine();
        CliHelper.write("This is the create recruitment request page");
        CliHelper.write("Please select one of the following operations:");
        CliHelper.write("1. Create recruitment request");
        CliHelper.write("2. Back");
        ArrayList<String> validInputs = new ArrayList<String>();
        validInputs.add("1");
        validInputs.add("2");

        String input = CliHelper.getInput("Select an operation (1-2)", validInputs);

        if (input.equals("1"))
            createRecruitmentRequest();
        else if (input.equals("2"))
            back();
        else
            System.out.println("ERROR: Unknown command");
    }

    private void createRecruitmentRequest()
    {
        CliHelper.newLine();
        CliHelper.write("Create recruitment request");

        String jobTitle = CliHelper.getInput("Job title:");
        String jobDescription = CliHelper.getInput("Job description:");
        String requirements = CliHelper.getInput("Applicant requirements:");

        ArrayList<String> validContractTypeInputs = new ArrayList<>();
        validContractTypeInputs.add("F");
        validContractTypeInputs.add("O");
        String contractType = CliHelper.getInput("Contract type (Full time (F)/Outsourcing (O)):", validContractTypeInputs);

        RecruitmentRequest.ContractType cType;

        if(contractType.equals("F"))
            cType = RecruitmentRequest.ContractType.FullTime;
        else
            cType = RecruitmentRequest.ContractType.Outsourcing;

        RecruitmentRequestService recruitmentRequestService = new RecruitmentRequestService(new RecruitmentRequestRepository());
        RecruitmentRequest rr = recruitmentRequestService.createRecruitmentRequest(
                jobTitle,
                jobDescription,
                requirements,
                cType
        );

        printRecruitmentRequest(rr);
        sendRequest(rr);

        displayPage();
    }

    private void sendRequest(RecruitmentRequest rr)
    {
        CliHelper.newLine();
        CliHelper.write("Send event request");
        CliHelper.newLine();

        if (userService.getAllUsers().isEmpty())
        {
            // NOTE: Should never happen
            CliHelper.write("ERROR: No users in system");
            return;
        }
        else
        {
            ArrayList<String> validInputs = new ArrayList<>();
            ArrayList<String> emailList = new ArrayList<>();

            int i = 1;
            for (User user : userService.getAllUsers())
            {
                if(user.role == User.Role.SeniorHRManager)
                {
                    CliHelper.write(
                            Integer.toString(i)
                                    + ".\t"
                                    + user.email
                    );
                    validInputs.add(Integer.toString(i));
                    emailList.add(user.email);
                    i++;
                }
            }

            if(emailList.size() == 0)
            {
                // NOTE: should never happen
                CliHelper.write("ERRIR: No financial manager in the system");
                return;
            }

            CliHelper.newLine();
            String selectedNumber = CliHelper.getInput(
                    "Select a user to send the request to:",
                    validInputs);

            String selectedEmail = emailList.get(Integer.parseInt(selectedNumber)-1);
            User recipient = userService.getUserByEmail(selectedEmail);

            if (recipient == null)
            {
                CliHelper.write("ERROR: Could not find user with email: " + selectedEmail);
                return;
            }

            this.requestMailService.sendRequest(rr, recipient);

            CliHelper.newLine();
            CliHelper.write("Request sent to: " + recipient.email);
        }
    }

    private void printRecruitmentRequest(RecruitmentRequest rr)
    {
        CliHelper.newLine();
        CliHelper.write(rr.toDisplayString());
    }

    private void back()
    {
        this.previousController.displayPage();
    }
}
