package org.liumy.processor.picture;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Typeface;
import android.text.Layout;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.WindowManager;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;
import io.reactivex.schedulers.Schedulers;

/**
 * @author: liumy
 * @date: 2018/10/30
 * @desctiption: xxxx
 */
public class PictureToAscii {
    /**
     * 构成图片的ascii码
     */
    public static final String ASCII = "MNHQ$OC?7>!:–;.";

    /**
     * 获取转换后的图片路径
     *
     * @param context 上下文
     * @param path    路径
     * @return 结果
     */
    public static Observable<String> getAsciiPicture(Context context, String path) {
        return getBitmap(context, path)
                .flatMap(new Function<Bitmap, ObservableSource<String>>() {
                    @Override
                    public ObservableSource<String> apply(Bitmap bitmap) throws Exception {
                        return Observable.just(picture2Ascii(bitmap));
                    }
                }).filter(new Predicate<String>() {
                    @Override
                    public boolean test(String s) throws Exception {
                        return !TextUtils.isEmpty(s);
                    }
                }).zipWith(Observable.just(context), new BiFunction<String, Context, String>() {
                    @Override
                    public String apply(String s, Context context) throws Exception {
                        return crateAsciiPicture(context, s);
                    }
                });
    }

    /**
     * 将图片转换为bitmap
     *
     * @param context 上下文
     * @param path    路径
     * @return 结果
     */
    private static Observable<Bitmap> getBitmap(Context context, String path) {
        return Observable.just(path)
                .subscribeOn(Schedulers.io())
                .filter(new Predicate<String>() {
                    @Override
                    public boolean test(String s) throws Exception {
                        return !TextUtils.isEmpty(s);
                    }
                }).zipWith(Observable.just(context), new BiFunction<String, Context, Bitmap>() {
                    @Override
                    public Bitmap apply(String s, Context context) throws Exception {
                        Bitmap bitmap = BitmapFactory.decodeFile(s);
                        StringBuffer builder = new StringBuffer();
                        // 获取屏幕的宽高用于判断图片是否需要缩放或拉伸
                        WindowManager manager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
                        DisplayMetrics metrics = new DisplayMetrics();
                        manager.getDefaultDisplay().getMetrics(metrics);
                        int pictureWidth;
                        int pictureHeight;
                        int scale = 7;
                        if (bitmap.getWidth() <= metrics.widthPixels / scale) {
                            pictureWidth = bitmap.getWidth();
                            pictureHeight = bitmap.getHeight();
                        } else {
                            pictureWidth = metrics.widthPixels / scale;
                            pictureHeight = pictureWidth * bitmap.getHeight() / bitmap.getWidth();
                        }
                        if (!bitmap.isRecycled()) {
                            bitmap.recycle();
                        }
                        return Bitmap.createScaledBitmap(BitmapFactory.decodeFile(s), pictureWidth, pictureHeight, true);
                    }
                });


    }

    /**
     * 将图片转换为Ascii码
     *
     * @param bitmap 图片
     * @return 结果
     */
    private static String picture2Ascii(Bitmap bitmap) {
        if (bitmap == null) {
            return null;
        }
        StringBuffer text = new StringBuffer();
        for (int y = 0; y < bitmap.getHeight(); y += 2) {
            for (int x = 0; x < bitmap.getWidth(); x++) {
                final int pixel = bitmap.getPixel(x, y);
                final int r = (pixel & 0xff0000) >> 16, g = (pixel & 0xff00) >> 8, b = pixel & 0xff;
                final float gray = 0.299f * r + 0.578f * g + 0.114f * b;
                final int index = Math.round(gray * (ASCII.length() + 1) / 255);
                String s = index >= ASCII.length() ? " " : String.valueOf(ASCII.charAt(index));
                text.append(s);
            }
            text.append("\n");
        }
        if (!bitmap.isRecycled()) {
            bitmap.recycle();
        }
        return text.toString();
    }

    /**
     * 根据Ascii码绘制图片
     *
     * @param context 上下文
     * @param text    Ascii码
     * @return 图片路径
     */
    private static String crateAsciiPicture(Context context, String text) {
        TextPaint textPaint = new TextPaint();
        textPaint.setColor(Color.BLACK);
        textPaint.setAntiAlias(true);
        textPaint.setTypeface(Typeface.MONOSPACE);
        textPaint.setTextSize(12);
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics dm = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(dm);
        int width = dm.widthPixels;
        StaticLayout layout = new StaticLayout(text, textPaint, width, Layout.Alignment.ALIGN_CENTER, 1f, 0.0f, true);
        Bitmap bitmap = Bitmap.createBitmap(layout.getWidth() + 20, layout.getHeight() + 20, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        canvas.translate(10, 10);
        canvas.drawColor(Color.WHITE);
        layout.draw(canvas);
        return PictureUtil.savePicture(bitmap, context);
    }
}
