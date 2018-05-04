package ui;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.PopupWindow;
import android.widget.ProgressBar;

import com.blankj.utilcode.util.ScreenUtils;
import com.blankj.utilcode.util.SizeUtils;
import com.blankj.utilcode.util.Utils;
import com.lzx.cardweather.R;

/**
 * 显示站点空气质量详情列表的popupview
 */
public class ListPopupWindow {
    private Context mContext;

    private int width, height;

    private View layout;
    private RecyclerView recyclerView;
    private PopupWindow popupWindow;
    private ProgressBar loading = null;
    private FrameLayout rootView;



    public ListPopupWindow(Context context) {
        this(context,
                ScreenUtils.getScreenWidth() - SizeUtils.dp2px(60),
                ScreenUtils.getScreenHeight() - SizeUtils.dp2px(100));
    }

    public ListPopupWindow(Context context, int width, int height) {
        mContext = context;
        this.width = width;
        this.height = height;
        initView();
    }

    private void initView() {
        layout = LayoutInflater.from(Utils.getApp()).inflate(R.layout.popup_list, null, false);
        rootView = layout.findViewById(R.id.popup_list_base);

        popupWindow = new PopupWindow(layout, width, height);
        popupWindow.setTouchable(true);
        popupWindow.setOutsideTouchable(true);
        popupWindow.setBackgroundDrawable(new ColorDrawable(mContext.getResources().getColor(android.R.color.transparent)));
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                dismissLoading();
            }
        });

    }

    public void setAdapter(RecyclerView.Adapter adapter) {
        recyclerView = layout.findViewById(R.id.popup_list);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
    }

    public void show(View anchor) {
        popupWindow.showAtLocation(anchor, Gravity.BOTTOM, 0, 0);
    }

    public void dismiss() {
        if (popupWindow.isShowing()) {
            popupWindow.dismiss();
        }
    }

    public boolean isShowing() {
        return popupWindow.isShowing();
    }
    public void showLoading() {
        if (loading == null) {
            loading = new ProgressBar(mContext);
        }
        FrameLayout.LayoutParams lp = new FrameLayout.LayoutParams(SizeUtils.dp2px(48), SizeUtils.dp2px(48));
        lp.gravity = Gravity.CENTER;
        loading.setLayoutParams(lp);
        rootView.addView(loading);
    }

    public void dismissLoading() {
        if (loading != null) {
            rootView.removeView(loading);
        }
    }

}
