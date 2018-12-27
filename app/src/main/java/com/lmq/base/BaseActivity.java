package com.lmq.base;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.os.Looper;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.example.newapp.R;
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import pub.devrel.easypermissions.EasyPermissions;


/**
 * 基类Activity
 * 备注:所有的Activity都继承自此Activity
 * 1.规范团队开发
 * 2.统一处理Activity所需配置,初始化
 *
 * @author ZhongDaFeng
 */
public abstract class BaseActivity extends RxAppCompatActivity implements EasyPermissions.PermissionCallbacks {

    protected Context mContext;
    protected Unbinder unBinder;
    public LifeCycleListener mListener;
    /**
     * 获取显示view的xml文件ID
     */
    protected abstract int setContentView();
    /**
     * 获取上一个界面传送过来的数据
     */
    protected abstract void initBundleData();
    /**
     * 初始化view
     */
    protected abstract void initView();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            Thread.setDefaultUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {

                @Override
                public void uncaughtException(Thread thread, Throwable ex) {
                    // TODO Auto-generated method stub
                    System.out.println(ex.getLocalizedMessage());
                    finish();//关闭后台
                    System.exit(0);
                }
            });
            if (mListener != null) {
                mListener.onCreate(savedInstanceState);
            }
            ActivityStackManager.getManager().push(this);
            supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
         //   getSupportActionBar().hide();
           // getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
//            getWindow().setFormat(PixelFormat.TRANSLUCENT);
//            Window win = getWindow();
//            win.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
//            win.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
//            win.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
//            win.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
//                    WindowManager.LayoutParams.FLAG_FULLSCREEN);
//            getWindow().setFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON, WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
//            //hideVirtualKey();
            //APPTool.hideBottomUIMenu(BaseActivity.this);影藏底部虚拟按键
            //hideBottomUIMenu();
          //  requestWindowFeature(Window.FEATURE_NO_TITLE);


            int view=setContentView();
            if(view==-1){
                View view2=setContentView_View();
                setContentView(view2);
                unBinder = ButterKnife.bind(this,view2);
            }else {
                setContentView(setContentView());
                unBinder = ButterKnife.bind(this);
            }
            mContext = this;

            initBundleData();
            initView();
        }catch (Exception e){
            e.printStackTrace();
        }
        //initData();
    }
    public View setContentView_View(){
        return null;
       /* View view= View.inflate(this, R.layout.logo, null);
        return view;*/
    }

    public static  void hideBottomUIMenu(Activity activity){
        Window window = activity.getWindow();
        WindowManager.LayoutParams params = window.getAttributes();
        //params.systemUiVisibility = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION;//触摸显示
        params.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_STABLE

                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION

                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN

                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION // hide nav bar

                | View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_IMMERSIVE;//始终隐藏，触摸屏幕时也不出现
        window.setAttributes(params);
    }
    public void showToast(String mes){

        //ToastUtils.showToast(BaseActivity.this,mes);
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
        toast.setDuration(Toast.LENGTH_SHORT);
        //显示
        toast.show();


    }
    private ProgressDialog pdialog ;
    public void showProDialog(final String mes) {
        Thread t = new Thread(new Runnable() {
            public void run() {
                Looper.prepare();
                pdialog = new ProgressDialog(BaseActivity.this);
                pdialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                pdialog.setTitle(null);
                pdialog.setMessage(mes.length()==0?null:mes);
                pdialog.setIndeterminate(false);
                pdialog.setCancelable(true);
                Window window=pdialog.getWindow();
                WindowManager.LayoutParams params = window.getAttributes();
                //params.systemUiVisibility = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION;//触摸显示
                params.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_STABLE

                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION

                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN

                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION // hide nav bar

                        | View.SYSTEM_UI_FLAG_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_IMMERSIVE;//始终隐藏，触摸屏幕时也不出现
               // params.width=100;
                window.setAttributes(params);


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
    protected void onStart() {
        super.onStart();
        if (mListener != null) {
            mListener.onStart();
        }
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        if (mListener != null) {
            mListener.onRestart();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mListener != null) {
            mListener.onResume();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mListener != null) {
            mListener.onPause();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (mListener != null) {
            mListener.onStop();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mListener != null) {
            mListener.onDestroy();
        }
        //移除view绑定
        if (unBinder != null) {
            unBinder.unbind();
        }
        ActivityStackManager.getManager().remove(this);
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

    @Override
    public void onPermissionsGranted(int requestCode, List<String> list) {
    }

    @Override
    public void onPermissionsDenied(int requestCode, List<String> list) {
    }



//    /**
//     * 初始化Data
//     */
//    protected abstract void initData();

    /**
     * 设置生命周期回调函数
     *
     * @param listener
     */
    public void setOnLifeCycleListener(LifeCycleListener listener) {
        mListener = listener;
    }


}
