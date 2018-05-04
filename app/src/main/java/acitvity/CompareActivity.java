package acitvity;

import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;

import com.blankj.utilcode.util.ToastUtils;
import com.lzx.cardweather.R;
import com.orhanobut.logger.Logger;

import base.BaseActivity;
import mvp.compare.CompareItemView;
import mvp.search.ISearchContract;
import mvp.search.SearchPresenter;
import ui.CompareDialog;
import ui.Loading;
import utils.AppContext;
import utils.DelayUtil;
import utils.StringUtil;

public class CompareActivity extends BaseActivity {

    private boolean firstShow = true;

    private ViewGroup container, aContainer, bContainer;
    private SearchPresenter aPresenter, bPresenter;
    private CompareItemView aView, bView;
    private CompareDialog dialog;

    @Override
    protected void beforeSetContentView() {
        setAllowScreenRoate(true);
        setCanFullScreen(false);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_compare;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {

        container = findView(R.id.container);
        aContainer = findView(R.id.container_a);
        bContainer = findView(R.id.container_b);

        aView = new CompareItemView();
        aView.init(this);
        bView = new CompareItemView();
        bView.init(this);

        dialog = new CompareDialog(this);
        dialog.setOnSearchClickListener(mOnSearchClickListener);
    }

    @Override
    protected void initData(Bundle savedInstanceState) {

    }

    @Override
    protected void initOtherComponent() {
        super.initOtherComponent();
        aPresenter = new SearchPresenter(this, new SearchPresenter.OnSearchCompleteListener() {
            @Override
            public void onSearchComplete() {
                aContainer.removeAllViews();
                aContainer.addView(aView.getView());
            }
        }, aView);
        bPresenter = new SearchPresenter(this, new SearchPresenter.OnSearchCompleteListener() {
            @Override
            public void onSearchComplete() {
                bContainer.removeAllViews();
                bContainer.addView(bView.getView());
            }
        }, bView);
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus && firstShow) {
            showDialog();
            firstShow = false;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        AppContext.stopRequestQueue();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_compare, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_search) {
            showDialog();
        }
        return true;
    }

    private void showLoading() {
        aContainer.removeAllViews();
        bContainer.removeAllViews();
        aContainer.addView(new Loading(this));
        bContainer.addView(new Loading(this));
    }

    private void showDialog() {
        dialog.show();
    }

    private void dismissDialog () {
        dialog.dismiss();
    }

    private Handler handler = new Handler();
    private CompareDialog.OnSearchClickListener mOnSearchClickListener = new CompareDialog.OnSearchClickListener() {
        @Override
        public void onSearchClick(String aType, String aLocation, String aDate, String aHour, final String bType, final String bLocation, final String bDate, final String bHour) {
            if (StringUtil.hasNull(aType, aLocation, aDate, aHour, bType, bLocation, bDate, bHour)) {
                ToastUtils.showShort(getString(R.string.notice_null));
                return;
            }
            /*aPresenter.releaseData();
            bPresenter.releaseData();*/
            handlerRequest(aPresenter, aType, aDate, aHour, aLocation);
            new DelayUtil(100, new Handler(), new DelayUtil.OnDelayExecuteInterface() {
                @Override
                public void delay() {
                    handlerRequest(bPresenter, bType, bDate, bHour, bLocation);
                }
            });
            dismissDialog();
            showLoading();
        }

        private void handlerRequest(ISearchContract.ISearchPresenter presenter, String type, String date, String hour, String location) {
            String year = date.substring(0, 4);
            String month = date.substring(4, 6);
            String day = date.length() > 6 ? date.substring(6, date.length()) : null;
            if (hour.equals("全天")) hour = null;
            Logger.d(year + "  " + month + "   " + day);
            if (type.equals("城市")) {
                if (day != null) {
                    if (hour != null) {
                        //小时
                        presenter.searchCityHour(location, year + month + day, hour);
                    } else {
                        //整天
                        presenter.searchCityDay(location, year + month + day);
                    }
                } else {
                    //本月
                    presenter.searchCityMonth(location, year, month);
                }
            } else {
                if (day != null) {
                    if (hour != null) {
                        //小时
                        presenter.searchSite(location, year + month + day, hour);
                    } else {
                        //整天
                        presenter.searchSiteDay(location, year + month + day);
                    }
                } else {
                    //本月
                    presenter.searchSiteMonth(location, year, month);
                }
            }
        }
    };


}
