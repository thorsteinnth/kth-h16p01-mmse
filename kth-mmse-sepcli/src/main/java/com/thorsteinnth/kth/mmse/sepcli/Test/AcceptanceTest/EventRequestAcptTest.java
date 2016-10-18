package com.thorsteinnth.kth.mmse.sepcli.Test.AcceptanceTest;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.LinkedList;
import java.util.Queue;

public class EventRequestAcptTest
{
    private static Queue<String> operationQueue;

    public static void runTest()
    {
        operationQueue = new LinkedList<>();
        operationQueue.add("sarah@sep.se");
        operationQueue.add("sarah123");
        operationQueue.add("1"); // select "1. Request management"
        operationQueue.add("2"); // select "2. Event requests"
        operationQueue.add("1"); // select "1. Create event request"
        operationQueue.add("Event 1");
        operationQueue.add("Description of the event");
        operationQueue.add("2016-10-20-10-00"); //Start time
        operationQueue.add("2016-10-22-18-00"); //End time
        operationQueue.add("200"); //Number of attendees
        operationQueue.add("Description of preferences");
        operationQueue.add("50000"); //Expected budget
        operationQueue.add("2"); //select 2: link event to client nr. 2
        operationQueue.add("1");
    }

    public static InputStream getInputStream()
    {
        if(operationQueue.size() == 0)
            return null;
        else
            return setInputStream(operationQueue.remove());
    }

    private static InputStream setInputStream(String message)
    {
        return new ByteArrayInputStream(message.getBytes());
    }
}
