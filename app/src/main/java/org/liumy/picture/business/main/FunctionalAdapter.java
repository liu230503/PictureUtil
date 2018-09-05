package org.liumy.picture.business.main;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.liumy.picture.R;
import org.liumy.picture.core.base.BaseRecyclerAdapter;
import org.liumy.picture.core.base.OnItemClickListener;

import java.util.ArrayList;

/**
 * @author: liumy
 * @date: 2018/09/04
 * @desctiption: xxxx
 */
public class FunctionalAdapter extends BaseRecyclerAdapter {
    /**
     * 上下文
     */
    private Context mContext;
    /**
     * 布局
     */
    private LayoutInflater mInflater;

    protected FunctionalAdapter(Context context, OnItemClickListener listener) {
        super(listener, new ArrayList());
        mContext = context;
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.layout_functional_item, parent, false);
        onBindClickListener(itemView);
        return new ItemHolder(itemView);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ItemHolder) {
            ItemHolder itemHolder = (ItemHolder) holder;
            itemHolder.mTitleTv.setText((String) mDatas.get(position));
        }
    }

    @Override
    public void onItemClick(View view) {

    }

    @Override
    public void onItemLongClick(View view) {

    }

    private class ItemHolder extends RecyclerView.ViewHolder {
        /**
         * 功能名
         */
        private TextView mTitleTv;

        public ItemHolder(View itemView) {
            super(itemView);
            mTitleTv = itemView.findViewById(R.id.tv_title);
        }
    }
}
