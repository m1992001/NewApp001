package com.example.newapp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.support.v7.widget.Toolbar;


import java.util.ArrayList;
import java.util.List;

public class Fragment3 extends Fragment {

    private List<MyListItem> myListItems = new ArrayList<>();
    private Context mContext;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_3, container, false);
        return view;

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        String username1 = (String) getArguments().get("input_username");
        String userclass1 = (String) getArguments().get("reg_class");
        Log.d("Fragment3", " ****** " + username1 + "  " + userclass1);
        TextView username = getActivity().findViewById(R.id.username);
        TextView userclass = getActivity().findViewById(R.id.user_class);
        username.setText(username1);
        userclass.setText(userclass1);


        setHasOptionsMenu(true);
        Toolbar toolbar_my = getActivity().findViewById(R.id.toolbar_my);
        toolbar_my.setTitle("  ");
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar_my);

        initItem();
        ItemAdapter adapter = new ItemAdapter(getContext(), R.layout.my_item, myListItems);
        final ListView listView = getActivity().findViewById(R.id.my_item_list);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                MyListItem myListItem = myListItems.get(i);
                switch (myListItem.getItemName()) {
                    case "病人治疗信息":
                        Log.d("Switch", myListItem.getItemName());
                        Intent intent = new Intent(getActivity(), WebView1.class);
                        startActivity(intent);
                        break;
                    case "医生工作量信息":
                        Toast.makeText(getContext(), myListItem.getItemName(), Toast.LENGTH_LONG);
                        Intent intent1 = new Intent(getActivity(), WebView2.class);
                        Log.d("Switch", myListItem.getItemName());
                        startActivity(intent1);
                        break;
                    case "本月康复信息":
                        Toast.makeText(getContext(), myListItem.getItemName(), Toast.LENGTH_LONG);
                        Intent intent2 = new Intent(getActivity(), WebView3.class);
                        Log.d("Switch", myListItem.getItemName());
                        startActivity(intent2);
                        break;
                    case "Berg平衡量表":
                        Intent intent3 = new Intent(getActivity(), Evaluate_form1.class);
                        startActivity(intent3);
                        break;
                    case "JOA颈椎评分":
                        Intent intent4 = new Intent(getActivity(), Evaluate_form2.class);
                        startActivity(intent4);
                        break;
                    case "Fugl-Meyer平衡量表":
                        Intent intent5 = new Intent(getActivity(), Evaluate_form3.class);
                        startActivity(intent5);
                        break;
                    case "Levine腕管综合征问卷":
                        Intent intent6 = new Intent(getActivity(), Evaluate_form4.class);
                        startActivity(intent6);
                        break;
                    case "AOFAS踝与后足功能评分":
                        Intent intent7 = new Intent(getActivity(), Evaluate_form.class);
                        startActivity(intent7);
                        break;
                    default:
                }
            }
        });

    }

//    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
//        super.onCreateOptionsMenu(menu, inflater);
//        inflater.inflate(R.menu.toolbar_my, menu);
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

    private void initItem() {
//        MyListItem item1 = new MyListItem("我的消息", R.drawable.my_info);
//        myListItems.add(item1);
        MyListItem item2 = new MyListItem("病人治疗信息", R.drawable.my_data);
        myListItems.add(item2);
        MyListItem item3 = new MyListItem("医生工作量信息", R.drawable.my_productivity);
        myListItems.add(item3);
        MyListItem item4 = new MyListItem("本月康复信息", R.drawable.my_health);
        myListItems.add(item4);
        MyListItem item5 = new MyListItem("AOFAS踝与后足功能评分", R.drawable.form);
        myListItems.add(item5);
        MyListItem item6 = new MyListItem("Berg平衡量表", R.drawable.form);
        myListItems.add(item6);
        MyListItem item7 = new MyListItem("JOA颈椎评分", R.drawable.form);
        myListItems.add(item7);
        MyListItem item8 = new MyListItem("Fugl-Meyer平衡量表", R.drawable.form);
        myListItems.add(item8);
        MyListItem item9 = new MyListItem("Levine腕管综合征问卷", R.drawable.form);
        myListItems.add(item9);

    }
}
