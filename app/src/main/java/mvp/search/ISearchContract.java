package mvp.search;

import bean.aqi.CityList;
import bean.aqi.SiteList;

public interface ISearchContract {
    interface ISearchView {
        void addCityAqis(CityList cityList);
        void addSiteAqis(SiteList siteList);
    }

    interface ISearchPresenter {
        void searchCity(String city, String date, String hour);
        void searchCityTimeRange(String city, String fromDate, String fromHour, String toDate, String toHour);

        void searchSite(String site, String date, String hour);
        void searchSiteTimeRange(String site, String fromDate, String fromHour, String toDate, String toHour);
    }
}
