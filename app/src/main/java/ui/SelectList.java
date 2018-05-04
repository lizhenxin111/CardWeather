package ui;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.PopupWindow;

import com.blankj.utilcode.util.Utils;
import com.lzx.cardweather.R;

import java.util.List;

import adapter.recycler.SelectCityAdapter;
import listener.OnListClickListener;

public class SelectList {

    private View mParent = null;

    private EditText cityInput = null;
    private RecyclerView cityList = null;
    private SelectCityAdapter cityListAdapter = null;

    private OnCitySelectedListener oncitySelect = null;

    private PopupWindow p;

    private List<String> data;

    public SelectList(View mParent, List<String> data, OnCitySelectedListener onPopupDismissListener) {
        this.mParent = mParent;
        this.oncitySelect = onPopupDismissListener;
        this.data = data;
    }

    /**
     * 初始化Popup的View并展示
     */
    public void show() {
        View v = LayoutInflater.from(Utils.getApp()).inflate(R.layout.popup_select_city, (ViewGroup) mParent, false);
        v.setBackgroundColor(Color.CYAN);
        p = new PopupWindow(mParent.getContext());
        p.setContentView(v);
        p.setBackgroundDrawable(new ColorDrawable(0));
        p.setElevation(10);
        p.setFocusable(true);
        p.setOutsideTouchable(false);
        p.setTouchable(true);

        p.showAtLocation(mParent, Gravity.CENTER, 0, 0);

        initCityList(p);
    }

    public void dismiss() {
        if (p != null && p.isShowing()) {
            p.dismiss();
        }
    }

    /**
     * 初始化城市数据）
     * @param p PopupView的类
     */
    private void initCityList(final PopupWindow p) {
        //初始化RecyclerView
        cityList = (RecyclerView) p.getContentView().findViewById(R.id.bottom_sheet_city_list);

        cityListAdapter = new SelectCityAdapter(data);

        GridLayoutManager layoutManager = new GridLayoutManager(mParent.getContext(), 2);
        cityList.setLayoutManager(layoutManager);

        cityListAdapter.setOnListClickListener(new OnListClickListener() {
            @Override
            public void onClick(View v, Object o) {
                //AppPref.setCity((String) o);
                oncitySelect.onSelected((String) o);
                p.dismiss();
            }
        });
        cityList.setAdapter(cityListAdapter);

        //城市输入框
        cityInput = (EditText) p.getContentView().findViewById(R.id.bottom_sheet_input);
        cityInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                cityListAdapter.getFilter().filter(charSequence);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    public interface OnCitySelectedListener {
        void onSelected(String city);
    }
}
