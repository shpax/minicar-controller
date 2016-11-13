package com.pidev.pidevminicar.arduino.common;

import java.io.IOException;
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

    public void sendData(byte[] data) {
        try {
            outputStream.write(data);
            outputStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        while (maintainConnection) {
            // todo: add sequence sending
        }
    }
}
