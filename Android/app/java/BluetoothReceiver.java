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

    static int ctr;

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

        int size=640*480*3 + 2; //640x480 pix screen resolution, with 1 pixel compressed to 3 bytes
        int noOfBytes = 0;
       /* incomingArray = new byte[size];

        try {
            noOfBytes = in.read(incomingArray);
            Log.d("BluetoothReceiver", "Data received!");
            Log.d("BluetoothReceiver", "Bytes read = "+noOfBytes);
        } catch (IOException e) {
            Log.d("BluetoothReceiver", "Data not received");
        }

        return noOfBytes; */

        byte[] buffer = new byte[size];
        int bytesRead = -2;
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        try {
            while ((bytesRead = in.read(buffer)) != -1) {
                output.write(buffer, 0, bytesRead);
            }
            Log.d("BluetoothReceiver", "Data received!");
        }
        catch(IOException e) { }

        incomingArray = output.toByteArray();

        Log.d("BluetoothReceiver", "Bytes read = "+bytesRead);
        Log.d("BluetoothReceiver", "incomingArray.length = "+incomingArray.length);

        return bytesRead;
    }

    void getImage() {

        Bitmap bitmapImage = BitmapFactory.decodeByteArray(incomingArray, 0, incomingArray.length);
        if(bitmapImage==null)
            Log.d("BluetoothReceiver","Bitmap is null");
        else
            Log.d("BluetoothReceiver","Bitmap is not null");

        ctr = getCounter();

        String filepath = "/sdcard/bluetooth/image"+ctr+".jpeg";
        ctr++;

        try {
            FileOutputStream fout = new FileOutputStream(filepath);
//            bitmapImage.compress(Bitmap.CompressFormat.JPEG, 100, fout);
            fout.flush();
            fout.close();
            Log.d("BluetoothReceiver", "Image saved!");
        } catch (IOException e) {
            Log.d("BluetoothReceiver", "Image not saved");
        }

    }

    int getCounter() {

        int count = 1;
        while(true) {

            try {
                FileInputStream file = new FileInputStream("/sdcard/bluetooth/image"+count+".jpeg");
                count ++;
            }
            catch(FileNotFoundException f) {
                return count;
            }
        }
    }
}
