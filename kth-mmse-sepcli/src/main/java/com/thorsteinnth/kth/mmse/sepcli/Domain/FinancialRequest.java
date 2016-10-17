package com.thorsteinnth.kth.mmse.sepcli.Domain;

import com.thorsteinnth.kth.mmse.sepcli.CliHelper;

import javax.print.DocFlavor;
import java.math.BigDecimal;

public class FinancialRequest extends Request
{
    private String title;
    private String reasonForBudgetAdjustment;
    private BigDecimal requiredAmount;
    private RequestingDepartment requestingDepartment;
    private EventRequest eventRequest;
    private Status status;

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

    public FinancialRequest(
            String title,
            String reasonForBudgetAdjustment,
            BigDecimal requiredAmount,
            RequestingDepartment requestingDepartment,
            EventRequest eventRequest,
            User createdByUser)
    {
        super(createdByUser);
        this.title = title;
        this.reasonForBudgetAdjustment = reasonForBudgetAdjustment;
        this.requiredAmount = requiredAmount;
        this.requestingDepartment = requestingDepartment;
        this.eventRequest = eventRequest;
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

    public EventRequest getEventRequest()
    {
        return eventRequest;
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
    public String toDisplayString()
    {
        StringBuilder sb = new StringBuilder();
        sb.append("ID:\t\t\t\t\t\t" + getId() + System.getProperty("line.separator"));
        sb.append("Title:\t\t\t\t\t" + getTitle() + System.getProperty("line.separator"));
        sb.append("Status:\t\t\t\t\t" + getStatus() + System.getProperty("line.separator"));
        sb.append("Reason:\t\t\t\t\t" + getReasonForBudgetAdjustment() + System.getProperty("line.separator"));
        sb.append("Required amt:\t\t" + getRequiredAmount().toString() + System.getProperty("line.separator"));
        sb.append("Event request:\t" + getEventRequest().getTitle() + System.getProperty("line.separator"));
        sb.append("Created by:\t\t" + getCreatedByUser().email + System.getProperty("line.separator"));

        return sb.toString();
    }

    @Override
    public String toString() {
        return "FinancialRequest{" +
                "title='" + title + '\'' +
                ", reasonForBudgetAdjustment='" + reasonForBudgetAdjustment + '\'' +
                ", requiredAmount=" + requiredAmount +
                ", requestingDepartment=" + requestingDepartment +
                ", eventRequest=" + eventRequest +
                ", createdByUser=" + super.getCreatedByUser() +
                '}';
    }
}
