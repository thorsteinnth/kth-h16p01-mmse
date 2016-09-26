package Domain;

import java.util.ArrayList;

/**
 * Created by tts on 25/09/2016.
 */
public class CustomerServiceOfficer extends User
{
    public ArrayList<EventRequest> EventRequests;

    public EventRequest CreateEventRequest()
    {
        return new EventRequest();
    }
}
