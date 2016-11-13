package com.pidev.pidevminicar.arduino.bluetooth;

import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;

import com.pidev.pidevminicar.arduino.common.ConnectionInterface;
import com.pidev.pidevminicar.arduino.common.OnConnectedListener;
import com.pidev.pidevminicar.arduino.common.OnDataReceivedListener;
import com.pidev.pidevminicar.arduino.common.InputStreamDataListener;
import com.pidev.pidevminicar.arduino.common.OutputStreamDataSender;

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

    private InputStreamDataListener dataListener;
    private OutputStreamDataSender dataSender;

    private OnConnectedListener onConnectedListener;
    private OnDataReceivedListener onDataReceivedListener;

    public BtConnection(BluetoothDevice device) {
        this.device = device;
    }

    @Override
    public void run() {
        if (socket != null) {
            try {
                socket.connect();
                if (socket.isConnected()) {
                    dataListener = new InputStreamDataListener(socket.getInputStream(), this);
                    dataSender = new OutputStreamDataSender(socket.getOutputStream());
                    this.onConnected(true, dataListener, dataSender);
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
        if (dataSender != null) dataSender.sendData(data);
    }

    @Override
    public void setOnDataReceivedListener(OnDataReceivedListener listener) {
        onDataReceivedListener = listener;
    }

    @Override
    public void setOnConnectedListener(OnConnectedListener listener) {
        onConnectedListener = listener;
    }

    @Override
    public void onData(int bytesCount, byte[] data) {
        if (onDataReceivedListener != null) onDataReceivedListener.onData(bytesCount, data);
    }

    @Override
    public void onConnected(boolean isConnected, InputStreamDataListener in, OutputStreamDataSender out) {
        if (onConnectedListener != null) onConnectedListener.onConnected(isConnected, in, out);
    }
}
