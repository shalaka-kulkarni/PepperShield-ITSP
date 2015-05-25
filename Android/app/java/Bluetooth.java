package com.mycompany.peppershield;

import android.bluetooth.*;
import android.content.Intent;

import java.io.*;
import java.util.*;


/**
 * Created by Shalaka on 5/25/2015.
 */
class Bluetooth {

    BluetoothAdapter mBluetoothAdapter;
    BluetoothServerSocket mmServerSocket;
    BluetoothSocket socket;

    void setupBT() {
        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        /*if (!mBluetoothAdapter.isEnabled()) {
            Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            int REQUEST_ENABLE_BT=1;
            startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);
        }*/
    }

    void runBT() {

        UUID mDeviceUUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");
        try {
            mmServerSocket = mBluetoothAdapter.listenUsingRfcommWithServiceRecord("HC-05", mDeviceUUID);
        } catch (IOException e) { }

        // Keep listening until exception occurs or a socket is returned
        while (true) {
            try {
                socket = mmServerSocket.accept();
            } catch (IOException e) {
                break;
            }
            // If a connection was accepted
            if (socket != null) {
                manageConnectedSocket();
                try {
                    mmServerSocket.close();
                } catch(IOException e)  { }
                break;
            }
        }

    }

    void manageConnectedSocket() {
        BluetoothReceiver btr = new BluetoothReceiver(socket);
        btr.getImage();
    }
}
