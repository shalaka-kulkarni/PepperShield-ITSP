package com.mycompany.debugtrial2;

import android.bluetooth.*;
import android.graphics.*;
import android.os.Environment;
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

    public BluetoothReceiver() {
        ctr = 0;
    }

    public BluetoothReceiver(BluetoothSocket mSocket) {
        socket = mSocket;
        try {
            in = socket.getInputStream();
            Log.d("BluetoothReceiver","Input stream received");
        }
        catch (Exception e) {
            e.getStackTrace();
            Log.d("BluetoothReceiver", "Input stream not received");
        }
    }
    int read() {

        int size=640*480*3 + 2; //640x480 pix screen resolution, with 1 pixel compressed to 3 bytes
/*        int noOfBytes = 0;
        incomingArray = new byte[size];

        try {
            noOfBytes = in.read(incomingArray);
            Log.d("BluetoothReceiver", "Data received!");
            Log.d("BluetoothReceiver", "Bytes read = "+noOfBytes);
        } catch (IOException e) {
            Log.d("BluetoothReceiver", "Data not received");
        }

        return noOfBytes;*/

        byte[] buffer = new byte[size];
        int bytesRead = -2;
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        try {
            while ((bytesRead = in.read(buffer)) != -1) {
                output.write(buffer, 0, bytesRead);
            }
            Log.d("BluetoothReceiver", "Data received!");
        }
        catch(IOException e) {
            Log.d("BluetoothReceiver", "Data not received");
        }

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

        //String filepath = "/sdcard/bluetooth/image"+ctr+".jpeg";

        try {
            File myDir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), "PepperShield");
            myDir.mkdirs();
            File file = new File(myDir,"image"+ctr+".jpeg");
            ctr++;
            FileOutputStream fout = new FileOutputStream(file);
            //bitmapImage.compress(Bitmap.CompressFormat.JPEG, 100, fout);
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
                File myDir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), "PepperShield");
                myDir.mkdirs();
                File file = new File(myDir,"image"+count+".jpeg");
                FileInputStream fileInputStream = new FileInputStream(file);
                count ++;
            }
            catch(FileNotFoundException f) {
                return count;
            }
        }
    }
}
