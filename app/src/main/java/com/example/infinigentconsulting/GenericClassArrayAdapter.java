package com.example.infinigentconsulting;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

public class GenericClassArrayAdapter extends ArrayAdapter<GenericClass> {


    private final Context context;
    private final int resourceId;
    private final ArrayList<GenericClass> items;
    private final ArrayList<GenericClass> tempItems;
    private final ArrayList<GenericClass> suggestions;
    public GenericClassArrayAdapter(@NonNull Context context, int resourceId, ArrayList<GenericClass> items) {
        super(context, resourceId, items);
        this.items = items;
        this.context = context;
        this.resourceId = resourceId;
        tempItems = new ArrayList<>(items);
        suggestions = new ArrayList<>();
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
            GenericClass genericDetais  = getItem(position);
            TextView name = (TextView) view.findViewById(R.id.spinner_list);
            name.setText(genericDetais.getName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return view;
    }
    @Nullable
    @Override
    public GenericClass getItem(int position) {
        return items.get(position);
    }
    @Override
    public int getCount() {
        return items.size();
    }
    @Override
    public long getItemId(int position) {
        return position;
    }
    @NonNull
    @Override
    public Filter getFilter() {
        return genericFilter;
    }
    private final Filter genericFilter = new Filter() {
        @Override
        public CharSequence convertResultToString(Object resultValue) {
            GenericClass genericDetails = (GenericClass) resultValue;
            return genericDetails .getName();
        }
        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {
            if (charSequence != null) {
                suggestions.clear();
                for (GenericClass genericDetails : tempItems) {
                    if (genericDetails .getName().toLowerCase().startsWith(charSequence.toString().toLowerCase())) {
                        suggestions.add(genericDetails );
                    }
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = suggestions;
                filterResults.count = suggestions.size();
                return filterResults;
            } else {
                return new FilterResults();
            }
        }
        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
            ArrayList<GenericClass> tempValues = (ArrayList<GenericClass>) filterResults.values;
            if (filterResults != null && filterResults.count > 0) {
                clear();
                for (GenericClass genericObj : tempValues) {
                    add(genericObj);
                }
                notifyDataSetChanged();
            } else {
                clear();
                notifyDataSetChanged();
            }
        }
    };

}

