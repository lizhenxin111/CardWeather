package net.request;

import android.os.Handler;
import android.text.TextUtils;

import com.orhanobut.logger.Logger;

import net.listener.StringListener;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import bean.aqi.City;
import cache.DiskCache;
import db.DB;
import utils.DateUtil;
import utils.StringUtil;

public class StringRequest implements Runnable {
    private String cacheKey = null;     //DiskLruCache的key，为空则不使用缓存。
    private boolean hasCache = false;

    protected String mUrl = null;
    protected String method = "GET";
    protected HttpURLConnection mConn = null;

    protected Handler mHandler = new Handler();

    private StringListener mStringListener = null;

    public StringRequest(String url, StringListener stringListener) {
        mUrl = url;
        mStringListener = stringListener;
    }

    public StringRequest(String url, String cacheKey, StringListener stringListener) {
        mUrl = url;
        mStringListener = stringListener;
        this.cacheKey = cacheKey;
    }


    @Override
    public void run() {
        if (!TextUtils.isEmpty(cacheKey)) {
            checkCache();
        }

        if (!hasCache) {
            searchNet();
        }
    }

    private void checkCache() {

        String cache = (String) DiskCache.get(cacheKey);
        if (cache == null) {
            hasCache = false;
            return;
        } else {
            hasCache = true;
            Logger.d("缓存： " + cache);
            mStringListener.onSuccess(cache);
        }
    }

    private void searchNet() {
        try {
            URL url = new URL(mUrl);
            mConn = (HttpURLConnection) url.openConnection();
            mConn.setRequestMethod(method);
            int responseCode = mConn.getResponseCode();
            if (responseCode == 200) {
                final String result = StringUtil.fromInputStream(mConn.getInputStream());
                if (hasCache) {
                    DiskCache.set(mUrl, result);
                }
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        mStringListener.onSuccess(result);
                    }
                });
            } else {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            mStringListener.onFailed("ResponseCode: " + mConn.getResponseCode() + ".  " + mConn.getResponseMessage());
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            mConn.disconnect();
        }
    }

    private String[] getParams(String url) {
        String[] result = new String[3];

        int i = url.indexOf("city");
        int ii = url.indexOf("&", i);
        if (ii == -1) {
            ii = url.length();
        }
        result[0] = url.substring(i + 5, ii);

        int j = url.indexOf("date");
        result[1] = url.substring(j + 5, j + 13);

        int k = url.indexOf("hour");
        result[2] = url.substring(k + 5, k + 7);

        return result;
    }

}
