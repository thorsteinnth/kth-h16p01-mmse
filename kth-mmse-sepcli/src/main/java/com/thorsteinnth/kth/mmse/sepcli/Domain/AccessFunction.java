package com.thorsteinnth.kth.mmse.sepcli.Domain;

public enum AccessFunction
{
    clientManagement,   // Everyone that can do anything with clients has this right
    requestManagement,  // Everyone that can do anything with requests has this right
    createClientRecord,
    browseClientRecords,
    createEventRequest,
    editEventRequest,
    browseEventRequests,
    approveEventRequest,
    rejectEventRequest,
    createTaskRequest,
    editTaskRequest,
    createFinancialRequest,
    approveFinancialRequest,
    rejectFinancialRequest,
    createRecruitmentRequest,
    approveRecruitmentRequest,
    rejectRecruitmentRequest
}
