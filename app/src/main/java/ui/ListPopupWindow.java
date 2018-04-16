package ui;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;

import com.blankj.utilcode.util.SizeUtils;

public class ListPopupWindow {
    private Context mContext;
    private RecyclerView.Adapter mAdapter;

    private int width, height;

    private RecyclerView recyclerView;
    private PopupWindow popupWindow;

    public ListPopupWindow(Context context, RecyclerView.Adapter adapter) {
        this.mContext = context;
        width = context.getResources().getDisplayMetrics().widthPixels - SizeUtils.dp2px(60);
        height = context.getResources().getDisplayMetrics().heightPixels - SizeUtils.dp2px(100);
        mAdapter = adapter;
    }

    public ListPopupWindow(int width, int height, RecyclerView.Adapter adapter) {
        this.width = width;
        this.height = height;
        mAdapter = adapter;
    }

    private void initView() {
        recyclerView = new RecyclerView(mContext);
        ViewGroup.LayoutParams lp = new ViewGroup.LayoutParams(width, height);
        recyclerView.setLayoutParams(lp);
        recyclerView.setAdapter(mAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(mContext));

        popupWindow = new PopupWindow(recyclerView);
        popupWindow.setTouchable(true);
        popupWindow.setOutsideTouchable(true);
        popupWindow.setBackgroundDrawable(new ColorDrawable(mContext.getResources().getColor(android.R.color.transparent)));
    }

    public void show(View anchor) {
        popupWindow.showAtLocation(anchor, Gravity.BOTTOM, 0, 0);
    }

    public void dismiss() {
        if (popupWindow.isShowing()) {
            popupWindow.dismiss();
        }
    }
}
