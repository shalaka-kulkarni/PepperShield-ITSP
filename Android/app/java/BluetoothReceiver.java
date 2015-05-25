package com.mycompany.peppershield;

import android.bluetooth.*;
import android.graphics.*;

import java.io.*;

/**
 * Created by Admin on 5/25/2015.
 */
public class BluetoothReceiver extends Thread {

    BluetoothSocket socket;
    InputStream in;

    static int ctr = 1;


    BluetoothReceiver(BluetoothSocket mSocket) {
        socket = mSocket;
        try {
            in = socket.getInputStream();
        } catch (IOException e) { }
    }

    byte[] read() {

        int size=640*480*2 + 2; //640x480 pix screen resolution, with 1 pixel compressed to 2 bytes
        byte[] arr = new byte[size];
        try {
            int noOfBytes = in.read(arr);
        } catch (IOException e) { }

        return arr;
    }

    void getImage() {

        byte[] incomingByteArray = read();

        Bitmap bitmapimage = BitmapFactory.decodeByteArray(incomingByteArray, 0, incomingByteArray.length);
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
