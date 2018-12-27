package com.lmq.http;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.r.http.cn.callback.HttpCallback;

/**
 * 根据业务进一步封装
 *
 * @author ZhongDaFeng
 */
public abstract class RHttpCallback<T> extends HttpCallback<T> {

    @Override
    public T onConvert(String data) {
        /**
         * 接口响应数据格式如@Response
         * 将result转化给success
         * 这里处理通过错误
         */
        T t = null;
        AppResponse response = new Gson().fromJson(data, AppResponse.class);
//        int code = response.getErrcode();
//        String msg = response.getMsg();
//        JsonElement result = response.getData();
//        switch (code) {
//            case 101:
//                break;
//            case 102://系统公告(示例)
//                break;
//            default:
//                if (response.isSuccess()) {//与服务器约定成功逻辑
//                    t = convert(result);
//
//                } else {//统一为错误处理
//                    onError(code, msg);
//                }
//                break;
//        }
        return t;
    }

    /**
     * 数据转换/解析
     *
     * @param data
     * @return
     */
    public abstract T convert(JsonElement data);

    /**
     * 成功回调
     *
     * @param value
     */
    public abstract void onSuccess(T value);

    /**
     * 失败回调
     *
     * @param code
     * @param desc
     */
    public abstract void onError(int code, String desc);

    /**
     * 取消回调
     */
    public abstract void onCancel();
}
