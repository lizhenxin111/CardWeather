package mvp.citydetail;

import bean.aqi.CitySite;

public class SitesPresenter implements ICityDetail.IPresenter{

    private String city;

    private ICityDetail.IModel iModel = null;
    private ICityDetail.IView mView = null;

    public SitesPresenter(ICityDetail.IView mView, String city) {
        this.mView = mView;
        this.city = city;
    }

    @Override
    public void loadSites() {
        mView.showPopupView();
        mView.showLoading();

        iModel = new SitesModel();
        iModel.getSites(city, new ICityDetail.OnGetResultListener<CitySite>() {
            @Override
            public void onGetResult(CitySite siteList) {
                mView.showSites(siteList.getSites());
                mView.dismissLoading();
            }
        });
    }

    @Override
    public void dismiss() {
        mView.dismiss();
    }
}
