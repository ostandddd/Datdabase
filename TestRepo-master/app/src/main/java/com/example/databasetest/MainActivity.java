package com.example.databasetest;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

public class MainActivity extends AppCompatActivity {
    private MyDatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        dbHelper=new MyDatabaseHelper(this,"library.db",null,2);
        Button createDatabase=(Button) findViewById(R.id.create_database);
        createDatabase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dbHelper.getWritableDatabase();
            }
        });


        Button addData=(Button) findViewById(R.id.add_data);
        addData.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                SQLiteDatabase db=dbHelper.getWritableDatabase();
                ContentValues values=new ContentValues();
                values.put("name","English");
                values.put("author","Dania");
                values.put("pages",240);
                values.put("price",70);
                values.put("category_id",89);
                db.insert("Book",null,values);
                values.clear();

                values.put("name","The lost symbol");
                values.put("author","Ban");
                values.put("pages",20);
                values.put("price",20);
                values.put("category_id",87);
                db.insert("Book",null,values);
                values.clear();
            }
        });


        Button addData2=(Button) findViewById(R.id.add_data2);
        addData2.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                SQLiteDatabase db=dbHelper.getWritableDatabase();
                ContentValues values=new ContentValues();
                values.put("category_name","Math");
                values.put("category_code",89765);
                db.insert("Category",null,values);
                values.clear();

                values.put("category_name","Chinese");
                values.put("category_code",34565);
                db.insert("Category",null,values);
                values.clear();
            }
        });

    Button queryButton=(Button) findViewById(R.id.query_data);
    queryButton.setOnClickListener(new View.OnClickListener(){
      @Override
      public void onClick(View v){

          setContentView(R.layout.activity_check);

            SQLiteDatabase db=dbHelper.getWritableDatabase();
            Cursor cursor=db.query("Book",null,null,null,null,null,null);
            if(cursor.moveToFirst()){
               do{
                    String name=cursor.getString(cursor.getColumnIndex("name"));
                    String author=cursor.getString(cursor.getColumnIndex("author"));
                    Log.d("MainActivity","book name is "+name);
                    Log.d("MainActivity","book author "+author);
                   String[] data={name,author};
                   ArrayAdapter<String> adapter=new ArrayAdapter<String>(
                           MainActivity.this, android.R.layout.simple_list_item_1,data);
                   ListView listView=(ListView) findViewById(R.id.list_view);
                   listView.setAdapter(adapter);
                }while(cursor.moveToNext());
            }
            cursor.close();




        }
    });
}
}