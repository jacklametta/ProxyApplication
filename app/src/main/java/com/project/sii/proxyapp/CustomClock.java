package com.project.sii.proxyapp;

import java.io.Serializable;
import java.util.GregorianCalendar;

public class CustomClock implements Serializable {

    private static final long serialVersionUID = 1L;
    private GregorianCalendar ora;

    public CustomClock() {
        ora = new GregorianCalendar();
    }

    public int get_sec()   { return ora.get(ora.SECOND); }
    public int get_min()   { return ora.get(ora.MINUTE); }
    public int get_hour()  { return ora.get(ora.HOUR_OF_DAY); }
    public int get_day()   { return ora.get(ora.DATE); }
    public int get_month() { return ora.get(ora.MONTH)+1; }
    public int get_year()  { return ora.get(ora.YEAR); }

    public String data()   {
        aggiorna();
        String hour, min, sec, day, month;

        // Operatori ternari al fine di una miglior formattazione
        hour   = (get_hour()<10) ? "0"+get_hour() : ""+get_hour();
        min   = (get_min()<10)  ? "0"+get_min() : ""+get_min();
        sec   = (get_sec()<10)  ? "0"+get_sec() : ""+get_sec();
        day   = (get_day()<10)  ? "0"+get_day() : ""+get_day();
        month = (get_month()<10) ? "0"+get_month() : ""+get_month();

        return hour + ":" + min +":" + sec +", " + day + "/" + month + "/" + get_year();
    }
    public void aggiorna() { ora = new GregorianCalendar(); }

    public static void main(String[] args) {
        CustomClock clock = new CustomClock();

        for(int i=0; i<120; i++) {
            System.out.println("Log di ADESSO! :  "+clock.data());
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }
}