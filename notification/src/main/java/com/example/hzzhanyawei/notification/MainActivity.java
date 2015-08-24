package com.example.hzzhanyawei.notification;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button bt_notify;
    private Button bt_sms;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bt_notify = (Button) findViewById(R.id.bt_notify);
        bt_sms = (Button) findViewById(R.id.bt_SMS);
        bt_notify.setOnClickListener(this);
        bt_sms.setOnClickListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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
            case R.id.bt_notify:
                NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
                Notification notification = new Notification(R.drawable.ic_launcher, "This is a test notify!", System.currentTimeMillis());//notify一闪而过的通知，第三个参数为通知出现的时间。
                //notification.setLatestEventInfo(this,"This is a test", "我是通知内容", null);//下拉标题栏显示的内容，第四个参数为点击通知后的响应
                Intent intent = new Intent(this, Main2Activity.class);
                PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_CANCEL_CURRENT);//getActivity()、getBroadcast()、还是getService()根据Intent传入数据而定。
                notification.setLatestEventInfo(this,"This is a test", "我是通知内容", pendingIntent);
                manager.notify(1, notification);//启动通知，第一个参数为通知的Id
                break;

            case R.id.bt_SMS:
                Intent in = new Intent(this, SMS.class);
                startActivity(in);
                break;
            default:
                break;
        }
    }
}
