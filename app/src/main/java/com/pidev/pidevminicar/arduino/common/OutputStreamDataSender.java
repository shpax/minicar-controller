package com.pidev.pidevminicar.arduino.common;

import java.io.OutputStream;

/**
 * Created by max on 10.11.16.
 */
public class OutputStreamDataSender extends Thread {

    private OutputStream outputStream;
    private boolean maintainConnection;
    private OnDataReceivedListener listener;

    public OutputStreamDataSender(OutputStream outputStream) {
        this.outputStream = outputStream;
        maintainConnection = true;
    }

    public void close() {
        this.maintainConnection = false;
    }

    @Override
    public void run() {
        while (maintainConnection) {
            // todo: add sequence sending
        }
    }
}
