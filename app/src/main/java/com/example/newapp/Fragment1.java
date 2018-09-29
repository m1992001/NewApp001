package com.example.newapp;

import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Response;


public class Fragment1 extends Fragment{

    private ArrayList<Content> contentList = new ArrayList<>();
    private SwipeRefreshLayout swipeRefresh;
    private ContentAdapter contentAdapter;
    private StringBuffer result;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_1, container, false);
        Log.d("FragmentLife", "onCreateView");
        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        contentList = (ArrayList<Content>) getActivity().getIntent().getSerializableExtra("news");
//        RecyclerView recyclerView = getActivity().findViewById(R.id.recycler_view_fragment);
//        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
//        recyclerView.setLayoutManager(layoutManager);
//        contentAdapter = new ContentAdapter(contentList);
//        recyclerView.setAdapter(contentAdapter);
//        Log.d("FragmentLife", "onCreate");
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

//        getInitContent();
//        contentList = (ArrayList<Content>) getActivity().getIntent().getSerializableExtra("news");
        Log.d("FragmentLife", contentList.toString());
        RecyclerView recyclerView = getActivity().findViewById(R.id.recycler_view_fragment);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        contentAdapter = new ContentAdapter(contentList);
        recyclerView.setAdapter(contentAdapter);

        swipeRefresh = getActivity().findViewById(R.id.swipe_refresh);
        swipeRefresh.setColorSchemeResources(R.color.colorPrimary);
        swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshContents();
            }
        });

        setHasOptionsMenu(true);
        Toolbar toolbar = getActivity().findViewById(R.id.toolbar);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);

    }

    private void refreshContents() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                HttpUtil.getHttpRequest(HttpUtil.IP + "/app/news", new okhttp3.Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        Log.d("Fragment1", "刷新失败");
                    }

                    @Override
                    public void onResponse(Call call, Response response) {
                        try {
                            String update_result = response.body().string();
                            contentList.clear();
                            parseJsonObject(update_result);
                            getActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    contentAdapter.notifyDataSetChanged();
                                    swipeRefresh.setRefreshing(false);
                                    Log.d("Fragment1_refresh_stop", contentList.toString());
                                }
                            });
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
        }).start();
    }

//    private void getInitContent() {
//        HttpUtil.getHttpRequest(HttpUtil.IP + "/app/news", new okhttp3.Callback() {
//            @Override
//            public void onFailure(Call call, IOException e) {
//                Log.d("Fragment1", "服务器访问失败");
//            }
//
//            @Override
//            public void onResponse(Call call, Response response) {
//                try {
//                    result = new StringBuffer();
//                    result.append(response.body().string());
////                    Log.d("Fragment1", "result; " + result.toString());
//                    parseJsonObject(result.toString());
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//        });
//    }
//
//
    public void parseJsonObject(String jsonData) {
        try {
            JSONArray jsonArray = new JSONArray(jsonData);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
//                Log.d("Fragment1", jsonObject.toString());
                String title = jsonObject.getString("title");
                String picture = jsonObject.getString("img");
//                String con = jsonObject.getString("con");
//                Content content = new Content(title, picture, con);
                Content content = new Content(title,picture);
                contentList.add(content);
//                Log.d("Fragment1","title: " + title);
//                Log.d("Fragment1","picture: " + picture);
//                Log.d("Fragment1","content: " + con);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

//    @Override
//    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
//        super.onCreateOptionsMenu(menu, inflater);
//        inflater.inflate(R.menu.toolbar, menu);
//    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.backup:
                Toast.makeText(getContext(), "You clicked Backup", Toast.LENGTH_SHORT).show();
                break;
            case R.id.delete:
                Toast.makeText(getContext(), "You clicked Delete", Toast.LENGTH_SHORT).show();
                break;
            case R.id.settings:
                Toast.makeText(getContext(), "You clicked Settings", Toast.LENGTH_SHORT).show();
                break;
            default:
        }
        return true;
    }
}
