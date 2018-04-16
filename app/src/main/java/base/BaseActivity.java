package base;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.lzx.cardweather.R;
import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.Logger;

import utils.ToastUtil;

/**
 *
 */
public abstract class BaseActivity extends AppCompatActivity implements View.OnClickListener{

    private boolean canLog = true;           //Log开关
    private boolean canFullScreen = true;   //全屏开关
    private boolean allowScreenRoate = false;   //屏幕旋转开关
    private int clickInterval = 800;        //两次点击之间的时间间隔，单位毫秒，默认800
    private boolean isSlideBack = false;        //是否滑动返回

    private ToastUtil toastUtil = null;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        beforeSetContentView();
        if (canFullScreen) {
            /*this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
            requestWindowFeature(Window.FEATURE_NO_TITLE);*/
            int option = View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                    | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
            getWindow().getDecorView().setSystemUiVisibility(option);
        }

        setContentView(getLayoutId());

        setTheme(R.style.BaceTheme);

        if (allowScreenRoate) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        } else {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }

        Logger.addLogAdapter(new AndroidLogAdapter());
        toastUtil = new ToastUtil(this);

        initView(savedInstanceState);
        initData(savedInstanceState);
        initOtherComponent();
    }

    protected abstract @LayoutRes int getLayoutId();
    protected abstract void initView(Bundle savedInstanceState);
    protected abstract void initData(Bundle savedInstanceState);
    protected void beforeSetContentView() {

    }
    /**
     * 初始化其他组件， 如Service、BroadcastReceiver等。可选择性重写
     */
    protected void initOtherComponent() { }

    /**
     * 点击事件重写该方法以防止快速点击
     * @param v
     */
    protected void onWidgetClick(View v) { }

    protected void setCanFullScreen(boolean canFullScreen) {
        this.canFullScreen = canFullScreen;
    }

    protected void setAllowScreenRoate(boolean allowScreenRoate) {
        this.allowScreenRoate = allowScreenRoate;
    }


    /**
     * 查找view
     * @param id
     * @param <T>
     * @return
     */
    protected <T extends View> T findView(int id) {
        return (T) findViewById(id);
    }


    /**
     * 以下三个方式设置了防止快速点击。使用点击事件时需要复写onWidgetClick而不是onClick
     * @param v
     */
    @Override
    public void onClick(View v) {
        if (!isFastClick()) {
            onWidgetClick(v);
        }
    }

    private boolean isFastClick() {
        long firstTime = System.currentTimeMillis();
        if (System.currentTimeMillis() - firstTime < clickInterval) {
            return true;
        }
        firstTime = 0;
        return false;
    }

    protected void setClickInterval(int interval) {
        clickInterval = interval;
    }

    public void setSlideBack(boolean slideBack) {
        isSlideBack = slideBack;
    }

    float lastX = 0;
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (isSlideBack) {
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    lastX = event.getX();
                    break;
                case MotionEvent.ACTION_MOVE:
                    if (event.getX() - lastX > 0 && lastX < getWindow().getDecorView().getMeasuredWidth() / 3)
                        getWindow().getDecorView().setX(event.getX() - lastX);
                    break;
                case MotionEvent.ACTION_UP:
                    if (event.getX() - lastX > getWindow().getDecorView().getMeasuredWidth() /3 && lastX < getWindow().getDecorView().getMeasuredWidth() / 3) {
                        final View v = getWindow().getDecorView();
                        ObjectAnimator animator = ObjectAnimator.ofFloat(v, "X", v.getX(), v.getMeasuredWidth());
                        animator.setDuration(100);
                        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                            @Override
                            public void onAnimationUpdate(ValueAnimator animation) {
                                if ((float)animation.getAnimatedValue() == v.getMeasuredWidth()) {
                                    finish();
                                }
                            }
                        });
                        animator.start();
                    }
                    getWindow().getDecorView().setX(0);

                    break;
            }
            return true;
        }
        return super.onTouchEvent(event);
    }

    /**
     * Log
     * @param info
     */
    protected void d(String info) {
        if (canLog) {
            Logger.d(info);
        }
    }

    protected void json(String info) {
        if (canLog) {
            Logger.json(info);
        }
    }




    /**
     * Toast
     * @param message
     */
    protected void toastLong(String message) {
        toastUtil.toastLong(message);
    }
    protected void toastShort(String message) {
        toastUtil.toastShort(message);
    }
}
