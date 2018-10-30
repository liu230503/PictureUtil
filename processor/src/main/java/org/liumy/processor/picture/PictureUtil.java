package org.liumy.processor.picture;

import android.content.Context;
import android.graphics.Bitmap;

import org.lmy.open.utillibrary.path.PathUtil;

import java.io.File;
import java.io.FileOutputStream;

/**
 * @author: liumy
 * @date: 2018/10/30
 * @desctiption: xxxx
 */
public class PictureUtil {
    /**
     * 图片种类
     */
    public static final String IMAGE_TYPE = ".png";

    public static String savePicture(Bitmap bitmap, Context context) {
        FileOutputStream outStream = null;
        String fileName = getPhotoFileName(context);
        try {
            outStream = new FileOutputStream(fileName);
            // 把数据写入文件，100表示不压缩
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, outStream);
            return fileName;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            try {
                if (outStream != null) {
                    // 记得要关闭流！
                    outStream.close();
                }
                if (bitmap != null) {
                    bitmap.recycle();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 使用当前系统时间作为上传图片的名称
     *
     * @param context 上下文
     * @return 存储的根路径+图片名称
     */
    public static String getPhotoFileName(Context context) {
        File file = new File(PathUtil.getInstance().getImageCachePath());
        // 判断文件是否已经存在，不存在则创建
        if (!file.exists()) {
            file.mkdirs();
        }
        // 设置图片文件名称

        String photoName = "/" + System.currentTimeMillis() + IMAGE_TYPE;
        return file + photoName;
    }
}
