package com.thorsteinnth.kth.mmse.sepcli.Test;

import com.thorsteinnth.kth.mmse.sepcli.AppData;
import com.thorsteinnth.kth.mmse.sepcli.Domain.RecruitmentRequest;
import com.thorsteinnth.kth.mmse.sepcli.Repository.RecruitmentRequestRepository;
import com.thorsteinnth.kth.mmse.sepcli.Repository.UserRepository;
import com.thorsteinnth.kth.mmse.sepcli.Service.RecruitmentRequestService;
import com.thorsteinnth.kth.mmse.sepcli.Service.UserService;

public class RecruitmentRequestTest
{
    public static RecruitmentRequestService getService()
    {
        return new RecruitmentRequestService(new RecruitmentRequestRepository());
    }

    public static boolean testCreateRecruitmentRequest()
    {
        RecruitmentRequestService service = getService();

        UserService userService = new UserService(new UserRepository());
        userService.addInitialUsers();
        userService.login("natalie@sep.se", "natalie123");

        String testJobTitle = "Test job title";
        String testJobDescription = "Test job description";
        String testRequirements = "Test requirements";
        RecruitmentRequest.ContractType testContractType = RecruitmentRequest.ContractType.FullTime;

        RecruitmentRequest rr = service.createRecruitmentRequest(
                testJobTitle,
                testJobDescription,
                testRequirements,
                testContractType
        );

        try
        {
            assert rr.getJobTitle().equals(testJobTitle)
                    && rr.getJobDescription().equals(testJobDescription)
                    && rr.getRequirements().equals(testRequirements)
                    && rr.getContractType().equals(testContractType)
                    && rr.getRequestingDepartment().equals(RecruitmentRequest.RequestingDepartment.ServiceDepartment)
                    && rr.getCreatedByUser().equals(AppData.loggedInUser);

            return true;
        }
        catch (AssertionError ae)
        {
            System.out.println("testCreateRecruitmentRequest() - failed");
            return false;
        }
    }
}
