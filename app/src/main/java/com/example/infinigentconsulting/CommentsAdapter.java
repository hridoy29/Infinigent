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

public class CommentsAdapter extends ArrayAdapter<CommnetsClass> {


    private final Context context;
    private final int resourceId;
    private final ArrayList<CommnetsClass> items;
    private final ArrayList<CommnetsClass> tempItems;
    private final ArrayList<CommnetsClass> suggestions;
    public CommentsAdapter(@NonNull Context context, int resourceId, ArrayList<CommnetsClass> items) {
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
            CommnetsClass comment  = getItem(position);
            TextView name = (TextView) view.findViewById(R.id.spinner_list);
            name.setText(comment.getComments());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return view;
    }
    @Nullable
    @Override
    public CommnetsClass getItem(int position) {
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
        return commentFilter;
    }
    private final Filter commentFilter = new Filter() {
        @Override
        public CharSequence convertResultToString(Object resultValue) {
            CommnetsClass comments = (CommnetsClass) resultValue;
            return comments.getComments();
        }
        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {
            if (charSequence != null) {
                suggestions.clear();
                for (CommnetsClass comment: tempItems) {
                    if (comment.getComments().toLowerCase().startsWith(charSequence.toString().toLowerCase())) {
                        suggestions.add(comment);
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
            ArrayList<CommnetsClass> tempValues = (ArrayList<CommnetsClass>) filterResults.values;
            if (filterResults != null && filterResults.count > 0) {
                clear();
                for (CommnetsClass commentObj : tempValues) {
                    add(commentObj);
                }
                notifyDataSetChanged();
            } else {
                clear();
                notifyDataSetChanged();
            }
        }
    };
}
