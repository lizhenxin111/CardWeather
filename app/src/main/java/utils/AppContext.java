package utils;

import android.app.Application;

import com.blankj.utilcode.util.Utils;

public class AppContext extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Utils.init(this);
    }
}
