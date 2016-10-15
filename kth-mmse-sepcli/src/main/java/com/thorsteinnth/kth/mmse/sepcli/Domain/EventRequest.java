package com.thorsteinnth.kth.mmse.sepcli.Domain;

import com.thorsteinnth.kth.mmse.sepcli.TextFormatHelper;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;

// TODO Refactor - add abstract request class?
public class EventRequest
{
    private final int id;
    private String title;
    private String description;
    private GregorianCalendar startDateTime;
    private GregorianCalendar endDateTime;
    private int numberOfAttendees;
    private String preferenceDescription;
    private BigDecimal expectedBudget;
    private ArrayList<RequestComment> comments;
    private Status status;
    private Date createdDateTime;

    // TODO public ArrayList<FinancialRequest> financialRequests;

    public Client client;
    public User createdByUser;

    public enum Status
    {
        Pending,
        Open,
        InProgress,
        Closed,
        Rejected
    }

    public EventRequest(
            int id,
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
        this.id = id;
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
    }

    public int getId()
    {
        return id;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        EventRequest that = (EventRequest) o;

        if (id != that.id) return false;
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
        if (createdDateTime != null ? !createdDateTime.equals(that.createdDateTime) : that.createdDateTime != null)
            return false;
        if (client != null ? !client.equals(that.client) : that.client != null) return false;
        return createdByUser != null ? createdByUser.equals(that.createdByUser) : that.createdByUser == null;

    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (title != null ? title.hashCode() : 0);
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
                "id=" + id +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", startDateTime=" + getStartDateTimeString() +
                ", endDateTime=" + getEndDateTimeString() +
                ", numberOfAttendees=" + numberOfAttendees +
                ", preferenceDescription='" + preferenceDescription + '\'' +
                ", expectedBudget=" + expectedBudget +
                ", comments=" + comments +
                ", status=" + status +
                ", createdDateTime=" + createdDateTime +
                ", client=" + client +
                ", createdByUser=" + createdByUser +
                '}';
    }
}
