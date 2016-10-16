package com.thorsteinnth.kth.mmse.sepcli.Repository;

import com.thorsteinnth.kth.mmse.sepcli.Domain.RecruitmentRequest;

import java.util.ArrayList;

public interface IRecruitmentRequestRepository
{
    void addRecruitmentRequest(RecruitmentRequest recruitmentRequest);
    ArrayList<RecruitmentRequest> getAllRecruitmentRequests();
}
