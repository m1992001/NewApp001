package com.example.newapp;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class ItemAdapter extends ArrayAdapter<MyListItem> {

    private int resourceId;

    public ItemAdapter(@NonNull Context context, int resource, @NonNull List<MyListItem> objects) {
        super(context, resource, objects);
        resourceId = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        MyListItem myListItem = getItem(position);
        View view = LayoutInflater.from(getContext()).inflate(resourceId, parent, false);
        ImageView imageView = view.findViewById(R.id.item_image);
        TextView textView = view.findViewById(R.id.item_name);
        imageView.setImageResource(myListItem.getItemImageId());
        textView.setText(myListItem.getItemName());

        return view;

    }
}
