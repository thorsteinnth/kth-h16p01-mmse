package com.thorsteinnth.kth.mmse.sepcli.Service;

import com.thorsteinnth.kth.mmse.sepcli.AppData;
import com.thorsteinnth.kth.mmse.sepcli.Domain.*;
import com.thorsteinnth.kth.mmse.sepcli.Repository.ITaskRequestRepository;

import java.util.ArrayList;

public class TaskRequestService
{
    private ITaskRequestRepository repository;

    public TaskRequestService(ITaskRequestRepository repository)
    {
        this.repository = repository;
    }

    public TaskRequest createTaskRequest(
            String title,
            String descrition,
            TaskRequest.Priority priority,
            EventRequest eventRequest,
            User assignee)
    {
        TaskRequest taskRequest = new TaskRequest(
                title,
                descrition,
                priority,
                eventRequest,
                AppData.loggedInUser,
                assignee
        );

        this.repository.addTaskRequest(taskRequest);

        return taskRequest;
    }

    public ArrayList<TaskRequest> getAllTaskRequests()
    {
        return this.repository.getAllTaskRequests();
    }

    public void addCommentToTaskRequest(TaskRequest request, String commentText)
    {
        // NOTE:
        // Not saving this to repo since our implementation is only using an
        // in-memory data store
        request.addComment(new RequestComment(AppData.loggedInUser, commentText));
    }

    public boolean updateTaskRequestStatus(TaskRequest request, TaskRequest.Status newStatus)
    {
        // NOTE:
        // Not saving this to repo since our implementation is only using an
        // in-memory data store

        // Check for access rights for the approved and rejected operations

        if (newStatus == TaskRequest.Status.Approved)
        {
            if (!AccessControlService.hasAccess(AccessFunction.approveTaskRequest))
                return false;
        }
        else if (newStatus == TaskRequest.Status.Rejected)
        {
            if (!AccessControlService.hasAccess(AccessFunction.rejectTaskRequest))
                return false;
        }

        request.setStatus(newStatus);
        return true;
    }
}
