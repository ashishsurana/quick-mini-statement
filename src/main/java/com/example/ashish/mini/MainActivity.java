package com.example.ashish.mini;

import android.app.Activity;
import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

import java.net.URI;
import java.util.ArrayList;

public class MainActivity extends Activity {
    Uri uri = Uri.parse("content://sms/inbox");
    String[] fetched = new String[] {"_id",  "address", "body" };
    String num,content;
    ArrayList<String> smsInbox = new ArrayList<String>();

    ListView listView;
    SimpleCursorAdapter adapter;
    TextView lblmsg,lblno;
    ArrayList arrayList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listView = (ListView) findViewById(R.id.listView1);

        //fetching
        ContentResolver cr = getContentResolver();
        Cursor c = cr.query(uri, fetched, null, null, null);
        if (c != null )
            c.moveToLast();
        if (c.getCount()>0){
            do {
                num=c.getString(c.getColumnIndex("body"));
                smsInbox.add(num);
            }while (c.moveToPrevious());
        }
        //feching done and added in smsInbox
        adapter = new SimpleCursorAdapter(this,R.layout.row,c,new String[] {"body" , "address"},new int[] {R.id.lblmsg,R.id.lblno});
        listView.setAdapter(adapter);
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
}
