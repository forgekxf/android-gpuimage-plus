package com.bhtc.huajuan.push.util;

import android.view.View;
import android.widget.RelativeLayout;

/**
 * Created by kouxiongfei on 2017/5/19.
 */

public class ViewWapper {
    private View mTargetView;

    public ViewWapper(View mTargetView) {
        this.mTargetView = mTargetView;
    }

    public int getBottomMargin() {
        return ((RelativeLayout.LayoutParams) mTargetView.getLayoutParams()).bottomMargin;
    }

    public void setBottomMargin(int bottomMargin){
        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) mTargetView.getLayoutParams();
        layoutParams.bottomMargin = bottomMargin;
        mTargetView.setLayoutParams(layoutParams);
    }
}
