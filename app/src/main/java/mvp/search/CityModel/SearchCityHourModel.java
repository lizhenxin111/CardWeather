package mvp.search.CityModel;

import com.android.volley.Response;

import net.GsonRequest;
import net.VolleyErrorListener;

import java.util.ArrayList;
import java.util.List;

import aqi.AQI;
import bean.aqi.City;
import bean.aqi.CityList;
import mvp.search.ISearchContract;
import utils.AppContext;

/**
 * 以小时为单位查询城市
 */
public class SearchCityHourModel implements ISearchContract.IModel<CityList> {
    @Override
    public void requestData(String city, String date, final ISearchContract.OnGetResultListener<CityList> onGetResultListener) {
        String d = date.substring(0, 8);
        String h = date.substring(8, date.length());
        GsonRequest<City> request = new GsonRequest<>(AQI.getCityHour(city, d, h), City.class,
                new Response.Listener<City>() {
                    @Override
                    public void onResponse(City response) {
                        List<City> list = new ArrayList<>();
                        list.add(response);
                        CityList cityList = new CityList();
                        cityList.setList(list);
                        onGetResultListener.onGetResult(cityList);
                    }
                },new VolleyErrorListener());
        AppContext.addRequest(request);
    }
}
