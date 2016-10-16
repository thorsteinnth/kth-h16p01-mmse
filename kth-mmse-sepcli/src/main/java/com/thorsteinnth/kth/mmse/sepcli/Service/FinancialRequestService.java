package com.thorsteinnth.kth.mmse.sepcli.Service;

import com.thorsteinnth.kth.mmse.sepcli.AppData;
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
            FinancialRequest.RequestingDepartment requestingDepartment
    )
    {
        FinancialRequest financialRequest = new FinancialRequest(
                title,
                reasonForBudgetAdjustment,
                requiredAmount,
                requestingDepartment,
                AppData.loggedInUser
        );

        this.repository.addFinancialRequest(financialRequest);

        return financialRequest;
    }

    public ArrayList<FinancialRequest> getAllFinancialRequests()
    {
        return this.repository.getAllFinancialRequests();
    }
}
