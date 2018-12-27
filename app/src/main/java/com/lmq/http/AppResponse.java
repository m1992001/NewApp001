package com.lmq.http;

import java.io.Serializable;

/**
 * Created by Administrator on 2018/12/25 0025.
 */

public class AppResponse implements Serializable{
    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    //@SerializedName("flag")
    public String flag;
}
