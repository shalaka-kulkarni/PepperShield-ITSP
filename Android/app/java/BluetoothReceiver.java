package com.mycompany.debugtrial2;

import android.bluetooth.*;
import android.graphics.*;
import android.os.Environment;
import android.util.Log;

import java.io.*;
import java.nio.ByteBuffer;

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

        int size=640*480*3; //640x480 pix screen resolution, with 1 pixel compressed to 3 bytes
/*
        int noOfBytes = 0;
        incomingArray = new byte[size];

        try {
            noOfBytes = in.read(incomingArray);
            Log.d("BluetoothReceiver", "Data received!");
        } catch (IOException e) {
            Log.d("BluetoothReceiver", "Data not received");
        }
        finally {
            Log.d("BluetoothReceiver", "Bytes read = "+noOfBytes);
        }

        return noOfBytes;
*/
        boolean smsSent = false;
        byte[] buffer = new byte[size];
        int bytesRead = 0;
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        try {
            int currentBytesRead;
            while ((currentBytesRead = in.read(buffer)) != -1 && bytesRead <= size+128) {
                if(!smsSent) {
                    MessageSender sms = new MessageSender();
                  //  sms.sendSMS();
                    Log.d("Bluetooth", "Message sent");
                    smsSent = true;
                }
                bytesRead += currentBytesRead;
                output.write(buffer, 0, currentBytesRead);
            }
            Log.d("BluetoothReceiver", "Data received!");
        }
        catch(IOException e) {
            Log.d("BluetoothReceiver", "Data not received");
        }

        incomingArray = new byte[size];
            //incomingArray = output.toByteArray();
            byte[] temp = output.toByteArray();
        try {
            for (int i = 0; i < incomingArray.length; i++) {
                incomingArray[i] = (byte) (temp[i] - 127 - 1);
            }
        }
        catch (ArrayIndexOutOfBoundsException a) { }

        Log.d("BluetoothReceiver", "Bytes read = "+bytesRead);
        Log.d("BluetoothReceiver", "incomingArray.length = "+incomingArray.length);

        /*if(bytesRead > 0)
            makeFile(output);
*/
        return bytesRead;
    }

    void makeFile(ByteArrayOutputStream baos) {


        try {
            ctr = getCounter();
            File myDir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), "PepperShield");
            myDir.mkdirs();
            File file = new File(myDir,"image"+ctr+".jpg");
            ctr++;
            FileOutputStream fout = new FileOutputStream(file);
            baos.writeTo(fout);
            fout.close();
            Log.d("BluetoothReceiver", "File saved!");
        } catch(IOException ioe) {
            // Handle exception here
            ioe.printStackTrace();
            Log.d("BluetoothReceiver", "File not saved");
        }
    }

    void getImage() {

       // Bitmap bitmapImage = BitmapFactory.decodeByteArray(incomingArray, 0, incomingArray.length);
        Bitmap bitmapImage = Bitmap.createBitmap(640, 480, Bitmap.Config.RGB_565);
        bitmapImage.copyPixelsFromBuffer(ByteBuffer.wrap(incomingArray));
        if(bitmapImage==null)
            Log.d("BluetoothReceiver","Bitmap is null");
        else
            Log.d("BluetoothReceiver","Bitmap created!");
        ctr = getCounter();

        try {
            File myDir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), "PepperShield");
            myDir.mkdirs();
            File file = new File(myDir,"image"+ctr+".jpeg");
            ctr++;
            FileOutputStream fout = new FileOutputStream(file);
            bitmapImage.compress(Bitmap.CompressFormat.JPEG, 100, fout);
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

    void printBytes() {
        for(int i=0;i<incomingArray.length;i++) {
            Log.d("Byte array data", " "+incomingArray[i]);
        }
    }
}
