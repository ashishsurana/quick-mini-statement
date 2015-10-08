package com.example.ashish.mini;

import android.content.ClipData;
import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.SurfaceHolder;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ListAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ashish on 16/9/15.
 */

public class MessageAdapter extends ArrayAdapter<MainContent>{
    List<MainContent> mainContentList ;
    public MessageAdapter(Context context,int arg0){
        super(context,arg0);
    }
    public MessageAdapter(Context context,int rid,List<MainContent> content){
        super(context,rid,content);
        mainContentList=content;
    }


    @Override
    public int getCount() {
        return mainContentList.size();
    }

    @Override
    public MainContent getItem(int position) {

        return mainContentList.get(position);
    }

//    @Override
//    public long getItemId(int position) {
//        return 0;
//    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v=convertView;
        if (v == null) {
            LayoutInflater vi;
            vi = LayoutInflater.from(getContext());
            v = vi.inflate(R.layout.row2, null);
        }

        MainContent p = getItem(position);


        if (p != null) {
            Holder holder = new Holder();
            holder.tvc_d = (TextView) v.findViewById(R.id.tvc_d);

            holder.tvbal = (TextView) v.findViewById(R.id.tvbal);
            holder.tvamt = (TextView) v.findViewById(R.id.tvamt);
            holder.tvdate = (TextView) v.findViewById(R.id.tvdate);
            holder.tvtime = (TextView) v.findViewById(R.id.tvtime);


            if (holder.tvamt != null) {
                holder.tvamt.setText(p.getAmt());
            }

            if (holder.tvbal != null) {
                holder.tvbal.setText(p.getBal());
            }

            if (holder.tvdate != null) {
                holder.tvdate.setText(p.getDate());
            }
            if (holder.tvtime != null) {
                holder.tvtime.setText(p.getTime());
            }
            if (holder.tvc_d != null ) {
                holder.tvc_d.setText(p.getC_d());
                if(p.getC_d().equalsIgnoreCase("D")) {

                    holder.tvc_d.setTextColor(Color.RED);
                }
                else {
                    holder.tvc_d.setTextColor(Color.GREEN);
                    holder.tvc_d.setBackgroundColor(Color.BLUE);
                }
            }
        }

        return v;
    }

    public static class Holder{
        public TextView tvc_d ;

        public TextView tvbal ;
        public TextView tvamt ;
        public TextView tvdate ;
        public TextView tvtime ;
    }
}
