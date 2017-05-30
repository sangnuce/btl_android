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

public class CategoriesAdapter extends RecyclerView.Adapter<CategoriesAdapter.ViewHolder> {
    public ArrayList<Category> categories;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView txt_category_name;

        public ViewHolder(View itemView) {
            super(itemView);
            txt_category_name = (TextView) itemView.findViewById(R.id.txt_category_name);
        }
    }

    public CategoriesAdapter(ArrayList<Category> categories) {
        this.categories = categories;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View categoryView = inflater.inflate(R.layout.category_item, parent, false);

        ViewHolder viewHolder = new ViewHolder(categoryView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        final Category category = categories.get(position);

        TextView textView = holder.txt_category_name;
        textView.setText(category.getName());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Context context = v.getContext();
                Intent intent = new Intent(context, CategoryActivity.class);
                intent.putExtra("category_id", category.getId());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return categories.size();
    }
}
