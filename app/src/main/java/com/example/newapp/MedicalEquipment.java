package com.example.newapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class MedicalEquipment extends AppCompatActivity {

    private List<String> equip_list = new ArrayList<>();
    private StringBuffer result;
    private EquipmentBean equipment;
    ListView equipList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medical_equipment);
        equip_list = getIntent().getStringArrayListExtra("equip_name");
        Log.d("MedicalEquipment", equip_list.toString());

        ArrayAdapter<String> adapter = new ArrayAdapter<>(MedicalEquipment.this,
                android.R.layout.simple_list_item_1, equip_list);
        equipList = findViewById(R.id.equipment_list);
        equipList.setAdapter(adapter);



        equipList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                String name = equipList.getItemAtPosition(position) + "";
                equipment = new EquipmentBean();
                equipment.setEquip_name(name);
                initEquipContent(name);
            }
        });

    }

    public void initEquipContent(String name) {
        HttpUtil.getHttpRequest(HttpUtil.IP + "/app/equip/" + name, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d("Fragment2", "访问服务器失败");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                try {
                    result = new StringBuffer();
                    result.append(response.body().string());
                    parseJsonObject(result.toString());
                    Log.d("MedicalEquipment", "result: " + result.toString());

                    Intent intent = new Intent(MedicalEquipment.this, Equip_item_content.class);
                    intent.putExtra("equip_name",equipment.getEquip_name());
                    intent.putExtra("equip_pic",equipment.getEquip_pic());
                    intent.putExtra("equip_intro",equipment.getEquip_intro());
                    startActivity(intent);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void parseJsonObject(String jsonData) {
        try {
            JSONArray jsonArray = new JSONArray(jsonData);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
//                Log.d("Fragment1", jsonObject.toString());
//                String equip_pic = jsonObject.getString("img");
//                String equip_intro = jsonObject.getString("con");
                equipment.setEquip_pic(jsonObject.getString("img"));
                equipment.setEquip_intro(jsonObject.getString("con"));
                Log.d("MedicalEquipment","pic: " + equipment.getEquip_pic());
                Log.d("MedicalEquipment","intro: " + equipment.getEquip_intro());
//                Log.d("MedicalEquipment","pic: " + equip_pic);
//                Log.d("MedicalEquipment","intro: " + equip_intro);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}


