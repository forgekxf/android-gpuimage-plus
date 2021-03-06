package com.bhtc.huajuan.push.activity;

import android.content.Context;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.ImageFormat;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.graphics.YuvImage;
import android.hardware.Camera;
import android.opengl.GLES20;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bhtc.huajuan.push.App;
import com.bhtc.huajuan.push.R;
import com.bhtc.huajuan.push.rtmp.CameraPreviewFrameView;
import com.bhtc.huajuan.push.rtmp.Config;
import com.bhtc.huajuan.push.rtmp.FBO;
import com.bhtc.huajuan.push.rtmp.RotateLayout;
import com.bhtc.huajuan.push.util.UIUtils;
import com.qiniu.android.dns.DnsManager;
import com.qiniu.android.dns.IResolver;
import com.qiniu.android.dns.NetworkInfo;
import com.qiniu.android.dns.http.DnspodFree;
import com.qiniu.android.dns.local.AndroidDnsServer;
import com.qiniu.android.dns.local.Resolver;
import com.qiniu.pili.droid.streaming.AVCodecType;
import com.qiniu.pili.droid.streaming.AudioSourceCallback;
import com.qiniu.pili.droid.streaming.CameraStreamingSetting;
import com.qiniu.pili.droid.streaming.CameraStreamingSetting.CAMERA_FACING_ID;
import com.qiniu.pili.droid.streaming.FrameCapturedCallback;
import com.qiniu.pili.droid.streaming.MediaStreamingManager;
import com.qiniu.pili.droid.streaming.MicrophoneStreamingSetting;
import com.qiniu.pili.droid.streaming.StreamStatusCallback;
import com.qiniu.pili.droid.streaming.StreamingPreviewCallback;
import com.qiniu.pili.droid.streaming.StreamingProfile;
import com.qiniu.pili.droid.streaming.StreamingSessionListener;
import com.qiniu.pili.droid.streaming.StreamingState;
import com.qiniu.pili.droid.streaming.StreamingStateChangedListener;
import com.qiniu.pili.droid.streaming.SurfaceTextureCallback;

import org.json.JSONObject;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.URISyntaxException;
import java.nio.ByteBuffer;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import us.pinguo.pgskinprettifyengine.PGSkinPrettifyEngine;
import us.pinguo.prettifyengine.PGPrettifySDK;

public class StreamingBaseActivity extends BaseActivity implements
        View.OnLayoutChangeListener,
        StreamStatusCallback,
        StreamingPreviewCallback,
        SurfaceTextureCallback,
        AudioSourceCallback,
        CameraPreviewFrameView.Listener,
        StreamingSessionListener,
        StreamingStateChangedListener {

    private final String SDK_KEY_NEW = "41YLrEs/YvoKmN39OrcOmnd5hJOm7cLjpfRuEhJOdHBIhGnqjA74O5VFl7FS3ZsOSqt24sJ+m5GAkS0X877PisFSUhzAYIWLUMKd6xmcUXm5rSwjTYmu/LBbyPAoJjThjxPVNOboFMi81tCGG6tt7u8hX43IOrC8RwhF+WELnNykhKvaScyuZeYiVBsvIqHlrCEgBk7P3KXBAQOsEcPg8xsVG5+jpzIFvIBH2lt11Cp7qivw6SdZkypBUXH5IXiynuSOHNm8jaPbq+e3V/xlIgCwfmoYSXnkxAx90BzqocvjDermdAriH/l09KoENl99JslyleLHnUfx8mJpAnrHeDsWM9EZp+WyM58wL9sqZc0cmfItz4q/4SR+QOdmQ6RM88U0/u9jbXtyYKXjYDTQNKSS31k937GfAtI8XIMJgHWNd6yfDK382XlQPCZD7/j0DPr+ImkMBUnHj69fN1R6/ZgSynX+rFsqxdmHvQJltlwI1yUu7xlkmkZRApS2t3fDF/7kgH/tFE9UlBUDGuef7u7wH52+jLynXXEey7as12E3O+D+Rw+LmbFgqstamrt73rBQ6NPYFPm6hIRk7DXN7iadYlXGRF3gN0ccax7SGiHz/Y6AAZdCAVqrXxHAAYirSAiCxTNrjgG+31AKpGMJ9ANhavXTTAIjh1EByTN3XZJszV6XENnYrltRVNt7w2rgAIRk7YMwWBlpmSZG8V2C/qvokLCvRUTmYyjJXrLZ67GwxHhmWMFGrYLapzFmvkGFO56RIzvB3ZEnCLvKode7s0wRdpb0tqxw6tlJISY3PsoQEt26XBC21zc1fcS5TLylF7jxELKsztO29vqE3HjElET3wtV9+77gha6DEjNLoI/jqC64Z9NL4FXKWbfiUmsdtZJ1PvMlZZwCo1LI/y/gk8lFdN0rigWnOgT1zH/t1xi2R6OMh6Qd7MGRGE+xsvAQ6F5usCFWvKv4j+RHPHQu8rZgWldxs4zrKbseIqogTcjgjFE+8UxV267iAb7lW2PbBGGVvfMIE6fIZLZH8+SzjGPUdPopyRWLqu4HLomVFCUKdVpeEXJBUsGfiLKkIozwjdnRxl2s/bJ7lh9L1yycrrZt/NVhlifxzAy3yNoZl0tZ9SI9bD9G6XbMsFgk9DPHu/GYWeN8A5VQoejz9NzoVAW5A/zwK+xs4LgYVttN1v67cvgemisPEq1YzfmrAz6RE3FpXV/2wE2xpeYmK7JuP2tLaHkBUYwtU17roI77l++yvJkrBbgkyxtvbECTgBD12QAD/caJBafwCXOCMD8gA5O8/znsdP4R4yIzsFHOLCvsV2wW/r4q4L4m4IULkc76AAnzTFlzwGUsmabJSt/BXTgzKfezJTsQuLaKjiWclDfv5racdFPDAv2id95GWJFrpjVNcA4IlGsbqFYomx4emqtAp8wAcgG1Nb55f1vEZpNrZaKQ7LC4iGD6w+BG/T5n0HQ+w5WMS+rsWmvBCZjwVJDTPBrqy6m1Z0ZcB2nr+x/DRdZVBokPmZ0Q0OTocSkrbQ/LwzZW4ZlBg+v4PgZaRvExogiZpQ/cCqdhkpzXM1AejyLgo20JoBlrBTecUZdwPx97OcJ7wCL6TB3mFac7Qw1a2A97mpN6rGRAH9lzB686WK2swYgYOgx8NrmB0Pwg+9eBmYupezWkF2YPsMDUoW1hNMEUW3uxjTXHSIdRaP4XoFlGM0sD+0GzfIcV2NlCfmYJmAsWeAZYGtyHNPXT+zmYmg4oMtaGsBa/gqpZ6VoFn33a00eZtXb8ZlOwIMs=";

    private static final String TAG = "StreamingBaseActivity";

    private static final int ZOOM_MINIMUM_WAIT_MILLIS = 33; //ms

    private static final int MSG_START_STREAMING = 0;
    private static final int MSG_STOP_STREAMING = 1;
    private static final int MSG_SET_ZOOM = 2;
    private static final int MSG_MUTE = 3;
    protected static final int MSG_FB = 4;
    private static final int MSG_PREVIEW_MIRROR = 5;
    private static final int MSG_ENCODING_MIRROR = 6;

    private Context mContext;

    protected Button mShutterButton;
    private Button mMuteButton;
    private Button mTorchBtn;
    private Button mCameraSwitchBtn;
    private Button mCaptureFrameBtn;
    private Button mEncodingOrientationSwitcherBtn;
    private Button mFaceBeautyBtn;
    private RotateLayout mRotateLayout;

    protected boolean mShutterButtonPressed = false;
    private boolean mIsTorchOn = false;
    private boolean mIsNeedMute = false;
    protected boolean mIsNeedFB = false;
    private boolean mIsEncOrientationPort = true;
    protected boolean mIsPreviewMirror = false;
    protected boolean mIsEncodingMirror = false;

    private String mStatusMsgContent;
    private String mLogContent = "\n";

    protected MediaStreamingManager mMediaStreamingManager;
    protected CameraStreamingSetting mCameraStreamingSetting;
    protected MicrophoneStreamingSetting mMicrophoneStreamingSetting;
    protected StreamingProfile mProfile;
    protected JSONObject mJSONObject;
    private boolean mOrientationChanged = false;

    protected boolean mIsReady = false;

    private int mCurrentZoom = 0;
    private int mMaxZoom = 0;

    protected FBO mFBO = new FBO();

    private ScreenShooter mScreenShooter = new ScreenShooter();
    protected Switcher mSwitcher = new Switcher();
    private EncodingOrientationSwitcher mEncodingOrientationSwitcher = new EncodingOrientationSwitcher();

    private int mCurrentCamFacingIndex;

    protected ImageView mRefreshImage;

    private int restartCount = 0;

    protected TextView mStartLive;

    public static int width;
    public static int height;

    public static int BIT_VIDEO = 5000;

    protected Handler mHandler = new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case MSG_START_STREAMING:
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            // disable the shutter button before startStreaming
                            setShutterButtonEnabled(false);
                            boolean res = mMediaStreamingManager.startStreaming();
                            mShutterButtonPressed = true;
                            Log.i(TAG, "res:" + res);
                            Log.i(TAG, "重连");
                            if (!res) {
                                mShutterButtonPressed = false;
                                setShutterButtonEnabled(true);
                            }
                            setShutterButtonPressed(mShutterButtonPressed);
                        }
                    }).start();
                    break;
                case MSG_STOP_STREAMING:
                    if (mShutterButtonPressed) {
                        // disable the shutter button before stopStreaming
                        setShutterButtonEnabled(false);
                        boolean res = mMediaStreamingManager.stopStreaming();
                        if (!res) {
                            mShutterButtonPressed = true;
                            setShutterButtonEnabled(true);
                        }
                        setShutterButtonPressed(mShutterButtonPressed);
                    }
                    break;
                case MSG_SET_ZOOM:
                    mMediaStreamingManager.setZoomValue(mCurrentZoom);
                    break;
                case MSG_MUTE:
                    mIsNeedMute = !mIsNeedMute;
                    mMediaStreamingManager.mute(mIsNeedMute);
                    updateMuteButtonText();
                    break;
                case MSG_FB:
                    mIsNeedFB = !mIsNeedFB;
                    mMediaStreamingManager.setVideoFilterType(mIsNeedFB ?
                            CameraStreamingSetting.VIDEO_FILTER_TYPE.VIDEO_FILTER_BEAUTY
                            : CameraStreamingSetting.VIDEO_FILTER_TYPE.VIDEO_FILTER_NONE);
                    updateFBButtonText();
                    break;
                case MSG_PREVIEW_MIRROR:
                    mIsPreviewMirror = !mIsPreviewMirror;
                    mMediaStreamingManager.setPreviewMirror(mIsPreviewMirror);
                    Toast.makeText(mContext, "镜像成功", Toast.LENGTH_SHORT).show();
                    break;
                case MSG_ENCODING_MIRROR:
                    mIsEncodingMirror = !mIsEncodingMirror;
                    mMediaStreamingManager.setEncodingMirror(mIsEncodingMirror);
                    Toast.makeText(mContext, "镜像成功", Toast.LENGTH_SHORT).show();
                    break;
                default:
                    Log.e(TAG, "Invalid message");
                    break;
            }
        }
    };
    private boolean onBack = false;
    private boolean m_bIsFirstFrame;
    protected boolean mSkinChange;
    protected boolean mSoftenChange;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            requestWindowFeature(Window.FEATURE_ACTION_BAR_OVERLAY);
        } else {
            requestWindowFeature(Window.FEATURE_NO_TITLE);
        }

        super.onCreate(savedInstanceState);

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        prettifySDK = new PGPrettifySDK(this);
        m_bIsFirstFrame = true;

        if (Config.SCREEN_ORIENTATION == ActivityInfo.SCREEN_ORIENTATION_PORTRAIT) {
            mIsEncOrientationPort = true;
        } else if (Config.SCREEN_ORIENTATION == ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE) {
            mIsEncOrientationPort = false;
        }
        setRequestedOrientation(Config.SCREEN_ORIENTATION);

        setContentView(R.layout.activity_camera_streaming);

        mStartLive = (TextView) findViewById(R.id.start_live);
        mRefreshImage = (ImageView) findViewById(R.id.refresh_image);

        String publishUrlFromServer = getIntent().getStringExtra(Config.EXTRA_KEY_PUB_URL);
        Log.i(TAG, "publishUrlFromServer:" + publishUrlFromServer);

        mContext = this;

        mProfile = new StreamingProfile();

        try {
            mProfile.setPublishUrl(publishUrlFromServer);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

        StreamingProfile.AudioProfile aProfile = new StreamingProfile.AudioProfile(44100, 96 * 1024);
        StreamingProfile.VideoProfile vProfile = new StreamingProfile.VideoProfile(30, BIT_VIDEO * 1024, 48);
        StreamingProfile.AVProfile avProfile = new StreamingProfile.AVProfile(vProfile, aProfile);

        mProfile.setEncodingSizeLevel(StreamingProfile.VIDEO_ENCODING_HEIGHT_1088)
                .setEncoderRCMode(StreamingProfile.EncoderRCModes.BITRATE_PRIORITY)
                .setDnsManager(getMyDnsManager())
//                .setAdaptiveBitrateEnable(false)
                .setFpsControllerEnable(true)
                .setStreamStatusConfig(new StreamingProfile.StreamStatusConfig(3))
//                .setEncodingOrientation(StreamingProfile.ENCODING_ORIENTATION.PORT)
                .setAVProfile(avProfile)
                .setSendingBufferProfile(new StreamingProfile.SendingBufferProfile(0.2f, 0.8f, 3.0f, 20 * 1000));

        CAMERA_FACING_ID cameraFacingId = chooseCameraFacingId();
        mCurrentCamFacingIndex = cameraFacingId.ordinal();
        mCameraStreamingSetting = new CameraStreamingSetting();
        mCameraStreamingSetting.setCameraId(Camera.CameraInfo.CAMERA_FACING_BACK)
                .setContinuousFocusModeEnabled(true)
                .setRecordingHint(false)
                .setCameraFacingId(cameraFacingId)
//                .setCameraSourceImproved(true)
                .setResetTouchFocusDelayInMs(3000)
//                .setFocusMode(CameraStreamingSetting.FOCUS_MODE_CONTINUOUS_PICTURE)
                .setCameraPrvSizeLevel(CameraStreamingSetting.PREVIEW_SIZE_LEVEL.MEDIUM)
                .setCameraPrvSizeRatio(CameraStreamingSetting.PREVIEW_SIZE_RATIO.RATIO_16_9)
//                .setBuiltInFaceBeautyEnabled(false)
//                .setBuiltInFaceBeautyEnabled(true)
//                .setFaceBeautySetting(new CameraStreamingSetting.FaceBeautySetting(0.0f, 0.0f, 0.0f))
                .setVideoFilter(CameraStreamingSetting.VIDEO_FILTER_TYPE.VIDEO_FILTER_BEAUTY);

        mIsNeedFB = true;
        mMicrophoneStreamingSetting = new MicrophoneStreamingSetting();
        mMicrophoneStreamingSetting.setBluetoothSCOEnabled(false);

        initUIs();



        SeekBar seekBar = (SeekBar) findViewById(R.id.seekBar);
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                float intensity = progress / 100.0f;
                mFBO.mHandler.setFilterIntensity(intensity);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        findViewById(R.id.switch_filter).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mFBO.mHandler.setFilterWithConfig("#unpack @dynamic wave 1");
            }
        });
    }

    @Override
    protected void onResume() {
        onBack = false;
        super.onResume();
        mMediaStreamingManager.resume();
        if (prettifySDK == null) {
            prettifySDK = new PGPrettifySDK(this);
        }
    }

    @Override
    protected void onPause() {
        onBack = true;
        super.onPause();
        mIsReady = false;
        mShutterButtonPressed = false;
        mHandler.removeCallbacksAndMessages(null);
        mMediaStreamingManager.pause();
        if (prettifySDK != null) {
            prettifySDK.release();
            prettifySDK = null;
            m_bIsFirstFrame = true;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mMediaStreamingManager.destroy();
    }

    protected void setShutterButtonPressed(final boolean pressed) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mShutterButtonPressed = pressed;
                mShutterButton.setPressed(pressed);
            }
        });
    }

    protected void setShutterButtonEnabled(final boolean enable) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mShutterButton.setFocusable(enable);
                mShutterButton.setClickable(enable);
                mShutterButton.setEnabled(enable);
            }
        });
    }

    protected void startStreaming() {
        mHandler.removeCallbacksAndMessages(null);
        mHandler.sendMessageDelayed(mHandler.obtainMessage(MSG_START_STREAMING), 50);
    }

    protected void stopStreaming() {
        mHandler.removeCallbacksAndMessages(null);
        mHandler.sendMessageDelayed(mHandler.obtainMessage(MSG_STOP_STREAMING), 50);
    }

    @Override
    public boolean onRecordAudioFailedHandled(int err) {
        mMediaStreamingManager.updateEncodingType(AVCodecType.SW_VIDEO_CODEC);
        mMediaStreamingManager.startStreaming();
        return true;
    }

    @Override
    public boolean onRestartStreamingHandled(int err) {
        Log.i(TAG, "onRestartStreamingHandled");
        startStreamingInternal();
        return true;
    }

    @Override
    public Camera.Size onPreviewSizeSelected(List<Camera.Size> list) {
        Camera.Size size = null;
        if (list != null) {
            for (Camera.Size s : list) {
                if (s.height >= 480) {
                    size = s;
                    break;
                }
            }
        }
        return size;
    }

    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        Log.i(TAG, "onSingleTapUp X:" + e.getX() + ",Y:" + e.getY());
        if (mIsReady) {
            setFocusAreaIndicator();
            return true;
        }
        return false;
    }

    @Override
    public boolean onZoomValueChanged(float factor) {
        if (mIsReady && mMediaStreamingManager.isZoomSupported()) {
            mCurrentZoom = (int) (mMaxZoom * factor);
            mCurrentZoom = Math.min(mCurrentZoom, mMaxZoom);
            mCurrentZoom = Math.max(0, mCurrentZoom);

            Log.d(TAG, "zoom ongoing, scale: " + mCurrentZoom + ",factor:" + factor + ",maxZoom:" + mMaxZoom);
            if (!mHandler.hasMessages(MSG_SET_ZOOM)) {
                mHandler.sendMessageDelayed(mHandler.obtainMessage(MSG_SET_ZOOM), ZOOM_MINIMUM_WAIT_MILLIS);
                return true;
            }
        }
        return false;
    }

    @Override
    public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
        Log.i(TAG, "view!!!!:" + v);
    }

    @Override
    public boolean onPreviewFrame(byte[] bytes, int width, int height, int rotation, int fmt, long tsInNanoTime) {
        if (bytes2 != null) {
            lock.lock();
            System.arraycopy(bytes2, 0, bytes, 0, bytes.length);
            lock.unlock();
        }
        return true;
    }

    @Override
    public void onSurfaceCreated() {
        mFBO.initialize(this);
    }

    @Override
    public void onSurfaceChanged(int width, int height) {
        mFBO.updateSurfaceSize(width, height);
        m_bIsFirstFrame = true;
    }

    @Override
    public void onSurfaceDestroyed() {
        mFBO.release();
    }

    @Override
    public int onDrawFrame(int texId, int texWidth, int texHeight, float[] transformMatrix) {
        int newTexId = mFBO.drawFrame(texId, texWidth, texHeight);
//        saveBitmapByTextureID(newTexId,texWidth,texHeight);   //纹理存储图片，这里正常
        if (m_bIsFirstFrame) {
            // Camera360 开发文档
            // https://sdk.camera360.com/views/beautySDK.html?doc=guide&his=New&platform=Android&version=1.8.1
            prettifySDK.InitialiseEngine(SDK_KEY_NEW, false);   //是否在 Native 层自己初始化 EGLContext
            prettifySDK.SetSizeForAdjustInput(texWidth, texHeight);
            prettifySDK.SetOutputFormat(PGSkinPrettifyEngine.PG_PixelFormat.PG_Pixel_NV21);
            prettifySDK.SetOutputOrientation(PGSkinPrettifyEngine.PG_Orientation.PG_OrientationNormal);
            prettifySDK.SetSkinSoftenStrength(mSoftenValue);
            prettifySDK.SetSkinColor(mPinkValue, mWhitenValue, mReddenValue);
            prettifySDK.SetSkinSoftenAlgorithm(PGSkinPrettifyEngine.PG_SoftenAlgorithm.PG_SoftenAlgorithmContrast);
            m_bIsFirstFrame = false;
        }

        if (mSoftenChange) {
            prettifySDK.SetSkinSoftenStrength(mSoftenValue);//磨皮强度
            mSoftenChange = false;
        }
        if (mSkinChange) {
            prettifySDK.SetSkinColor(mPinkValue, mWhitenValue, mReddenValue);//美颜参数
            mSkinChange = false;
        }
        prettifySDK.SetInputFrameByTexture(newTexId, texWidth, texHeight,1);  //接收内部纹理
//        prettifySDK.SetInputFrameByTexture(texId, texWidth, texHeight);   //接收外部纹理

        prettifySDK.RunEngine();
        prettifySDK.GetOutputToScreen(texWidth, texHeight);

        buffer = prettifySDK.SkinSoftenGetResult();
        if (buffer != null) {
            lock.lock();
            buffer.clear();
            bytes2 = new byte[buffer.capacity()];
            buffer.get(bytes2, 0, bytes2.length);
            lock.unlock();
        }
//        saveBitmapByTextureID(prettifySDK.GetOutputTextureID(),texWidth,texHeight);   //纹理存储图片
        return prettifySDK.GetOutputTextureID();  //返回处理过的纹理ID
    }

    protected float mPinkValueDefault = 0.24f;
    protected float mWhitenValueDefault = 0.6f;
    protected float mReddenValueDefault = 0.12f;
    protected int mSoftenValueDefault = 50;
    protected float mFilterOneDefault = 0.23f;
    protected float mFilterTwoDefault = 0.1f;
    protected float mFilterThreeDefault = 0.45f;

    protected float mPinkValue = 0.24f;
    protected float mWhitenValue = 0.6f;
    protected float mReddenValue = 0.12f;
    protected int mSoftenValue = 50;

    protected float mFilterOne = 0.23f;
    protected float mFilterTwo = 0.1f;
    protected float mFilterThree = 0.45f;

    protected PGPrettifySDK prettifySDK;
    ByteBuffer buffer;
    byte[] bytes2;
    Lock lock = new ReentrantLock();

    @Override
    public void onAudioSourceAvailable(ByteBuffer byteBuffer, int size, long tsInNanoTime, boolean eof) {
    }

    @Override
    public void notifyStreamStatusChanged(final StreamingProfile.StreamStatus streamStatus) {

    }

    private void setTorchEnabled(final boolean enabled) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                String flashlight = enabled ? getString(R.string.flash_light_off) : getString(R.string.flash_light_on);
                mTorchBtn.setText(flashlight);
            }
        });
    }

    @Override
    public void onStateChanged(StreamingState streamingState, Object extra) {
        Log.i(TAG, "StreamingState streamingState:" + streamingState + ",extra:" + extra);
        switch (streamingState) {
            case PREPARING:
                mStatusMsgContent = getString(R.string.string_state_preparing);
                break;
            case READY:
                mIsReady = true;
                mMaxZoom = mMediaStreamingManager.getMaxZoom();
                mStatusMsgContent = getString(R.string.string_state_ready);
                // TODO 开始推流
                if (mStartLive.getVisibility() != View.VISIBLE) {
                    startStreamingInternal();
                }
                break;
            case CONNECTING:
                mStatusMsgContent = getString(R.string.string_state_connecting);
                restartCount = 0;
                App.getMainThreadHandler().post(new Runnable() {
                    @Override
                    public void run() {
                        mRefreshImage.setVisibility(View.GONE);
                    }
                });

                break;
            case STREAMING:
                mStatusMsgContent = getString(R.string.string_state_streaming);
                setShutterButtonEnabled(true);
                setShutterButtonPressed(true);
                App.getMainThreadHandler().post(new Runnable() {
                    @Override
                    public void run() {
                        mRefreshImage.setVisibility(View.GONE);
                    }
                });
                break;
            case SHUTDOWN:
                mStatusMsgContent = getString(R.string.string_state_ready);
                setShutterButtonEnabled(true);
                setShutterButtonPressed(false);
                startStreamingInternal();
                break;
            case IOERROR:
                UIUtils.showToastSafe(R.string.net_error_push);
                mLogContent += "IOERROR\n";
                mStatusMsgContent = getString(R.string.string_state_ready);
                setShutterButtonEnabled(true);
                startStreamingInternal();
                break;
            case UNKNOWN:
                mStatusMsgContent = getString(R.string.string_state_ready);
                break;
            case SENDING_BUFFER_EMPTY:
                UIUtils.showToastSafe(R.string.data_error_push);
                break;
            case SENDING_BUFFER_FULL:
                UIUtils.showToastSafe(R.string.data_error_push);
                break;
            case AUDIO_RECORDING_FAIL:
                UIUtils.showToastSafe(R.string.check_audio);
                break;
            case OPEN_CAMERA_FAIL:
                Log.e(TAG, "Open Camera Fail. id:" + extra);
                UIUtils.showToastSafe(R.string.check_camera);
                break;
            case DISCONNECTED:
                mLogContent += "DISCONNECTED\n";
                // TODO 断开时刷新重连
                startStreamingInternal();
                break;
            case INVALID_STREAMING_URL:
                Log.e(TAG, "Invalid streaming url:" + extra);
                UIUtils.showToastSafe(R.string.url_not);
                break;
            case UNAUTHORIZED_STREAMING_URL:
                Log.e(TAG, "Unauthorized streaming url:" + extra);
                mLogContent += "Unauthorized Url\n";
                UIUtils.showToastSafe(R.string.url_not);
                break;
            case CAMERA_SWITCHED:
                if (extra != null) {
                    Log.i(TAG, "current camera id:" + (Integer) extra);
                }
                Log.i(TAG, "camera switched");
                final int currentCamId = (Integer) extra;
                this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        updateCameraSwitcherButtonText(currentCamId);
                    }
                });
                break;
            case TORCH_INFO:
                if (extra != null) {
                    final boolean isSupportedTorch = (Boolean) extra;
                    Log.i(TAG, "isSupportedTorch=" + isSupportedTorch);
                    this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (isSupportedTorch) {
                                mTorchBtn.setVisibility(View.VISIBLE);
                            } else {
                                mTorchBtn.setVisibility(View.GONE);
                            }
                        }
                    });
                }
                break;
        }
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
            }
        });
    }

    private void initUIs() {
        View rootView = findViewById(R.id.content);
        rootView.addOnLayoutChangeListener(this);

        mMuteButton = (Button) findViewById(R.id.mute_btn);
        mShutterButton = (Button) findViewById(R.id.toggleRecording_button);
        mTorchBtn = (Button) findViewById(R.id.torch_btn);
        mCameraSwitchBtn = (Button) findViewById(R.id.camera_switch_btn);
        mCaptureFrameBtn = (Button) findViewById(R.id.capture_btn);
        mFaceBeautyBtn = (Button) findViewById(R.id.fb_btn);
        Button previewMirrorBtn = (Button) findViewById(R.id.preview_mirror_btn);
        Button encodingMirrorBtn = (Button) findViewById(R.id.encoding_mirror_btn);

        mFaceBeautyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!mHandler.hasMessages(MSG_FB)) {
                    mHandler.sendEmptyMessage(MSG_FB);
                }
            }
        });

        mMuteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!mHandler.hasMessages(MSG_MUTE)) {
                    mHandler.sendEmptyMessage(MSG_MUTE);
                }
            }
        });

        previewMirrorBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!mHandler.hasMessages(MSG_PREVIEW_MIRROR)) {
                    mHandler.sendEmptyMessage(MSG_PREVIEW_MIRROR);
                }
            }
        });

        encodingMirrorBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!mHandler.hasMessages(MSG_ENCODING_MIRROR)) {
                    mHandler.sendEmptyMessage(MSG_ENCODING_MIRROR);
                }
            }
        });

        mShutterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mShutterButtonPressed) {
                    stopStreaming();
                } else {
                    startStreaming();
                }
            }
        });

        mTorchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        if (!mIsTorchOn) {
                            mIsTorchOn = true;
                            mMediaStreamingManager.turnLightOn();
                        } else {
                            mIsTorchOn = false;
                            mMediaStreamingManager.turnLightOff();
                        }
                        setTorchEnabled(mIsTorchOn);
                    }
                }).start();
            }
        });

        mCameraSwitchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mHandler.removeCallbacks(mSwitcher);
                mHandler.postDelayed(mSwitcher, 100);
            }
        });

        mCaptureFrameBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mHandler.removeCallbacks(mScreenShooter);
                mHandler.postDelayed(mScreenShooter, 100);
            }
        });

        mEncodingOrientationSwitcherBtn = (Button) findViewById(R.id.orientation_btn);
        mEncodingOrientationSwitcherBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mHandler.removeCallbacks(mEncodingOrientationSwitcher);
                mHandler.post(mEncodingOrientationSwitcher);
            }
        });

        SeekBar seekBarBeauty = (SeekBar) findViewById(R.id.beautyLevel_seekBar);
        seekBarBeauty.setVisibility(View.GONE);
        seekBarBeauty.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                CameraStreamingSetting.FaceBeautySetting fbSetting = mCameraStreamingSetting.getFaceBeautySetting();
                fbSetting.beautyLevel = progress / 100.0f;
                fbSetting.whiten = progress / 100.0f;
                fbSetting.redden = progress / 100.0f;

                mMediaStreamingManager.updateFaceBeautySetting(fbSetting);
                Log.i("beautyful", fbSetting.beautyLevel + "===" + fbSetting.whiten + "===" + fbSetting.redden);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });

        initButtonText();
    }

    private void initButtonText() {
        updateFBButtonText();
        updateCameraSwitcherButtonText(mCameraStreamingSetting.getReqCameraId());
        mCaptureFrameBtn.setText("Capture");
        updateFBButtonText();
        updateMuteButtonText();
        updateOrientationBtnText();
    }

    private void updateOrientationBtnText() {
        if (mIsEncOrientationPort) {
            mEncodingOrientationSwitcherBtn.setText("Land");
        } else {
            mEncodingOrientationSwitcherBtn.setText("Port");
        }
    }

    protected void setFocusAreaIndicator() {
        if (mRotateLayout == null) {
            mRotateLayout = (RotateLayout) findViewById(R.id.focus_indicator_rotate_layout);
            mMediaStreamingManager.setFocusAreaIndicator(mRotateLayout,
                    mRotateLayout.findViewById(R.id.focus_indicator));
        }
    }

    private void updateFBButtonText() {
        if (mFaceBeautyBtn != null) {
            mFaceBeautyBtn.setText(mIsNeedFB ? "FB Off" : "FB On");
        }
    }

    private void updateMuteButtonText() {
        if (mMuteButton != null) {
            mMuteButton.setText(mIsNeedMute ? "Unmute" : "Mute");
        }
    }

    private void updateCameraSwitcherButtonText(int camId) {
        if (mCameraSwitchBtn == null) {
            return;
        }
        if (camId == Camera.CameraInfo.CAMERA_FACING_FRONT) {
            mCameraSwitchBtn.setText("Back");
        } else {
            mCameraSwitchBtn.setText("Front");
        }
    }

    private void saveToSDCard(String filename, Bitmap bmp) throws IOException {
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            File file = new File(Environment.getExternalStorageDirectory(), filename);
            BufferedOutputStream bos = null;
            try {
                bos = new BufferedOutputStream(new FileOutputStream(file));
                bmp.compress(Bitmap.CompressFormat.PNG, 90, bos);
                bmp.recycle();
                bmp = null;
            } finally {
                if (bos != null) bos.close();
            }

            final String info = "Save frame to:" + Environment.getExternalStorageDirectory().getAbsolutePath() + "/" + filename;
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(mContext, info, Toast.LENGTH_LONG).show();
                }
            });
        }
    }

    private static DnsManager getMyDnsManager() {
        IResolver r0 = new DnspodFree();
        IResolver r1 = AndroidDnsServer.defaultResolver();
        IResolver r2 = null;
        try {
            r2 = new Resolver(InetAddress.getByName("119.29.29.29"));
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return new DnsManager(NetworkInfo.normal, new IResolver[]{r0, r1, r2});
    }

    private CAMERA_FACING_ID chooseCameraFacingId() {
        if (CameraStreamingSetting.hasCameraFacing(CAMERA_FACING_ID.CAMERA_FACING_3RD)) {
            return CAMERA_FACING_ID.CAMERA_FACING_3RD;
        } else if (CameraStreamingSetting.hasCameraFacing(CAMERA_FACING_ID.CAMERA_FACING_FRONT)) {
            return CAMERA_FACING_ID.CAMERA_FACING_FRONT;
        } else {
            return CAMERA_FACING_ID.CAMERA_FACING_BACK;
        }
    }

    private class Switcher implements Runnable {
        @Override
        public void run() {
            mCurrentCamFacingIndex = (mCurrentCamFacingIndex + 1) % CameraStreamingSetting.getNumberOfCameras();

            CAMERA_FACING_ID facingId;
            if (mCurrentCamFacingIndex == CAMERA_FACING_ID.CAMERA_FACING_BACK.ordinal()) {
                facingId = CAMERA_FACING_ID.CAMERA_FACING_BACK;
            } else if (mCurrentCamFacingIndex == CAMERA_FACING_ID.CAMERA_FACING_FRONT.ordinal()) {
                facingId = CAMERA_FACING_ID.CAMERA_FACING_FRONT;
            } else {
                facingId = CAMERA_FACING_ID.CAMERA_FACING_3RD;
            }
            Log.i(TAG, "switchCamera:" + facingId);
            mMediaStreamingManager.switchCamera(facingId);
        }
    }

    private class EncodingOrientationSwitcher implements Runnable {

        @Override
        public void run() {
            Log.i(TAG, "mIsEncOrientationPort:" + mIsEncOrientationPort);
            stopStreaming();
            mOrientationChanged = !mOrientationChanged;
            mIsEncOrientationPort = !mIsEncOrientationPort;
            mProfile.setEncodingOrientation(mIsEncOrientationPort ? StreamingProfile.ENCODING_ORIENTATION.PORT : StreamingProfile.ENCODING_ORIENTATION.LAND);
            mMediaStreamingManager.setStreamingProfile(mProfile);
            setRequestedOrientation(mIsEncOrientationPort ? ActivityInfo.SCREEN_ORIENTATION_PORTRAIT : ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
            mMediaStreamingManager.notifyActivityOrientationChanged();
            updateOrientationBtnText();
            Toast.makeText(StreamingBaseActivity.this, Config.HINT_ENCODING_ORIENTATION_CHANGED,
                    Toast.LENGTH_SHORT).show();
            Log.i(TAG, "EncodingOrientationSwitcher -");
        }
    }

    private class ScreenShooter implements Runnable {
        @Override
        public void run() {
            final String fileName = "PLStreaming_" + System.currentTimeMillis() + ".jpg";
            mMediaStreamingManager.captureFrame(100, 100, new FrameCapturedCallback() {
                private Bitmap bitmap;

                @Override
                public void onFrameCaptured(Bitmap bmp) {
                    if (bmp == null) {
                        return;
                    }
                    bitmap = bmp;
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                saveToSDCard(fileName, bitmap);
                            } catch (IOException e) {
                                e.printStackTrace();
                            } finally {
                                if (bitmap != null) {
                                    bitmap.recycle();
                                    bitmap = null;
                                }
                            }
                        }
                    }).start();
                }
            });
        }
    }

    protected void startStreamingInternal() {
        if (onBack) {
            return;
        }
        UIUtils.showToastSafe(UIUtils.getString(R.string.link_restart));
        App.getMainThreadHandler().post(new Runnable() {
            @Override
            public void run() {
                mRefreshImage.setVisibility(View.VISIBLE);
            }
        });

        new Thread(new Runnable() {
            @Override
            public void run() {
                setShutterButtonEnabled(false);
                boolean res = mMediaStreamingManager.startStreaming();
                mShutterButtonPressed = true;
                if (!res) {
                    mShutterButtonPressed = false;
                    setShutterButtonEnabled(true);
                }
                setShutterButtonPressed(mShutterButtonPressed);
            }
        }).start();
    }





    public static void saveBitmapByTextureID(int tempTextId, int w, int h){
        int previewBufferSize =  w * h * 4;
        byte[] mPreviewBuffer = new byte[previewBufferSize];
        int[] frame = new int[1];
        GLES20.glGenFramebuffers(1, frame, 0);
        GLES20.glBindFramebuffer(GLES20.GL_FRAMEBUFFER, frame[0]);
        GLES20.glFramebufferTexture2D(GLES20.GL_FRAMEBUFFER, GLES20.GL_COLOR_ATTACHMENT0, GLES20.GL_TEXTURE_2D, tempTextId, 0);

        ByteBuffer buffer = ByteBuffer.allocate(w * h * 4);
        GLES20.glReadPixels(0, 0, w, h, GLES20.GL_RGBA, GLES20.GL_UNSIGNED_BYTE, buffer);
        GLES20.glBindFramebuffer(GLES20.GL_FRAMEBUFFER, 0);
        GLES20.glDeleteFramebuffers(1, frame, 0);

        byte[] output = new byte[w * h * 4];
        buffer.get(output, 0, output.length);
        System.arraycopy(output, 0, mPreviewBuffer, 0, output.length);

        // use Bitmap.Config.ARGB_8888 instead of type is OK
        Bitmap stitchBmp = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        stitchBmp.copyPixelsFromBuffer(ByteBuffer.wrap(mPreviewBuffer));
        int rotate = 0;
        Matrix matrix = new Matrix();
        matrix.postRotate(rotate);
        Bitmap bitmap = Bitmap.createBitmap(stitchBmp, 0, 0, w, h, matrix, true);
        if (bitmap != null)
            saveBitmap(bitmap);
    }

    public static void saveBitmapByData(byte[] data,int width,int height){
        YuvImage image = new YuvImage(data, ImageFormat.NV21, width, height, null);
        if (image != null) {
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            image.compressToJpeg(new Rect(0, 0, width, height), 80, stream);
            Bitmap bmp = BitmapFactory.decodeByteArray(stream.toByteArray(), 0, stream.size());
            //因为图片会放生旋转，因此要对图片进行旋转到和手机在一个方向上
            Matrix matrix = new Matrix();
            matrix.postRotate(90);
            Bitmap nbmp2 = Bitmap.createBitmap(bmp, 0, 0, bmp.getWidth(), bmp.getHeight(), matrix, true);
            saveBitmap(nbmp2);
            try {
                stream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void saveBitmap(Bitmap bitmap) {
        if (bitmap == null)
            return;
        File f = new File("/sdcard/", "a_test.jpg");

        try {
            FileOutputStream out = new FileOutputStream(f);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, out);
            out.flush();
            out.close();
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
