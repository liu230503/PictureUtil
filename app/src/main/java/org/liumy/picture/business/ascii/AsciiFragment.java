package org.liumy.picture.business.ascii;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;

import org.liumy.picture.R;
import org.liumy.picture.core.base.BaseFragment;
import org.liumy.picture.core.util.PictureUtil;
import org.liumy.picture.core.widget.TitleView;
import org.lmy.open.utillibrary.ToastUtil;

import java.lang.ref.SoftReference;

/**
 * @author: liumy
 * @date: 2018/09/04
 * @desctiption: xxxx
 */
public class AsciiFragment extends BaseFragment implements TitleView.TitleListener {
    private TitleView mTitleView;
    private ImageView mPhotoView;
    private SoftReference<Bitmap> mSoftReference;

    public static AsciiFragment newInstance(Bundle bundle) {
        if (bundle == null) {
            bundle = new Bundle();
        }
        AsciiFragment fragment = new AsciiFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_picture2ascii;
    }

    @Override
    protected void initData() {
    }

    @Override
    protected void getViews() {
        mTitleView = findView(R.id.title);
        mPhotoView = findView(R.id.iv_picture);
    }

    @Override
    protected void setListeners() {
        mTitleView.registerListener(this);
    }

    @Override
    protected void setViewsValue() {

    }

    @Override
    protected void processClick(View v) {

    }

    @Override
    public void onSavePicture() {
        if (mSoftReference == null) {
            return;
        }
        Bitmap bitmap = mSoftReference.get();
        if (bitmap != null) {
            String s = PictureUtil.savePhotoToSD(bitmap, mContext);
            ToastUtil.showToastShort(mContext, s);
        }
    }

    @Override
    public BaseFragment getFragment() {
        return this;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        String path = PictureUtil.getPath(requestCode, resultCode, data);
        if (TextUtils.isEmpty(path)) {
            return;
        }
        Bitmap bitmap = PictureUtil.showAsciiPicture(path);
        mSoftReference = new SoftReference<>(bitmap);
        mPhotoView.setImageBitmap(mSoftReference.get());
    }
}
