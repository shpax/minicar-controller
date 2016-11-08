package com.pidev.pidevminicar.logic;

/**
 * Created by max on 08.11.16.
 */
public interface ConnectionInterface {

    void connect();

    boolean isConnected();

    void sendBytes(byte[] data);

    void setOnDataReceivedListener(OnDataReceivedListener listener);

    void setOnConnectedListener(OnConnectedListener listener);

}
