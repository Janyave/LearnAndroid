package com.example.hzzhanyawei.notification;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.Objects;


public class SMS extends AppCompatActivity {

    private TextView tv_sender;
    private TextView tv_content;

    private MySMSReceiver receiver;
    private IntentFilter filter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sms);

        tv_sender = (TextView) findViewById(R.id.tv_sender);
        tv_content = (TextView) findViewById(R.id.tv_content);

        filter = new IntentFilter();
        filter.addAction("android.provider.Telephony.SMS_RECEIVED");
        receiver = new MySMSReceiver();
        registerReceiver(receiver, filter);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_sm, menu);
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
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(receiver);
    }

    class  MySMSReceiver extends BroadcastReceiver{

        @Override
        public void onReceive(Context context, Intent intent) {
            Bundle bundle = intent.getExtras();
            Objects[] pdus = (Objects[]) bundle.get("puts");
            SmsMessage[] messages = new SmsMessage[pdus.length];
            for(int i = 0; i < messages.length; i++){
                messages[i] = SmsMessage.createFromPdu(toByteArray(pdus[i]));
            }
            String addr = messages[0].getOriginatingAddress();
            String content = "";
            for(SmsMessage message : messages){
                content += message.getMessageBody();
            }
            tv_sender.setText(addr);
            tv_content.setText(content);
        }
        public byte[] toByteArray (Object obj) {//对象序列化
            byte[] bytes = null;
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            try {
                ObjectOutputStream oos = new ObjectOutputStream(bos);
                oos.writeObject(obj);
                oos.flush();
                bytes = bos.toByteArray ();
                oos.close();
                bos.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            return bytes;
        }
    }
}










