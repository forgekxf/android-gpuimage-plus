package com.bhtc.huajuan.push.rtmp;

import android.content.Context;
import android.opengl.GLES20;

import org.wysaid.common.Common;
import org.wysaid.common.FrameBufferObject;
import org.wysaid.nativePort.CGEImageHandler;
import org.wysaid.texUtils.TextureRendererDrawOrigin;

/**
 * Created by jerikc on 16/2/23.
 */
public class FBO {

    private boolean mEnable = true;
    private int mContentWidth;
    private int mContentHeight;

    private int mOffscreenTexture = 0;
    private FrameBufferObject mFramebuffer;
    public CGEImageHandler mHandler;
    private TextureRendererDrawOrigin drawer;

    public void updateSurfaceSize(int width, int height) {
        if (!mEnable) {
            return;
        }
    }

    public void initialize(Context context) {
        if (!mEnable) {
            return;
        }

        mHandler = new CGEImageHandler();
        drawer = TextureRendererDrawOrigin.create(true);
        mHandler.setFilterWithConfig("@beautify face 1 480 640");
        mHandler.setFilterIntensity(1.0f);
        mContentWidth = mContentHeight = 0;
        Common.checkGLError("handler create...");
    }

    public void release() {
        if (!mEnable) {
            return;
        }

        if (mHandler != null) {
            mHandler.release();
            mHandler = null;
        }

        if (mFramebuffer != null) {
            mFramebuffer.release();
            mFramebuffer = null;
        }

        if (mOffscreenTexture != 0) {
            Common.deleteTextureID(mOffscreenTexture);
            mOffscreenTexture = 0;
        }
    }

    public int drawFrame(int texId, int texWidth, int texHeight) {
        // 因为没有开启内置美颜，此处纹理类型为 GL_TEXTURE_EXTERNAL_OES
        if (!mEnable) {
            return texId;
        }

        if (mContentWidth != texWidth || mContentHeight != texHeight) {
            mContentWidth = texWidth;
            mContentHeight = texHeight;
            if (mOffscreenTexture != 0) {
                Common.deleteTextureID(mOffscreenTexture);
            }
            mOffscreenTexture = Common.genBlankTextureID(texWidth, texHeight);
            mFramebuffer = new FrameBufferObject();
            mFramebuffer.bindTexture(mOffscreenTexture);

            mHandler.initWithSize(texWidth, texHeight);
            Common.checkGLError("drawFrame - init handler");
        }

        mHandler.bindTargetFBO();
        GLES20.glViewport(0, 0, texWidth, texHeight);
        drawer.renderTexture(texId, null);
        mHandler.processFilters();
        mFramebuffer.bind();
        mHandler.drawResult();
        return mOffscreenTexture;
    }
}
