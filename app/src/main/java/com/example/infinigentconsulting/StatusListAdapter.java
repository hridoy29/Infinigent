package com.example.infinigentconsulting;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;


public class StatusListAdapter extends ArrayAdapter<StatusUser> {


    private final Context context;
    private final int resourceId;
    private final ArrayList<StatusUser> auditItems;

    public StatusListAdapter(@NonNull Context context, int resourceId, ArrayList<StatusUser> auditItems) {
        super(context, resourceId, auditItems);
        this.auditItems = auditItems;
        this.context = context;
        this.resourceId = resourceId;
    }



    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = convertView;
        try {
            if (convertView == null) {
                LayoutInflater inflater = ((Activity) context).getLayoutInflater();
                view = inflater.inflate(resourceId, parent, false);
            }
            StatusUser user = getItem(position);
            TextView serial = (TextView) view.findViewById(R.id.sl_number);
            serial.setText(user.getNumberStatus());
            TextView name = (TextView) view.findViewById(R.id.outlet_name_status);
            assert user != null;
            name.setText(user.getOutletNameStatus());
            TextView phone = (TextView) view.findViewById(R.id.retailer_mobile_status);
            phone.setText(user.getMobileNumberStatus());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return view;
    }

    @Nullable
    @Override
    public StatusUser getItem(int position) {
        return auditItems.get(position);
    }

    @Override
    public int getCount() {
        return auditItems.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
}