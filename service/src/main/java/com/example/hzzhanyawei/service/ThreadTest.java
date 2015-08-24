package com.example.hzzhanyawei.service;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.FileInputStream;

public class ThreadTest extends AppCompatActivity implements View.OnClickListener {

    private static final int UPDATE_TEXT = 1;
    private Handler handler;

    private Button bt_thread;
    private TextView tv_content;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thread_test);

        bt_thread = (Button) findViewById(R.id.bt_start_thread);
        tv_content = (TextView) findViewById(R.id.tv_content);

        bt_thread.setOnClickListener(this);

        handler = new Handler(){
            public void handleMessage(Message msg) {
                switch (msg.what){
                    case UPDATE_TEXT:
                        tv_content.setText("你好！");
                        break;
                    default:
                        break;
                }
            }
        };

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_thread_test, menu);
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
            case R.id.bt_start_thread:
                new Thread(new Runnable(){
                    @Override
                    public void run() {
                        Message msg = new Message();
                        msg.what = UPDATE_TEXT;
                        handler.sendMessage(msg);
                    }
                }).start();
                break;
            default:
                break;
        }
    }
}
