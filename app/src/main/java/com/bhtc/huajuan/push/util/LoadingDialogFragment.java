package com.bhtc.huajuan.push.util;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;

import com.bhtc.huajuan.push.R;

/**
 * Created by forge on 2016/5/12.
 */
public class LoadingDialogFragment extends Dialog {
    private View view;
    private RelativeLayout dialog_loading_layout;
    private Context context;

    public LoadingDialogFragment(Context context, int themeResId) {
        super(context, themeResId);
        this.context = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        view = LayoutInflater.from(context).inflate(R.layout.dialog_loading_fragment, null);
        setContentView(view);
        initView();
    }

    private void initView() {
        dialog_loading_layout = (RelativeLayout) view.findViewById(R.id.dialog_loading_layout);

        dialog_loading_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }
}
