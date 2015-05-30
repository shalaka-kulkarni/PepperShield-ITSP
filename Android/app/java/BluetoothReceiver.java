package com.mycompany.debugtrial2;

import android.bluetooth.*;
import android.graphics.*;

import java.io.*;

/**
 * Created by Shalaka on 5/25/2015.
 */
public class BluetoothReceiver extends Thread {

    BluetoothSocket socket;
    InputStream in;
    byte incomingArray[];

    static int ctr = 1;


    BluetoothReceiver(BluetoothSocket mSocket) {
        socket = mSocket;
        try {
            in = socket.getInputStream();
        } catch (IOException e) { }
    }

    int read() {

        int size=640*480*2 + 2; //640x480 pix screen resolution, with 1 pixel compressed to 2 bytes
        incomingArray = new byte[size];
        int noOfBytes = 0;
        try {
            noOfBytes = in.read(incomingArray);
        } catch (IOException e) { }

        return noOfBytes;
    }

    void getImage() {

        Bitmap bitmapimage = BitmapFactory.decodeByteArray(incomingArray, 0, incomingArray.length);
        String filepath = "/image"+ctr+".jpeg";
        ctr++;

        try {
            File imgfile = new File(filepath);
            FileOutputStream fout = new FileOutputStream(imgfile);
            bitmapimage.compress(Bitmap.CompressFormat.JPEG, 100, fout);
            fout.flush();
            fout.close();
        } catch (IOException e) { }

    }
}
