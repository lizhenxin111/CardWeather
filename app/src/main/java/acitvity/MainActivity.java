package acitvity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.util.ArrayMap;
import android.support.v7.widget.Toolbar;
import android.util.SparseArray;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.blankj.utilcode.util.ActivityUtils;
import com.lzx.cardweather.R;
import com.orhanobut.logger.Logger;

import java.util.ArrayList;
import java.util.List;

import adapter.recycler.SiteListAdapter;
import base.BaseActivity;
import bean.aqi.Site;
import bean.card.CardAttr;
import bean.card.CardAttrList;
import bean.heweather.forecast.ForecastItem;
import mvp.citydetail.ICityDetail;
import mvp.citydetail.SitesPresenter;
import mvp.mainpage.IContract;
import mvp.mainpage.MainPreferencr;
import ui.ListPopupWindow;
import ui.card.BaseCard;
import ui.card.CardAQI;
import ui.card.CardLineChart;
import ui.card.CardWeather;
import ui.card.CardWeatherForecast;
import ui.card.OrderLinearLayout;
import utils.AppContext;
import utils.AppPref;

public class MainActivity extends BaseActivity implements IContract.IView, ICityDetail.IView {

    private IContract.IPresenter mMainPresenter = null;
    private ICityDetail.IPresenter mSitesPresenter = null;

    private OrderLinearLayout mParent = null;
    private CardAQI mAqi = null;
    private CardWeather mWeather = null;
    private CardLineChart mAqiChange = null;
    private CardWeatherForecast mWeatherForecast = null;
    private CollapsingToolbarLayout toolbarLayout = null;

    private ListPopupWindow sitesDetails = null;

    private LocalReceiver localReceiver = null;
    private LocalBroadcastManager localBroadcastManager = null;


    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void beforeSetContentView() {
        super.beforeSetContentView();
        setCanFullScreen(false);
        setAllowScreenRoate(false);
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        mParent = findView(R.id.main_parent);
        mAqi = findView(R.id.main_aqi);
        mWeather = findView(R.id.main_weather);
        mAqiChange = findView(R.id.main_aqi_chage);
        mWeatherForecast = findView(R.id.main_weather_forecast);
        toolbarLayout = findView(R.id.collapsing);

        Toolbar toolbar = findViewById(R.id.main_fragment_toobar);
        setSupportActionBar(toolbar);
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
//        if (AppPref.getCity() == null) {
//            Intent intent = new Intent(this, InitActivity.class);
//            startActivityForResult(intent, 1);
//        } else {
//            loadData();
//        }
        loadData();
    }

    private void loadData() {
//        String city = AppPref.getCity();
        String city = "广州";
        mMainPresenter = new MainPreferencr(this, city);
        mMainPresenter.loadAll();
        //mMainPresenter.changeOrder();

        mSitesPresenter = new SitesPresenter(this, city);

        toolbarLayout.setTitle("广州");
    }

    @Override
    protected void initOtherComponent() {
        IntentFilter filter = new IntentFilter();
        filter.addAction(AppContext.ACTION_CHANGE_ORDER);
        filter.addAction(AppContext.ACTION_EXIT_APP);
        localReceiver = new LocalReceiver();
        localBroadcastManager = LocalBroadcastManager.getInstance(this);
        localBroadcastManager.registerReceiver(localReceiver, filter);
    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if (resultCode == InitActivity.RESULT_CODE) {
                loadData();
            }
        }
    }

    @Override
    public void onBackPressed() {
        if (sitesDetails != null && sitesDetails.isShowing()) {
            sitesDetails.dismiss();
        } else {
            AppContext.exitApp(this);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (localBroadcastManager != null) {
            localBroadcastManager.unregisterReceiver(localReceiver);
        }
    }


    public class LocalReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (AppContext.ACTION_EXIT_APP.equals(action)) {
                MainActivity.this.finish();
            } else if (AppContext.ACTION_CHANGE_ORDER.equals(action)) {
                Toast.makeText(context, "change order", Toast.LENGTH_SHORT).show();
                mMainPresenter.changeOrder();
            }
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_scrolling, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_change_order:
                ActivityUtils.startActivity(ChangeOrderActivity.class);
                break;
            case R.id.action_search:
                ActivityUtils.startActivity(SearchActivity.class);
                break;
            case R.id.action_conpare:
                ActivityUtils.startActivity(CompareActivity.class);
                break;
            case R.id.action_settings:
                ActivityUtils.startActivity(SettingsActivity.class);
                break;
        }
        return onContextItemSelected(item);
    }



    /*
     * MainPage的View接口
     * */
    @Override
    public void setAQI(int aqi, int[] data) {
        Logger.d("set View");
        mAqi.setAqi(aqi);
        mAqi.setItems(data);
        mAqi.setDrawToggle(true);
        mAqi.setOnAqiClick(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mSitesPresenter.loadSites();
            }
        });
    }

    @Override
    public void setWeather(String tmp, String weather, String weatherCode, String[] data) {
        mWeather.setTmp(tmp);
        //mWeather.setWeather(weather);
        mWeather.setWeathreCode(weatherCode);
        mWeather.setItems(data);
    }

    @Override
    public void setWeatherForecast(List<ForecastItem> list) {
        mWeatherForecast.requestData(list);
        /*mWeatherForecast.setOnForecastClickListener(new CardWeatherForecast.OnForecastClickListener() {
            @Override
            public void onClick(int position) {

            }
        });*/
    }

    @Override
    public void setLifeStyle(ArrayMap<String, String> data) {
        mWeather.requestData(data);
    }

    @Override
    public void setAQIChange(ArrayMap<String, Integer> list) {
        mAqiChange.setTitle(getResources().getString(R.string.card_title_aqi_change));
        mAqiChange.requestData(list);
        mAqiChange.setxUnit("h");
        mAqiChange.setyUnit("mg/m3");
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
            mParent = findViewById(R.id.main_parent);
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
    public void addLoading(ViewGroup parent) {

    }

    @Override
    public void removeLoading(ViewGroup parent) {

    }




    /*
     * CityDetail的View接口
     * */
    @Override
    public void showPopupView() {
        sitesDetails = new ListPopupWindow(AppContext.getAppContext());
        sitesDetails.show(mParent);
    }

    @Override
    public void showLoading() {
        //listPopup.showLoading();
    }

    @Override
    public void dismissLoading() {
        //listPopup.dismissLoading();
    }

    @Override
    public void showSites(List<Site> list) {
        if (list == null || list.size() == 0) {
            Snackbar s = Snackbar.make(mParent, "暂未查找到相关站点的信息", Snackbar.LENGTH_INDEFINITE);
            s.show();
        } else {
            SiteListAdapter adapter = new SiteListAdapter(list);
            sitesDetails.setAdapter(adapter);
        }
    }

    @Override
    public void dismiss() {
        sitesDetails.dismiss();
    }
}
