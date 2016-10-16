package com.thorsteinnth.kth.mmse.sepcli.Repository;

import com.thorsteinnth.kth.mmse.sepcli.AppData;
import com.thorsteinnth.kth.mmse.sepcli.Domain.FinancialRequest;

import java.util.ArrayList;

public interface IFinancialRequestRepository
{
    void addFinancialRequest(FinancialRequest financialRequest);
    ArrayList<FinancialRequest> getAllFinancialRequests();
}
