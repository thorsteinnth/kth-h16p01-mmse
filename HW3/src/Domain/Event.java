package Domain;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by tts on 25/09/2016.
 */
public class Event
{
    public Client Client;
    public String Description;
    public Date StartDateTime;
    public Date EndDateTime;
    public int NumberOfAttendees;
    public BigDecimal Budget;
    public String PreferencesDescription;
    public String Comments;
}
