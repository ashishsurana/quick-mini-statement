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

import java.security.BasicPermission;
import java.sql.SQLDataException;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by ashish on 25/8/15.
 */
public class Message extends ListActivity {

    ListView listView ;//= new ListView(R.id.listView);
    ArrayAdapter<String> adapter ;
    TextView textView;
    ArrayList<String> s;
    String tbname=new String();
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.sheet);
        textView = (TextView) findViewById(R.id.textView2);
        listView = (ListView) findViewById(android.R.id.list);
        Bundle bundle = getIntent().getExtras();
         s =  bundle.getStringArrayList("pos");
//        tbname=bundle.getString("")
        adapter = new ArrayAdapter<String>(this, R.layout.row2,android.R.id.text1,s);
//        listView.setAdapter(adapter);//Display size here is 81 as in previousclass

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

        ContentValues values = new ContentValues();

        Boolean valid;
        for (int i=0; i<arrayList.size(); i++)
        {
//            values.put(dbHelper.FeedEntry._ID,i);
            valid=checkvalidity(arrayList.get(i));//if debit/credit present in message
            if(valid==true) {


                values.put(dbHelper.FeedEntry.COLUMN_NAME_RAW_MESSAGE, arrayList.get(i));
                values.put(dbHelper.FeedEntry.COLUMN_NAME_CREDDEB,credordeb(arrayList.get(i)));
                values.put(dbHelper.FeedEntry.COLUMN_NAME_BALLANCE,getbal(arrayList.get(i)));
                values.put(dbHelper.FeedEntry.COLUMN_NAME_AMOUNT,getamt(arrayList.get(i)));
                values.put(dbHelper.FeedEntry.COLUMN_NAME_DATE,getdate(arrayList.get(i)));
                values.put(dbHelper.FeedEntry.COLUMN_NAME_ACNO,getacno(arrayList.get(i)));

//                Log.d("No Trash ", arrayList.get(i));

                try{
                    db.insert(dbHelper.FeedEntry.TABLE_NAME, null, values);
//                    db.execSQL(dbHelper.SQL_DELETE_ENTRIES);

                }
                catch (SQLException e){

                }



            }
            else{

//                Log.d("Trash ", arrayList.get(i));
            }

        }


        //Fetching the values
        SQLiteDatabase db2 = helper.getReadableDatabase();

        Cursor cursor = db2.rawQuery("SELECT * FROM MESSAGE", null);
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
        listView.setAdapter(adapter);//Display of fetched stuff
//        credordeb(arrayList.get(5));
//        getacno(arrayList.get(5));
//        getbal(arrayList.get(5));
//        getdate(arrayList.get(5));
//        gettime(arrayList.get(5));
//        getamt(arrayList.get(5));

//        Log.d("C/D" ,credordeb(arrayList.get(5)));
//        Log.d("A/C",getacno(arrayList.get(5)));
//        Log.d("Bal",getbal(arrayList.get(5)));
//        Log.d("Date",getdate(arrayList.get(5)));
        Log.d("SampleIP",arrayList.get(5));
        Log.d("Time",gettime(arrayList.get(5)));
        Log.d("Amt",getamt(arrayList.get(5)));


    }//DatabaseOperation Ending



    public String credordeb(String temp){
     String result = new String();
        String c_d =  ".*([Dd]ebited|[Cc]redited).*";
        Pattern pattern = Pattern.compile(c_d);
        Matcher matcher = pattern.matcher(temp);
        if(matcher.matches()==true){
            result=matcher.group(1).substring(0,1).toUpperCase();
            Log.d("Sample" ,temp);
        }
        return result;
    }
    public String getacno(String temp){
        String result = new String();
        String acno = ".*([Aa][/]?[cC].*([\\.X0\\s]{3,8}([\\d]{4}))).*";//1 for a/c 3 for ac no//// .
        Pattern pattern = Pattern.compile(acno);
        Matcher matcher = pattern.matcher(temp);
        if(matcher.find()){
            result=matcher.group(3);
        }
        return result;

    }
    public static String getbal(String temp){
        String result=new String();
        String bal = ".*[bB]al.*([Rr][s][,\\s+\\.^0-9][.\\D]?[\\D]?(.*[.][\\d][\\d])).*";//group 1 for ballance
        Pattern pattern = Pattern.compile(bal);
        Matcher matcher = pattern.matcher(temp);
        if(matcher.find()){
            result=matcher.group(2);
        }
        return result;
    }
    public static String getdate(String temp){
        String result = new String();
//        Log.d("first","Running");
        String date = ".*([0-3][0-9][-/][0-1][0-9][-/](([0-1][0-9])|([0-9]{4}))).*";
//        Log.d("second","Running");
        Pattern pattern = Pattern.compile(date);
        Matcher matcher = pattern.matcher(temp);
        if(matcher.find()){
            result = matcher.group(1);
        }
        return result;
    }
    public static String gettime(String temp){
        String result       = new String();
        Log.d("Step1","working");
        String time=new String();
        time = ".*([0-1][0-9][:][0-5][0-9][:][0-5][0-9]).*";
        Log.d("Step2","working");
        Pattern pattern = Pattern.compile(time);
        Log.d("Step3","working");
        Matcher matcher = pattern.matcher(temp);
        if(matcher.matches()){
            result = matcher.group(1);
        }
        return result;
    }
    public static String getamt(String temp){
        String result=new String();
        String amtpre = ".*([Rr][s][,\\s+\\.^0-9][.\\D]?[\\D]?(\\d+[,]?\\d+[.][\\d][\\d]?)).*([Dd]ebited|[Cc]redited).*";
        String amtpost = ".*([Dd]ebited|[Cc]redited).*(with|by).([Rr][s][,\\s+\\.^0-9][.\\D]?[\\D]?(\\d+[,]?\\d+([.][\\d][\\d])?)).*";
        Pattern pattern1 = Pattern.compile(amtpre);
        Pattern pattern2 = Pattern.compile(amtpost);
        Matcher matcher1 = pattern1.matcher(temp);
        Matcher matcher2 = pattern2.matcher(temp);
        if(matcher2.find()){
            result=matcher2.group(4);
        }
        else if (matcher1.find()){
            result=matcher1.group(2);
        }
        return result;
    }

    public Boolean checkvalidity(String s){
        Boolean result = new Boolean(null);
        String c_d =  ".*([Dd]ebited|[Cc]redited).*";
        Pattern pattern = Pattern.compile(c_d);
        Matcher matcher = pattern.matcher(s);
        return  matcher.matches();

    }




    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Message.this.finish();
    }
}
