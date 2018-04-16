package mvp.mainpage;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.util.ArrayMap;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.SparseArray;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.blankj.utilcode.util.ActivityUtils;
import com.lzx.cardweather.R;

import java.util.List;

import acitvity.ChangeOrderActivity;
import acitvity.SearchActivity;
import base.BaseFragment;
import bean.aqi.Site;
import bean.card.CardAttr;
import bean.card.CardAttrList;
import ui.card.BaseCard;
import ui.card.CardAQI;
import ui.card.CardLineChart;
import ui.card.CardWeather;
import ui.card.OrderLinearLayout;
import utils.AppPref;

public class MainFragment extends BaseFragment implements IContract.IView {

    private IContract.IPresenter presenter = null;

    private CardAQI mAqi = null;
    private CardWeather mWeather = null;
    private CardLineChart mAqiChange = null;
    private OrderLinearLayout mParent = null;

    private LocalReceiver localReceiver = null;
    private LocalBroadcastManager localBroadcastManager = null;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_main;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        IntentFilter filter = new IntentFilter("broadcast_local_change_order");
        LocalReceiver localReceiver = new LocalReceiver();
        LocalBroadcastManager localBroadcastManager = LocalBroadcastManager.getInstance(getActivity());
        localBroadcastManager.registerReceiver(localReceiver, filter);
    }

    @Override
    protected void initView(View root, @Nullable Bundle savedInstanceState) {
        mAqi = root.findViewById(R.id.main_aqi);
        mWeather = root.findViewById(R.id.main_weather);
        mAqiChange = root.findViewById(R.id.main_aqi_chage);
        mParent = root.findViewById(R.id.main_parent);

        Toolbar toolbar = root.findViewById(R.id.main_fragment_toobar);
        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);
        setHasOptionsMenu(true);
    }

    @Override
    protected void initData(@Nullable Bundle savedInstanceState) {
        String city = AppPref.getCity();
        presenter = new MainPreferencr(this);
        presenter.loadAll(city);
        presenter.changeOrder();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (localBroadcastManager != null) {
            localBroadcastManager.unregisterReceiver(localReceiver);
        }
    }

    @Override
    public void setAQI(int aqi, int[] data) {
        if (mAqi == null) {
            mAqi = mRootView.findViewById(R.id.main_aqi);
        }
        mAqi.setAqi(aqi);
        mAqi.setItems(data);
        mAqi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.loadSites(AppPref.getCity());
            }
        });
    }

    @Override
    public void setWeather(String tmp, String weather, String[] data) {
        if (mWeather == null) {
            mWeather = mRootView.findViewById(R.id.main_weather);
        }
        mWeather.setTmp(tmp);
        mWeather.setWeather(weather);
        mWeather.setItems(data);
    }

    @Override
    public void setAQIChange(ArrayMap<Integer, Integer> list) {
        if (mAqiChange == null) {
            mAqiChange = mRootView.findViewById(R.id.main_aqi_chage);
        }
        mAqiChange.setTitle(getResources().getString(R.string.card_title_aqi_change));
        mAqiChange.setData(list);
        mAqiChange.setxUnit("h");
        mAqiChange.setyUnit("mg/m3");
    }

    @Override
    public void addSites(List<Site> list) {

    }

    /**
     * 调整卡片顺序
     * 思路：为所有子view设置优先级，之后排序
     * 步骤：1。遍历所有子view，做两件事：1。根据配置文件为子view设置优先级    2。把子view加入到SparseArray中。
     *      2。遍历结束后再依次添加所有view（SparseArray会自动调整顺序）
     * @param list
     */
    @Override
    public void setOrder(CardAttrList list) {
        if (mParent == null) {
            mParent = mRootView.findViewById(R.id.main_parent);
        }

        //记录设置完优先级的card。key是优先级
        SparseArray<BaseCard> cardSparseArray = new SparseArray<>();
        for (int i = 0; i < mParent.getChildCount(); i++) {
            BaseCard card = (BaseCard) mParent.getChildAt(i);
            int index = list.getIndex((String) card.getTag());
            if (index < list.size()) {
                CardAttr attr = list.get(index);
                card.setCardPriority(index);
                card.setVisibility(attr.getVisibility() == true ? View.VISIBLE : View.GONE);
                cardSparseArray.put(index, card);
            }
        }

        mParent.removeAllViews();

        //添加子view
        for (int i = 0; i < cardSparseArray.size(); i++) {
            mParent.addView(cardSparseArray.valueAt(i));
        }
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_scrolling, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_change_order:
                changerOrderFragment();
                break;
            case R.id.action_search:
                ActivityUtils.startActivity(SearchActivity.class);
                break;
        }
        return onContextItemSelected(item);
    }

    private void changerOrderFragment() {
        ActivityUtils.startActivity(ChangeOrderActivity.class);
    }

    public class LocalReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            Toast.makeText(context, "change order", Toast.LENGTH_SHORT).show();
            presenter.changeOrder();
        }
    }
}
