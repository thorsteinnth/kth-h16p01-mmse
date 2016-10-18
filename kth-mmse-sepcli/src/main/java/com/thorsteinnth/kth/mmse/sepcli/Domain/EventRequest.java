package com.thorsteinnth.kth.mmse.sepcli.Domain;

import com.thorsteinnth.kth.mmse.sepcli.TextFormatHelper;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;

public class EventRequest extends Request
{
    private String title;
    private String description;
    private GregorianCalendar startDateTime;
    private GregorianCalendar endDateTime;
    private int numberOfAttendees;
    private String preferenceDescription;
    private BigDecimal expectedBudget;
    private ArrayList<RequestComment> comments;
    private Status status;
    private WorkflowStatus workflowStatus;
    private Date createdDateTime;

    // TODO public ArrayList<FinancialRequest> financialRequests - or not, can lookup the other way around

    public Client client;
    public User createdByUser;

    public enum Status
    {
        Pending,
        Open,
        InProgress,
        Closed,
        Rejected,
        Approved
    }

    // TODO implement
    /*
    Can go to rejected whenever a user has authority to reject
    Otherwise
    Pending
    -> all task requests accepted -> open (both automatic and manual)
    ->
    // When we are staffmanager and are viewing an event request we get "edit workflow status"
    he changes the status of the request
    */

    public enum WorkflowStatus
    {
        Initial,
        StaffingDoneTaskRequestsSent,           // Set status to open
        AllBudgetAndStaffingIssuesResolved,     // Set status to in progress
        EventFinalized                          //  Set status to closed or archived
    }

    public EventRequest(
            String title,
            String description,
            GregorianCalendar startDateTime,
            GregorianCalendar endDateTime,
            int numberOfAttendees,
            String preferenceDescription,
            BigDecimal expectedBudget,
            Client client,
            User createdByUser
    )
    {
        super(createdByUser);
        this.title = title;
        this.description = description;
        this.startDateTime = startDateTime;
        this.endDateTime = endDateTime;
        this.numberOfAttendees = numberOfAttendees;
        this.preferenceDescription = preferenceDescription;
        this.expectedBudget = expectedBudget;
        this.client = client;
        this.createdByUser = createdByUser;

        this.comments = new ArrayList<RequestComment>();
        this.createdDateTime = new Date();
        this.status = Status.Pending;
        this.workflowStatus = WorkflowStatus.Initial;
    }

    public String getId()
    {
        return super.getId();
    }

    public void addComment(RequestComment comment)
    {
        comments.add(comment);
    }

    public ArrayList<RequestComment> getComments()
    {
        return comments;
    }

    public void setStatus(Status status)
    {
        this.status = status;
    }

    public Status getStatus()
    {
        return status;
    }

    /**
     * Update workflow status and update event request status accordingly
     * @param newWorkflowStatus
     */
    public void setWorkflowStatus(WorkflowStatus newWorkflowStatus)
    {
        this.workflowStatus = newWorkflowStatus;

        if (newWorkflowStatus == WorkflowStatus.StaffingDoneTaskRequestsSent)
            this.status = Status.Open;
        else if (newWorkflowStatus == WorkflowStatus.AllBudgetAndStaffingIssuesResolved)
            this.status = Status.InProgress;
        else if (newWorkflowStatus == WorkflowStatus.EventFinalized)
            this.status = Status.Closed;
    }

    public WorkflowStatus getWorkflowStatus()
    {
        return workflowStatus;
    }

    public String getTitle()
    {
        return title;
    }

    public String getDescription()
    {
        return description;
    }

    public GregorianCalendar getStartDateTime()
    {
        return startDateTime;
    }

    public String getStartDateTimeString()
    {
        return TextFormatHelper.getSimpleDateTimeFromGregorianCalendar(startDateTime);
    }

    public GregorianCalendar getEndDateTime()
    {
        return endDateTime;
    }

    public String getEndDateTimeString()
    {
        return TextFormatHelper.getSimpleDateTimeFromGregorianCalendar(endDateTime);
    }

    public int getNumberOfAttendees()
    {
        return numberOfAttendees;
    }

    public String getPreferenceDescription()
    {
        return preferenceDescription;
    }

    public BigDecimal getExpectedBudget()
    {
        return expectedBudget;
    }

    public Client getClient()
    {
        return client;
    }

    public User getCreatedByUser()
    {
        return createdByUser;
    }

    public Date getCreatedDateTime()
    {
        return createdDateTime;
    }

    public String toDisplayString()
    {
        StringBuilder sb = new StringBuilder();
        sb.append("ID:\t\t\t\t\t\t" + getId() + System.getProperty("line.separator"));
        sb.append("Status:\t\t\t\t\t" + getStatus() + System.getProperty("line.separator"));
        sb.append("Workflow status:\t\t" + getWorkflowStatus() + System.getProperty("line.separator"));
        sb.append("Title:\t\t\t\t\t" + getTitle() + System.getProperty("line.separator"));
        sb.append("Description:\t\t\t" + getDescription() + System.getProperty("line.separator"));
        sb.append("Start date time:\t\t" + getStartDateTimeString() + System.getProperty("line.separator"));
        sb.append("End date time:\t\t\t" + getEndDateTimeString() + System.getProperty("line.separator"));
        sb.append("Number of attendees:\t" + getNumberOfAttendees() + System.getProperty("line.separator"));
        sb.append("Preference description:\t" + getPreferenceDescription() + System.getProperty("line.separator"));
        sb.append("Expected budget:\t\t" + getExpectedBudget() + System.getProperty("line.separator"));
        sb.append("Client:\t\t\t\t\t" + getClient().name + System.getProperty("line.separator"));
        sb.append("Created by:\t\t\t\t" + getCreatedByUser().email + System.getProperty("line.separator"));
        sb.append("Created date time:\t\t" + getCreatedDateTime().toString() + System.getProperty("line.separator"));
        sb.append("Comments:" + System.getProperty("line.separator"));

        for (RequestComment rc : getComments())
        {
            sb.append(rc.toDisplayString() + System.getProperty("line.separator"));
        }

        return sb.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        EventRequest that = (EventRequest) o;

        if (numberOfAttendees != that.numberOfAttendees) return false;
        if (title != null ? !title.equals(that.title) : that.title != null) return false;
        if (description != null ? !description.equals(that.description) : that.description != null) return false;
        if (startDateTime != null ? !startDateTime.equals(that.startDateTime) : that.startDateTime != null)
            return false;
        if (endDateTime != null ? !endDateTime.equals(that.endDateTime) : that.endDateTime != null) return false;
        if (preferenceDescription != null ? !preferenceDescription.equals(that.preferenceDescription) : that.preferenceDescription != null)
            return false;
        if (expectedBudget != null ? !expectedBudget.equals(that.expectedBudget) : that.expectedBudget != null)
            return false;
        if (comments != null ? !comments.equals(that.comments) : that.comments != null) return false;
        if (status != that.status) return false;
        if (workflowStatus != that.workflowStatus) return false;
        if (createdDateTime != null ? !createdDateTime.equals(that.createdDateTime) : that.createdDateTime != null)
            return false;
        if (client != null ? !client.equals(that.client) : that.client != null) return false;
        return createdByUser != null ? createdByUser.equals(that.createdByUser) : that.createdByUser == null;

    }

    @Override
    public int hashCode() {
        int result = title != null ? title.hashCode() : 0;
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + (startDateTime != null ? startDateTime.hashCode() : 0);
        result = 31 * result + (endDateTime != null ? endDateTime.hashCode() : 0);
        result = 31 * result + numberOfAttendees;
        result = 31 * result + (preferenceDescription != null ? preferenceDescription.hashCode() : 0);
        result = 31 * result + (expectedBudget != null ? expectedBudget.hashCode() : 0);
        result = 31 * result + (comments != null ? comments.hashCode() : 0);
        result = 31 * result + (status != null ? status.hashCode() : 0);
        result = 31 * result + (createdDateTime != null ? createdDateTime.hashCode() : 0);
        result = 31 * result + (client != null ? client.hashCode() : 0);
        result = 31 * result + (createdByUser != null ? createdByUser.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "EventRequest{" +
                "title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", startDateTime=" + startDateTime +
                ", endDateTime=" + endDateTime +
                ", numberOfAttendees=" + numberOfAttendees +
                ", preferenceDescription='" + preferenceDescription + '\'' +
                ", expectedBudget=" + expectedBudget +
                ", comments=" + comments +
                ", status=" + status +
                ", workflowStatus=" + workflowStatus +
                ", createdDateTime=" + createdDateTime +
                ", client=" + client +
                ", createdByUser=" + createdByUser +
                '}';
    }
}
