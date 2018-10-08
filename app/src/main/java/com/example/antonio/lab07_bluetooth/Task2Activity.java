package com.example.antonio.lab07_bluetooth;

import android.Manifest;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.hardware.Sensor;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import static android.Manifest.permission.ACCESS_COARSE_LOCATION;
import static android.Manifest.permission.ACCESS_WIFI_STATE;
import static android.Manifest.permission.CHANGE_WIFI_STATE;

public class Task2Activity extends AppCompatActivity  {
    BluetoothAdapter myBTAdapter;
    BluetoothDevice myDevice;
    BluetoothSocket mySocket;
    // Create a BroadcastReceiver for ACTION_FOUND


    Button btn_status, btn_discovery, btn_send;
    TextView txt_status;
    ListView ls_view;
    EditText input_msg;

    private ArrayList<String> myList = new ArrayList<>();
    private ArrayAdapter adapter;



    // Register the broadcast receiver



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_task1 );

        btn_status = findViewById( R.id.btn_status );
        btn_discovery = findViewById( R.id.btn_discovery );
        btn_send = findViewById( R.id.btn_send );
        ls_view = findViewById(R.id.ls_view);
        input_msg = findViewById( R.id.input_msg );
        txt_status = findViewById( R.id.txt_status );

        myBTAdapter = BluetoothAdapter.getDefaultAdapter ();

        btn_status.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkStatus();
            }
        });

        btn_discovery.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                discovery(view);
            }
        });

        adapter = new ArrayAdapter<>(this,android.R.layout.simple_list_item_1,myList);
        ls_view.setAdapter(adapter);
        IntentFilter filter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
        registerReceiver(mReceiver, filter);



    }

    // Create a BroadcastReceiver for ACTION_FOUND.
    private final BroadcastReceiver mReceiver = new BroadcastReceiver() {
        public void onReceive(Context context, Intent intent) {

            String action = intent.getAction();
            if (BluetoothDevice.ACTION_FOUND.equals(action)) {
                // Discovery has found a device. Get the BluetoothDevice
                // object and its info from the Intent.
                BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                String deviceName = device.getName();
                String deviceHardwareAddress = device.getAddress(); // MAC address
                myList.add(deviceName + "\n" + deviceHardwareAddress);
            }
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();


        // Don't forget to unregister the ACTION_FOUND receiver.
        unregisterReceiver(mReceiver);
    }

    private void discovery(View view) {



        Intent discoverableIntent =
                new Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
        discoverableIntent.putExtra(BluetoothAdapter.EXTRA_DISCOVERABLE_DURATION, 300);
        startActivity(discoverableIntent);

        if (ContextCompat.checkSelfPermission(this, ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,new String[] {
                      ACCESS_COARSE_LOCATION
            },10);
        } else {
            myBTAdapter.startDiscovery();
        }




    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case 10:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    myBTAdapter.startDiscovery();
                } else {
                    if (ActivityCompat.shouldShowRequestPermissionRationale(this, ACCESS_WIFI_STATE)) {
                        new AlertDialog.Builder(this).setTitle("ACCESS_WIFI_STATE Permission").setMessage("ACCESS_WIFI_STATE Permission").show();
                    }
                }
        }
    }

    private void checkStatus() {
        if (myBTAdapter == null) {
            txt_status.setText( "This device doesn't support Bluetooth" );
            return;
        }

        if (!myBTAdapter.isEnabled()) {
            txt_status.setText( "Bluetooth Disable" );
        } else {
            txt_status.setText( "Bluetooth Enable" );
        }

    }

}
