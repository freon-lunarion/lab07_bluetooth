package com.example.antonio.lab07_bluetooth;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.hardware.Sensor;
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

public class Task1Activity extends AppCompatActivity implements View.OnClickListener {
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

    private final BroadcastReceiver mReceiver = new BroadcastReceiver() {
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();

            Log.i("DEBUG", ": " + action);
            if (BluetoothDevice.ACTION_FOUND.equals(action)) {
                // A Bluetooth device was found
                // Getting device information from the intent
                BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                Log.i("DEBUG", "Device found: " + device.getName() + "; MAC " + device.getAddress());
                myList.add(device.getName() + "\n"
                        +device.getAddress());
            }
        }
    };

    // Register the broadcast receiver
    IntentFilter filter = new IntentFilter(BluetoothDevice.ACTION_FOUND);


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

        btn_status.setOnClickListener( this );
        btn_discovery.setOnClickListener( this );
        btn_send.setOnClickListener( this );

        myBTAdapter = BluetoothAdapter.getDefaultAdapter ();

        adapter = new ArrayAdapter<>(this,android.R.layout.simple_list_item_1,myList);
        ls_view.setAdapter(adapter);

        registerReceiver(mReceiver, filter);
        myBTAdapter.startDiscovery();


    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {

            case R.id.btn_status:
                    checkStatus();
                break;

            case R.id.btn_discovery:
                    discovery();
                break;
            case R.id.btn_send:

                break;
            default:

                break;
        }

    }

    private void discovery() {


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
