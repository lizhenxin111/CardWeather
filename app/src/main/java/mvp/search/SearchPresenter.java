package mvp.search;

import android.content.Context;

import bean.aqi.CityList;
import bean.aqi.SiteList;
import mvp.search.CityModel.SearchCityDayModel;
import mvp.search.CityModel.SearchCityHourModel;
import mvp.search.CityModel.SearchCityMonthModel;
import mvp.search.SiteModel.SearchSiteDayModel;
import mvp.search.SiteModel.SearchSiteHourModel;
import mvp.search.SiteModel.SearchSiteMonthModel;

public class SearchPresenter implements ISearchContract.ISearchPresenter {

    private ISearchContract.ISearchView view;

    private Context context;
    private OnSearchCompleteListener onSearchCompleteListener;

    public SearchPresenter(Context context,
                           OnSearchCompleteListener onSearchCompleteListener,
                           ISearchContract.ISearchView view) {
        this.context = context;
        this.onSearchCompleteListener = onSearchCompleteListener;
        this.view = view;
    }

    @Override
    public void searchCityHour(final String city, final String date, final String hour) {
        SearchCityHourModel model = new SearchCityHourModel();
        model.requestData(city, date + hour, new ISearchContract.OnGetResultListener<CityList>() {
            @Override
            public void onGetResult(CityList cityList) {
                view.setCityData(cityList.getList());
                view.setCondition(city + " " + date + " " + hour);
                onSearchCompleteListener.onSearchComplete();
            }
        });
    }

    @Override
    public void searchCityDay(final String city, final String date) {
        SearchCityDayModel model = new SearchCityDayModel();
        model.requestData(city, date, new ISearchContract.OnGetResultListener<CityList>() {
            @Override
            public void onGetResult(CityList cityList) {
                view.setCityData(cityList.getList());
                view.setCondition(city + " " + date);
                onSearchCompleteListener.onSearchComplete();
            }
        });
    }

    @Override
    public void searchCityMonth(final String city, final String year, final String month) {
        SearchCityMonthModel model = new SearchCityMonthModel();
        model.requestData(city, year + month, new ISearchContract.OnGetResultListener<CityList>() {
            @Override
            public void onGetResult(CityList cityList) {
                view.setCityData(cityList.getList());
                view.setCondition(city + " " + year + " " + month);
                onSearchCompleteListener.onSearchComplete();
            }
        });
    }

    @Override
    public void searchSite(final String site, final String date, final String hour) {
        SearchSiteHourModel model = new SearchSiteHourModel();
        model.requestData(site, date + hour, new ISearchContract.OnGetResultListener<SiteList>() {
            @Override
            public void onGetResult(SiteList siteList) {
                view.setSiteList(siteList.getList());
                view.setCondition(site + " " + date + " " + hour);
                onSearchCompleteListener.onSearchComplete();
            }
        });
    }

    @Override
    public void searchSiteDay(final String site, final String date) {
        SearchSiteDayModel model = new SearchSiteDayModel();
        model.requestData(site, date, new ISearchContract.OnGetResultListener<SiteList>() {
            @Override
            public void onGetResult(SiteList siteList) {
                view.setSiteList(siteList.getList());
                view.setCondition(site + " " + date);
                onSearchCompleteListener.onSearchComplete();
            }
        });
    }

    @Override
    public void searchSiteMonth(final String site, final String year, final String month) {
        SearchSiteMonthModel model = new SearchSiteMonthModel();
        model.requestData(site, year + month, new ISearchContract.OnGetResultListener<SiteList>() {
            @Override
            public void onGetResult(SiteList siteList) {
                view.setSiteList(siteList.getList());
                view.setCondition(site + " " + year + " " + month);
                onSearchCompleteListener.onSearchComplete();
            }
        });
    }

    public interface OnSearchCompleteListener {
        void onSearchComplete();
    }
}
