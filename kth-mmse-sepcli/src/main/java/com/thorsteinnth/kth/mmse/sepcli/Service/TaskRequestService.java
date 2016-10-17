package com.thorsteinnth.kth.mmse.sepcli.Service;

import com.thorsteinnth.kth.mmse.sepcli.AppData;
import com.thorsteinnth.kth.mmse.sepcli.Domain.EventRequest;
import com.thorsteinnth.kth.mmse.sepcli.Domain.RequestComment;
import com.thorsteinnth.kth.mmse.sepcli.Domain.TaskRequest;
import com.thorsteinnth.kth.mmse.sepcli.Domain.User;
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

    public void updateTaskRequestStatus(TaskRequest request, TaskRequest.Status newStatus)
    {
        // TODO Status progression rules
        // NOTE:
        // Not saving this to repo since our implementation is only using an
        // in-memory data store
        request.setStatus(newStatus);
    }
}
