package mvp.citydetail;

import com.android.volley.Response;

import net.GsonRequest;
import net.VolleyErrorListener;

import aqi.AQI;
import bean.aqi.CitySite;
import utils.AppContext;

/**
 * 获取指定城市内所有监测点的AQI
 */
public class SitesModel implements ICityDetail.IModel{
    @Override
    public void getSites(String city, final ICityDetail.OnGetResultListener<CitySite> onGetResult) {
        GsonRequest<CitySite> request = new GsonRequest<>(AQI.getCityAndSites(city), CitySite.class,
                new Response.Listener<CitySite>() {
                    @Override
                    public void onResponse(CitySite response) {
                        onGetResult.onGetResult(response);
                    }
                },new VolleyErrorListener());
        AppContext.addRequest(request);
    }
}
