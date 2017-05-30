package com.example.sang.baitaplon;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by sang on 29/05/2017.
 */

public class StoriesAdapter extends RecyclerView.Adapter<StoriesAdapter.ViewHolder> {
    public ArrayList<Story> stories;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView txt_story_title;
        public TextView txt_story_content;

        public ViewHolder(View itemView) {
            super(itemView);
            txt_story_title = (TextView) itemView.findViewById(R.id.txt_story_title);
            txt_story_content = (TextView) itemView.findViewById(R.id.txt_story_content);
        }
    }

    public StoriesAdapter(ArrayList<Story> stories) {
        this.stories = stories;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View storyView = inflater.inflate(R.layout.story_item, parent, false);

        ViewHolder viewHolder = new ViewHolder(storyView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        final Story story = stories.get(position);

        TextView txtTitle = holder.txt_story_title;
        txtTitle.setText(story.getTitle());
        TextView txtContent = holder.txt_story_content;
        txtContent.setText(story.getContent().substring(0, 150) + "...");

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Context context = v.getContext();
                Intent intent = new Intent(context, StoryActivity.class);
                intent.putExtra("id", story.getId());
                intent.putExtra("title", story.getTitle());
                intent.putExtra("content", story.getContent());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return stories.size();
    }
}
