package com.mycompany.debugtrial2;

import android.bluetooth.*;
import android.graphics.*;
import android.util.Log;

import java.io.*;

/**
 * Created by Shalaka on 5/25/2015.
 */
public class BluetoothReceiver extends Thread {

    BluetoothSocket socket;
    InputStream in;
    byte incomingArray[];

    static int ctr = 1;

    public BluetoothReceiver(BluetoothSocket mSocket) {
        socket = mSocket;
        try {
            in = socket.getInputStream();
            Log.d("BluetoothReceiver","Input stream received");
        }
        catch (IOException e) {
            Log.d("BluetoothReceiver", "Input stream not received");
        }
    }
    int read() {

        int size=640*480 + 2; //640x480 pix screen resolution, with 1 pixel compressed to 3 bytes
        incomingArray = new byte[size];
        int noOfBytes = 0;
        try {
            noOfBytes = in.read(incomingArray);
            Log.d("BluetoothReceiver", "Data received!");
            Log.d("BluetoothReceiver", "Bytes read = "+noOfBytes);
        } catch (IOException e) {
            Log.d("BluetoothReceiver", "Data not received");
        }

        return noOfBytes;
    }

    void getImage() {

        Bitmap bitmapImage = BitmapFactory.decodeByteArray(incomingArray, 0, incomingArray.length);
        if(bitmapImage==null)
            Log.d("BluetoothReceiver","Bitmap is null");
        else
            Log.d("BluetoothReceiver","Bitmap is not null");

        String filepath = "/sdcard/bluetooth/image"+ctr+".jpeg";
        ctr++;

        try {
            FileOutputStream fout = new FileOutputStream(filepath);
            bitmapImage.compress(Bitmap.CompressFormat.JPEG, 100, fout);
            fout.flush();
            fout.close();
            Log.d("BluetoothReceiver", "Image saved!");
        } catch (IOException e) {
            Log.d("BluetoothReceiver", "Image not saved");
        }

    }
}
