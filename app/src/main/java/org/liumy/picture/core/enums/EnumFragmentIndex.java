package org.liumy.picture.core.enums;

/**
 * @author: liumy
 * @date: 2018/09/04
 * @desctiption: xxxx
 */
public enum EnumFragmentIndex {
    /**
     * 主页面
     */
    MAIN(1, "主页面"),
    /**
     * 图片转换为字符图片页面
     */
    PICTURE_TO_ASCII(2, "图片转换为字符图片页面");


    /**
     * 索引
     */
    private int mIndex;
    /**
     * 描述
     */
    private String mDes;

    EnumFragmentIndex(int index, String des) {
        mIndex = index;
        mDes = des;
    }

    private int getIndex() {
        return mIndex;
    }

    public String getDes() {
        return mDes;
    }

}
