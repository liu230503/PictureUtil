package org.liumy.picture.core.fm;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import android.support.v4.app.FragmentManager;

import org.liumy.picture.core.enums.EnumFragmentIndex;
import org.lmy.open.utillibrary.LogUtil;


/**********************************************************************
 *
 *
 * @类名 FragmentPageManager
 * @包名 org.lmy.open.wanandroid.core.fhelp
 * @author lmy
 * @创建日期 2018/3/2
 ***********************************************************************/
public final class FragmentPageManager implements Handler.Callback {

    /**
     * 单例对象
     */
    private static FragmentPageManager sFragmentPageManager = null;
    /**
     * 上下文
     */
    private Context mContext;
    /**
     * 页面跳转帮助类
     */
    private FragmentPageHelper mPageHelper;
    /**
     * 是否初始化完成
     */
    private boolean mIsInitialization = false;
    /**
     * 子线程
     */
    private HandlerThread mHandlerThread;

    private FragmentPageManager() {
        mHandlerThread = new HandlerThread(FragmentPageManager.class.getName());
        mHandlerThread.start();

    }

    /**
     * 单例方法
     *
     * @return FragmentPageManager
     */
    public static FragmentPageManager getInstance() {
        if (sFragmentPageManager == null) {
            synchronized (FragmentPageManager.class) {
                if (sFragmentPageManager == null) {
                    sFragmentPageManager = new FragmentPageManager();
                }
            }
        }
        return sFragmentPageManager;
    }

    /**
     * 初始化
     *
     * @param context         上下文
     * @param containerViewId 容器布局id
     * @param fragmentManager 页面管理器
     */
    public void init(Context context, int containerViewId, FragmentManager fragmentManager) {
        mContext = context;
        mPageHelper = new FragmentPageHelper(context, fragmentManager);
        mPageHelper.setContainerViewId(containerViewId);
        mIsInitialization = true;
    }

    /**
     * 启动欢迎页面
     *
     * @param bundle          参数列表
     * @param switchAnimation 动画
     */
    public void onStartPicture2AsciiFragment(Bundle bundle, ISwitchAnimation switchAnimation) {
        checkInit();
        mPageHelper.onStart(EnumFragmentIndex.PICTURE_TO_ASCII, bundle, switchAnimation);
    }

    /**
     * 检测是否需要初始化
     */
    private void checkInit() {
        if (!mIsInitialization) {
            LogUtil.d("Must first be initialized to use");
            new Throwable("FragmentPageManager 使用前必须先进行初始化！");
        }
    }

    /**
     * 启动主页面
     *
     * @param bundle          参数列表
     * @param switchAnimation 动画
     */
    public void onStartMainFragment(Bundle bundle, ISwitchAnimation switchAnimation) {
        checkInit();
        mPageHelper.onStart(EnumFragmentIndex.MAIN, bundle, switchAnimation);
    }



    /**
     * 回退到上一页面
     *
     * @param bundle          参数列表
     * @param switchAnimation 动画
     */
    public void onBack(Bundle bundle, ISwitchAnimation switchAnimation) {
        checkInit();
        mPageHelper.onBack(bundle, switchAnimation);
    }

    @Override
    public boolean handleMessage(Message message) {
        return false;
    }
}
