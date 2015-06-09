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

        //bt.run();

        boolean flag = false;
        while(!flag) {
            bt.connectAsClient();
            boolean trigger = bt.runBT();
            if (trigger) {
                MessageSender sms = new MessageSender();
                // sms.sendSMS();
                Log.d("Bluetooth", "Message sent");
                //sendImage();
                Log.d("Bluetooth", "MMS initiated");
                flag = true;
            }
        }

        return START_STICKY;
    }

    void sendImage() {

        MessageSender mms = new MessageSender();

        for(int i=0;i<mms.contacts.length;i++) {
            int ctr = new BluetoothReceiver().getCounter();
            ctr -- ;
            Intent sendIntent = mms.getMMSIntent(i, ctr);
            getApplicationContext().startActivity(sendIntent);
        }
    }

}
