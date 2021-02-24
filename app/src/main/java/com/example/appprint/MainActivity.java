package com.example.appprint;

import androidx.appcompat.app.AppCompatActivity;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.os.Bundle;
import android.os.ParcelUuid;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Set;

public class MainActivity extends AppCompatActivity {
    private OutputStream outputStream;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    public void my_click(View v)throws IOException {
        Toast.makeText(this,"开始发送,打印指令。蓝牙打印开始",Toast.LENGTH_LONG).show();
        init();
        Toast.makeText(this,"结束发送，打印指令。蓝牙打印结束",Toast.LENGTH_LONG).show();
    }
    public void init() throws IOException {
        BluetoothAdapter blueAdapter = BluetoothAdapter.getDefaultAdapter();
        if (blueAdapter != null) {
            if (blueAdapter.isEnabled()) {
                Set<BluetoothDevice> bondedDevices = blueAdapter.getBondedDevices();

                if(bondedDevices.size() > 0) {
                    Object[] devices = (Object []) bondedDevices.toArray();
                    BluetoothDevice device = (BluetoothDevice) devices[0];
                    ParcelUuid[] uuids = device.getUuids();
                    BluetoothSocket socket = device.createRfcommSocketToServiceRecord(uuids[0].getUuid());
                    socket.connect();
                    outputStream = socket.getOutputStream();
                    send2Print();
                }

                Log.e("error", "No appropriate paired devices.");
            } else {
                Log.e("error", "Bluetooth is disabled.");
            }
        }
    }

    public void send2Print() throws IOException {
        String s="^XA\n" +
                "^FT416,276^A0I,34,33^FH\\^FDwanghongjun2021^FS\n" +
                "^FT301,260^BQN,2,4^FH\\^FDMA甲晟科技蓝牙打印二维码^FS\n" +
                "^PQ2^XZ\n";
        outputStream.write(s.getBytes());

    }
}
