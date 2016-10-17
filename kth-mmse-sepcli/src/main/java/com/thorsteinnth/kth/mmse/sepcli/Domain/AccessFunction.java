package com.thorsteinnth.kth.mmse.sepcli.Domain;

public enum AccessFunction
{
    // Access rights get incresingly fine grained as we go down this list

    // LVL 1

    clientManagement,   // Everyone that can do anything with clients has this right
    requestManagement,  // Everyone that can do anything with requests has this right

    // LVL2

    eventRequestManagement,
    taskRequestManagement,
    financialRequestManagement,
    recruitmentRequestManagement,

    createClientRecord,
    browseClientRecords,

    // LVL 3

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
