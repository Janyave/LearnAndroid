package com.example.hzzhanyawei.testsoftkeyboard;

import android.graphics.Rect;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.io.BufferedReader;
import java.util.zip.Inflater;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText et_input;
    private TextView tv_show;
    private Button bt_show;
    private Button bt_hide;

    private LinearLayout ll_main;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ll_main = (LinearLayout) findViewById(R.id.ll_main);

        et_input = (EditText) findViewById(R.id.et_input);
        tv_show = (TextView) findViewById(R.id.tv_show);
        bt_show = (Button) findViewById(R.id.bt_show_keyboard);
        bt_hide = (Button) findViewById(R.id.bt_hide_keyboard);

        bt_show.setOnClickListener(this);
        bt_hide.setOnClickListener(this);

        ll_main.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {

            @Override
            public void onGlobalLayout() {
                Rect r = new Rect();
                View rootview = getWindow().getDecorView(); // 获取显示屏最顶层View
                rootview.getWindowVisibleDisplayFrame(r);

                int screenHeight = rootview.getHeight();//getWindowManager().getDefaultDisplay().getHeight();
                int heightDifference = screenHeight - r.bottom;
                Log.d("Keyboard Size", "______________________________________________");
                Log.d("Keyboard Size", "top" + r.top);
                Log.d("Keyboard Size", "bottom" + r.bottom);
                Log.d("Keyboard Size", "total height" + screenHeight);
                Log.d("Keyboard Size", "______________________________________________");

                if(heightDifference > 100) {
                    Log.d("Keyboard Size", "Size: " + heightDifference);
                }
            }
        });
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
            case R.id.bt_show_keyboard:
                InputMethodManager imm = (InputMethodManager) getSystemService(this.INPUT_METHOD_SERVICE);
                imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
                break;
            case R.id.bt_hide_keyboard:
                break;
            default:
                break;
        }

    }
}
