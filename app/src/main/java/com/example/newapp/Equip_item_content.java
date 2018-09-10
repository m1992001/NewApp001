package com.example.newapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

public class Equip_item_content extends AppCompatActivity {

    private TextView equipName;
    private ImageView equipPic;
    private TextView equipIntro;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_equip_item_content);

        equipName = findViewById(R.id.equip_name);
        equipPic = findViewById(R.id.equip_pic);
        equipIntro =findViewById(R.id.equip_intro);

        equipName.setText(getIntent().getStringExtra("equip_name"));
//        Log.d("MedicalEquipment",HttpUtil.IP + getIntent().getStringExtra("equip_pic"));
        Glide.with(this).load(HttpUtil.IP + getIntent().getStringExtra("equip_pic")).into(equipPic);
        equipIntro.setText(getIntent().getStringExtra("equip_intro"));
    }
}
