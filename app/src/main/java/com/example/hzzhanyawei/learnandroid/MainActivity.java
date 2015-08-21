package com.example.hzzhanyawei.learnandroid;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private ListView lv_msg;
    private EditText et_msg;
    private Button bt_send;

    public List<Msg> msgs;
    private MsgAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lv_msg = (ListView) findViewById(R.id.msg_list_view);
        et_msg = (EditText) findViewById(R.id.input_text);
        bt_send = (Button) findViewById(R.id.send);

        initMsgs();
        lv_msg = (ListView) findViewById(R.id.msg_list_view);
        adapter = new MsgAdapter(this, msgs);
        lv_msg.setAdapter(adapter);
        bt_send.setOnClickListener(this);

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

    private void initMsgs(){
        msgs = new ArrayList<Msg>();
        msgs.add(new Msg(Msg.TYPE_REVD,"你好,我是个测试，我要打个长长的一串字试下.9图片，必须要很长啊"));
        msgs.add(new Msg(Msg.TYPE_SEND,"hello"));
        msgs.add(new Msg(Msg.TYPE_REVD,"你在干什么？"));
    }

    @Override
    public void onClick(View v) {
        String content = et_msg.getText().toString();
        if(content != null && !content.equals("")){
            msgs.add(new Msg(Msg.TYPE_SEND, content));
            adapter.notifyDataSetChanged();
            lv_msg.setSelection(adapter.getCount());//将list的焦点设置在最后一行。
            et_msg.setText("");

        }
        //showDialog();
        //showProgressDialog();
    }
    private void showDialog(){
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setTitle("我是对话框");
        dialog.setMessage("一些消息提示");
        dialog.setCancelable(false);
        dialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        dialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        dialog.show();
    }

    private void showProgressDialog(){
        ProgressDialog dialog = new ProgressDialog(this);
        dialog.setTitle("我是进度状态对话框");
        dialog.setMessage("Loading........");
        //dialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);//设置进度条样式
        dialog.setCancelable(true);
        dialog.show();
        //dialog.dismiss()//在别处调用，关闭进度框
    }


}


