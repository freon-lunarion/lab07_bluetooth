package com.example.antonio.lab07_bluetooth;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.BroadcastReceiver;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {


    Button btnTask_1, btnTask_2, btnTask_3, btnTask_4;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_main );

        btnTask_1 = findViewById( R.id.btnTask_1 );
        btnTask_1.setOnClickListener( this );
        btnTask_2 = findViewById( R.id.btnTask_2 );
        btnTask_2.setOnClickListener( this );
        btnTask_3 = findViewById( R.id.btnTask_3 );
        btnTask_3.setOnClickListener( this );
        btnTask_4 = findViewById( R.id.btnTask_4 );
        btnTask_4.setOnClickListener( this );


    }


    @Override
    public void onClick(View view) {
        Intent intent;
        switch (view.getId()) {

            case R.id.btnTask_1:
                intent = new Intent(this, Task1Activity.class);
                break;

            case R.id.btnTask_2:
                intent = new Intent(this, Task2Activity.class);
                break;

            case R.id.btnTask_3:
                intent = new Intent(this, Task3Activity.class);
                break;

            case R.id.btnTask_4:
                intent = new Intent(this, Task4Activity.class);
                break;

            default:
                intent = new Intent(this, Task1Activity.class);
                break;
        }
        startActivity(intent);
    }
}
