package org.liumy.picture.business.ascii;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;

import org.liumy.picture.R;
import org.liumy.picture.core.application.Application;
import org.liumy.picture.core.base.BaseFragment;
import org.liumy.picture.core.util.PictureUtil;
import org.liumy.picture.core.widget.TitleView;
import org.liumy.processor.picture.PictureToAscii;
import org.lmy.open.utillibrary.ToastUtil;
import org.lmy.open.utillibrary.imageload.LoadImageHelper;

import java.lang.ref.SoftReference;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;
import io.reactivex.schedulers.Schedulers;

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
        mTitleView.setTitle("字节图片转换");
    }

    @Override
    protected void processClick(View v) {

    }

    @Override
    public void onSavePicture() {
        Observable.just(mSoftReference)
                .subscribeOn(Schedulers.io())
                .filter(new Predicate<SoftReference<Bitmap>>() {
                    @Override
                    public boolean test(SoftReference<Bitmap> bitmapSoftReference) throws Exception {
                        return bitmapSoftReference != null;
                    }
                }).flatMap(new Function<SoftReference<Bitmap>, ObservableSource<Bitmap>>() {
            @Override
            public ObservableSource<Bitmap> apply(SoftReference<Bitmap> bitmapSoftReference) throws Exception {
                return Observable.just(bitmapSoftReference.get());
            }
        }).filter(new Predicate<Bitmap>() {
            @Override
            public boolean test(Bitmap bitmap) throws Exception {
                return bitmap != null;
            }
        }).flatMap(new Function<Bitmap, ObservableSource<String>>() {
            @Override
            public ObservableSource<String> apply(Bitmap bitmap) throws Exception {
                return Observable.just(PictureUtil.savePhotoToSD(bitmap, mContext));
            }
        }).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String s) throws Exception {
                        ToastUtil.showToastShort(mContext, s);
                    }
                });
    }

    @Override
    public BaseFragment getFragment() {
        return this;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Observable.just(PictureUtil.getPath(requestCode, resultCode, data))
                .observeOn(Schedulers.io())
                .filter(new Predicate<String>() {
                    @Override
                    public boolean test(String s) throws Exception {
                        return !TextUtils.isEmpty(s);
                    }
                }).flatMap(new Function<String, ObservableSource<String>>() {
            @Override
            public ObservableSource<String> apply(String s) throws Exception {
                return PictureToAscii.getAsciiPicture(Application.getInstance().getContext(), s);
            }
        }).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String s) throws Exception {
                        LoadImageHelper.getInstance().loadImage(mPhotoView, LoadImageHelper.getInstance().getSdCardUrl(s));
                    }
                });
    }
}
