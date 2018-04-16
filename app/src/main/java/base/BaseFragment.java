package base;

import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.orhanobut.logger.Logger;

import utils.ToastUtil;

public abstract class BaseFragment extends Fragment {
    private boolean isLog = true;
    private ToastUtil toastUtil = null;

    protected View mRootView = null;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        toastUtil = new ToastUtil(getContext());
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mRootView = inflater.inflate(getLayoutId(), container, false);
        initView(mRootView, savedInstanceState);
        initData(savedInstanceState);
        return mRootView;
    }

    protected abstract @LayoutRes int getLayoutId();

    protected abstract void initView(View root, @Nullable Bundle savedInstanceState);

    protected abstract void initData(@Nullable Bundle savedInstanceState);

    /**
     * Log
     * @param info
     */
    protected void d(String info) {
        if (isLog) {
            Logger.d(info);
        }
    }

    protected void json(String info) {
        if (isLog) {
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
