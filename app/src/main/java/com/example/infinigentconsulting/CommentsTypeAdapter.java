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

public class CommentsTypeAdapter extends ArrayAdapter<CommentsTypeClass> {


    private final Context context;
    private final int resourceId;
    private final ArrayList<CommentsTypeClass> items;
    private final ArrayList<CommentsTypeClass> tempItems;
    private final ArrayList<CommentsTypeClass> suggestions;
    public CommentsTypeAdapter(@NonNull Context context, int resourceId, ArrayList<CommentsTypeClass> items) {
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
            CommentsTypeClass commentType  = getItem(position);
            TextView name = (TextView) view.findViewById(R.id.spinner_list);
            name.setText(commentType.getCommentsType());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return view;
    }
    @Nullable
    @Override
    public CommentsTypeClass getItem(int position) {
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
        return commentTypeFilter;
    }
    private final Filter commentTypeFilter = new Filter() {
        @Override
        public CharSequence convertResultToString(Object resultValue) {
            CommentsTypeClass commentsType = (CommentsTypeClass) resultValue;
            return commentsType.getCommentsType();
        }
        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {
            if (charSequence != null) {
                suggestions.clear();
                for (CommentsTypeClass commentType: tempItems) {
                    if (commentType.getCommentsType().toLowerCase().startsWith(charSequence.toString().toLowerCase())) {
                        suggestions.add(commentType);
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
            ArrayList<CommentsTypeClass> tempValues = (ArrayList<CommentsTypeClass>) filterResults.values;
            if (filterResults != null && filterResults.count > 0) {
                clear();
                for (CommentsTypeClass commentTypeObj : tempValues) {
                    add(commentTypeObj);
                }
                notifyDataSetChanged();
            } else {
                clear();
                notifyDataSetChanged();
            }
        }
    };

}
