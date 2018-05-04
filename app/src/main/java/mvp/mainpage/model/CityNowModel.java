package mvp.mainpage.model;

import com.android.volley.Response;

import net.GsonRequest;
import net.VolleyErrorListener;

import aqi.AQI;
import bean.aqi.City;
import mvp.mainpage.IContract;
import utils.AppContext;

/**
 * 站点当前
 */
public class CityNowModel implements IContract.IModel {
    @Override
    public void requestData(String city, final IContract.OnGetResultListener onGetResultListener) {
        GsonRequest<City> request = new GsonRequest<>(AQI.getCityNow(city), City.class,
                new Response.Listener<City>() {
                    @Override
                    public void onResponse(City response) {
                        onGetResultListener.onGetResult(response);
                    }
                },new VolleyErrorListener());
        AppContext.addRequest(request);
    }
}
