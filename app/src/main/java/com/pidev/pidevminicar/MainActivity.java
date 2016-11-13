package com.pidev.pidevminicar;

import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.pidev.pidevminicar.arduino.common.InputStreamDataListener;
import com.pidev.pidevminicar.arduino.common.OnConnectedListener;
import com.pidev.pidevminicar.arduino.common.OutputStreamDataSender;
import com.pidev.pidevminicar.model.Minicar;

public class MainActivity extends AppCompatActivity implements OnConnectedListener {

    public static final String btMAC = "20:15:04:29:15:14";

    private BluetoothAdapter bluetoothAdapter;
    private Minicar minicar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if (!bluetoothAdapter.isEnabled()) {
            Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableBtIntent, 123);
        }

    }

    @Override
    protected void onResume() {
        super.onResume();

        if (minicar == null) {
            minicar = new Minicar(bluetoothAdapter.getRemoteDevice(btMAC));
            minicar.setOnConnectedListener(this);
            minicar.connect();
        }
    }

    @Override
    public void onConnected(boolean isConnected, InputStreamDataListener in, OutputStreamDataSender out) {
        if (isConnected) {
            startActivity(new Intent(this, MinicarMenuActivity.class));
        }
    }
}
