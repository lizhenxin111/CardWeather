package utils;

import android.content.Context;
import android.widget.Toast;

public class ToastUtil {
    private Context mContext = null;

    public ToastUtil(Context mContext) {
        this.mContext = mContext;
    }

    public void toastLong(String message) {
        Toast.makeText(mContext, message, Toast.LENGTH_SHORT).show();
    }

    public void toastShort(String message) {
        Toast.makeText(mContext, message, Toast.LENGTH_SHORT).show();
    }
}
