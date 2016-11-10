package com.pidev.pidevminicar.arduino;

/**
 * Created by max on 08.11.16.
 */
public interface OnDataReceivedListener {

    void onData(int bytesCount, byte[] data);
}
