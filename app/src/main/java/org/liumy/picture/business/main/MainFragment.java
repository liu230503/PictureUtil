package org.liumy.picture.business.main;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import org.liumy.picture.R;
import org.liumy.picture.core.base.BaseFragment;
import org.liumy.picture.core.base.OnItemClickListener;
import org.liumy.picture.core.fm.FragmentPageManager;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: liumy
 * @date: 2018/09/04
 * @desctiption: xxxx
 */
public class MainFragment extends BaseFragment implements OnItemClickListener {
    /**
     * 功能列表
     */
    private RecyclerView mListView;
    /**
     * 功能名称
     */
    private List<String> mTitles;
    /**
     * 适配器
     */
    private FunctionalAdapter mAdapter;

    public static MainFragment newInstance(Bundle bundle) {
        MainFragment fragment = new MainFragment();
        if (bundle == null) {
            bundle = new Bundle();
        }
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_main;
    }

    @Override
    protected void initData() {
        mAdapter = new FunctionalAdapter(mContext, this);
        mTitles = new ArrayList<>();
        mTitles.add(mContext.getResources().getString(R.string.title_ascii));
    }

    @Override
    protected void getViews() {
        mListView = findView(R.id.rcv_list);
    }

    @Override
    protected void setListeners() {

    }

    @Override
    protected void setViewsValue() {
        mListView.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false));
        mListView.setAdapter(mAdapter);
        mAdapter.addFooterItem(mTitles);
    }

    @Override
    protected void processClick(View v) {

    }

    @Override
    public void onItemClick(View view) {
        FragmentPageManager.getInstance().onStartPicture2AsciiFragment(null, null);
    }

    @Override
    public void onItemLongClick(View view) {

    }
}
