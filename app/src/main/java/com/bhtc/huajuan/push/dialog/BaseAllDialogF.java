package com.bhtc.huajuan.push.dialog;

import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.BackgroundColorSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.ImageSpan;
import android.text.style.StyleSpan;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bhtc.huajuan.push.R;
import com.bhtc.huajuan.push.util.UIUtils;

/**
 * 提示弹出框，可变化字体大小颜色，加图片。
 * 默认字体大小16dp、颜色4d4d4d
 * Created by Administrator on 2016/8/4.
 */
public class BaseAllDialogF extends BaseDialogFragment {

    private TextView mTvContent;
    private RelativeLayout mContentBg;
    private RelativeLayout mDialogBg;

    private String mContent, mCancelTxt, mConfirmTxt;
    private SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder("");

    private DialogInterface.OnClickListener mConfirmListener;
    private DialogInterface.OnClickListener mCancelListener;
    public View view;

    public BaseAllDialogF() {

    }

    @Override
    public void onStart() {
        super.onStart();
        //使dialog在屏幕中留白（因为适配：小屏手机为同宽，需在此中手设留白）
        DisplayMetrics dm = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(dm);
        getDialog().getWindow().setLayout(dm.widthPixels - UIUtils.dp2px(28), getDialog().getWindow().getAttributes().height);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Dialog dialog = getDialog();
        Window window = dialog.getWindow();
//        window.setWindowAnimations(R.style.dialogWindowAnim2);
        window.setBackgroundDrawableResource(R.color.transparent);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);

        view = UIUtils.inflate(mActivity, R.layout.dialog_all);
        initView(view, dialog);
        return view;
    }

    private void initView(final View view, final Dialog dialog) {
        mTvContent = (TextView) view.findViewById(R.id.dialog_all_content);
        mContentBg = (RelativeLayout) view.findViewById(R.id.dialog_all_content_bg);
        mDialogBg = (RelativeLayout) view.findViewById(R.id.dialog_bg);

        view.setVisibility(View.VISIBLE);
//        AnimatorUtil.rootViewVisible(view);

        Button mCancelButton = (Button) view.findViewById(R.id.btn_cancel);
        Button mConfirmButton = (Button) view.findViewById(R.id.btn_confirm);

        View mDividerVertical = view.findViewById(R.id.divide_vertical);

        if (mContent != null) {
            mTvContent.setText(mContent);
        }

        if (!UIUtils.isEmpty(spannableStringBuilder.toString())) {
            mTvContent.append(spannableStringBuilder);
        }

        if (UIUtils.isEmpty(mCancelTxt)) {
            mCancelButton.setVisibility(View.GONE);
            mDividerVertical.setVisibility(View.GONE);
        } else {
            mCancelButton.setText(mCancelTxt);
            mCancelButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mCancelListener != null) {
                        mCancelListener.onClick(dialog, DialogInterface.BUTTON_NEGATIVE);
                    }
                    dialog.dismiss();
                }
            });
        }

        if (UIUtils.isEmpty(mConfirmTxt)) {
            mConfirmButton.setVisibility(View.GONE);
            mDividerVertical.setVisibility(View.GONE);
        } else {
            mConfirmButton.setText(mConfirmTxt);
            mConfirmButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mConfirmListener != null) {
                        mConfirmListener.onClick(dialog, DialogInterface.BUTTON_POSITIVE);
                    }
                    dialog.dismiss();
                }
            });
        }

        mCancelButton.setText(mCancelTxt);
        mConfirmButton.setText(mConfirmTxt);
    }

    public BaseAllDialogF setContent(String content) {
        mContent = content;
        return this;
    }

    /**
     * 文字颜色
     */
    public BaseAllDialogF setContentColor(String content, int color) {
        SpannableString spannableString = new SpannableString(content);
        ForegroundColorSpan span = new ForegroundColorSpan(color);
        spannableString.setSpan(span, 0, content.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannableStringBuilder.append(spannableString);
        return this;
    }

    /**
     * 字体大小:单位dp
     */
    public BaseAllDialogF setContentSize(String content, int size) {
        SpannableString spannableString = new SpannableString(content);
        AbsoluteSizeSpan span = new AbsoluteSizeSpan(size, true);
        spannableString.setSpan(span, 0, content.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannableStringBuilder.append(spannableString);
        return this;
    }

    /**
     * 文字颜色、大小
     */
    public BaseAllDialogF setContentColorAndSize(String content, int color, int size) {
        SpannableString spannableString = new SpannableString(content);
        ForegroundColorSpan span = new ForegroundColorSpan(color);
        spannableString.setSpan(span, 0, content.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        AbsoluteSizeSpan spans = new AbsoluteSizeSpan(size, true);
        spannableString.setSpan(spans, 0, content.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannableStringBuilder.append(spannableString);
        return this;
    }

    /**
     * 字体格式 Typeface
     * 粗体：BOLD
     * 斜体：ITALIC
     * 粗体斜体：BOLD_ITALIC
     */
    public BaseAllDialogF setContentStyle(String content, int style) {
        SpannableString spannableString = new SpannableString(content);
        StyleSpan span = new StyleSpan(style);
        spannableString.setSpan(span, 0, content.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannableStringBuilder.append(spannableString);
        return this;
    }

    /**
     * 图片
     */
    public BaseAllDialogF setContentImage(String content, int drawable, int start, int end) {
        SpannableString spannableString = new SpannableString(content);
        Drawable d = UIUtils.getDrawable(drawable);
        d.setBounds(0, 0, d.getIntrinsicWidth(), d.getIntrinsicHeight());
        ImageSpan span = new ImageSpan(d, ImageSpan.ALIGN_BASELINE);
        spannableString.setSpan(span, start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannableStringBuilder.append(spannableString);
        return this;
    }

    /**
     * 文字背景颜色
     */
    private void addBackColorSpan(String content) {
        SpannableString spanString = new SpannableString("颜色2");
        BackgroundColorSpan span = new BackgroundColorSpan(Color.green(R.color.red_DF484A));
        spanString.setSpan(span, 0, 3, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
    }

    public BaseAllDialogF setConfirmTxt(String confirmTxt, DialogInterface.OnClickListener mConfirmListener) {
        mConfirmTxt = confirmTxt;
        this.mConfirmListener = mConfirmListener;
        return this;
    }

    public BaseAllDialogF setCancelTxt(String cancelTxt, DialogInterface.OnClickListener mCancelListener) {
        mCancelTxt = cancelTxt;
        this.mCancelListener = mCancelListener;
        return this;
    }

}
