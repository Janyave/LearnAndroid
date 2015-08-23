package com.example.hzzhanyawei.datasave;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class SQLite extends AppCompatActivity implements View.OnClickListener {

    public final static String DEBUG = "SQLite";

    @InjectView(R.id.bt_create)
    Button bt_create;
    @InjectView(R.id.bt_add)
    Button bt_add;
    @InjectView(R.id.bt_update)
    Button bt_update;
    @InjectView(R.id.bt_delete)
    Button bt_delete;
    @InjectView(R.id.bt_query)
    Button bt_query;

    private MyDatabaseHelper databaseHelper;
    private SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sqlite);
        ButterKnife.inject(this);

        databaseHelper = new MyDatabaseHelper(this, "Test.db", null, 1);//创建一个数据库，最后一个参数为版本号，用于动态更新数据库
        db = databaseHelper.getWritableDatabase();

        bt_create.setOnClickListener(this);
        bt_add.setOnClickListener(this);
        bt_update.setOnClickListener(this);
        bt_delete.setOnClickListener(this);
        bt_query.setOnClickListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_sqlite, menu);
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
        switch (v.getId()){//可以用SQLiteDatabase类中的方法来实现数据库的操作，也可以用SQL语句操作
            case R.id.bt_create:
                db.execSQL("create table Book (id integer primary key autoincrement, author text, price real, pages integer, name text)");
                break;
            case R.id.bt_add:
                //db.insert()
                db.execSQL("insert into Book (name, author, pages, price) values(?, ?, ?, ?)", new String[]{"book1", "zyw", "108",  "19.2"});
                db.execSQL("insert into Book (name, author, pages, price) values(?, ?, ?, ?)", new String[]{"book2", "zyw", "156",  "11.2"});
                break;
            case R.id.bt_update:
                db.execSQL("update Book set price = ? where name = ?", new String[]{"10.0", "book2"});
                break;
            case R.id.bt_delete:
                db.execSQL("delete from Book where pages > 150");//for test
                break;
            case R.id.bt_query:
                Cursor cursor = db.rawQuery("select * from Book", null);//注意与其他语句的区别，此处用rawQuery或者query。
                if (cursor.moveToFirst()){
                    do{
                        String name = cursor.getString(cursor.getColumnIndex("name"));
                        int page = cursor.getInt(cursor.getColumnIndex("pages"));
                        double price = cursor.getDouble(cursor.getColumnIndex("price"));
                        Log.d(DEBUG,"book name is " + name + " pages is" + page  + " price is " + price);
                    }while (cursor.moveToNext());
                }
                cursor.close();
                break;
        }
//        //使用事物来保证一个事物要执行则执行完全，不执行则完全不执行
//        db.beginTransaction();
//        //监视的事物
//        db.setTransactionSuccessful();
//        db.endTransaction();
    }
}

class MyDatabaseHelper extends SQLiteOpenHelper{

    public MyDatabaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //第一次创建数据库时执行，如果要创建的数据库已经存在，则不执行
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //如果创建的数据库版本与当前版本不一致，则执行。
        switch (oldVersion){
            case 1:
                //TODO
            case 2:
                //TODO
            default://注意此处case后没有break，是为了保证跨版本升级。
        }
    }
}
