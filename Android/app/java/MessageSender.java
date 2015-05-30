package com.mycompany.debugtrial2;

import android.telephony.SmsManager;

/**
 * Created by Shalaka on 5/25/2015.
 */
public class MessageSender {

    String emergency[][] = {/*{"Aai", "+91 9619745270"},*/ {"Shalaka", "+91 7506438504"}};
    String message = "Testing PepperShield app";

    void sendSMS() {
        SmsManager smsMgr = SmsManager.getDefault();

        for(int i=0;i<emergency.length;i++) {
            smsMgr.sendTextMessage(emergency[i][1], null, message, null, null);
        }

    }


}

