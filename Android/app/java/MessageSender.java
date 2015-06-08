package com.mycompany.debugtrial2;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.telephony.SmsManager;
import android.util.Log;

import java.io.File;
import java.util.List;

/**
 * Created by Shalaka on 5/25/2015.
 */
public class MessageSender {

    String emergency[][] = {/*{"Aai", "+91 9619745270"},*/ {"Shalaka", "+91 7506438504"}};

    void sendSMS() {

        String message = "Testing PepperShield app";

        SmsManager smsMgr = SmsManager.getDefault();

        for(int i=0;i<emergency.length;i++) {
            smsMgr.sendTextMessage(emergency[i][1], null, message, null, null);
        }

    }

    Intent getMMSIntent(int i) {


        Intent sendIntent = new Intent(Intent.ACTION_SEND);

        /*PackageManager pm = getPackageManager();
        List<ResolveInfo> resInfo = pm.queryIntentActivities(sendIntent, 0);
        for (int i = 0; i < resInfo.size(); i++) {
            ResolveInfo ri = resInfo.get(i);
            String packageName = ri.activityInfo.packageName;

            if(packageName.contains("Messaging")) {
                Log.d("TAG", packageName + " : " + ri.activityInfo.name);
                sendIntent.setComponent(new ComponentName(packageName, ri.activityInfo.name));
            }
        }*/
        String message = "Testing MMS sending";

        Uri uri = Uri.fromFile(new File("/sdcard/bluetooth/test.jpg"));

        sendIntent.putExtra("address", emergency[i][1]);
        sendIntent.putExtra("sms_body", message);
        sendIntent.putExtra(Intent.EXTRA_STREAM, uri);
        sendIntent.setType("image/jpg");
        sendIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        return sendIntent;
    }


}

