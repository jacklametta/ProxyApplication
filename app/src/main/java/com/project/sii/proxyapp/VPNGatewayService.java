package com.project.sii.proxyapp;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

public class VPNGatewayService extends Service {

    private static String TAG = "VPNGatewayService";

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    // It is used only when the service is instantiated
    @Override
    public void onCreate() {
        // TODO Auto-generated method stub
        super.onCreate();
        Log.d(TAG, "VPNGatewayService created");
        //this.stopSelf();
    }

    // It is used every time a client starts the service with startService() and
    // service is already instantiated
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        //TODO do something useful
        Log.d(TAG, "VPNGatewayService resumed");
        return Service.START_NOT_STICKY;
    }

    @Override
    public void onDestroy() {
        // TODO Auto-generated method stub
        super.onDestroy();
        Log.d(TAG, "VPNGatewayService destroyed");
    }


}
