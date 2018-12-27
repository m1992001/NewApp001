package com.lmq.ui;

import com.example.newapp.Content;

import java.util.ArrayList;

/**
 * Created by Administrator on 2018/12/25 0025.
 */

public interface Login_View {
    void loginresult(String result);
    void getinitcontent( ArrayList<Content> result);
}
