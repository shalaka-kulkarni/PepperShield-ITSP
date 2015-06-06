package com.mycompany.debugtrial2;

import android.app.Service;
import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

public class StandbyService extends Service {
    public StandbyService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        if(!BluetoothAdapter.getDefaultAdapter().isEnabled()) {
            stopSelf();
        }

        Log.d("StandbyService", "Service started");
        Bluetooth bt = new Bluetooth();

        bt.run();

        return START_STICKY;
    }
}
