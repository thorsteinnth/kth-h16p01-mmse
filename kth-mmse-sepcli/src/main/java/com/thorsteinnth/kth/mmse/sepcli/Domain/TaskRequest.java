package com.thorsteinnth.kth.mmse.sepcli.Domain;

import java.util.ArrayList;

public class TaskRequest extends Request
{
    private String title;
    private String description;
    private ArrayList<RequestComment> comments;
    private Priority priority;
    private Status status;

    private EventRequest eventRequest;
    private User assignee;

    public enum Priority
    {
        Low,
        Medium,
        High
    }

    public enum Status
    {
        Pending,
        Approved,
        Rejected
    }

    public TaskRequest(
            String title,
            String description,
            Priority priority,
            EventRequest eventRequest,
            User createdByUser,
            User assignee)
    {
        super(createdByUser);
        this.title = title;
        this.description = description;
        this.comments = new ArrayList<>();
        this.priority = priority;
        this.status = Status.Pending;
        this.eventRequest = eventRequest;
        this.assignee = assignee;
    }

    public void addComment(RequestComment comment)
    {
        this.comments.add(comment);
    }

    public String getId()
    {
        return super.getId();
    }

    public String getTitle()
    {
        return title;
    }

    public String getDescription()
    {
        return description;
    }

    public ArrayList<RequestComment> getComments()
    {
        return comments;
    }

    public Priority getPriority()
    {
        return priority;
    }

    public Status getStatus()
    {
        return this.status;
    }

    public void setStatus(Status status)
    {
        this.status = status;
    }

    public EventRequest getEventRequest()
    {
        return eventRequest;
    }

    public User getCreatedByUser()
    {
        return super.getCreatedByUser();
    }

    public User getAssignee()
    {
        return assignee;
    }

    @Override
    public String toDisplayString()
    {
        StringBuilder sb = new StringBuilder();
        sb.append("ID:\t\t\t\t" + getId() + System.getProperty("line.separator"));
        sb.append("Title:\t\t\t" + getTitle() + System.getProperty("line.separator"));
        sb.append("Description:\t" + getDescription() + System.getProperty("line.separator"));
        sb.append("Priority:\t\t" + getPriority().toString() + System.getProperty("line.separator"));
        sb.append("Status:\t\t\t" + getStatus() + System.getProperty("line.separator"));
        sb.append("Event request:\t" + getEventRequest().getTitle() + System.getProperty("line.separator"));
        sb.append("Assignee:\t\t" + getAssignee().email + System.getProperty("line.separator"));
        sb.append("Created by:\t\t" + getCreatedByUser().email + System.getProperty("line.separator"));
        sb.append("Comments:" + System.getProperty("line.separator"));

        for (RequestComment rc : getComments())
        {
            sb.append(rc.toDisplayString() + System.getProperty("line.separator"));
        }

        return sb.toString();
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TaskRequest that = (TaskRequest) o;

        if (title != null ? !title.equals(that.title) : that.title != null) return false;
        if (description != null ? !description.equals(that.description) : that.description != null) return false;
        if (comments != null ? !comments.equals(that.comments) : that.comments != null) return false;
        if (priority != that.priority) return false;
        if (status != that.status) return false;
        if (eventRequest != null ? !eventRequest.equals(that.eventRequest) : that.eventRequest != null) return false;
        return assignee != null ? assignee.equals(that.assignee) : that.assignee == null;

    }

    @Override
    public int hashCode()
    {
        int result = title != null ? title.hashCode() : 0;
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + (comments != null ? comments.hashCode() : 0);
        result = 31 * result + (priority != null ? priority.hashCode() : 0);
        result = 31 * result + (status != null ? status.hashCode() : 0);
        result = 31 * result + (eventRequest != null ? eventRequest.hashCode() : 0);
        result = 31 * result + (assignee != null ? assignee.hashCode() : 0);
        return result;
    }

    @Override
    public String toString()
    {
        return "TaskRequest{" +
                "title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", comments=" + comments +
                ", priority=" + priority +
                ", status=" + status +
                ", eventRequest=" + eventRequest +
                ", createdByUser=" + super.getCreatedByUser() +
                ", assignee=" + assignee +
                '}';
    }
}
