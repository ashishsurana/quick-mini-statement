package com.example.ashish.mini;

import android.app.ListActivity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

import java.util.ArrayList;


public class MainActivity extends ListActivity {
    Uri uri = Uri.parse("content://sms/inbox");
    String[] fetched = new String[] {"_id",  "address", "body" };
    String num,content;
    final ArrayList<String> numArray = new ArrayList<String>();
    final ArrayList<String> contentArray = new ArrayList<>();
    String s = new String();
    ListView listView;
    SimpleCursorAdapter adapter;
    final ArrayList<String> result = new ArrayList<String>();
//    String tablename = new String();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listView = (ListView) findViewById(android.R.id.list);


        
        
        //fetching
        ContentResolver cr = getContentResolver();
        Cursor c = cr.query(uri, fetched, null, null, null);
        if (c != null )
            c.moveToLast();
        if (c.getCount()>0){
            do {
                num=c.getString(c.getColumnIndex("address"));
                content=c.getString(c.getColumnIndex("body"));
                numArray.add(num);
                contentArray.add(content);

            }while (c.moveToPrevious());

        }
//        tablename = c.getString(c.getColumnIndex("address"));
//        Log.d("tbNME",tablename);
        //feching done and added in smsInbox



        adapter = new SimpleCursorAdapter(this,R.layout.row,c,new String[] {"body" , "address"},new int[] {R.id.lblmsg,R.id.lblno});
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                Toast.makeText(getBaseContext(), position, Toast.LENGTH_LONG).show();

                position += 1;
                int a = numArray.size();
                String selected = numArray.get(a - position);
                String subs = selected.substring(3,selected.length());
//                Log.d("tbNME",subs);
                ArrayList<String> searchedcontent = createList(selected, a);//recieves whole list of selected messagess
//                searchedcontent = createList(subs,a);


                //Starting New Activity
                Intent intent = new Intent(MainActivity.this, Message.class);
                intent.putStringArrayListExtra("pos", searchedcontent);
                intent.putExtra("tbNME",subs);
                startActivity(intent);

            }
        });
        }//end of onCreate Method

        public ArrayList<String> createList(String a,int size){

            for (int i=0; i<size; i++){
                if(numArray.get(i).substring(3,numArray.get(i).length()).equalsIgnoreCase(a.substring(3,a.length())))
                {

                    result.add(String.valueOf(contentArray.get(i)));
                }

            }



            return result;
        }

}//end of main class
