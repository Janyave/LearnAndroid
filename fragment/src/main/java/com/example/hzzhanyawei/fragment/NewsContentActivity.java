package com.example.hzzhanyawei.fragment;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.FrameLayout;

public class NewsContentActivity extends AppCompatActivity {


    public static void actionStart(Context context, News news){
        Intent intent = new Intent(context, NewsContentActivity.class);
        intent.putExtra("title", news.getTitle());
        intent.putExtra("content", news.getContent());
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_content);

        String title = getIntent().getStringExtra("title");
        String content = getIntent().getStringExtra("content");


        NewsContentFragment newsFragment = (NewsContentFragment) getFragmentManager().findFragmentById(R.id.news_fragment);
        newsFragment.refreshContent(title, content);

    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_news_content, menu);
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
}
