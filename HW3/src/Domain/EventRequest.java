package Domain;

import java.util.Date;
import java.math.BigDecimal;

/**
 * Created by tts on 25/09/2016.
 */
public class EventRequest
{
    public Client Client;
    public String Description;
    public Date StartDateTime;
    public Date EndDateTime;
    public int NumberOfAttendees;
    public String PreferencesDescription;
    public BigDecimal Budget;
}
