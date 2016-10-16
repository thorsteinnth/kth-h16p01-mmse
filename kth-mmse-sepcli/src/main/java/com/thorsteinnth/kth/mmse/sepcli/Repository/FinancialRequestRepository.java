package com.thorsteinnth.kth.mmse.sepcli.Repository;

import com.thorsteinnth.kth.mmse.sepcli.AppData;
import com.thorsteinnth.kth.mmse.sepcli.Domain.FinancialRequest;

import java.util.ArrayList;

public class FinancialRequestRepository implements IFinancialRequestRepository
{
    public void addFinancialRequest(FinancialRequest financialRequest)
    {
        AppData.financialRequests.add(financialRequest);
    }

    public ArrayList<FinancialRequest> getAllFinancialRequests()
    {
        return AppData.financialRequests;
    }
}
