package com.lmq.common;

import android.content.Context;

import com.ddmeng.preferencesprovider.provider.PreferencesStorageModule;
import com.example.newapp.Content;
import com.lmq.tool.LmqTool;

import java.util.ArrayList;

/**
 * Created by Administrator on 2018/12/25 0025.
 */

public class Appstorage  {

    public static PreferencesStorageModule myModule=null;
    public static void initModel(Context context){
        if(myModule==null)
            myModule = new PreferencesStorageModule(context, "appModule");
    }

    public static void setLoginUsernameAndPwd(Context context,String username,String pwd){

        initModel(context);
        myModule.put("user.username", username);
        myModule.put("user.pwd_"+username, pwd);//密码需要加密保存，与服务端协商，AES

    }

    public static String getLoginUserName(Context context){

        initModel(context);
        return myModule.getString("user.username", "");
    }
    public static void setLoginUserPwd(Context context,String username,String pwd){

        initModel(context);

        myModule.put("user.pwd_"+username, pwd);
    }
    public static String getLoginUserPwd(Context context,String username){

        return myModule.getString("user.pwd_"+username, "");
    }
    //登陆状态, true为已登陆,false为未登陆.需要跳转到登陆页面
    public  static void setLoginState(Context mcontext,boolean islogin){
        initModel(mcontext);
        myModule.put("user.loginstate",islogin);
    }
    public static boolean getLoginState(Context mcontext){
        initModel(mcontext);
        return myModule.getBoolean("user.loginstate",false);
    }
    public  static  void saveContentList(Context mcontext,String contentstr){
        initModel(mcontext);
        myModule.put("contentstr",contentstr);
    }
    public static  ArrayList<Content> getContentList(Context mcontext){
        initModel(mcontext);
        String data=myModule.getString("contentstr","");
        if(data.length()==0)
            return null;
        ArrayList<Content> arrayList= LmqTool.jsonToArrayList(data, Content.class);
        return arrayList;
    }

}
