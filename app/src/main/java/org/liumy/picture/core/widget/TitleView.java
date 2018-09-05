package org.liumy.picture.core.widget;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;

import org.liumy.picture.R;
import org.liumy.picture.core.base.BaseFragment;
import org.liumy.picture.core.fm.FragmentPageManager;
import org.lmy.open.utillibrary.path.PathUtil;

/**
 * @author: liumy
 * @date: 2018/09/04
 * @desctiption: xxxx
 */
public class TitleView extends LinearLayout implements View.OnClickListener {
    /**
     * 根布局
     */
    private View mRootView;
    /**
     * 返回按钮
     */
    private ImageButton mBackBtn;
    /**
     * 保存按钮
     */
    private ImageButton mSavebtn;
    /**
     * 选择图片按钮
     */
    private ImageButton mChoiceBtn;
    /**
     * 标题文本
     */
    private TextView mTitleTv;
    /**
     * 回调监听
     */
    private TitleListener mTitleListenerl;

    public TitleView(Context context) {
        this(context, null);
    }

    public TitleView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TitleView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    /**
     * 初始化
     *
     * @param context 上下文
     */
    private void init(Context context) {
        mRootView = View.inflate(context, R.layout.layout_title, this);
        mBackBtn = mRootView.findViewById(R.id.ib_back);
        mSavebtn = mRootView.findViewById(R.id.ib_save);
        mChoiceBtn = mRootView.findViewById(R.id.ib_choice);
        mTitleTv = mRootView.findViewById(R.id.tv_title);
        mBackBtn.setOnClickListener(this);
        mSavebtn.setOnClickListener(this);
        mChoiceBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ib_back:
                FragmentPageManager.getInstance().onBack(null, null);
                break;
            case R.id.ib_save:
                if (mTitleListenerl != null) {
                    mTitleListenerl.onSavePicture();
                }
                break;
            case R.id.ib_choice:
                choosePhoto(v.getContext());
                break;
            default:
                break;
        }
    }

    /**
     * 选择图片
     *
     * @param context 上下文
     */
    private void choosePhoto(Context context) {
        PictureSelector.create(mTitleListenerl.getFragment())
                //全部.PictureMimeType.ofAll()、图片.ofImage()、视频.ofVideo()、音频.ofAudio()
                .openGallery(PictureMimeType.ofImage())
                //主题样式(不设置为默认样式)
//                .theme()
                // 最大图片选择数量 int
                .maxSelectNum(1)
                // 最小选择数量 int
//                .minSelectNum()
                // 每行显示个数 int
                .imageSpanCount(4)
                // 多选 or 单选 PictureConfig.MULTIPLE or PictureConfig.SINGLE
                .selectionMode(PictureConfig.SINGLE)
                // 是否可预览图片 true or false
                .previewImage(true)
                // 是否可预览视频 true or false
//                .previewVideo()
                // 是否可播放音频 true or false
//                .enablePreviewAudio()
                // 是否显示拍照按钮 true or false
                .isCamera(true)
                // 拍照保存图片格式后缀,默认jpeg
                .imageFormat(PictureMimeType.PNG)
                // 图片列表点击 缩放效果 默认true
                .isZoomAnim(true)
                // glide 加载图片大小 0~1之间 如设置 .glideOverride()无效
                .sizeMultiplier(0.5f)
                // 自定义拍照保存路径,可不填
                .setOutputCameraPath(PathUtil.getInstance().getImageCachePath())
                // 是否裁剪 true or false
                .enableCrop(false)
                // 是否压缩 true or false
                .compress(false)
                // int glide 加载宽高，越小图片列表越流畅，但会影响列表图片浏览的清晰度
//                .glideOverride()
                // int 裁剪比例 如16:9 3:2 3:4 1:1 可自定义
                .withAspectRatio(1, 1)
                // 是否显示uCrop工具栏，默认不显示 true or false
                .hideBottomControls(true)
                // 是否显示gif图片 true or false
                .isGif(true)
                //压缩图片保存地址
                .compressSavePath(PathUtil.getInstance().getThumbImageCachePath())
                // 裁剪框是否可拖拽 true or false
                .freeStyleCropEnabled(false)
                // 是否圆形裁剪 true or false
                .circleDimmedLayer(false)
                // 是否显示裁剪矩形边框 圆形裁剪时建议设为false   true or false
                .showCropFrame(true)
                // 是否显示裁剪矩形网格 圆形裁剪时建议设为false    true or false
                .showCropGrid(true)
                // 是否开启点击声音 true or false
                .openClickSound(false)
                // 是否传入已选图片 List<LocalMedia> list
//                .selectionMedia()
                // 预览图片时 是否增强左右滑动图片体验(图片滑动一半即可看到上一张是否选中) true or false
                .previewEggs(true)
                // 裁剪压缩质量 默认90 int
                .cropCompressQuality(50)
                // 小于100kb的图片不压缩
                .minimumCompressSize(50)
                //同步true或异步false 压缩 默认同步
                .synOrAsy(true)
                // 裁剪宽高比，设置如果大于图片本身宽高则无效 int
//                .cropWH()
                // 裁剪是否可旋转图片 true or false
//                .rotateEnabled()
                // 裁剪是否可放大缩小图片 true or false
                .scaleEnabled(true)
                // 视频录制质量 0 or 1 int
//                .videoQuality
                // 显示多少秒以内的视频or音频也可适用 int
//                .videoMaxSecond(15)
                // 显示多少秒以内的视频or音频也可适用 int
//                .videoMinSecond(10)
                //视频秒数录制 默认60s int
//                .recordVideoSecond()
                // 是否可拖动裁剪框(固定)
                .isDragFrame(false)
                //结果回调onActivityResult code
                .forResult(PictureConfig.CHOOSE_REQUEST);
    }

    /**
     * 设置标题
     *
     * @param title 标题
     */
    public void setTitle(String title) {
        mTitleTv.setText(title);
    }

    /**
     * 注册监听
     *
     * @param listener 监听器
     */
    public void registerListener(TitleListener listener) {
        mTitleListenerl = listener;
    }

    /**
     * 监听回调
     */
    public interface TitleListener {
        /**
         * 保存图片
         */
        void onSavePicture();

        /**
         * 获取当前页面的fragment
         *
         * @return fragment
         */
        BaseFragment getFragment();
    }
}
