package com.evs.dalinkup.net.adapter;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.evs.dalinkup.net.R;

public class UsersAdapter extends ArrayAdapter<String> {
	
	ArrayList<String> t_listdata;
	
	
    public UsersAdapter(Context context, ArrayList<String> users) {
       super(context, 0, users);
       
       t_listdata=users;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        
       if (convertView == null) {
          convertView = LayoutInflater.from(getContext()).inflate(R.layout.spinnerpopup, parent, false);
       } 
       TextView tvName = (TextView) convertView.findViewById(R.id.text1); 
       tvName.setText(t_listdata.get(position)); 
       return convertView;
   }
}
	