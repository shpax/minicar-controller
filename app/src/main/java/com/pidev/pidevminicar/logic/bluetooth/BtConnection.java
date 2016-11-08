package com.pidev.pidevminicar.logic.bluetooth;

import com.pidev.pidevminicar.logic.ConnectionInterface;
import com.pidev.pidevminicar.logic.OnConnectedListener;
import com.pidev.pidevminicar.logic.OnDataReceivedListener;

/**
 * Created by max on 08.11.16.
 */
public class BtConnection implements ConnectionInterface, OnDataReceivedListener {

    @Override
    public void connect() {

    }

    @Override
    public boolean isConnected() {
        return false;
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
}
