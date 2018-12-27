package com.lmq.http;

import com.r.http.cn.callback.HttpCallback;

/**
 * 根据业务进一步封装
 *
 * @author ZhongDaFeng
 */
public abstract class CommonAllHttpCallback<T> extends HttpCallback<T> {

    @Override
    public T onConvert(String data) {
        /**
         * 接口响应数据格式如@Response
         * 将result转化给success
         * 这里处理通过错误
         */


            return convert(data);

    }

    /**
     * 数据转换/解析
     *
     * @param data
     * @return
     */
    public abstract T convert(String data);

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
