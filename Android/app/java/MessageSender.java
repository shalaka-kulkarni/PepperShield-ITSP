package com.mycompany.peppershield;

import android.telephony.SmsManager;

/**
 * Created by Admin on 5/25/2015.
 */
public class MessageSender {

    String emergency[][] = {{"Police Helpline", "7738144144"}, {"Police Helpline", "7738133133"}, {"Women's Helpline", "9969777888"}};
    String message = "HELP! I am in trouble. ";

    void sendSMS() {
        SmsManager smsMgr = SmsManager.getDefault();

        for(int i=0;i<emergency.length;i++) {
            smsMgr.sendTextMessage(emergency[i][1], null, message, null, null);
        }

    }


}
