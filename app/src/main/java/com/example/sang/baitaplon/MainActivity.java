package com.example.sang.baitaplon;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private String link = "http://truyen-api.herokuapp.com/categories.json";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        loadData();
    }

    private void loadData() {
        new GetData(link, this, new CallbackInterface() {
            @Override
            public void onGetDataFinished(JSONObject result) {
                if (result != null) {
                    try {
                        ArrayList<Category> categories = new ArrayList<>();
                        JSONArray arr_categories = result.getJSONArray("categories");
                        for (int i = 0; i < arr_categories.length(); i++) {
                            JSONObject c = arr_categories.getJSONObject(i);
                            int id = c.getInt("id");
                            String name = c.getString("name");
                            Category category = new Category(id, name);
                            categories.add(category);
                        }
                        RecyclerView rvCategories = (RecyclerView) findViewById(R.id.rvCategories);
                        CategoriesAdapter adapter = new CategoriesAdapter(categories);
                        rvCategories.setAdapter(adapter);
                        rvCategories.setLayoutManager(new LinearLayoutManager(MainActivity.this));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).execute();
    }
}
