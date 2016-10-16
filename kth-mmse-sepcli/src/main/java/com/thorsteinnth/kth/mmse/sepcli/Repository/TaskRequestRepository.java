package com.thorsteinnth.kth.mmse.sepcli.Repository;

import com.thorsteinnth.kth.mmse.sepcli.AppData;
import com.thorsteinnth.kth.mmse.sepcli.Domain.TaskRequest;

import java.util.ArrayList;

public class TaskRequestRepository implements ITaskRequestRepository
{
    public void addTaskRequest(TaskRequest taskRequest)
    {
        AppData.taskRequests.add(taskRequest);
    }

    public ArrayList<TaskRequest> getAllTaskRequests()
    {
        return AppData.taskRequests;
    }
}
