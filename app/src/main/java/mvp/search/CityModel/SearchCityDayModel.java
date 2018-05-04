package mvp.search.CityModel;


import com.android.volley.Response;

import net.GsonRequest;
import net.VolleyErrorListener;

import aqi.AQI;
import bean.aqi.CityList;
import mvp.search.ISearchContract;
import utils.AppContext;

/**
 * 以天为单位查询城市
 */
public class SearchCityDayModel implements ISearchContract.IModel<CityList> {
    @Override
    public void requestData(String city, String date,
                            final ISearchContract.OnGetResultListener<CityList> onGetResultListener) {

        GsonRequest<CityList> request = new GsonRequest<>(AQI.getCityDay(city, date), CityList.class,
                new Response.Listener<CityList>() {
                    @Override
                    public void onResponse(CityList response) {
                        onGetResultListener.onGetResult(response);
                    }
                },new VolleyErrorListener());
        AppContext.addRequest(request);
    }
}
