package org.liumy.picture.core.fm;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import org.liumy.picture.business.ascii.AsciiFragment;
import org.liumy.picture.business.main.MainFragment;
import org.liumy.picture.core.base.BaseFragment;
import org.liumy.picture.core.enums.EnumFragmentIndex;

import java.util.Stack;

/**
 * @author: liumy
 * @date: 2018/09/04
 * @desctiption: xxxx
 */
class FragmentPageHelper {
    /**
     * FragmentManager
     */
    private FragmentManager mFragmentManager;
    /**
     * FragmentTransaction
     */
    private FragmentTransaction mFragmentTransaction;
    /**
     * 加载的fragment管理栈
     */
    private Stack<EnumFragmentIndex> mFragmentIndexStack;
    /**
     * 上下文
     */
    private Context mContext;
    /**
     * 容器布局id
     */
    private int mContainerViewId;

    FragmentPageHelper(@NonNull Context context, @NonNull FragmentManager fragmentManager) {
        mContext = context;
        mFragmentManager = fragmentManager;
        init();
    }

    /**
     * 初始化
     */
    private void init() {
        mFragmentIndexStack = new Stack<>();
    }

    /**
     * 设置布局id
     *
     * @param id 布局id
     */
    public void setContainerViewId(int id) {
        if (id > 0) {
            mContainerViewId = id;
        }
    }

    /**
     * 返回上一个页面
     *
     * @param bundle          参数列表
     * @param switchAnimation 动画
     */
    public void onBack(Bundle bundle, ISwitchAnimation switchAnimation) {
        int last = mFragmentIndexStack.size() - 2;
        if (last < 0) {
            if (mContext != null && mContext instanceof Activity) {
                ((Activity) mContext).finish();
            }
        } else {
            this.onBack(mFragmentIndexStack.elementAt(last), bundle, switchAnimation);
        }
    }

    /**
     * 返回上一个页面
     *
     * @param index           指定页面
     * @param bundle          参数列表
     * @param switchAnimation 动画
     */
    public void onBack(EnumFragmentIndex index, Bundle bundle, ISwitchAnimation switchAnimation) {
        if (mFragmentIndexStack.empty() || mFragmentIndexStack.size() <= 0) {
            if (mContext != null && mContext instanceof Activity) {
                ((Activity) mContext).finish();
            }
            return;
        }
        onReplaceFragment(mContainerViewId, index, bundle, switchAnimation, false);
    }

    /**
     * 替换页面
     *
     * @param containerViewId 容器布局id
     * @param index           跳转的fragment索引
     * @param bundle          参数列表
     * @param switchAnimation 需要做的动画
     * @param isAddStack      是否添加到管理栈中
     */
    private void onReplaceFragment(int containerViewId, EnumFragmentIndex index, Bundle bundle, ISwitchAnimation switchAnimation, boolean isAddStack) {
        if (containerViewId <= 0) {
            return;
        }
        if (index == null) {
            return;
        }
        if (!mFragmentIndexStack.empty() && mFragmentIndexStack.peek() == index) {
            return;
        }
        Fragment fragment = createFragment(index, bundle);
        if (fragment == null) {
            return;
        }
        mFragmentTransaction = mFragmentManager.beginTransaction();
        if (switchAnimation != null) {
            if (mFragmentIndexStack.empty()) {
                mFragmentTransaction.setCustomAnimations(switchAnimation.getEnterAnim(), switchAnimation.getExitAnim());
            } else {
                mFragmentTransaction.setCustomAnimations(switchAnimation.getEnterAnim(), switchAnimation.getExitAnim(), switchAnimation.getPopEnterAnim(), switchAnimation.getPopExitAnim());
            }
        }
        mFragmentTransaction.replace(containerViewId, fragment, fragment.getClass().getSimpleName());
        mFragmentTransaction.commitAllowingStateLoss();

        if (isAddStack) {
            mFragmentIndexStack.push(index);
        } else {
            while (!mFragmentIndexStack.empty() && mFragmentIndexStack.peek() != index) {
                mFragmentIndexStack.pop();
            }
        }
    }

    /**
     * 创建fragment
     *
     * @param index  索引
     * @param bundle 参数列表
     * @return fragment
     */
    private BaseFragment createFragment(EnumFragmentIndex index, Bundle bundle) {
        BaseFragment fragment = null;
        switch (index) {
            case PICTURE_TO_ASCII:
                fragment = AsciiFragment.newInstance(bundle);
                break;
            case MAIN:
                fragment = MainFragment.newInstance(bundle);
                break;

            default:
                break;
        }
        return fragment;
    }

    /**
     * 页面加载
     *
     * @param index           指定页面
     * @param bundle          参数列表
     * @param switchAnimation 动画
     */
    public void onLoad(EnumFragmentIndex index, Bundle bundle, ISwitchAnimation switchAnimation) {
        onReplaceFragment(mContainerViewId, index, bundle, switchAnimation, true);
    }

    /**
     * 添加或返回到指定页面
     *
     * @param index           指定页面
     * @param bundle          参数列表
     * @param switchAnimation 动画
     */
    public void onStart(EnumFragmentIndex index, Bundle bundle, ISwitchAnimation switchAnimation) {
        if (mFragmentIndexStack.contains(index)) {
            this.onBack(index, bundle, switchAnimation);
        } else {
            this.onLoad(index, bundle, switchAnimation);
        }
    }

    /**
     * 关闭页面
     */
    public void onFinishActivity() {
        if (mContext != null && mContext instanceof Activity) {
            onDestroy();
            ((Activity) mContext).finish();
        }
    }

    /**
     * 释放资源
     */
    private void onDestroy() {
        mFragmentManager = null;
        mFragmentTransaction = null;
        mFragmentIndexStack.clear();
        mContainerViewId = 0;
    }
}

