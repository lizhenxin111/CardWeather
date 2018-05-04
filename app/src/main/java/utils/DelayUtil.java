package utils;

import android.os.Handler;

public class DelayUtil {
    private OnDelayExecuteInterface onDelayExecuteInterface;
    public DelayUtil(int ms, Handler handler, final OnDelayExecuteInterface onDelayExecuteInterface) {
        this.onDelayExecuteInterface = onDelayExecuteInterface;
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                onDelayExecuteInterface.delay();
            }
        }, ms);
    }

    public interface OnDelayExecuteInterface {
        void delay();
    }
}
