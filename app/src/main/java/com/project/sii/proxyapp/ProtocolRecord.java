package com.project.sii.proxyapp;
import java.sql.Timestamp;

public class ProtocolRecord {

    private static long TTL = 60000L; /* millis */
    private Timestamp deadline;

    ProtocolRecord(){
        updateDeadline();
    }

    public boolean isValid(){
        long ts = System.currentTimeMillis();
        Timestamp actualTs = new Timestamp(ts);
        return actualTs.before(deadline);
    }

    public void updateDeadline(){
        long ts = System.currentTimeMillis();
        deadline = new Timestamp(ts + TTL);
    }

}

