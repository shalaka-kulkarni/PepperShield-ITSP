package com.mycompany.debugtrial2;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;


/**
 * Created by Shalaka on 5/25/2015.
 */
public class StandbyIntentService extends IntentService {

    public StandbyIntentService() {
        super("StandbyIntentService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {

        Log.d("StandbyIntentService","Intent fired");
        Bluetooth bt = new Bluetooth();
        bt.connectAsClient();
        boolean trigger = bt.run();

        if(trigger) {
            MessageSender sms = new MessageSender();
            sms.sendSMS();
            Log.d("StandbyIntentService","Message sent");
        }

    }
}
