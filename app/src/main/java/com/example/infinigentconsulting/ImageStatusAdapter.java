package com.example.infinigentconsulting;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

public class ImageStatusAdapter extends ArrayAdapter<StatusImage> {

    private final Context context;
    private final int resourceId;
    private final ArrayList<StatusImage> imageItems;

    public ImageStatusAdapter(@NonNull Context context, int resourceId, ArrayList<StatusImage> imageItems) {
        super(context, resourceId, imageItems);
        this.imageItems = imageItems;
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
            StatusImage user = getItem(position);
            TextView name = (TextView) view.findViewById(R.id.outlet_name_status);
            assert user != null;
            name.setText(user.getSerialNumberStatus());
            TextView phone = (TextView) view.findViewById(R.id.retailer_mobile_status);
            phone.setText(""+user.getImageCountStatus());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return view;
    }

    @Nullable
    @Override
    public StatusImage getItem(int position) {
        return imageItems.get(position);
    }

    @Override
    public int getCount() {
        return imageItems.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
}