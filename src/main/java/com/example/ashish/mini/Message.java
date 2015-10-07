package com.example.ashish.mini;

import android.app.Activity;
import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.provider.SyncStateContract;
import android.util.Log;
import android.view.View;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import java.security.BasicPermission;
import java.sql.SQLDataException;
import java.util.ArrayList;
import java.util.logging.LogRecord;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import android.os.Handler;
import android.widget.Toast;

/**
 * Created by ashish on 25/8/15.
 */
public class Message extends ListActivity {

    ListView listView ;//= new ListView(R.id.listView);
    ArrayAdapter<String> adapter ;
    TextView textView;
    ArrayList<String> s;
    public static String tbname=new String();

    public Boolean valid_selection=false;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sheet);
        Log.d("Working","Message");
        textView = (TextView) findViewById(R.id.textView2);
        listView = (ListView) findViewById(android.R.id.list);
        Bundle bundle = getIntent().getExtras();
         s =  bundle.getStringArrayList("pos");
        tbname=bundle.getString("tbNME");
        
        adapter = new ArrayAdapter<String>(this, R.layout.row2,android.R.id.text1,s);
        listView.setAdapter(adapter);//Display size here is 81 as in previousclass
//        insert_data(s);

        fetching_data(s);

    }

    public void insert_data(ArrayList<String> arrayList)    {
        Boolean valid;//for validating a message
        dbHelper helper = new dbHelper(getBaseContext());
        SQLiteDatabase db = helper.getWritableDatabase();
        try {
//            helper.setTABLE_NAME(tbname);
//            helper.onCreate(db);
            helper.create_table(tbname,db);

        }
        catch (SQLException e){

        }

        //Inserting block

        for (int i=0; i<arrayList.size(); i++)

        {
            ContentValues values = new ContentValues();

//            valid=checkvalidity(arrayList.get(i));//if debited/credited present in message
//            if(valid==true) {


//                values.put(dbHelper.FeedEntry.COLUMN_NAME_RAW_MESSAGE, arrayList.get(i));
                values.put(dbHelper.FeedEntry.COLUMN_NAME_CORD,credordeb(arrayList.get(i)));
                values.put(dbHelper.FeedEntry.COLUMN_NAME_BALLANCE,getbal(arrayList.get(i)));
                values.put(dbHelper.FeedEntry.COLUMN_NAME_AMOUNT,getamt(arrayList.get(i)));
                values.put(dbHelper.FeedEntry.COLUMN_NAME_DATE,getdate(arrayList.get(i)));
                values.put(dbHelper.FeedEntry.COLUMN_NAME_ACNO, getacno(arrayList.get(i)));
                values.put(dbHelper.FeedEntry.COLUMN_NAME_TIME, gettime(arrayList.get(i)));
                try{
//                    db.insert(dbHelper.TABLE_NAME, null, values);
//                    db.execSQL(dbHelper.SQL_DELETE_ENTRIES);
                    db.insert(tbname, null, values);
                }
                catch (SQLException e){
                    e.fillInStackTrace();
                }
//            }


        }//Ending of Inserting loop
    }

    public void fetching_data(ArrayList<String> arrayList){
        dbHelper helper = new dbHelper(getBaseContext());
        SQLiteDatabase db2 = helper.getReadableDatabase();
        ArrayList<MainContent> mainContentArrayList=new ArrayList<MainContent>();
//        try {
//               helper.create_table(tbname,db2);
//
//        }
//        catch (SQLException e){
//
//        }
        Cursor cursor = db2.rawQuery("SELECT * FROM " + tbname, null);

        if(cursor != null)
            cursor.moveToLast();

        if(cursor.getCount()>0)
            arrayList.clear();
        do{
            MainContent message=new MainContent();
            message.setDate(cursor.getString(cursor.getColumnIndex(dbHelper.FeedEntry.COLUMN_NAME_DATE)));
            message.setTime(cursor.getString(cursor.getColumnIndex(dbHelper.FeedEntry.COLUMN_NAME_TIME)));
            message.setC_d(cursor.getString(cursor.getColumnIndex(dbHelper.FeedEntry.COLUMN_NAME_CORD)));
            message.setAmt(cursor.getString(cursor.getColumnIndex(dbHelper.FeedEntry.COLUMN_NAME_AMOUNT)));
            message.setBal(cursor.getString(cursor.getColumnIndex(dbHelper.FeedEntry.COLUMN_NAME_BALLANCE)));
            mainContentArrayList.add(message);
        }while (cursor.moveToPrevious());


        ListView messageListView = (ListView) findViewById(android.R.id.list);
        MessageAdapter adapter1=new MessageAdapter(this,R.layout.row2,mainContentArrayList);
        messageListView.setAdapter(adapter1);
//        adapter = new ArrayAdapter<String>(this, R.layout.row2,android.R.id.text1,arrayList);
//        listView.setAdapter(adapter);//Display of fetched stuff
    }//end of fetching data


    public static String credordeb(String temp){
     String result = new String();
        String c_d =  ".*([Dd]ebited|[Cc]redited).*";
        Pattern pattern = Pattern.compile(c_d);
        Matcher matcher = pattern.matcher(temp);
        if(matcher.matches()==true){
            result=matcher.group(1).substring(0,1).toUpperCase();

        }
        return result;
    }
    public static String getacno(String temp){
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
        String result = new String();
        String time=new String();
        time = ".*([0-2][0-9][\\:][0-5][0-9][\\:][0-5][0-9]).*";
        Pattern pattern = Pattern.compile(time);
        Matcher matcher = pattern.matcher(temp);
        if(matcher.matches()){
            result = matcher.group(1);
        }
        return result;
    }
    public static String getamt(String temp){
        String result=new String();
        String amtpre = ".*([Rr][s][,\\s+\\.^0-9][.\\D]?[\\D]?(\\d+[,]?([\\d]{1,3}[.][\\d][\\d])?)).*([Dd]ebited|[Cc]redited).*";
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
        if(matcher.matches()==true)
            valid_selection=true;
        return  matcher.matches();

    }




    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Message.this.finish();
    }
}
