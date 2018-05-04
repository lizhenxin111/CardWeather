package mvp.search.SiteModel;

import com.android.volley.Response;

import net.GsonRequest;
import net.VolleyErrorListener;

import aqi.AQI;
import bean.aqi.SiteList;
import mvp.search.ISearchContract;
import utils.AppContext;

public class SearchSiteDayModel implements ISearchContract.IModel<SiteList> {
    @Override
    public void requestData(String city, String date, final ISearchContract.OnGetResultListener<SiteList> onGetResultListener) {
        GsonRequest<SiteList> request = new GsonRequest<>(AQI.getSiteDay(city, date), SiteList.class,
                new Response.Listener<SiteList>() {
                    @Override
                    public void onResponse(SiteList response) {
                        onGetResultListener.onGetResult(response);
                    }
                },new VolleyErrorListener());
        AppContext.addRequest(request);
    }
}
