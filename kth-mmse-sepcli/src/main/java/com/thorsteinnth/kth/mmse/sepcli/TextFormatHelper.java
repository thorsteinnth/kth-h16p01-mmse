package com.thorsteinnth.kth.mmse.sepcli;

import java.text.SimpleDateFormat;
import java.util.GregorianCalendar;

public class TextFormatHelper
{
    public static String getSimpleDateTimeFromGregorianCalendar(GregorianCalendar calendar)
    {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        format.setCalendar(calendar);
        return format.format(calendar.getTime());
    }
}
