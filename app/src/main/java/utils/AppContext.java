package utils;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.os.StrictMode;
import android.support.v4.content.LocalBroadcastManager;
import android.text.TextUtils;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.blankj.utilcode.util.Utils;
import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.Logger;

import acitvity.MainActivity;


public class AppContext extends Application {

    public static final String ACTION_EXIT_APP = "ACTION_EXIT_APP";
    public static final String ACTION_CHANGE_ORDER = "ACTION_CHANGE_ORDER";


    private boolean DEVELOPER_MODE = true;
    private static Context mAppContext;
    private static RequestQueue mQueue = null;

    private static String VOLLEY_TAG = "VOLLEY_REQUEST";

    @Override
    public void onCreate() {

        /*if (DEVELOPER_MODE) {
            StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
                    .detectDiskReads()
                    .detectDiskWrites()
                    .detectNetwork()   // or .detectAll() for all detectable problems
                    .detectAll()
                    .penaltyLog()
                    .penaltyDialog()
                    .build());
            StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder()
                    .detectLeakedSqlLiteObjects()
                    .detectLeakedClosableObjects()
                    .penaltyLog()
                    .penaltyDeath()
                    .build());
        }*/

        super.onCreate();

        Utils.init(this);

        Logger.addLogAdapter(new AndroidLogAdapter());

        mAppContext = getApplicationContext();

    }

    /**
     * Receiver+singleTask方式退出App
     * 将mainActivity设为SingleTask，退出时跳转至mainactivity即可结束栈中其他Activity。再发送广播结束MainActivity
     * @param act
     */
    public static void exitApp(Activity act) {
        mAppContext.startActivity(new Intent(act, MainActivity.class));
        LocalBroadcastManager lbm = LocalBroadcastManager.getInstance(act);
        Intent intent = new Intent(ACTION_EXIT_APP);
        lbm.sendBroadcast(intent);
    }

    public static Context getAppContext() {
        return mAppContext;
    }

    public static RequestQueue getQueue() {
        if (mQueue == null) {
            synchronized (AppContext.class) {
                if (mQueue == null) {
                    mQueue = Volley.newRequestQueue(mAppContext);
                }
            }
        }
        return mQueue;
    }

    public static <T> void addRequest(Request<T> req) {
        getQueue().add(req);
    }

    public static <T> void addRequest(Request<T> req, String tag) {
        req.setTag(TextUtils.isEmpty(tag) ? VOLLEY_TAG : tag);
        addRequest(req);
    }

    public static void stopRequestQueue() {
        if (mQueue != null) {
            mQueue.stop();
        }
    }
}
