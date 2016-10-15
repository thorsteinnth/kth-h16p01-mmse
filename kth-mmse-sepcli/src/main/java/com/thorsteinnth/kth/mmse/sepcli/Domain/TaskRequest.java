package com.thorsteinnth.kth.mmse.sepcli.Domain;

import java.util.ArrayList;

public class TaskRequest
{
    private String id;
    private String title;
    private String description;
    private String comment;
    private Priority priority;

    private EventRequest eventRequest;
    private User createdByUser;
    private User assignee;

    public enum Priority
    {
        Low,
        Medium,
        High
    }

    public TaskRequest(
            String id,
            String title,
            String description,
            Priority priority,
            EventRequest eventRequest,
            User createdByUser,
            User assignee)
    {
        this.id = id;
        this.title = title;
        this.description = description;
        this.comment = "";
        this.priority = priority;
        this.eventRequest = eventRequest;
        this.createdByUser = createdByUser;
        this.assignee = assignee;
    }

    public void addComment(String comment)
    {
        this.comment = comment;
    }

    public String getId()
    {
        return id;
    }

    public String getTitle()
    {
        return title;
    }

    public String getDescription()
    {
        return description;
    }

    public String getComment()
    {
        return comment;
    }

    public Priority getPriority()
    {
        return priority;
    }

    public EventRequest getEventRequest()
    {
        return eventRequest;
    }

    public User getCreatedByUser()
    {
        return createdByUser;
    }

    public User getAssignee()
    {
        return assignee;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TaskRequest that = (TaskRequest) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (title != null ? !title.equals(that.title) : that.title != null) return false;
        if (description != null ? !description.equals(that.description) : that.description != null) return false;
        if (comment != null ? !comment.equals(that.comment) : that.comment != null) return false;
        if (priority != that.priority) return false;
        if (eventRequest != null ? !eventRequest.equals(that.eventRequest) : that.eventRequest != null) return false;
        if (createdByUser != null ? !createdByUser.equals(that.createdByUser) : that.createdByUser != null)
            return false;
        return assignee != null ? assignee.equals(that.assignee) : that.assignee == null;

    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (title != null ? title.hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + (comment != null ? comment.hashCode() : 0);
        result = 31 * result + (priority != null ? priority.hashCode() : 0);
        result = 31 * result + (eventRequest != null ? eventRequest.hashCode() : 0);
        result = 31 * result + (createdByUser != null ? createdByUser.hashCode() : 0);
        result = 31 * result + (assignee != null ? assignee.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "TaskRequest{" +
                "id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", comment='" + comment + '\'' +
                ", priority=" + priority +
                ", eventRequest=" + eventRequest +
                ", createdByUser=" + createdByUser +
                ", assignee=" + assignee +
                '}';
    }
}
