package com.example.ashish.mini;

import android.app.ListActivity;
import android.app.ProgressDialog;
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
import android.widget.ProgressBar;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


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
    public Integer temp=new Integer(0);


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
        //feching done and added in smsInbox



        adapter = new SimpleCursorAdapter(this,R.layout.row,c,new String[] {"body" , "address"},new int[] {R.id.lblmsg,R.id.lblno});
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                position += 1;
                int a = numArray.size();
                String selected = numArray.get(a - position);
                String subs = selected.substring(3, selected.length());
                ArrayList<String> searchedcontent = createList(selected, a);//recieves whole list of selected messages

                if (temp==0){
                    Toast toast=Toast.makeText(getApplicationContext(),"You may have selected wrong message", Toast.LENGTH_LONG);
                    toast.show();
                }

                //Starting New Activity
                else {
                    Log.d("Working","Working1");
                    Toast toast=Toast.makeText(getApplicationContext(),"Content is Loading, It may take few seconds or more", Toast.LENGTH_SHORT);
                    toast.show();
                    Log.d("Working", "Working2");
                    Intent intent = new Intent(MainActivity.this, Message.class);
                    intent.putStringArrayListExtra("pos", searchedcontent);
                    intent.putExtra("tbNME", subs);
                    startActivity(intent);
                    toast.cancel();
                }
            }
        });
        }//end of onCreate Method

        public ArrayList<String> createList(String a,int size){

            for (int i=0; i<size; i++){
                if(numArray.get(i).substring(3,numArray.get(i).length()).equalsIgnoreCase(a.substring(3,a.length())))
                {

                    if(checkvalidity(contentArray.get(i))==true){
                        result.add(String.valueOf(contentArray.get(i)));
                    }
                }
            }
            return result;
        }

    public Boolean checkvalidity(String s){
        Boolean result = new Boolean(null);
        String c_d =  ".*([Dd]ebited|[Cc]redited).*";
        Pattern pattern = Pattern.compile(c_d);
        Matcher matcher = pattern.matcher(s);
        if(matcher.matches()==true)
            temp++;
        return  matcher.matches();

    }
}//end of main class
