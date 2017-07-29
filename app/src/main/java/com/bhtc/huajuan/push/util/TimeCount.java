package com.bhtc.huajuan.push.util;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.TextView;

/**
 * 时间倒计时
 * Created by Administrator on 2016/4/26.
 */
public class TimeCount extends CountDownTimer {

    private Context mContext;
    public TextView go_money_time;
    private String text = "";

    public TimeCount(Context context, TextView go_money_time, long millisInFuture, long countDownInterval) {
        super(millisInFuture, countDownInterval);//参数依次为总时长,和计时的时间间隔
        mContext = context;
        this.go_money_time = go_money_time;
    }

    public void setText(String text){
        this.text = text;
    }

    public interface onFinishClickListerner{
        void onClick();
    }

    private onFinishClickListerner mOnFinishClickListerner;

    public void setOnFinishClickListerner(onFinishClickListerner mOnFinishClickListerner){
        this.mOnFinishClickListerner = mOnFinishClickListerner;
    }

    @Override
    public void onFinish() {//计时完毕时触发
        go_money_time.setVisibility(View.GONE);
        if (mOnFinishClickListerner != null){
            mOnFinishClickListerner.onClick();
        }
    }

    @SuppressLint("SimpleDateFormat")
    @Override
    public void onTick(long millisUntilFinished) {//计时过程显示
//        go_money_time.setText("距您直播还有"+ generateTime(millisUntilFinished));
        go_money_time.setText(text+ generateTime(millisUntilFinished));
    }

    /**
     * 转换时间显示
     *
     * @param time 毫秒
     * @return
     */
    public static String generateTime(long time) {
        int totalSeconds = (int) (time / 1000);
        int seconds = totalSeconds % 60;
        int minutes = (totalSeconds / 60) % 60;
        int hours = totalSeconds / 3600;

        return hours > 0 ? String.format("%02d时%02d分%02d秒", hours, minutes,
                seconds) : String.format("%02d分%02d秒", minutes, seconds);
    }


    public static String hotVideoTime(int totalSeconds) {
        int seconds = totalSeconds % 60;
        int minutes = (totalSeconds / 60) % 60;
        int hours = totalSeconds / 3600;

        if (totalSeconds < 600){
            return String.format("%2d:%02d", minutes, seconds);
        }
        return hours > 0 ? String.format("%02d:%02d:%02d", hours, minutes,
                seconds) : String.format("%02d:%02d", minutes, seconds);
    }

}
