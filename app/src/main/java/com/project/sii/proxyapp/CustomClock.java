package com.project.sii.proxyapp;

import java.io.Serializable;
import java.util.GregorianCalendar;
/**
 * The class is used for the creation of a clock
 */
public class CustomClock implements Serializable {

    /*  It provides the standard calendar system used by most of the world  */
    private GregorianCalendar calendar;

    /*  Class Constructor   */
    public CustomClock()    {  calendar = new GregorianCalendar();  }

    /**********  Get Functions   ***********/
    /**
     * The function gets the number of seconds
     * @return  time seconds
     */
    public int get_sec()    { return calendar.get(calendar.SECOND); }

    /**
     * The function gets the number of minuted
     * @return  time minutes
     */
    public int get_min()    { return calendar.get(calendar.MINUTE); }

    /**
     * The function gets the number of hours
     * @return  time hours
     */
    public int get_hour()   { return calendar.get(calendar.HOUR_OF_DAY); }

    /**
     * The function gets the number of days
     * @return  time days
     */
    public int get_day()    { return calendar.get(calendar.DATE); }

    /**
     * The function gets the number of months
     * @return  time months
     */
    public int get_month()  { return calendar.get(calendar.MONTH)+1; }

    /**
     * The function gets the number of years
     * @return  time years
     */
    public int get_year()   { return calendar.get(calendar.YEAR); }

    /**
     *  This function gets the actual time before converting it in hours, minutes,
     *  seconds, days and months. Finally, it prints it as a String
     *  @return  time converted as a String
     */
    public String data()   {
        update();
        String hour, min, sec, day, month;

        hour   = (get_hour()<10) ? "0"+get_hour() : ""+get_hour();
        min   = (get_min()<10)  ? "0"+get_min() : ""+get_min();
        sec   = (get_sec()<10)  ? "0"+get_sec() : ""+get_sec();
        day   = (get_day()<10)  ? "0"+get_day() : ""+get_day();
        month = (get_month()<10) ? "0"+get_month() : ""+get_month();

        return hour + ":" + min +":" + sec +", " + day + "/" + month + "/" + get_year();
    }

    /**
     *  The method gets the actual time
     */
    public void update()    { calendar = new GregorianCalendar(); }
}