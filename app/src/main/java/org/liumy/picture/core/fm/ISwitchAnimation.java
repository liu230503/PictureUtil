package org.liumy.picture.core.fm;

import android.support.annotation.AnimRes;

/**
 * @author: liumy
 * @date: 2018/09/04
 * @desctiption: xxxx
 */
public interface ISwitchAnimation {
    /**
     * 进入动画
     *
     * @return 动画
     */
    @AnimRes
    int getEnterAnim();

    /**
     * 退出动画
     *
     * @return 动画
     */
    @AnimRes
    int getExitAnim();

    /**
     * 弹出页面的进入动画
     *
     * @return 动画
     */
    @AnimRes
    int getPopEnterAnim();

    /**
     * 弹出页面的退出动画
     *
     * @return 动画
     */
    @AnimRes
    int getPopExitAnim();
}