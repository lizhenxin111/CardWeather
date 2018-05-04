package cache;

import android.content.Context;

import com.blankj.utilcode.util.AppUtils;
import com.blankj.utilcode.util.CloseUtils;
import com.blankj.utilcode.util.EncodeUtils;
import com.blankj.utilcode.util.Utils;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;

import utils.StringUtil;

public class DiskCache {

    public static final String CACHE_NAME_AQI = "aqi";

    private static DiskLruCache cache;

    static {
        open();
    }

    public static void set(String link, Object o) {
        open();
        String key = getKey(link);
        try {
            DiskLruCache.Editor editor = cache.edit(key);
            if (editor != null) {
                OutputStream os = editor.newOutputStream(0);
                os.write(((String) o).getBytes());
                editor.commit();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Object get(String link) {
        open();
        String key = getKey(link);
        try {
            DiskLruCache.Snapshot snapshot = cache.get(key);
            if (snapshot != null) {
                return StringUtil.fromInputStream(snapshot.getInputStream(0));
            }
        } catch (IOException e) {
            e.printStackTrace();

        }
        return null;
    }

    public static void remove(String link) {
        open();
        String key = getKey(link);
        try {
            cache.remove(key);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static boolean isClosed() {
        if (cache != null && !cache.isClosed()) {
            return false;
        }
        return true;
    }

    public static void close() {
        if (cache != null && !cache.isClosed()) {
            CloseUtils.closeIO(cache);
        }
    }

    public static void flush() {
        try {
            if (cache != null) {
                cache.flush();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void open() {
        if (cache == null || cache.isClosed()) {
            try {
                File cacheDir = getDislCacheDir(Utils.getApp().getApplicationContext(), CACHE_NAME_AQI);
                if (cacheDir != null) {
                    cacheDir.mkdirs();
                }
                cache = DiskLruCache.open(cacheDir, AppUtils.getAppVersionCode(), 1, 10 * 1024 * 1024);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private static String getKey(String link) {
        return EncodeUtils.base64Encode2String(link.getBytes());
    }

    private static File getDislCacheDir(Context context, String name) {
        String cachePath = context.getCacheDir().getPath();
        return new File(cachePath + File.separator + name);
    }
}
