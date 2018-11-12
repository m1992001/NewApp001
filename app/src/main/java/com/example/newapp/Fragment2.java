package com.example.newapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.miguelcatalan.materialsearchview.MaterialSearchView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;


public class Fragment2 extends Fragment{

    private MaterialSearchView searchView;
    private StringBuffer result;
    private StringBuffer answer;
    private ArrayList<String> equip_list = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_2, container, false);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        searchView = getActivity().findViewById(R.id.search_view);


        setHasOptionsMenu(true);
        Toolbar search = getActivity().findViewById(R.id.search_toolbar);
        search.setTitle("  ");
        ((AppCompatActivity) getActivity()).setSupportActionBar(search);


    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.search, menu);

        MenuItem item = menu.findItem(R.id.action_search);
        searchView.setMenuItem(item);

        MaterialSearchView searchView = getActivity().findViewById(R.id.search_view);
        searchView.setHint("请输入医疗设备名称");
        searchView.setOnQueryTextListener(new MaterialSearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Log.d("Search", query);
                initEquipmentItem(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                Log.d("Search",newText);
                return false;
            }
        });

        CardView medical_equipment = getActivity().findViewById(R.id.medical_equipment);
        medical_equipment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                initEquipmentList();
            }
        });

        CardView study_video = getActivity().findViewById(R.id.study_video);
        study_video.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), VideoList.class);
                startActivity(intent);
            }
        });
    }

    public void initEquipmentList(){
        HttpUtil.getHttpRequest(HttpUtil.IP + "/app/equip", new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d("Fragment2","访问服务器失败");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                try {
                    result = new StringBuffer();
                    result.append(response.body().string());
                    parseJsonObject(result.toString());
//                    Log.d("Fragment1", "result; " + result.toString());
                    Intent intent = new Intent(getActivity(), MedicalEquipment.class);
                    intent.putStringArrayListExtra("equip_name", equip_list);
                    startActivity(intent);
                    equip_list.clear();
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
                String equip_name = jsonObject.getString("name");
                equip_list.add(equip_name);
//                Log.d("MedicalEquipment","name: " + equip_name);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void initEquipmentItem(final String query){
        HttpUtil.getHttpRequest(HttpUtil.IP + "/app/equip/" + query, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d("Fragment2","访问服务器失败");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                answer = new StringBuffer();
                String name = query;
                answer.append(response.body().string());
                String strAnswer = answer.toString();
                Log.d("Answer",strAnswer);
                Log.d("Answer","" + strAnswer.length());
                if ("0".equals(strAnswer.trim())){
                    Log.d("Answer", "Toast");
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getActivity(),"搜索没有结果",Toast.LENGTH_LONG).show();
                        }
                    });
                }else{
                    Log.d("Answer", "------------");
                    parseJsonQuery(name, answer.toString());
                }
            }
        });
    }

    public void parseJsonQuery(String name,String jsonQuery){
        try {
            JSONArray jsonArray = new JSONArray(jsonQuery);
            for(int i = 0; i < jsonArray.length(); i++){
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                String equip_name = name;
                String equip_pic = jsonObject.getString("img");
                String equip_intro = jsonObject.getString("con");
                Intent intent = new Intent(getActivity(),Equip_item_content.class);
                intent.putExtra("equip_name",equip_name);
                intent.putExtra("equip_pic",equip_pic);
                intent.putExtra("equip_intro",equip_intro);
                startActivity(intent);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
