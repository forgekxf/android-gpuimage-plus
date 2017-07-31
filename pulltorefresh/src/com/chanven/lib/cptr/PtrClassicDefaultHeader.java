package com.chanven.lib.cptr;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.TypedArray;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.RotateAnimation;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.chanven.lib.cptr.indicator.PtrIndicator;

import java.text.SimpleDateFormat;
import java.util.Date;

public class PtrClassicDefaultHeader extends FrameLayout implements PtrUIHandler {

    private final static String KEY_SharedPreferences = "cube_ptr_classic_last_update";
    private static SimpleDateFormat sDataFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private int mRotateAniTime = 150;
    private RotateAnimation mFlipAnimation;
    private RotateAnimation mReverseFlipAnimation;
    //    private TextView mTitleTextView;
//    private View mRotateView;
//    private View mProgressBar;
    //    private TextView mLastUpdateTextView;
    private long mLastUpdateTime = -1;
    private String mLastUpdateTimeKey;
    private boolean mShouldShowLastUpdate;
    private ImageView mIvEyes;
    private static Context mContext;

    private LastUpdateTimeUpdater mLastUpdateTimeUpdater = new LastUpdateTimeUpdater();

    public PtrClassicDefaultHeader(Context context) {
        super(context);
        mContext = context;
        initViews(null);
    }

    public PtrClassicDefaultHeader(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        initViews(attrs);
    }

    public PtrClassicDefaultHeader(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        mContext = context;
        initViews(attrs);
    }

    protected void initViews(AttributeSet attrs) {
        TypedArray arr = getContext().obtainStyledAttributes(attrs, R.styleable.PtrClassicHeader, 0, 0);
        if (arr != null) {
            mRotateAniTime = arr.getInt(R.styleable.PtrClassicHeader_ptr_rotate_ani_time, mRotateAniTime);
        }
//        buildAnimation();

        View header = LayoutInflater.from(getContext()).inflate(R.layout.cube_ptr_classic_default_header, this);
//        mRotateView = header.findViewById(com.chanven.lib.cptr.R.id.ptr_classic_header_rotate_view);
//        mTitleTextView = (TextView) header.findViewById(com.chanven.lib.cptr.R.id.ptr_classic_header_rotate_view_header_title);
//        mLastUpdateTextView = (TextView) header.findViewById(com.chanven.lib.cptr.R.id.ptr_classic_header_rotate_view_header_last_update);
//        mProgressBar = header.findViewById(com.chanven.lib.cptr.R.id.ptr_classic_header_rotate_view_progressbar);

        mIvEyes = (ImageView) header.findViewById(R.id.loading_eyes_tran);

//        TranslateAnimation animation = new TranslateAnimation(0,10,0,0);
//        animation.setDuration(2000);
//        animation.setRepeatCount(Animation.INFINITE);
//        animation.setRepeatMode(Animation.RESTART);
//        mIvEyes.startAnimation(animation);


//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
//            float right = 0;
//            ObjectAnimator objRight = ObjectAnimator.ofFloat(mIvEyes, "translationX", right, right + dp2px(5), right);
//            objRight.setDuration(2000);
//            objRight.setRepeatMode(ValueAnimator.RESTART);
//            objRight.setRepeatCount(ValueAnimator.INFINITE);
//            objRight.start();
//        }

        resetView();
    }

    public static int dp2px(float dip) {
        final float scale = mContext.getResources().getDisplayMetrics().density;
        return (int) (dip * scale + 0.5f);
    }

    public void setRotateAniTime(int time) {
        if (time == mRotateAniTime || time == 0) {
            return;
        }
        mRotateAniTime = time;
//        buildAnimation();
    }

    /**
     * Specify the last update time by this key string
     *
     * @param key
     */
    public void setLastUpdateTimeKey(String key) {
        if (TextUtils.isEmpty(key)) {
            return;
        }
        mLastUpdateTimeKey = key;
    }

    /**
     * Using an object to specify the last update time.
     *
     * @param object
     */
    public void setLastUpdateTimeRelateObject(Object object) {
        setLastUpdateTimeKey(object.getClass().getName());
    }

//    private void buildAnimation() {
//        mFlipAnimation = new RotateAnimation(0, -180, RotateAnimation.RELATIVE_TO_SELF, 0.5f, RotateAnimation.RELATIVE_TO_SELF, 0.5f);
//        mFlipAnimation.setInterpolator(new LinearInterpolator());
//        mFlipAnimation.setDuration(mRotateAniTime);
//        mFlipAnimation.setFillAfter(true);
//
//        mReverseFlipAnimation = new RotateAnimation(-180, 0, RotateAnimation.RELATIVE_TO_SELF, 0.5f, RotateAnimation.RELATIVE_TO_SELF, 0.5f);
//        mReverseFlipAnimation.setInterpolator(new LinearInterpolator());
//        mReverseFlipAnimation.setDuration(mRotateAniTime);
//        mReverseFlipAnimation.setFillAfter(true);
//    }

    private void resetView() {
        hideRotateView();
//        mProgressBar.setVisibility(INVISIBLE);
    }

    private void hideRotateView() {
//        mRotateView.clearAnimation();
//        mRotateView.setVisibility(INVISIBLE);
    }

    @Override
    public void onUIReset(PtrFrameLayout frame) {
        resetView();
        mShouldShowLastUpdate = true;
        tryUpdateLastUpdateTime();
    }

    @Override
    public void onUIRefreshPrepare(PtrFrameLayout frame) {

        mShouldShowLastUpdate = true;
        tryUpdateLastUpdateTime();
        mLastUpdateTimeUpdater.start();

//        mProgressBar.setVisibility(INVISIBLE);
//        mRotateView.setVisibility(VISIBLE);
//        mTitleTextView.setVisibility(VISIBLE);
//        if (frame.isPullToRefresh()) {
//            mTitleTextView.setText(getResources().getString(com.chanven.lib.cptr.R.string.cube_ptr_pull_down_to_refresh));
//        } else {
//            mTitleTextView.setText(getResources().getString(com.chanven.lib.cptr.R.string.cube_ptr_pull_down));
//        }
    }

    @Override
    public void onUIRefreshBegin(PtrFrameLayout frame) {
        mShouldShowLastUpdate = false;
        hideRotateView();
//        mProgressBar.setVisibility(VISIBLE);
//        mTitleTextView.setVisibility(VISIBLE);
//        mTitleTextView.setText(com.chanven.lib.cptr.R.string.cube_ptr_refreshing);

        tryUpdateLastUpdateTime();
        mLastUpdateTimeUpdater.stop();
    }

    @Override
    public void onUIRefreshComplete(PtrFrameLayout frame) {

        hideRotateView();
//        mProgressBar.setVisibility(INVISIBLE);
//        mTitleTextView.setVisibility(VISIBLE);
//        mTitleTextView.setText(getResources().getString(com.chanven.lib.cptr.R.string.cube_ptr_refresh_complete));

        // update last update time
        SharedPreferences sharedPreferences = getContext().getSharedPreferences(KEY_SharedPreferences, 0);
        if (!TextUtils.isEmpty(mLastUpdateTimeKey)) {
            mLastUpdateTime = new Date().getTime();
            sharedPreferences.edit().putLong(mLastUpdateTimeKey, mLastUpdateTime).commit();
        }
    }

    private void tryUpdateLastUpdateTime() {
        if (TextUtils.isEmpty(mLastUpdateTimeKey) || !mShouldShowLastUpdate) {
//            mLastUpdateTextView.setVisibility(GONE);
        } else {
            String time = getLastUpdateTime();
//            if (TextUtils.isEmpty(time)) {
//                mLastUpdateTextView.setVisibility(GONE);
//            } else {
//                mLastUpdateTextView.setVisibility(VISIBLE);
//                mLastUpdateTextView.setText(time);
//            }
        }
    }

    private String getLastUpdateTime() {

        if (mLastUpdateTime == -1 && !TextUtils.isEmpty(mLastUpdateTimeKey)) {
            mLastUpdateTime = getContext().getSharedPreferences(KEY_SharedPreferences, 0).getLong(mLastUpdateTimeKey, -1);
        }
        if (mLastUpdateTime == -1) {
            return null;
        }
        long diffTime = new Date().getTime() - mLastUpdateTime;
        int seconds = (int) (diffTime / 1000);
        if (diffTime < 0) {
            return null;
        }
        if (seconds <= 0) {
            return null;
        }
        StringBuilder sb = new StringBuilder();
        sb.append(getContext().getString(com.chanven.lib.cptr.R.string.cube_ptr_last_update));

        if (seconds < 60) {
            sb.append(seconds + getContext().getString(com.chanven.lib.cptr.R.string.cube_ptr_seconds_ago));
        } else {
            int minutes = (seconds / 60);
            if (minutes > 60) {
                int hours = minutes / 60;
                if (hours > 24) {
                    Date date = new Date(mLastUpdateTime);
                    sb.append(sDataFormat.format(date));
                } else {
                    sb.append(hours + getContext().getString(com.chanven.lib.cptr.R.string.cube_ptr_hours_ago));
                }

            } else {
                sb.append(minutes + getContext().getString(com.chanven.lib.cptr.R.string.cube_ptr_minutes_ago));
            }
        }
        return sb.toString();
    }

    @Override
    public void onUIPositionChange(PtrFrameLayout frame, boolean isUnderTouch, byte status, PtrIndicator ptrIndicator) {

        final int mOffsetToRefresh = frame.getOffsetToRefresh();
        final int currentPos = ptrIndicator.getCurrentPosY();
        final int lastPos = ptrIndicator.getLastPosY();

        if (currentPos < mOffsetToRefresh && lastPos >= mOffsetToRefresh) {
            if (isUnderTouch && status == PtrFrameLayout.PTR_STATUS_PREPARE) {
//                crossRotateLineFromBottomUnderTouch(frame);
//                if (mRotateView != null) {
//                    mRotateView.clearAnimation();
//                    mRotateView.startAnimation(mReverseFlipAnimation);
//                }
            }
        } else if (currentPos > mOffsetToRefresh && lastPos <= mOffsetToRefresh) {
            if (isUnderTouch && status == PtrFrameLayout.PTR_STATUS_PREPARE) {
                crossRotateLineFromTopUnderTouch(frame);
//                if (mRotateView != null) {
//                    mRotateView.clearAnimation();
//                    mRotateView.startAnimation(mFlipAnimation);
//                }
            }
        }
    }

    private void crossRotateLineFromTopUnderTouch(PtrFrameLayout frame) {
        if (!frame.isPullToRefresh()) {
//            mTitleTextView.setVisibility(VISIBLE);
//            mTitleTextView.setText(com.chanven.lib.cptr.R.string.cube_ptr_release_to_refresh);
        }
    }

//    private void crossRotateLineFromBottomUnderTouch(PtrFrameLayout frame) {
//        mTitleTextView.setVisibility(VISIBLE);
//        if (frame.isPullToRefresh()) {
//            mTitleTextView.setText(getResources().getString(com.chanven.lib.cptr.R.string.cube_ptr_pull_down_to_refresh));
//        } else {
//            mTitleTextView.setText(getResources().getString(com.chanven.lib.cptr.R.string.cube_ptr_pull_down));
//        }
//    }

    private class LastUpdateTimeUpdater implements Runnable {

        private boolean mRunning = false;

        private void start() {
            if (TextUtils.isEmpty(mLastUpdateTimeKey)) {
                return;
            }
            mRunning = true;
            run();
        }

        private void stop() {
            mRunning = false;
            removeCallbacks(this);
        }

        @Override
        public void run() {
            tryUpdateLastUpdateTime();
            if (mRunning) {
                postDelayed(this, 1000);
            }
        }
    }
}