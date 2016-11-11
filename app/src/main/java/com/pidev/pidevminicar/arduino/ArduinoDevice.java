package com.pidev.pidevminicar.arduino;

import com.pidev.pidevminicar.arduino.common.ConnectionInterface;
import com.pidev.pidevminicar.arduino.common.OnDataReceivedListener;
import com.pidev.pidevminicar.arduino.common.ProtocolInterface;

import java.util.ArrayList;

public class ArduinoDevice implements OnDataReceivedListener {

    private ConnectionInterface connection;
    private ArrayList<OnDataReceivedListener> onDataReceivedListeners;
    private ProtocolInterface protocol;

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

    public void setProtocol(ProtocolInterface protocol) {
        this.protocol = protocol;
    }

    public void sendBytes(byte[] data) {
        if (this.protocol != null) {
            this.connection.sendBytes(protocol.makeDataPackage(data));
        } else {
            this.connection.sendBytes(data);
        }
    }

    public int setOnDataReceivedListener(OnDataReceivedListener listener) {
        onDataReceivedListeners.add(listener);
        return onDataReceivedListeners.indexOf(listener);
    }

    @Override
    public void onData(int bytesCount, byte[] data) {
        for (OnDataReceivedListener listener : onDataReceivedListeners) {
            listener.onData(bytesCount, data);
        }
    }
}
