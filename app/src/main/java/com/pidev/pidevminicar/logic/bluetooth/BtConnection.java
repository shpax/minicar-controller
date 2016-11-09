package com.pidev.pidevminicar.logic.bluetooth;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;

import com.pidev.pidevminicar.logic.ConnectionInterface;
import com.pidev.pidevminicar.logic.OnConnectedListener;
import com.pidev.pidevminicar.logic.OnDataReceivedListener;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.UUID;

/**
 * Created by max on 08.11.16.
 */
public class BtConnection extends Thread implements ConnectionInterface, OnConnectedListener, OnDataReceivedListener {

    private static final UUID MY_UUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");

    private BluetoothDevice device;
    private BluetoothSocket socket;

    public BtConnection(BluetoothDevice device) {
        this.device = device;
    }

    @Override
    public void run() {
        if (socket != null) {
            try {
                socket.connect();
                if (socket.isConnected()) {
                    this.onConnected(true, socket.getInputStream(), socket.getOutputStream());
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void connect() {
        try {
            socket = device.createRfcommSocketToServiceRecord(MY_UUID);
            this.start();
        } catch (IOException e) {
            socket = null;
        }
    }

    @Override
    public void disconnect() {
        if (socket != null && socket.isConnected()) try {
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean isConnected() {
        return socket != null && socket.isConnected();
    }

    @Override
    public void sendBytes(byte[] data) {

    }

    @Override
    public void setOnDataReceivedListener(OnDataReceivedListener listener) {

    }

    @Override
    public void setOnConnectedListener(OnConnectedListener listener) {

    }

    @Override
    public void onData(byte[] data) {

    }

    @Override
    public void onConnected(boolean isConnected, InputStream in, OutputStream out) {

    }

    protected class SocketConnectionListener extends Thread {

        private InputStream inputStream;
        private boolean maintainConnection;
        private OnDataReceivedListener listener;

        public SocketConnectionListener(InputStream inputStream) {
            this.inputStream = inputStream;
            maintainConnection = true;
        }

        public SocketConnectionListener(InputStream inputStream, OnDataReceivedListener listener) {
            this(inputStream);
            setOnDataReceivedListener(listener);
        }

        public void setOnDataReceivedListener(OnDataReceivedListener listener) {
            this.listener = listener;
        }

        @Override
        public void run() {
            byte[] buffer = new byte[1024];
            int bytesCount;

            while (maintainConnection) {
                try {
                    bytesCount = inputStream.read(buffer);
                    if (listener != null) listener.onData(buffer)
                } catch (IOException e) {
                    e.printStackTrace();
                    break;
                }
            }
        }
    }

}
