package mvp.search;

import android.content.Context;
import android.view.View;

import java.util.List;

import bean.aqi.City;
import bean.aqi.Site;

public interface ISearchContract {

    interface ISearchView<T extends View> {
        //初始化View视图
        void init(Context context);
        //返回View实例
        T getView();
        //设置城市数据
        void setCityData(List<City> list);
        //设置站点数据
        void setSiteList(List<Site> list);
        //设置搜索条件
        void setCondition(String condition);
    }

    interface ISearchPresenter {
        //以小时为单位搜索城市数据
        void searchCityHour(String city, String date, String hour);
        //以日期为单位搜索城市数据
        void searchCityDay(String city, String date);
        //以月份为单位搜索城市数据
        void searchCityMonth(String city, String year, String month);

        void searchSite(String site, String date, String hour);
        void searchSiteDay(String site, String date);
        void searchSiteMonth(String site, String year, String month);
    }

    interface IModel<T> {
        //发起网络请求
        void requestData(String city, String date,
                     OnGetResultListener<T> onGetResultListener);
    }

    /**
     * 得到数据后的回调
     * @param <T>
     */
    interface OnGetResultListener<T> {
        void onGetResult(T t);
    }
}
