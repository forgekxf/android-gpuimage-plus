/*
Copyright 2015 Chanven

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

   http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
 */
package com.chanven.lib.cptr.loadmore;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.graphics.Color;
import android.os.Build;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.chanven.lib.cptr.PtrClassicDefaultHeader;
import com.chanven.lib.cptr.R;

/**
 * default load more view
 */
public class DefaultLoadMoreFooter implements ILoadViewMoreFactory {

    public LoadMoreHelper mLoadMore;

    @Override
    public ILoadMoreView madeLoadMoreView() {
        if (mLoadMore == null) {
            mLoadMore = new LoadMoreHelper();
        }
        return mLoadMore;
    }

    public class LoadMoreHelper implements ILoadMoreView {

        private ImageView mIvEyes;
        private ImageView mIvEyesBottom;

        protected OnClickListener onClickRefreshListener;

        private RelativeLayout mFooterLayout;

        public void setFooterColor(String color) {
            try {
                mFooterLayout.setBackgroundColor(Color.parseColor(color));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        @Override
        public void init(FootViewAdder footViewHolder, OnClickListener onClickRefreshListener) {
            View view = footViewHolder.addFootView(R.layout.loadmore_default_footer);

            mIvEyes = (ImageView) view.findViewById(R.id.loading_eyes_tran);
            mIvEyesBottom = (ImageView) view.findViewById(R.id.loading_eyes_bottom);
            mFooterLayout = (RelativeLayout) view.findViewById(R.id.footer_layout);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
                float right = mIvEyes.getTranslationX();
                ObjectAnimator objRight = ObjectAnimator.ofFloat(mIvEyes, "translationX", right, right + PtrClassicDefaultHeader.dp2px(1.48f), right);
                objRight.setDuration(2500);
                objRight.setRepeatMode(ValueAnimator.RESTART);
                objRight.setRepeatCount(ValueAnimator.INFINITE);
                objRight.start();
            }
            this.onClickRefreshListener = onClickRefreshListener;
            showNormal();
        }

        @Override
        public void showNormal() {
            mIvEyesBottom.setVisibility(View.GONE);
            mIvEyes.setVisibility(View.GONE);
        }

        @Override
        public void showLoading() {
            mIvEyesBottom.setVisibility(View.VISIBLE);
            mIvEyes.setVisibility(View.VISIBLE);
        }

        @Override
        public void showFail(Exception exception) {
            mIvEyesBottom.setVisibility(View.GONE);
            mIvEyes.setVisibility(View.GONE);
        }

        @Override
        public void showNomore() {
            mIvEyesBottom.setVisibility(View.GONE);
            mIvEyes.setVisibility(View.GONE);
        }

    }

}
