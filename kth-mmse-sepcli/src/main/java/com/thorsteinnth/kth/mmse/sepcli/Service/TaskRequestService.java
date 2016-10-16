package com.thorsteinnth.kth.mmse.sepcli.Service;

import com.thorsteinnth.kth.mmse.sepcli.AppData;
import com.thorsteinnth.kth.mmse.sepcli.Domain.EventRequest;
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
}
