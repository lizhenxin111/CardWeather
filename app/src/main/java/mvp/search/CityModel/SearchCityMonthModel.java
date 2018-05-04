package mvp.search.CityModel;

import com.android.volley.Response;

import net.GsonRequest;
import net.VolleyErrorListener;

import aqi.AQI;
import bean.aqi.CityList;
import mvp.search.ISearchContract;
import utils.AppContext;

/**
 * 以月为单位查询城市
 */
public class SearchCityMonthModel implements ISearchContract.IModel<CityList> {

    @Override
    public void requestData(String city, String date, final ISearchContract.OnGetResultListener<CityList> onGetResultListener) {
        String year = date.substring(0, 4);
        String month = date.substring(4, date.length());
        GsonRequest<CityList> request = new GsonRequest<>(AQI.getCityMonth(city, year, month), CityList.class,
                new Response.Listener<CityList>() {
                    @Override
                    public void onResponse(CityList response) {
                        onGetResultListener.onGetResult(response);
                    }
                },new VolleyErrorListener());
        AppContext.addRequest(request);
    }
}
