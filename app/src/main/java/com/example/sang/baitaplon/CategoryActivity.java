package com.example.sang.baitaplon;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import static android.R.attr.name;

public class CategoryActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);
        loadData();
    }

    private void loadData() {
        Intent intent = getIntent();
        int category_id = intent.getIntExtra("category_id", 0);
        String link = "http://truyen-api.herokuapp.com/categories/" + category_id + ".json";
        new GetData(link, this, new CallbackInterface() {
            @Override
            public void onGetDataFinished(JSONObject result) {
                if (result != null) {
                    try {
                        ActionBar actionBar = getSupportActionBar();
                        JSONObject category = result.getJSONObject("category");
                        actionBar.setTitle(category.getString("name"));
                        ArrayList<Story> stories = new ArrayList<>();
                        JSONArray arr_stories = category.getJSONArray("stories");
                        for (int i = 0; i < arr_stories.length(); i++) {
                            JSONObject c = arr_stories.getJSONObject(i);
                            int id = c.getInt("id");
                            String title = c.getString("title");
                            String content = c.getString("content");
                            Story story = new Story(id, title, content);
                            stories.add(story);
                        }
                        RecyclerView rvStories = (RecyclerView) findViewById(R.id.rvStories);
                        StoriesAdapter adapter = new StoriesAdapter(stories);
                        rvStories.setAdapter(adapter);
                        rvStories.setLayoutManager(new LinearLayoutManager(CategoryActivity.this));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).execute();
    }
}
