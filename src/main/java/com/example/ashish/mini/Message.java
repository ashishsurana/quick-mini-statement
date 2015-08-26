package com.example.ashish.mini;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

/**
 * Created by ashish on 25/8/15.
 */
public class Message extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sheet);


        Bundle bundle = getIntent().getExtras();
        String s = getIntent().getExtras().getString("pos");
        TextView v = (TextView) findViewById(R.id.textView2);
        v.setText(s );
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Message.this.finish();
    }
}
