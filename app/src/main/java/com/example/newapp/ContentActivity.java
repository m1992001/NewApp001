package com.example.newapp;

import android.content.Intent;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.support.v7.widget.Toolbar;

import com.bumptech.glide.Glide;

public class ContentActivity extends AppCompatActivity {

    public static final String CONTENT_NAME = "content_name";
    public static final String CONTENT_IMAGE_ID = "content_image_id";
    public static final String CONTENT_TEXT = "content_text";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_content);

        Intent intent = getIntent();
        String content_Name = intent.getStringExtra(CONTENT_NAME);
        String content_Text = intent.getStringExtra(CONTENT_TEXT);
        String content_Image = intent.getStringExtra(CONTENT_IMAGE_ID);

        Toolbar toolbar = findViewById(R.id.toolbar);
        CollapsingToolbarLayout collapsingToolbar = findViewById(R.id.collapsing_toolbar);
        ImageView contentImageView = findViewById(R.id.content_image_view);
        TextView contentText = findViewById(R.id.content_text);

        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        collapsingToolbar.setTitle(content_Name);
        Glide.with(this).load(HttpUtil.IP + content_Image).into(contentImageView);
        contentText.setText(content_Text);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
