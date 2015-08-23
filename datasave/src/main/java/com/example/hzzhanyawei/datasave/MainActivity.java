package com.example.hzzhanyawei.datasave;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import butterknife.ButterKnife;
import butterknife.InjectView;


public class MainActivity extends BaseActivity implements View.OnClickListener {

    @InjectView(R.id.bt_file)
    Button bt_file;
    @InjectView(R.id.bt_shared)
    Button bt_shared;
    @InjectView(R.id.bt_sqlite)
    Button bt_sqlite;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.inject(this);

        bt_file.setOnClickListener(this);
        bt_shared.setOnClickListener(this);
        bt_sqlite.setOnClickListener(this);
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
        Intent intent = null;
        switch (v.getId()){
            case R.id.bt_file:
                intent = new Intent(this, File.class);
                startActivity(intent);
                break;
            case R.id.bt_shared:
                intent = new Intent(this, SharedPreference.class);
                startActivity(intent);
                break;
            case R.id.bt_sqlite:
                intent = new Intent(this, SQLite.class);
                startActivity(intent);
                break;
        }

    }
}
