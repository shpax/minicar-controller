package com.pidev.pidevminicar.logic;

import java.util.ArrayList;

public class ArduinoDevice implements OnDataReceivedListener{

    private ConnectionInterface connection;
    private ArrayList<OnDataReceivedListener> onDataReceivedListeners;

    public ArduinoDevice() {
        this.onDataReceivedListeners = new ArrayList<>();
    }

    public ArduinoDevice(ConnectionInterface connection) {
        this();
        setConnection(connection);
    }

    public void setConnection(ConnectionInterface connection) {
        this.connection = connection;
        this.connection.setOnDataReceivedListener(this);
    }

    public void sendBytes(byte[] data) {
        this.connection.sendBytes(data);
    }

    public int setOnDataReceivedListener(OnDataReceivedListener listener) {
        onDataReceivedListeners.add(listener);
        return onDataReceivedListeners.indexOf(listener);
    }

    @Override
    public void onData(byte[] data) {
        for (OnDataReceivedListener listener : onDataReceivedListeners) {
            listener.onData(data);
        }
    }
}
