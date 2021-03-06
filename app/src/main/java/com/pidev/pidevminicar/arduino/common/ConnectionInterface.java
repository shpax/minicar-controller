package com.pidev.pidevminicar.arduino.common;

/**
 * Created by max on 08.11.16.
 */
public interface ConnectionInterface {

    void connect();

    void disconnect();

    boolean isConnected();

    void sendBytes(byte[] data);

    void setOnDataReceivedListener(OnDataReceivedListener listener);

    void setOnConnectedListener(OnConnectedListener listener);

}
