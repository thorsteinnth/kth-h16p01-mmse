package com.thorsteinnth.kth.mmse.sepcli.Repository;

import com.thorsteinnth.kth.mmse.sepcli.AppData;
import com.thorsteinnth.kth.mmse.sepcli.Domain.RecruitmentRequest;

import java.util.ArrayList;

public class RecruitmentRequestRepository implements IRecruitmentRequestRepository
{
    public void addRecruitmentRequest(RecruitmentRequest recruitmentRequest)
    {
        AppData.recruitmentRequests.add(recruitmentRequest);
    }

    public ArrayList<RecruitmentRequest> getAllRecruitmentRequests()
    {
        return AppData.recruitmentRequests;
    }
}
