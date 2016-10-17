package com.thorsteinnth.kth.mmse.sepcli.Service;

import com.thorsteinnth.kth.mmse.sepcli.AppData;
import com.thorsteinnth.kth.mmse.sepcli.Domain.FinancialRequest;
import com.thorsteinnth.kth.mmse.sepcli.Domain.RecruitmentRequest;
import com.thorsteinnth.kth.mmse.sepcli.Domain.User;
import com.thorsteinnth.kth.mmse.sepcli.Repository.IRecruitmentRequestRepository;
import com.thorsteinnth.kth.mmse.sepcli.Repository.RecruitmentRequestRepository;

import java.util.ArrayList;

public class RecruitmentRequestService
{
    private IRecruitmentRequestRepository repository;

    public RecruitmentRequestService(IRecruitmentRequestRepository repository)
    {
        this.repository = repository;
    }

    public RecruitmentRequest createRecruitmentRequest(
            String jobTitle,
            String jobDescription,
            String requirements,
            RecruitmentRequest.ContractType contractType
    )
    {
        RecruitmentRequest.RequestingDepartment requestingDepartment = RecruitmentRequest.RequestingDepartment.Unknown;

        if(AppData.loggedInUser.role == User.Role.ProductionManager)
            requestingDepartment = RecruitmentRequest.RequestingDepartment.ProductionDepartment;
        else if(AppData.loggedInUser.role == User.Role.ServiceDepartmentManager)
            requestingDepartment = RecruitmentRequest.RequestingDepartment.ServiceDepartment;

        RecruitmentRequest recruitmentRequest = new RecruitmentRequest(
                jobTitle,
                jobDescription,
                requirements,
                contractType,
                requestingDepartment,
                AppData.loggedInUser
        );

        this.repository.addRecruitmentRequest(recruitmentRequest);

        return recruitmentRequest;
    }

    public ArrayList<RecruitmentRequest> getAllRecruitmentRequests()
    {
        return this.repository.getAllRecruitmentRequests();
    }

    public void updateRecruitmentRequestStatus(RecruitmentRequest request, RecruitmentRequest.Status newStatus)
    {
        // TODO Status progression rules
        // NOTE:
        // Not saving this to repo since our implementation is only using an
        // in-memory data store
        request.setStatus(newStatus);
    }
}
