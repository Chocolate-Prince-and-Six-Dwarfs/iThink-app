package com.example.myapplication.model.Adapter;

import android.content.Context;
import android.icu.text.Transliterator;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.myapplication.R;
import com.example.myapplication.model.pojo.Idea;

import java.util.List;

public class IdeaAdapter extends ArrayAdapter<Idea> {

    private int resourceId;

    public IdeaAdapter(Context context ,int textViewResourceId , List<Idea> Object){
        super(context , textViewResourceId , Object);
        resourceId = textViewResourceId;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Idea idea = getItem(position);
        View view = LayoutInflater.from(getContext()).inflate(resourceId,parent,false);
        TextView idea_title = (TextView) view.findViewById(R.id.idea_title);
        TextView idea_content = (TextView) view.findViewById(R.id.idea_content);
        idea_title.setText(idea.getTitle());
        idea_content.setText(idea.getContent());
        return view;
    }
}
