package com.example.administrator.mytechnologyproject.Util;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.util.LruCache;
import android.widget.ImageView;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Administrator on 2016/9/2.
 * 缓存图片
 */
public class ImageLoader {

    private Context context;
    //LruCache缓存
    private static LruCache<String, Bitmap> mCache = new LruCache<String, Bitmap>((int) (Runtime.getRuntime().freeMemory() / 4));
    private static final String TAG = "ImageLoader";

    public ImageLoader(Context context) {
        this.context = context;
    }

    /**
     * 加载图片
     * @param path      图片的地址
     * @param imageView imageView控件
     * @return
     */
    public Bitmap dispaly(String path, ImageView imageView) {
        Bitmap bitmap = null;
        if (bitmap == null && path.length() <= 0) {
            return bitmap;
        }
        //先去缓存中看看有没有图片
        bitmap = loadImageFromReference(path);
        if (bitmap != null) {
            Log.i(TAG, "dispaly: 内存缓存中的数据");
            imageView.setImageBitmap(bitmap);
            return bitmap;
        }

        //去本地文件中的缓存中去取
        bitmap = loadImageFromCache(path);
        if (bitmap != null) {
            mCache.put(path, bitmap);   //存入缓存区
            imageView.setImageBitmap(bitmap);
            Log.i(TAG, "dispaly: 本地缓存中的数据");
            return bitmap;
        }

        //从网络中获取图片
        DownPic(path,imageView);
        Log.i(TAG, "dispaly: 网络上下载数据");
        return bitmap;
    }

    private void DownPic(final String path, final ImageView iv) {
        final Handler handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                int code = msg.what;
                if(code == 1){
                    Bitmap bm = (Bitmap) msg.obj;
                    iv.setImageBitmap(bm);
                }
            }
        };

        new Thread() {
            @Override
            public void run() {
                super.run();
                try {
                    URL url = new URL(path);
                    HttpURLConnection con = (HttpURLConnection) url.openConnection();
                    InputStream is = con.getInputStream();
                    Bitmap bitmap = BitmapFactory.decodeStream(is); //从网络上加载图片，Bitmap
                    //存入缓存
                    mCache.put(path, bitmap);
                    //存入本地
                    saveLocal(path, bitmap);
                    Message message = new Message();
                    message.what = 1;
                    message.obj = bitmap;
                    handler.sendMessage(message);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }.start();

    }


    /**
     * 从缓存中去取图片
     * 根据存的时候的一个类似于Key
     *
     * @param url
     * @return
     */
    public Bitmap loadImageFromReference(String url) {
        return mCache.get(url);
    }

    /**
     * 从本地中去取，也就是ＳＤ卡
     *
     * @param url
     * @return
     */
    public Bitmap loadImageFromCache(String url) {
        //http://www.baidu.com/text/gg.jpg
        String name = url.substring(url.lastIndexOf("/") + 1);
        if (name == null) {
            return null;
        }
        File cacheDir = context.getExternalCacheDir();      //返回一个缓存目录
        if (cacheDir == null) {
            return null;
        }

        File[] files = cacheDir.listFiles();
        if (files == null) {
            return null;
        }

        File bitmapFile = null;

        for (File file : files) {
            if (file.getName().equals(name)) {
                bitmapFile = file;
            }
        }

        if (bitmapFile == null) {
            return null;
        }

        Bitmap fileBitmap = null;
        fileBitmap = BitmapFactory.decodeFile(bitmapFile.getAbsolutePath());
        return fileBitmap;
    }

    /**
     * 将图片存入本地SD卡
     */
    private void saveLocal(String url, Bitmap bitmap) {
        String name = url.substring(url.lastIndexOf("/") + 1);
        File cacheDir = context.getExternalCacheDir();
        if (!cacheDir.exists()) {
            cacheDir.mkdir();
        }
        OutputStream stream = null;
        try {
            stream = new FileOutputStream(new File(cacheDir, name));
            bitmap.compress(Bitmap.CompressFormat.JPEG,100,stream);     //写入本地
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }finally {
            try {
                stream.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


}
