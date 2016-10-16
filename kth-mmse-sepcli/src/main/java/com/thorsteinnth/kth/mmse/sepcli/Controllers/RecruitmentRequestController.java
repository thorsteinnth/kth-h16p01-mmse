package com.thorsteinnth.kth.mmse.sepcli.Controllers;

import com.thorsteinnth.kth.mmse.sepcli.CliHelper;
import com.thorsteinnth.kth.mmse.sepcli.Domain.FinancialRequest;
import com.thorsteinnth.kth.mmse.sepcli.Domain.RecruitmentRequest;
import com.thorsteinnth.kth.mmse.sepcli.Repository.RecruitmentRequestRepository;
import com.thorsteinnth.kth.mmse.sepcli.Service.RecruitmentRequestService;

import java.util.ArrayList;

public class RecruitmentRequestController extends BaseController
{
    private BaseController previousController;

    public RecruitmentRequestController(BaseController previousController)
    {
        this.previousController = previousController;
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
        String requirements = CliHelper.getInput("Requirements:");

        ArrayList<String> validContractTypeInputs = new ArrayList<>();
        validContractTypeInputs.add("F");
        validContractTypeInputs.add("O");
        String contractType = CliHelper.getInput("Contract type (Full time (F)/Outsourcing (O):", validContractTypeInputs);

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
        //TODO : send request

        displayPage();
    }

    private void printRecruitmentRequest(RecruitmentRequest rr)
    {
        CliHelper.newLine();

        StringBuilder sb = new StringBuilder();
        sb.append("ID:\t\t\t\t\t\t" + rr.getId() + System.getProperty("line.separator"));
        sb.append("Title:\t\t\t\t" + rr.getJobTitle() + System.getProperty("line.separator"));
        sb.append("Description:\t\t\t" + rr.getJobDescription() + System.getProperty("line.separator"));
        sb.append("Requirements:\t\t\t" + rr.getRequirements() + System.getProperty("line.separator"));
        sb.append("Contract type:\t\t" + rr.getContractType().toString() + System.getProperty("line.separator"));
        sb.append("Req department:\t" + rr.getRequestingDepartment().toString() + System.getProperty("line.separator"));
        sb.append("Status:\t\t\t\t\t" + rr.getStatus() + System.getProperty("line.separator"));
        sb.append("Created by:\t\t" + rr.getCreatedByUser().email + System.getProperty("line.separator"));

        CliHelper.write(sb.toString());
    }

    private void back()
    {
        this.previousController.displayPage();
    }
}
