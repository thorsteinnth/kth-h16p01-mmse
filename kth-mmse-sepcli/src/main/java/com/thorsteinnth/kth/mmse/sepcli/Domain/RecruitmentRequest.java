package com.thorsteinnth.kth.mmse.sepcli.Domain;

public class RecruitmentRequest extends Request
{
    private String jobTitle;
    private String jobDescription;
    private String requirements;
    private ContractType contractType;
    private RequestingDepartment requestingDepartment;
    private Status status;

    public enum ContractType
    {
        FullTime,
        Outsourcing
    }

    public enum RequestingDepartment
    {
        Unknown,
        ProductionDepartment,
        ServiceDepartment
    }

    public enum Status
    {
        Pending,
        Approved,
        Rejected
    }

    public RecruitmentRequest(
            String jobTitle,
            String jobDescription,
            String requirements,
            ContractType contractType,
            RequestingDepartment requestingDepartment,
            User createdByUser
    )
    {
        super(createdByUser);
        this.jobTitle = jobTitle;
        this.jobDescription = jobDescription;
        this.requirements = requirements;
        this.contractType = contractType;
        this.requestingDepartment = requestingDepartment;
        this.status = Status.Pending;
    }

    public String getJobTitle()
    {
        return this.jobTitle;
    }

    public String getJobDescription()
    {
        return this.jobDescription;
    }

    public String getRequirements()
    {
        return this.requirements;
    }

    public ContractType getContractType()
    {
        return this.contractType;
    }

    public RequestingDepartment getRequestingDepartment()
    {
        return this.requestingDepartment;
    }

    public Status getStatus()
    {
        return this.status;
    }
}
