package com.thorsteinnth.kth.mmse.sepcli.Domain;

import javax.print.DocFlavor;
import java.math.BigDecimal;

public class FinancialRequest extends Request
{
    private String title;
    private String reasonForBudgetAdjustment;
    private BigDecimal requiredAmount;
    private RequestingDepartment requestingDepartment;
    private Status status;

    public enum RequestingDepartment
    {
        ProductionDepartment,
        ServiceDepartment
    }

    public enum Status
    {
        Pending,
        Approved,
        Rejected
    }

    public FinancialRequest(
            String title,
            String reasonForBudgetAdjustment,
            BigDecimal requiredAmount,
            RequestingDepartment requestingDepartment,
            User createdByUser)
    {
        super(createdByUser);
        this.title = title;
        this.reasonForBudgetAdjustment = reasonForBudgetAdjustment;
        this.requiredAmount = requiredAmount;
        this.requestingDepartment = requestingDepartment;
        this.status = Status.Pending;
    }

    public String getTitle()
    {
        return this.title;
    }

    public String getReasonForBudgetAdjustment()
    {
        return this.reasonForBudgetAdjustment;
    }

    public BigDecimal getRequiredAmount()
    {
        return this.requiredAmount;
    }

    public RequestingDepartment getRequestingDepartment()
    {
        return this.requestingDepartment;
    }

    public Status getStatus()
    {
        return this.status;
    }

    public void setStatus(Status status)
    {
        this.status = status;
    }

    @Override
    public String toString() {
        return "FinancialRequest{" +
                "title='" + title + '\'' +
                ", reasonForBudgetAdjustment='" + reasonForBudgetAdjustment + '\'' +
                ", requiredAmount=" + requiredAmount +
                ", requestingDepartment=" + requestingDepartment +
                ", createdByUser=" + super.getCreatedByUser() +
                '}';
    }
}
