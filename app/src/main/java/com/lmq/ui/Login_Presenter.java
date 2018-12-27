package com.lmq.ui;

import com.example.newapp.Content;
import com.lmq.base.BaseActivity;
import com.lmq.base.BasePresenter;
import com.lmq.common.Appservices;
import com.lmq.http.CommonHttpCallback;
import com.lmq.tool.LmqTool;
import com.r.http.cn.utils.LogUtils;
import com.trello.rxlifecycle2.LifecycleProvider;

import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Administrator on 2018/12/25 0025.
 */

public class Login_Presenter extends BasePresenter<Login_View, LifecycleProvider> {
    private final String TAG = Login_Presenter.class.getSimpleName();

    public Login_Presenter(Login_View view, LifecycleProvider activity) {
        super(view, activity);
    }

    /**
     * 登录
     *
     * @author ZhongDaFeng
     */
    public void login(String userName, String password) {

        if (getView() != null)
            // getView().showLoading();
            ((BaseActivity)getActivity()).showLoading();



        CommonHttpCallback httpCallback = new CommonHttpCallback<String>() {

            @Override
            public String convert(String data) {
                return data;
               // return new Gson().fromJson(data, String.class);
            }

            @Override
            public void onSuccess(String object) {
                if (getView() != null) {

                    ((BaseActivity)getActivity()).closeLoading();
                    if(object!=null) {

                        getView().loginresult(object);
                    }
                }
            }

            @Override
            public void onError(int code, String desc) {
                if (getView() != null) {

                    ((BaseActivity)getActivity()).showError(desc);
                }
            }

            @Override
            public void onCancel() {
                LogUtils.e("请求取消了");
                if (getView() != null) {

                    ((BaseActivity)getActivity()).closeLoading();
                }
            }
        };

        new Appservices().login(userName, password, getActivity(), httpCallback);

    }
    public void getinitContent(){
        if (getView() != null)
            // getView().showLoading();
            ((BaseActivity)getActivity()).showLoading();



        CommonHttpCallback httpCallback = new CommonHttpCallback< ArrayList<Content>>() {

            @Override
            public  ArrayList<Content> convert(String data) {

                try {

                    ArrayList<Content> arrayList= LmqTool.jsonToArrayList(data, Content.class);
                    return arrayList;
                    // return new Gson().fromJson(data, String.class);
                }catch (Exception e){
                    e.printStackTrace();
                }
                return null;
                // return new Gson().fromJson(data, String.class);
            }

            @Override
            public void onSuccess( ArrayList<Content> object) {
                if (getView() != null) {

                    ((BaseActivity)getActivity()).closeLoading();
                    if(object!=null) {

                        getView().getinitcontent(object);
                    }
                }
            }

            @Override
            public void onError(int code, String desc) {
                if (getView() != null) {

                    ((BaseActivity)getActivity()).showError(desc);
                }
            }

            @Override
            public void onCancel() {
                LogUtils.e("请求取消了");
                if (getView() != null) {

                    ((BaseActivity)getActivity()).closeLoading();
                }
            }
        };
        new Appservices().getinitContent( getActivity(), httpCallback);
    }
}
