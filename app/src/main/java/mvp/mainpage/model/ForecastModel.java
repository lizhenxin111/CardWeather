package mvp.mainpage.model;

import com.android.volley.Response;

import net.GsonRequest;
import net.VolleyErrorListener;

import aqi.HeWeather;
import bean.heweather.forecast.ForecastList;
import mvp.mainpage.IContract;
import utils.AppContext;

/**
 * 天气预测
 */
public class ForecastModel implements IContract.IModel {
    @Override
    public void requestData(String city, final IContract.OnGetResultListener onGetResultListener) {
        GsonRequest<ForecastList> request = new GsonRequest<>(HeWeather.getForecast(city), ForecastList.class,
                new Response.Listener<ForecastList>() {
                    @Override
                    public void onResponse(ForecastList response) {
                        onGetResultListener.onGetResult(response.getHeWeather6().get(0).getDaily_forecast());
                    }
                },new VolleyErrorListener());
        AppContext.addRequest(request);
    }
}
