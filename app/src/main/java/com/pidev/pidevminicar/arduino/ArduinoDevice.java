package com.pidev.pidevminicar.arduino;

import com.pidev.pidevminicar.arduino.common.ConnectionInterface;
import com.pidev.pidevminicar.arduino.common.InputStreamDataListener;
import com.pidev.pidevminicar.arduino.common.OnConnectedListener;
import com.pidev.pidevminicar.arduino.common.OnDataReceivedListener;
import com.pidev.pidevminicar.arduino.common.OutputStreamDataSender;
import com.pidev.pidevminicar.arduino.common.ProtocolInterface;

import java.util.ArrayList;

public class ArduinoDevice implements OnDataReceivedListener, OnConnectedListener {

    private ConnectionInterface connection;
    private ProtocolInterface protocol;

    private OnDataReceivedListener onDataReceivedListener;
    private OnConnectedListener onConnectedListener;

    public ArduinoDevice() {
        setProtocol(new DefaultProtocol());
    }

    public ArduinoDevice(ConnectionInterface connection) {
        this();
        setConnection(connection);
    }

    public static byte toByte(int val) {

        if (val >= 255) return (byte) 0b11111111;
        if (val <= 0) return 0;

        byte result = (byte) (val & 127);
        if (val > 127) result |= 1 << 7;

//        System.out.println("value: " + val + ";\t parsed: " + result);

        return result;
    }

    public void connect() {
        if (this.connection != null) connection.connect();
    }

    public void sendBytes(byte[] data) {
        connection.sendBytes(protocol.makeDataPackage(data));
    }

    public void setConnection(ConnectionInterface connection) {
        this.connection = connection;
        this.connection.setOnDataReceivedListener(this);
        this.connection.setOnConnectedListener(this);
    }

    public void setProtocol(ProtocolInterface protocol) {
        this.protocol = protocol;
    }

    public void setOnDataReceivedListener(OnDataReceivedListener listener) {
        onDataReceivedListener = listener;
    }

    public void setOnConnectedListener(OnConnectedListener onConnectedListener) {
        this.onConnectedListener = onConnectedListener;
    }

    @Override
    public void onData(int bytesCount, byte[] data) {
        if (onDataReceivedListener != null) onDataReceivedListener.onData(bytesCount, data);
    }

    @Override
    public void onConnected(boolean isConnected, InputStreamDataListener in, OutputStreamDataSender out) {
        if (this.onConnectedListener != null) onConnectedListener.onConnected(isConnected, in, out);
    }

    private class DefaultProtocol implements ProtocolInterface {

        @Override
        public byte[] makeDataPackage(byte[] data) {
            return data;
        }
    }
}
