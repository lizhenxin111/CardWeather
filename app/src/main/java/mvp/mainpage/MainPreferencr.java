package mvp.mainpage;

import android.support.v4.util.ArrayMap;

import com.google.gson.Gson;
import com.orhanobut.logger.Logger;

import net.listener.StringListener;
import net.queue.RequestQueue;
import net.request.StringRequest;

import java.util.List;

import aqi.AQI;
import aqi.HeWeather;
import bean.aqi.City;
import bean.aqi.CityList;
import bean.card.CardAttr;
import bean.card.CardAttrList;
import bean.heweather.weathernow.HeWeather6;
import bean.heweather.weathernow.Now;
import bean.heweather.weathernow.Weather;
import ui.card.BaseCard;
import utils.AppPref;
import utils.DateUtil;

public class MainPreferencr implements IContract.IPresenter {

    private IContract.IView mView = null;
    private RequestQueue mQueue = null;

    public MainPreferencr(IContract.IView view) {
        mView = view;
        mQueue = new RequestQueue();
    }

    @Override
    public void loadAll(String city) {
        String json = AppPref.getCardList();
        CardAttrList list = new Gson().fromJson(json, CardAttrList.class);
        for (CardAttr attr:
             list.getList()) {
            if (attr.getVisibility() == true) {
                load(attr.getTag(), city);
            }
        }
    }

    @Override
    public void changeOrder() {
        String json = AppPref.getCardList();
        CardAttrList list = new Gson().fromJson(json, CardAttrList.class);
        mView.setOrder(list);
    }

    @Override
    public void load(String tag, String city) {
        switch (tag) {
            case BaseCard.TAG_AQI:
                loadAqi(city);
                break;
            case BaseCard.TAG_WEATHER:
                loadWeather(city);
                break;
            case BaseCard.TAG_AQICHANGE:
                loadAqiChange(city);
                break;
        }
    }

    @Override
    public void loadSites(String city) {
        StringRequest req = new StringRequest("", new StringListener() {
            @Override
            public void onSuccess(String result) {

            }
            @Override
            public void onFailed(Object o) {

            }
        });
        mQueue.execute(req);
    }

    private void loadAqi(String city) {
        StringRequest aqiRequest = new StringRequest(AQI.getCityNow(city), AQI.getCityNow(city) + DateUtil.getApiHour(), new StringListener() {
            @Override
            public void onSuccess(String result) {
                City c = new Gson().fromJson(result, City.class);
                int[] list = new int[]{
                        (int) (c.getO3() /2),
                        (int) (c.getNo2()/2),
                        (int) c.getCo(),
                        (int) (c.getSo2()/2),
                        (int) (c.getPm10()/2),
                        (int) (c.getPm2_5()/2)
                };
                mView.setAQI((int) c.getAqi(), list);
            }

            @Override
            public void onFailed(Object o) {

            }
        });
        mQueue.execute(aqiRequest);
    }

    private void loadWeather(String city) {
        StringRequest weatherRequest = new StringRequest(HeWeather.getWeatherNow(city), HeWeather.getWeatherNow(city) + DateUtil.getHourString(), new StringListener() {
            @Override
            public void onSuccess(String result) {
                //Logger.json(result);
                HeWeather6 weather6 = new Gson().fromJson(result, HeWeather6.class);
                Weather weather = weather6.getHeWeather6().get(0);
                Now now = weather.getNow();
                String[] list = new String[]{
                        now.getVis() + " m",
                        now.getPres() + " Pa",
                        now.getPcpn() + " mm",
                        now.getHum() + "%",
                        now.getWind_dir() + " " + now.getWind_sc() + " km/h",
                        now.getFl() + "℃"
                };
                //Logger.d(now.getTmp() + "  " + now.getCond_txt());
                mView.setWeather(now.getTmp(), now.getCond_txt(), list);
            }

            @Override
            public void onFailed(Object o) {

            }
        });
        mQueue.execute(weatherRequest);
    }

    private void loadAqiChange(String city) {
        StringRequest aqiChangeRequest = new StringRequest(AQI.getCityDay(city, DateUtil.getDate()), AQI.getCityDay(city, DateUtil.getDate()) + DateUtil.getApiHour(), new StringListener() {
            @Override
            public void onSuccess(String result) {
                CityList cityList = new Gson().fromJson(result, CityList.class);
                List<City> list = cityList.getList();
                ArrayMap<Integer, Integer> map = new ArrayMap<>();
                for (int i = 0; i < list.size(); i++) {
                    map.put(i, (int) list.get(i).getAqi());
                }
                mView.setAQIChange(map);
            }

            @Override
            public void onFailed(Object o) {

            }
        });
        mQueue.execute(aqiChangeRequest);
    }
}
