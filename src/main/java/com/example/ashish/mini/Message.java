package com.example.ashish.mini;

import android.app.Activity;
import android.app.ListActivity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.provider.SyncStateContract;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by ashish on 25/8/15.
 */
public class Message extends ListActivity {

    ListView listView ;//= new ListView(R.id.listView);
    ArrayAdapter<String> adapter ;
    TextView textView;
    ArrayList<String> s;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.sheet);
        textView = (TextView) findViewById(R.id.textView2);
        listView = (ListView) findViewById(android.R.id.list);
        Bundle bundle = getIntent().getExtras();
         s =  bundle.getStringArrayList("pos");
        adapter = new ArrayAdapter<String>(this, R.layout.row2,android.R.id.text1,s);
        listView.setAdapter(adapter);//Display size here is 81 as in previousclass

//        s.clear();
        Log.d("AryLt Size befo fetch",String.valueOf(s.size()));
        databaseoperation(s);


    }
    //function to perform operations on database
    public void databaseoperation(ArrayList<String> arrayList){
        dbHelper helper = new dbHelper(getBaseContext());
        SQLiteDatabase db = helper.getWritableDatabase();
        try {
            helper.onCreate(db);
        }
        catch (SQLException e){

        }
//        try {
//            db.execSQL(dbHelper.SQL_DELETE_ENTRIES);
//        }                                                       //Deleting the database
//        catch (SQLException e){
//            e.printStackTrace();
//        }

        ContentValues values = new ContentValues();


        for (int i=0; i<arrayList.size(); i++)
        {
//            values.put(dbHelper.FeedEntry._ID,i);
            if(arrayList.get(i).contains("Credited") || arrayList.get(i).contains("Debited")) {


                values.put(dbHelper.FeedEntry.COLUMN_NAME_RAW_MESSAGE, arrayList.get(i));
//                Log.d("No Trash ", arrayList.get(i));

                try{
                    db.insert(dbHelper.FeedEntry.TABLE_NAME, null, values);
                }
                catch (SQLException e)
                {
                    e.printStackTrace();
                }
            }
            else{

                Log.d("Trash ", arrayList.get(i));
            }
//            values.put(dbHelper.FeedEntry.COLUMN_NAME_CREDDEB,credordeb(arrayList.get(i)));
        }



        //Fetching the values
        SQLiteDatabase db2 = helper.getReadableDatabase();
        Cursor cursor = db2.rawQuery("SELECT * FROM MESSAGE",null);
        String temp = new String();
        if(cursor != null)
            cursor.moveToLast();
        Log.d("Cursor Size",String.valueOf(cursor.getCount()));
        if(cursor.getCount()>0)
            arrayList.clear();
        do{
            temp=cursor.getString(cursor.getColumnIndex(dbHelper.FeedEntry.COLUMN_NAME_RAW_MESSAGE));
//            Log.d("Fetched Message",temp.substring(0,10));
//
            arrayList.add(temp);
        }while (cursor.moveToPrevious());

        adapter = new ArrayAdapter<String>(this, R.layout.row2,android.R.id.text1,arrayList);
        listView.setAdapter(adapter);

    }//DatabaseOperation Ending



    public String credordeb(String temp){
     String result = new String();
        if(temp.contains("Debited"))
            result = "D";
        else if(temp.contains("Credited"))
            result = "C";
        else
        result = null;
        return result;
    }



    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Message.this.finish();
    }
}
