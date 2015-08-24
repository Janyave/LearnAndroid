package com.example.hzzhanyawei.service;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

public class ServiceTest extends AppCompatActivity implements View.OnClickListener {

    public final static String DEBUG = "ServiceTest";

    private Button bt_start;
    private Button bt_stop;
    private Button bt_bind;
    private Button bt_unbind;

    private ServiceConnection connection;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_test);

        bt_start = (Button) findViewById(R.id.bt_start);
        bt_stop = (Button) findViewById(R.id.bt_stop);
        bt_bind = (Button) findViewById(R.id.bt_bind);
        bt_unbind = (Button) findViewById(R.id.bt_unbind);

        bt_start.setOnClickListener(this);
        bt_stop.setOnClickListener(this);
        bt_bind.setOnClickListener(this);
        bt_unbind.setOnClickListener(this);

        connection = new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName name, IBinder service) {
                MyService.MyBinder binder = (MyService.MyBinder) service;
                binder.startDownload();
                binder.getProgress();
            }

            @Override
            public void onServiceDisconnected(ComponentName name) {

            }
        };
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_service_test, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.bt_start:
                Intent startInent = new Intent(this, MyService.class);
                startService(startInent);
                break;
            case R.id.bt_stop:
                Intent stopInent = new Intent(this, MyService.class);
                stopService(stopInent);
                break;
            case R.id.bt_bind:
                Intent bindIntent = new Intent(this, MyService.class);
                bindService(bindIntent, connection, BIND_AUTO_CREATE);
                break;
            case R.id.bt_unbind:
                unbindService(connection);
                break;
            default:
                break;
        }

    }
}
