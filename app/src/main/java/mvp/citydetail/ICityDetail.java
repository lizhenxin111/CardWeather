package mvp.citydetail;

import java.util.List;

import bean.aqi.CitySite;
import bean.aqi.Site;

public interface ICityDetail {
    interface IView {
        void showPopupView();
        void showLoading();
        void dismissLoading();
        void showSites(List<Site> list);
        void dismiss();
    }

    interface IPresenter {
        void loadSites();
        void dismiss();
    }

    interface IModel {
        void getSites(String city, OnGetResultListener<CitySite> listener);
    }

    interface OnGetResultListener<T> {
        void onGetResult(T t);
    }
}
