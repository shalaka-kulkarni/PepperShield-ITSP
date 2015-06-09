package com.mycompany.debugtrial2;

import android.bluetooth.*;
import android.util.Log;

import java.io.*;
import java.util.*;


/**
 * Created by Shalaka on 5/25/2015.
 */
public class Bluetooth implements Runnable {

    BluetoothAdapter mBluetoothAdapter;
    BluetoothServerSocket mServerSocket;
    BluetoothSocket socket;
    BluetoothDevice mDevice;

    public Bluetooth() {
        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
    }

    void connectAsServer() {

        boolean receivedImage  = false;
        UUID mDeviceUUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");
        try {
            mServerSocket = mBluetoothAdapter.listenUsingRfcommWithServiceRecord("PepperShield", mDeviceUUID);
        } catch (IOException e) { }

        // Keep listening until exception occurs or a socket is returned
       while (true) {
           try {
               socket = mServerSocket.accept();
           } catch (IOException e) {
               break;
           }
       }
        try {
            mServerSocket.close();
        } catch (IOException e) { }
    }

    void connectAsClient() {

        //mDevice = mBluetoothAdapter.getRemoteDevice("98:D3:31:80:3D:3B");

        Set<BluetoothDevice> pairedDevices = mBluetoothAdapter.getBondedDevices();

        if (pairedDevices.size() > 0) {

            for (BluetoothDevice device : pairedDevices) {
                if(device.getName().contains("HC-05")) {
                    mDevice = device;
                    break;
                }
            }
        }

        UUID mDeviceUUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");
        try {
            socket = mDevice.createRfcommSocketToServiceRecord(mDeviceUUID);
            Log.d("Bluetooth","Device found!");
        }
        catch (Exception e) {
            Log.d("Bluetooth","Device not found.");
        }

        try {
            socket.connect();
            Log.d("Bluetooth", "Bluetooth connection successful!");
        } catch (IOException connectException) {
            // Unable to connect; close the socket and get out
            try {
                socket.close();
                Log.d("Bluetooth","Bluetooth connection unsuccessful. Socket closed.");
            }
            catch (IOException closeException) {
                Log.d("Bluetooth","Socket closure unsuccessful.");
            }
        }

    }

    boolean runBT() {

        int noOfBytes = 0;

        if (socket != null) {
            BluetoothReceiver btr = new BluetoothReceiver(socket);
            noOfBytes = btr.read();
            btr.getImage();
        }

        if(noOfBytes > 0)
            return true;
        else
            return false;
    }

    @Override
    public void run() {
        // Moves the current Thread into the background
        android.os.Process.setThreadPriority(android.os.Process.THREAD_PRIORITY_BACKGROUND);

        connectAsClient();
        boolean trigger = runBT();
        if(trigger) {
            MessageSender sms = new MessageSender();
           // sms.sendSMS();
            Log.d("Bluetooth", "Message sent");
        }

    }

}
