package mvp.mainpage.model;

import com.android.volley.Response;

import net.GsonRequest;
import net.VolleyErrorListener;

import aqi.HeWeather;
import bean.heweather.lifestyle.LifeStyle;
import mvp.mainpage.IContract;
import utils.AppContext;

/**
 * 生活指数
 */
public class LifeStyleModel implements IContract.IModel {
    @Override
    public void requestData(String city, final IContract.OnGetResultListener onGetResultListener) {
        GsonRequest<LifeStyle> request = new GsonRequest<>(HeWeather.getLifeStyle(city), LifeStyle.class,
                new Response.Listener<LifeStyle>() {
                    @Override
                    public void onResponse(LifeStyle response) {
                        onGetResultListener.onGetResult(response.getHeWeather6().get(0).getLifestyle());
                    }
                },new VolleyErrorListener());
        AppContext.addRequest(request);
    }

}
