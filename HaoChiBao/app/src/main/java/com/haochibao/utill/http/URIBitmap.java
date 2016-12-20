package com.haochibao.utill.http;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;

/**
 * Created by Administrator on 2016/12/20.
 */

public class URIBitmap {
    public static Bitmap getBitmapFormUri(Activity activity, Uri uri){
        try {
            InputStream input=activity.getContentResolver().openInputStream(uri);
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inJustDecodeBounds = true;
            options.inDither=true;
            options.inPreferredConfig = Bitmap.Config.ARGB_8888;//
            BitmapFactory.decodeStream(input,null,options);
            input.close();
            int originalWidth=options.outWidth;
            int originalHeight=options.outHeight;
            if ((originalWidth == -1) || (originalHeight == -1))
                return null;
                //图片分辨率以480X800为
            float hh=800f;
            float ww=480f;
            //缩放比。由于是固定比例缩放，只用高或者宽其中一个数据进行计算即可
            int be=1;
            if (originalWidth>originalHeight && originalWidth>ww){//如果宽度大的话根据宽度固定大小缩放
                be=(int)(originalWidth/ww);
            }else if (originalWidth<originalHeight && originalHeight>hh){
                be=(int)(originalHeight/hh);
            }
            if (be <=0){
                be=1;
            }

            //比例压缩
            BitmapFactory.Options bitmapOptions=new BitmapFactory.Options();
            bitmapOptions.inSampleSize=be;

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
