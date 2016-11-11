package com.pidev.pidevminicar.arduino.common;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by max on 10.11.16.
 */
public class InputStreamDataListener extends Thread {

    private InputStream inputStream;
    private boolean maintainConnection;
    private OnDataReceivedListener listener;

    public InputStreamDataListener(InputStream inputStream) {
        this.inputStream = inputStream;
        maintainConnection = true;
    }

    public InputStreamDataListener(InputStream inputStream, OnDataReceivedListener listener) {
        this(inputStream);
        setOnDataReceivedListener(listener);
    }

    public void setOnDataReceivedListener(OnDataReceivedListener listener) {
        this.listener = listener;
    }

    public void stopListening() {
        this.maintainConnection = false;
    }

    @Override
    public void run() {
        byte[] buffer = new byte[1024];
        int bytesCount;

        while (maintainConnection) {
            try {
                bytesCount = inputStream.read(buffer);
                if (listener != null) listener.onData(bytesCount, buffer);
            } catch (IOException e) {
                e.printStackTrace();
                break;
            }
        }
    }
}

