package com.pidev.pidevminicar.logic;

import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created by max on 08.11.16.
 */
public interface OnConnectedListener {

    void onConnected(boolean isConnected, InputStream in, OutputStream out);
}
