package com.example.sang.baitaplon;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class StoryActivity extends AppCompatActivity {
    private TextView txt_story_title;
    private TextView txt_story_content;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_story);
        init();
    }

    private void init() {
        Intent intent = getIntent();
        String title = intent.getStringExtra("title");
        String content = intent.getStringExtra("content");

        txt_story_title = (TextView) findViewById(R.id.txt_story_title);
        txt_story_title.setText(title);

        txt_story_content = (TextView) findViewById(R.id.txt_story_content);
        txt_story_content.setText(content);
    }
}
