package com.lmq.base;


import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.os.Looper;
import android.support.annotation.Nullable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.newapp.R;
import com.trello.rxlifecycle2.components.support.RxFragment;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import pub.devrel.easypermissions.EasyPermissions;


/**
 * 基类Fragment
 * 备注:所有的Fragment都继承自此Fragment
 * 1.规范团队开发
 * 2.统一处理Fragment所需配置,初始化
 *
 * @author ZhongDaFeng
 */
public abstract class BaseFragment extends RxFragment implements EasyPermissions.PermissionCallbacks {

    protected Context mContext;
    protected Unbinder unBinder;
    protected View mView;
    public LifeCycleListener mListener;


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if (mListener != null) {
            mListener.onAttach(activity);
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            if (mListener != null) {
                mListener.onCreate(savedInstanceState);
            }
            mContext = getActivity();
            if (mContext == null) return;
            mView = LayoutInflater.from(mContext).inflate(setContentView(), null);
            unBinder = ButterKnife.bind(this, mView);
            initBundleData();
            initView();
            // initData();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    public void showToast(String mes){

       // ToastUtils.showToast(getActivity(),mes);
        showMes(mes);
    }

    public  void showLoading(){
        showProDialog("");
    }

    public   void closeLoading(){
        hidProDialog();
    }

    public void showError(String err){
        hidProDialog();
        showToast(err);
    }
    public  void showMes( String msg) {
        Toast toast = new Toast(mContext);
        //设置Toast显示位置，居中，向 X、Y轴偏移量均为0
        toast.setGravity(Gravity.CENTER, 0, 0);
        //获取自定义视图
        View view = LayoutInflater.from(mContext).inflate(R.layout.toast_view, null);
        TextView tvMessage = (TextView) view.findViewById(R.id.tv_message_toast);
        //设置文本
        tvMessage.setText(msg);
        //设置视图
        toast.setView(view);
        //设置显示时长
        toast.setDuration(Toast.LENGTH_LONG);
        //显示
        toast.show();


    }

    private ProgressDialog pdialog ;
    public void showProDialog(final String mes) {
        Thread t = new Thread(new Runnable() {
            public void run() {
                Looper.prepare();
                pdialog = new ProgressDialog(getActivity());
                pdialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                pdialog.setTitle("");
                pdialog.setMessage(mes);
                pdialog.setIndeterminate(false);
                pdialog.setCancelable(true);


                pdialog.show();
                Looper.loop();
            }
        });
        t.start();
    }
    public void hidProDialog(){
        if(pdialog!=null){
            pdialog.cancel();
            pdialog.cancel();
        }
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (mListener != null) {
            mListener.onCreateView(inflater, container, savedInstanceState);
        }
        if (mView.getParent() != null) {
            ((ViewGroup) mView.getParent()).removeView(mView);
        }
        return mView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (mListener != null) {
            mListener.onActivityCreated(savedInstanceState);
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        if (mListener != null) {
            mListener.onStart();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (mListener != null) {
            mListener.onResume();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (mListener != null) {
            mListener.onPause();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mListener != null) {
            mListener.onStop();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (mListener != null) {
            mListener.onDestroyView();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mListener != null) {
            mListener.onDestroy();
        }
        //移除view绑定
        if (unBinder != null) {
            unBinder.unbind();
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        if (mListener != null) {
            mListener.onDetach();
        }
    }

    /**
     * 是否已经创建
     *
     * @return
     */
    public boolean isCreated() {
        return mView != null;
    }

    /**
     * 获取显示view
     */
    public abstract int setContentView();

    /**
     * 获取上一个界面传送过来的数据
     */
    protected abstract void initBundleData();

    /**
     * 初始化view
     */
    protected abstract void initView();

//    /**
//     * 初始化Data
//     */
//    protected abstract void initData();

    @Override
    public void onPermissionsGranted(int requestCode, List<String> list) {
    }

    @Override
    public void onPermissionsDenied(int requestCode, List<String> list) {
    }

    /**
     * 设置生命周期回调函数
     *
     * @param listener
     */
    public void setOnLifeCycleListener(LifeCycleListener listener) {
        mListener = listener;
    }

}
