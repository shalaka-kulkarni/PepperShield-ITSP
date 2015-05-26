package com.mycompany.peppershield;

import android.app.IntentService;
import android.content.Intent;
import android.content.Context;

public class StandbyIntentService extends IntentService {

    public StandbyIntentService(String name) {
        super(name);
    }

    @Override
    protected void onHandleIntent(Intent workIntent) {

        Bluetooth bt = new Bluetooth();
        boolean receivedImage = bt.runBT();
        if(receivedImage) {
            buildGoogleApiClient();

            (new MessageSender()).sendSMS();
        }
    }


}
