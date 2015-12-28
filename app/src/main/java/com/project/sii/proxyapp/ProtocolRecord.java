package com.project.sii.proxyapp;
import java.sql.Timestamp;

/**
 * The class is used to handle with the hashtable's record.
 */
public class ProtocolRecord {
    /*  Time to Live */
    private static long TTL = 60000L; /* millis */
    /*  Timestamp   */
    private Timestamp deadline;

    /*  Class Constructor   */
    public ProtocolRecord(){
        updateDeadline();
    }

    /**
     *  The function returns if the hashtable record is still valid
     *  @return true if the record is still valid, false otherwise
     */
    public boolean isValid(){
        long ts = System.currentTimeMillis();
        Timestamp actualTs = new Timestamp(ts);
        return actualTs.before(deadline);
    }

    /**
     *  The method udpdates the timestamp of the record
     */
    public void updateDeadline(){
        long ts = System.currentTimeMillis();
        deadline = new Timestamp(ts + TTL);
    }

}

