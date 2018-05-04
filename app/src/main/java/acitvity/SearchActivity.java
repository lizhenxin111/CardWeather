package acitvity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.blankj.utilcode.util.ToastUtils;
import com.lzx.cardweather.R;

import base.BaseActivity;
import mvp.search.ISearchContract;
import mvp.search.SearchPresenter;
import mvp.search.SearchResultView;
import ui.SearchDialog;
import utils.AppContext;
import utils.StringUtil;

public class SearchActivity extends BaseActivity {

    private boolean firstShow = true;

    private SearchPresenter searchPresenter;
    private ISearchContract.ISearchView searchResultView;


    private LinearLayout root;
    private SearchDialog dialog;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_search;
    }

    @Override
    protected void beforeSetContentView() {
        setAllowScreenRoate(false);
        setCanFullScreen(false);
        setSlideBack(true);
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        root = findView(R.id.search_root);

        searchResultView = new SearchResultView();
        searchResultView.init(this);

        dialog = new SearchDialog(this);
        dialog.setOnSearchClickListener(new SearchDialog.OnSearchClickListener() {
            @Override
            public void onSearchClick(String type, String location, String date, String hour) {
                if (StringUtil.hasNull(type, location, date, hour)) {
                    ToastUtils.showShort(getString(R.string.notice_null));
                    return;
                }
                root.removeAllViews();
                executeSearch(type, location, date, hour);
                dismissDialog();
                showLoading();
            }
        });
    }

    @Override
    protected void initData(Bundle savedInstanceState) {

    }


    @Override
    protected void initOtherComponent() {
        searchPresenter = new SearchPresenter(this, new SearchPresenter.OnSearchCompleteListener() {
            @Override
            public void onSearchComplete() {
                root.removeAllViews();
                root.addView(searchResultView.getView());
            }
        }, searchResultView);

    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus && firstShow) {
            showSearchDialog();
            firstShow = false;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        AppContext.stopRequestQueue();
        //searchResultView.releaseData();
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_compare, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_search) {
            showSearchDialog();
        }
        return true;
    }


    private void showLoading() {
        ProgressBar progressBar = new ProgressBar(this);
        root.addView(progressBar);
    }

    private void showSearchDialog() {
        dialog.show();
    }

    private void dismissDialog() {
        dialog.dismiss();
    }


    private void executeSearch(String type, String location, String date, String hour) {
        //searchPresenter.releaseData();

        String year = date.substring(0, 4);
        String month = date.substring(4, 6);
        String day = date.length() > 6 ? date.substring(6, 8) : null;
        if (getResources().getString(R.string.time_picker_day).equals(hour)) {
            hour = null;
        }
        if (type.equals("城市")) {
            if (day != null) {
                if (hour != null) {
                    //小时
                    searchPresenter.searchCityHour(location, year + month + day, hour);
                } else {
                    //整天
                    searchPresenter.searchCityDay(location, year + month + day);
                }
            } else {
                //本月
                searchPresenter.searchCityMonth(location, year, month);
            }
        } else {
            if (date != null) {
                if (hour != null) {
                    //小时
                    searchPresenter.searchSite(location, year + month + day, hour);
                } else {
                    //整天
                    searchPresenter.searchSiteDay(location, year + month + day);
                }
            } else {
                //本月
                searchPresenter.searchSiteMonth(location, year, month);
            }
        }
    }

}
