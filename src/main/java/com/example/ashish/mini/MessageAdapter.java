package com.example.ashish.mini;

import android.content.ClipData;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.SurfaceHolder;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by ashish on 16/9/15.
 */
public class MessageAdapter extends ArrayAdapter<MainContent> {
    private Context ctx;
    public ArrayList<MainContent> contentArrayList;
    LayoutInflater inflater;
    MainContent tempdata;

    //Constructor
    public MessageAdapter(Context context ,int textviewid,ArrayList<MainContent> messages){
        super(context,textviewid);
        this.ctx=context;
        this.contentArrayList=messages;

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {



        View vi = convertView;
        ViewHolder holder;
        if(convertView==null){
            inflater = (LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            vi=inflater.inflate(R.layout.row2,null);
            holder=new ViewHolder();
            holder.tvc_d = (TextView) vi.findViewById(R.id.tvcord);
            holder.tvbal = (TextView) vi.findViewById(R.id.tvbal);
            holder.tvamt = (TextView) vi.findViewById(R.id.tvamt);
            holder.tvdate = (TextView) vi.findViewById(R.id.tvdate);
            holder.tvtime = (TextView) vi.findViewById(R.id.tvtime);
            vi.setTag(holder);


        }
        else {
            holder=(ViewHolder)vi.getTag();
        }

        MainContent message= getItem(position);
        Log.d("Position in GetItem",String.valueOf(position));
        holder.tvamt.setText(message.getAmt());
        holder.tvbal.setText(message.getBal());
        holder.tvc_d.setText(message.getC_d());
        holder.tvdate.setText(message.getDate());
        holder.tvtime.setText(message.getTime());


//        Log.d("Data",)
        return vi;

    }

    @Override
    public int getCount() {

        return contentArrayList.size();
    }

    @Override
    public MainContent getItem(int position) {
        return contentArrayList.get(position);
    }

    public static class ViewHolder{
        public TextView tvc_d ;
        public TextView tvbal ;
        public TextView tvamt ;
        public TextView tvdate ;
        public TextView tvtime ;
    }


}
