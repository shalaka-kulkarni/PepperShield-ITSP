package com.mycompany.debugtrial2;

import android.content.Intent;
import android.net.Uri;
import android.telephony.SmsManager;

import java.io.File;

/**
 * Created by Shalaka on 5/25/2015.
 */
public class MessageSender {

    String contacts[][];
    MessageSender()
    {
        EmergencyContacts init=new EmergencyContacts();
       init.updateList();
        for (int i=0;i<init.EmergencyContactsName.size();i++)
        {   EmergencyContacts.Person p=init.EmergencyContactsName.get(i);
            contacts[i][0]=p.name;
            contacts[i][1]=p.phoneNumber;
        }
    }

    void sendSMS() {

        String message = "Testing PepperShield app";

        SmsManager smsMgr = SmsManager.getDefault();

        for(int i=0;i< contacts.length;i++) {
            smsMgr.sendTextMessage(contacts[i][1], null, message, null, null);
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

        sendIntent.putExtra("address", contacts[i][1]);
        sendIntent.putExtra("sms_body", message);
        sendIntent.putExtra(Intent.EXTRA_STREAM, uri);
        sendIntent.setType("image/jpg");
        sendIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        return sendIntent;
    }


}

