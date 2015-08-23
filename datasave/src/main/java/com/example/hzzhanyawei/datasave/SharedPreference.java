package com.example.hzzhanyawei.datasave;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class SharedPreference extends AppCompatActivity implements View.OnClickListener {
    static final String DEBUG = "SharedPreference";

    @InjectView(R.id.bt_write)
    Button bt_write;
    @InjectView(R.id.bt_read)
    Button bt_read;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shared_preference);
        ButterKnife.inject(this);
        bt_write.setOnClickListener(this);
        bt_read.setOnClickListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_shared_preference, menu);
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
            case R.id.bt_write:
                saveSharedPreference();
                break;
            case R.id.bt_read:
                readSharedPreference();
                break;
        }
    }
    private void saveSharedPreference(){
//        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);//自动使用包名作为文件名前缀
//        SharedPreferences.Editor editor = sp.edit();
//        SharedPreferences sp = getPreferences(MODE_PRIVATE);//自动使用Activity名作为sp文件名前缀
//        SharedPreferences.Editor editor = sp.edit();
        SharedPreferences.Editor editor = getSharedPreferences("data", MODE_PRIVATE).edit();
        editor.putString("name", "zyw");
        editor.putInt("ID", 0);
        editor.putBoolean("success", true);
        editor.commit();
    }
    private void readSharedPreference(){
        SharedPreferences sp = getSharedPreferences("data",MODE_PRIVATE);
        Log.d(DEBUG, sp.getString("name", "") );
        Log.d(DEBUG, sp.getInt("ID", 1) + "" );
        Log.d(DEBUG, sp.getBoolean("success", false) + "");
    }
}
