package com.thorsteinnth.kth.mmse.sepcli.Service;

import com.thorsteinnth.kth.mmse.sepcli.AppData;
import com.thorsteinnth.kth.mmse.sepcli.Domain.EventRequest;
import com.thorsteinnth.kth.mmse.sepcli.Domain.FinancialRequest;
import com.thorsteinnth.kth.mmse.sepcli.Domain.TaskRequest;
import com.thorsteinnth.kth.mmse.sepcli.Domain.User;
import com.thorsteinnth.kth.mmse.sepcli.Repository.IFinancialRequestRepository;

import java.math.BigDecimal;
import java.util.ArrayList;

public class FinancialRequestService
{
    private IFinancialRequestRepository repository;

    public FinancialRequestService(IFinancialRequestRepository repository)
    {
        this.repository = repository;
    }

    public FinancialRequest createFinancialRequest(
            String title,
            String reasonForBudgetAdjustment,
            BigDecimal requiredAmount,
            EventRequest eventRequest
    )
    {
        FinancialRequest.RequestingDepartment requestingDepartment = FinancialRequest.RequestingDepartment.Unknown;

        if(AppData.loggedInUser.role == User.Role.ProductionManager)
            requestingDepartment = FinancialRequest.RequestingDepartment.ProductionDepartment;
        else if(AppData.loggedInUser.role == User.Role.ServiceDepartmentManager)
            requestingDepartment = FinancialRequest.RequestingDepartment.ServiceDepartment;

        FinancialRequest financialRequest = new FinancialRequest(
                title,
                reasonForBudgetAdjustment,
                requiredAmount,
                requestingDepartment,
                eventRequest,
                AppData.loggedInUser
        );

        this.repository.addFinancialRequest(financialRequest);

        return financialRequest;
    }

    public ArrayList<FinancialRequest> getAllFinancialRequests()
    {
        return this.repository.getAllFinancialRequests();
    }

    public void updateFinancialRequestStatus(FinancialRequest request, FinancialRequest.Status newStatus)
    {
        // TODO Status progression rules
        // NOTE:
        // Not saving this to repo since our implementation is only using an
        // in-memory data store
        request.setStatus(newStatus);
    }
}
