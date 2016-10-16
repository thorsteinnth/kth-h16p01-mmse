package com.thorsteinnth.kth.mmse.sepcli.Repository;

import com.thorsteinnth.kth.mmse.sepcli.Domain.TaskRequest;

import java.util.ArrayList;

public interface ITaskRequestRepository
{
    void addTaskRequest(TaskRequest taskRequest);
    ArrayList<TaskRequest> getAllTaskRequests();
}
