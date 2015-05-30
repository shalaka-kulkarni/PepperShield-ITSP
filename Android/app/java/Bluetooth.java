package com.mycompany.debugtrial2;

import android.bluetooth.*;
import android.util.Log;

import java.io.*;
import java.util.*;


/**
 * Created by Shalaka on 5/25/2015.
 */
public class Bluetooth {

    BluetoothAdapter mBluetoothAdapter;
    BluetoothServerSocket mServerSocket;
    BluetoothSocket socket;
    BluetoothDevice mDevice;

    public Bluetooth() {
        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        /*if (!mBluetoothAdapter.isEnabled()) {
            Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            int REQUEST_ENABLE_BT=1;
            startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);
        }*/
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

        mDevice = mBluetoothAdapter.getRemoteDevice("98:D3:31:80:3D:3B");
        UUID mDeviceUUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");
        try {
            socket = mDevice.createRfcommSocketToServiceRecord(mDeviceUUID);
            Log.d("Bluetooth","Device found!");
        }
        catch (IOException e) {
            Log.d("Bluetooth","Device not found.");
        }

        try {
            socket.connect();
            Log.d("Bluetooth","Bluetooth connection successful!");
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

    boolean run() {

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

}
