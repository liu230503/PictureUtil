package org.liumy.picture;

import org.liumy.picture.core.base.BaseFragmentActivity;
import org.liumy.picture.core.fm.FragmentPageManager;

/**
 * @author: liumy
 * @date: 2018/09/04
 * @desctiption: xxxx
 */
public class MainActivity extends BaseFragmentActivity {


    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initData() {
        FragmentPageManager.getInstance().init(mContext, R.id.container, getSupportFragmentManager());
    }

    @Override
    protected void getViews() {

    }

    @Override
    protected void setViewsValue() {
        FragmentPageManager.getInstance().onStartMainFragment(null, null);
    }

    @Override
    protected void setListeners() {

    }
}
