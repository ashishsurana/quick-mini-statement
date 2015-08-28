package com.example.ashish.mini;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
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
         s = bundle.getStringArrayList("pos");
//        Log.d("Message",String.valueOf(s.get(2)));
//        textView.setText(String.valueOf(s.get(3)) );
        Log.d("Testing", "onCreated called" + String.valueOf(s.get(3)));
        adapter = new ArrayAdapter<String>(this, R.layout.row2,android.R.id.text1,s);
        listView.setAdapter(adapter);



    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Message.this.finish();
    }
}
