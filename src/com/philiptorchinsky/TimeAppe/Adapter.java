package com.philiptorchinsky.TimeAppe;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;


public class Adapter extends BaseAdapter {

    ArrayList<Item> data = new ArrayList<Item>();
    Context context;



    public Adapter(Context context, ArrayList<Item> arr) {
        if (arr != null) {
            data = arr;
        }
        this.context = context;
    }

    public int getCount() {
        // TODO Auto-generated method stub
        return data.size();
    }

    public Object getItem(int num) {
        // TODO Auto-generated method stub
        return data.get(num);
    }

    public long getItemId(int arg0) {
        return arg0;
    }


    public View getView(int position, View convertView, ViewGroup parent) {

        Item cItem = data.get(position);

        LayoutInflater inflater = LayoutInflater.from(context);
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.list_item, parent, false);
        }
        if (cItem.getStatus().equals("active")) {
            convertView.setBackgroundColor(Color.GREEN);

        }
        else {
            convertView.setBackgroundColor(Color.RED);
        }
        TextView project = (TextView) convertView.findViewById(R.id.project);
        project.setText(cItem.getProject());
        TextView secondsspent = (TextView) convertView.findViewById(R.id.secondsspent);
        secondsspent.setText(Double.toString(cItem.getSecondsSpent()/1000.0));
        return convertView;
    }


}