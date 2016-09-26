package Domain;

import java.util.ArrayList;

/**
 * Created by tts on 25/09/2016.
 */
public class SeniorCustomerServiceOfficer extends User
{
    public ArrayList<Client> CreatedClients;

    public void ApproveOrRejectEventRequest(EventRequest request)
    {}

    public Client CreateClient()
    {
        return new Client();
    }

    public void SearchEventRequests()
    {}

    public void ViewEventRequest()
    {}

    public void SearchClientRecords()
    {}

    public void ViewClientRecord()
    {}
}
