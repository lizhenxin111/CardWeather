package utils;

import android.content.Context;
import android.support.annotation.IdRes;

public class ResourceUtil {

    /**
     * 根据资源名称和类型获取id
     * @param name  资源名称
     * @param type  资源类型
     * @return      id
     */
    public static @IdRes int getId(String name, String type) {
        Context context = AppContext.getAppContext();
        return context.getResources().getIdentifier(name, type, context.getPackageName());
    }
}
