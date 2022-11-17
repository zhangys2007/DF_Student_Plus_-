package com.example.df.zhiyun.app;


/***
 * 用来标记app在后台的时候是否被系统回收，被回收过就得重走启动流程
 */
public class StatusHolder {
    private static StatusHolder mInstance;
    private boolean isKill = true;

    public boolean isKill() {
        return isKill;
    }

    public void setKill(boolean kill) {
        isKill = kill;
    }

    private StatusHolder() {

    }

    public static StatusHolder getInstance() {
        if (mInstance == null) {
            synchronized (StatusHolder.class) {
                if (mInstance == null) {
                    mInstance = new StatusHolder();
                }
            }
        }
        return mInstance;
    }
}
