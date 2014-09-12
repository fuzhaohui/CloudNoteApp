package com.ces.cloudnote.app.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.Shader;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.view.View;

/**
 * 处理图片的类
 * Created by remex on 14-7-25.
 */
public class ImageUtils {

    public static Bitmap toRoundBitmap(Bitmap bitmap) {
        return toRoundBitmap(bitmap, 0);
    }

    public static Bitmap toRoundBitmap(Bitmap bitmap, float borderWidth) {
        return toRoundBitmap(bitmap, borderWidth, Color.WHITE);
    }

    /**
     * 转换图片成圆形
     *
     * @param bitmap 传入Bitmap对象
     * @return
     */
    public static Bitmap toRoundBitmap(Bitmap bitmap, float borderWidth, int color) {
        if(bitmap == null){
            return null;
        }
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        if(width <= 0 || height <= 0){
            return bitmap;
        }

        float radiusContent, radiusBackground, left, top, right, bottom, dst_left, dst_top, dst_right, dst_bottom, border = 0;
        if (width <= height) {
            if(width > 2*borderWidth) {
                border = borderWidth;
            }
            radiusBackground = width / 2;
            radiusContent =  radiusBackground - border;

            left = 0;
            top = 0;
            right = width;
            bottom = width;
            height = width;
            dst_left = 0;
            dst_top = 0;
            dst_right = width;
            dst_bottom = width;
        } else {
            if(height > 2*borderWidth){
                border = borderWidth;
            }
            radiusBackground = height / 2;
            radiusContent = radiusBackground - border;

            float clip = (width - height) / 2;
            left = clip;
            right = width - clip;
            top = 0;
            bottom = height;
            width = height;
            dst_left = 0;
            dst_top = 0;
            dst_right = height;
            dst_bottom = height;
        }

        //数据初始化完成。开始绘图。realCanvas为最后需要的图片。
        Bitmap realCanvas;

        /**
         * 以PorterDuff.Mode.SRC_IN(类似蒙板的功能)将图片中需要的圆形部分挖出来
         * output就是需要的图片
         */
        Bitmap output = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);

        Canvas canvasSmall = new Canvas(output);
        canvasSmall.drawARGB(0, 0, 0, 0); // 用透明色填充整个Canvas

        final Paint paint = new Paint();
        paint.setAntiAlias(true);// 设置画笔无锯齿
        paint.setColor(color);

        final Rect src = new Rect((int) left, (int) top, (int) right, (int) bottom);
        final Rect dst = new Rect((int) dst_left, (int) dst_top, (int) dst_right, (int) dst_bottom);

        // 以下有两种方法画圆,drawRounRect和drawCircle
        // final RectF rectF = new RectF(dst);
        // canvas.drawRoundRect(rectF, roundPx, roundPx, paint);// 画圆角矩形，第一个参数为图形显示区域，第二个参数和第三个参数分别是水平圆角半径和垂直圆角半径。
        canvasSmall.drawCircle(radiusBackground, radiusBackground, radiusContent, paint);
        // 设置两张图片相交时的模式,参考http://trylovecatch.iteye.com/blog/1189452
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        //以Mode.SRC_IN模式合并bitmap和已经draw了的Circle
        canvasSmall.drawBitmap(bitmap, src, dst, paint);

        /**
         * 画一个白色的圆形，用刚才得到的图片放在中间
         */
        if(border <= 0){
            realCanvas = output;
        }else {
            realCanvas = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
            Canvas canvasBig = new Canvas(realCanvas);
            canvasBig.drawARGB(0, 255, 255, 255); // 用透明色填充整个Canvas
            //设置画笔模式，这样才能画出圆圈的背景
            paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_OVER));
            //画圆圈背景
            canvasBig.drawCircle(radiusBackground, radiusBackground, radiusBackground, paint);
            //将之前得到的圆形图画在背景上
            canvasBig.drawBitmap(output, src, dst, paint);
        }

        return realCanvas;
    }

    public static Bitmap blur(Bitmap bkg, View view) {
        return blur(bkg, view, 4, 20);
    }

    public static Bitmap blur(Bitmap bkg, View view, float scaleFactor, float radius) {
        if(bkg == null || view == null || view.getMeasuredHeight() <= 0
                || view.getMeasuredWidth() <= 0 || scaleFactor <= 0 || radius <= 0 ){
            return bkg;
        }
        Bitmap overlay = Bitmap.createBitmap((int) (view.getMeasuredWidth() / scaleFactor),
                (int) (view.getMeasuredHeight() / scaleFactor), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(overlay);
        canvas.translate(-view.getLeft() / scaleFactor, -view.getTop() / scaleFactor);
        canvas.scale(1 / scaleFactor, 1 / scaleFactor);
        Paint paint = new Paint();
        paint.setFlags(Paint.FILTER_BITMAP_FLAG);
        canvas.drawBitmap(bkg, 0, 0, paint);
        overlay = ImageUtils.doBlur(overlay, (int) radius, true);
        return overlay;
    }

    public static Bitmap clipCenterCrop(Bitmap bitmap, View view) {
        if(bitmap == null || view == null)
            return bitmap;
        Bitmap resultBitmap;
        int bitmapWidth = bitmap.getWidth();
        int bitmapHeight = bitmap.getHeight();
        int viewWidth = view.getMeasuredWidth();
        int viewHeight = view.getMeasuredHeight();
        if(bitmapHeight <= 0 || bitmapWidth <= 0 ||
                viewWidth <= 0 || viewHeight <= 0){
            return bitmap;
        }
        float scaleX = (float) viewWidth / bitmapWidth ;
        float scaleY = (float) viewHeight / bitmapHeight ;
        //得到需要的缩放大小
        float scale = scaleX > scaleY ? scaleX : scaleY;
        //缩放图片
        Matrix matrix = new Matrix();
        matrix.postScale(scale, scale);
//        if(bitmapWidth <= 0 || bitmapHeight <= 0 ){
//            return bitmap;
//        }
        Bitmap tempBitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmapWidth, bitmapHeight, matrix, true);
        //接下来裁剪
        int x = 0;
        int y = 0;
        if (scaleX >= scaleY) {
            x = (tempBitmap.getWidth() - viewWidth) / 2;
            if (x < 0) x = 0;
        } else {
            y = (tempBitmap.getHeight() - viewHeight) / 2;
            if (y < 0) y = 0;
        }
        if(x + viewWidth <= tempBitmap.getWidth()
                && y + viewHeight <= tempBitmap.getHeight()){
            resultBitmap = Bitmap.createBitmap(tempBitmap, x, y, viewWidth, viewHeight, null, false);
        }else{
            resultBitmap = tempBitmap;
        }
        return resultBitmap;
    }


    /**
     * Bitmap -> Drawable
     *
     * @param ctx
     * @param bitmap
     * @return Drawable
     */
    public static Drawable getDrawableFromBitmap(Context ctx, Bitmap bitmap) {
        if(bitmap == null || ctx == null)
            return null;
        BitmapDrawable bd = new BitmapDrawable(ctx.getResources(), bitmap);
        return bd;
    }


    /**
     * 获得带倒影的图片
     *
     * @param bitmap
     * @return
     */
    public static Bitmap createReflectionImageWithOrigin(Bitmap bitmap) {
        final int reflectionGap = 4;
        int w = bitmap.getWidth();
        int h = bitmap.getHeight();

        Matrix matrix = new Matrix();
        matrix.preScale(1, -1);

        Bitmap reflectionImage = Bitmap.createBitmap(bitmap, 0, h / 2, w,
                h / 2, matrix, false);

        Bitmap bitmapWithReflection = Bitmap.createBitmap(w, (h + h / 2),
                Bitmap.Config.ARGB_8888);

        Canvas canvas = new Canvas(bitmapWithReflection);
        canvas.drawBitmap(bitmap, 0, 0, null);
        Paint deafalutPaint = new Paint();
        canvas.drawRect(0, h, w, h + reflectionGap, deafalutPaint);

        canvas.drawBitmap(reflectionImage, 0, h + reflectionGap, null);

        Paint paint = new Paint();
        LinearGradient shader = new LinearGradient(0, bitmap.getHeight(), 0,
                bitmapWithReflection.getHeight() + reflectionGap, 0x70ffffff,
                0x00ffffff, Shader.TileMode.CLAMP);
        paint.setShader(shader);
        // Set the Transfer mode to be porter duff and destination in
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_IN));
        // Draw a rectangle using the paint with our linear gradient
        canvas.drawRect(0, h, w, bitmapWithReflection.getHeight()
                + reflectionGap, paint);

        return bitmapWithReflection;
    }


    /**
     * Created by paveld on 3/6/14.
     */
    public static Bitmap doBlur(Bitmap sentBitmap, int radius, boolean canReuseInBitmap) {

        // Stack Blur v1.0 from
        // http://www.quasimondo.com/StackBlurForCanvas/StackBlurDemo.html
        //
        // Java Author: Mario Klingemann <mario at quasimondo.com>
        // http://incubator.quasimondo.com
        // created Feburary 29, 2004
        // Android port : Yahel Bouaziz <yahel at kayenko.com>
        // http://www.kayenko.com
        // ported april 5th, 2012

        // This is a compromise between Gaussian Blur and Box blur
        // It creates much better looking blurs than Box Blur, but is
        // 7x faster than my Gaussian Blur implementation.
        //
        // I called it Stack Blur because this describes best how this
        // filter works internally: it creates a kind of moving stack
        // of colors whilst scanning through the image. Thereby it
        // just has to add one new block of color to the right side
        // of the stack and remove the leftmost color. The remaining
        // colors on the topmost layer of the stack are either added on
        // or reduced by one, depending on if they are on the right or
        // on the left side of the stack.
        //
        // If you are using this algorithm in your code please add
        // the following line:
        //
        // Stack Blur Algorithm by Mario Klingemann <mario@quasimondo.com>

        Bitmap bitmap;
        if (canReuseInBitmap) {
            bitmap = sentBitmap;
        } else {
            bitmap = sentBitmap.copy(sentBitmap.getConfig(), true);
        }

        if (radius < 1) {
            return (null);
        }

        int w = bitmap.getWidth();
        int h = bitmap.getHeight();

        int[] pix = new int[w * h];
        bitmap.getPixels(pix, 0, w, 0, 0, w, h);

        int wm = w - 1;
        int hm = h - 1;
        int wh = w * h;
        int div = radius + radius + 1;

        int r[] = new int[wh];
        int g[] = new int[wh];
        int b[] = new int[wh];
        int rsum, gsum, bsum, x, y, i, p, yp, yi, yw;
        int vmin[] = new int[Math.max(w, h)];

        int divsum = (div + 1) >> 1;
        divsum *= divsum;
        int dv[] = new int[256 * divsum];
        for (i = 0; i < 256 * divsum; i++) {
            dv[i] = (i / divsum);
        }

        yw = yi = 0;

        int[][] stack = new int[div][3];
        int stackpointer;
        int stackstart;
        int[] sir;
        int rbs;
        int r1 = radius + 1;
        int routsum, goutsum, boutsum;
        int rinsum, ginsum, binsum;

        for (y = 0; y < h; y++) {
            rinsum = ginsum = binsum = routsum = goutsum = boutsum = rsum = gsum = bsum = 0;
            for (i = -radius; i <= radius; i++) {
                p = pix[yi + Math.min(wm, Math.max(i, 0))];
                sir = stack[i + radius];
                sir[0] = (p & 0xff0000) >> 16;
                sir[1] = (p & 0x00ff00) >> 8;
                sir[2] = (p & 0x0000ff);
                rbs = r1 - Math.abs(i);
                rsum += sir[0] * rbs;
                gsum += sir[1] * rbs;
                bsum += sir[2] * rbs;
                if (i > 0) {
                    rinsum += sir[0];
                    ginsum += sir[1];
                    binsum += sir[2];
                } else {
                    routsum += sir[0];
                    goutsum += sir[1];
                    boutsum += sir[2];
                }
            }
            stackpointer = radius;

            for (x = 0; x < w; x++) {

                r[yi] = dv[rsum];
                g[yi] = dv[gsum];
                b[yi] = dv[bsum];

                rsum -= routsum;
                gsum -= goutsum;
                bsum -= boutsum;

                stackstart = stackpointer - radius + div;
                sir = stack[stackstart % div];

                routsum -= sir[0];
                goutsum -= sir[1];
                boutsum -= sir[2];

                if (y == 0) {
                    vmin[x] = Math.min(x + radius + 1, wm);
                }
                p = pix[yw + vmin[x]];

                sir[0] = (p & 0xff0000) >> 16;
                sir[1] = (p & 0x00ff00) >> 8;
                sir[2] = (p & 0x0000ff);

                rinsum += sir[0];
                ginsum += sir[1];
                binsum += sir[2];

                rsum += rinsum;
                gsum += ginsum;
                bsum += binsum;

                stackpointer = (stackpointer + 1) % div;
                sir = stack[(stackpointer) % div];

                routsum += sir[0];
                goutsum += sir[1];
                boutsum += sir[2];

                rinsum -= sir[0];
                ginsum -= sir[1];
                binsum -= sir[2];

                yi++;
            }
            yw += w;
        }
        for (x = 0; x < w; x++) {
            rinsum = ginsum = binsum = routsum = goutsum = boutsum = rsum = gsum = bsum = 0;
            yp = -radius * w;
            for (i = -radius; i <= radius; i++) {
                yi = Math.max(0, yp) + x;

                sir = stack[i + radius];

                sir[0] = r[yi];
                sir[1] = g[yi];
                sir[2] = b[yi];

                rbs = r1 - Math.abs(i);

                rsum += r[yi] * rbs;
                gsum += g[yi] * rbs;
                bsum += b[yi] * rbs;

                if (i > 0) {
                    rinsum += sir[0];
                    ginsum += sir[1];
                    binsum += sir[2];
                } else {
                    routsum += sir[0];
                    goutsum += sir[1];
                    boutsum += sir[2];
                }

                if (i < hm) {
                    yp += w;
                }
            }
            yi = x;
            stackpointer = radius;
            for (y = 0; y < h; y++) {
                // Preserve alpha channel: ( 0xff000000 & pix[yi] )
                pix[yi] = (0xff000000 & pix[yi]) | (dv[rsum] << 16) | (dv[gsum] << 8) | dv[bsum];

                rsum -= routsum;
                gsum -= goutsum;
                bsum -= boutsum;

                stackstart = stackpointer - radius + div;
                sir = stack[stackstart % div];

                routsum -= sir[0];
                goutsum -= sir[1];
                boutsum -= sir[2];

                if (x == 0) {
                    vmin[y] = Math.min(y + r1, hm) * w;
                }
                p = x + vmin[y];

                sir[0] = r[p];
                sir[1] = g[p];
                sir[2] = b[p];

                rinsum += sir[0];
                ginsum += sir[1];
                binsum += sir[2];

                rsum += rinsum;
                gsum += ginsum;
                bsum += binsum;

                stackpointer = (stackpointer + 1) % div;
                sir = stack[stackpointer];

                routsum += sir[0];
                goutsum += sir[1];
                boutsum += sir[2];

                rinsum -= sir[0];
                ginsum -= sir[1];
                binsum -= sir[2];

                yi += w;
            }
        }

        bitmap.setPixels(pix, 0, w, 0, 0, w, h);

        return (bitmap);
    }

}
