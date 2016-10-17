package com.thorsteinnth.kth.mmse.sepcli.Test.AcceptanceTest;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.LinkedList;
import java.util.Queue;

public class CreateClientRecordAcptTest
{
    private static Queue<String> operationQueue;

    public static void runTest()
    {
        operationQueue = new LinkedList<>();
        operationQueue.add("janet@sep.se");
        operationQueue.add("janet123");
        operationQueue.add("2"); // select "2. Client management"
        operationQueue.add("1"); // select "1. Create client record"
        operationQueue.add("Client name");
        operationQueue.add("Client address");
        operationQueue.add("client@client.com");
        operationQueue.add("07229999");
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
