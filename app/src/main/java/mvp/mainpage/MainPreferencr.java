package mvp.mainpage;

import android.support.v4.util.ArrayMap;

import com.android.volley.Response;
import com.google.gson.Gson;
import com.orhanobut.logger.Logger;

import net.GsonRequest;

import java.util.ArrayList;
import java.util.List;

import aqi.AQI;
import aqi.HeWeather;
import bean.aqi.City;
import bean.aqi.CityList;
import bean.card.CardAttr;
import bean.card.CardAttrList;
import bean.heweather.forecast.ForecastItem;
import bean.heweather.lifestyle.Item;
import bean.heweather.weathernow.HeWeather6;
import bean.heweather.weathernow.Now;
import bean.heweather.weathernow.Weather;
import mvp.mainpage.model.CityNowModel;
import mvp.mainpage.model.ForecastModel;
import mvp.mainpage.model.LifeStyleModel;
import ui.card.BaseCard;
import utils.AppContext;
import utils.AppPref;
import utils.DateUtil;

public class MainPreferencr implements IContract.IPresenter {

    private String city = null;

    private IContract.IView mView = null;
    private IContract.IModel cityNowModel = null;



    public MainPreferencr(IContract.IView view, String city) {
        mView = view;
        this.city = city;
    }

    @Override
    public void loadAll() {
        String json = AppPref.getCardList();
        CardAttrList list = new Gson().fromJson(json, CardAttrList.class);
        for (CardAttr attr:
             list.getList()) {
            if (attr.getVisibility() == true) {
                load(attr.getTag());
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
    public void load(String tag)    {
        Logger.d("load");
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
            case BaseCard.TAG_WEATHERFORECAST:
                loadWeatherForecast(city);
                break;
        }
    }

    private void loadAqi(String city) {
        cityNowModel = new CityNowModel();
        cityNowModel.requestData(city, new IContract.OnGetResultListener<City>() {
            @Override
            public void onGetResult(City city) {
                int[] list = new int[]{
                        (int) (city.getO3() /2),
                        (int) (city.getNo2()/2),
                        (int) city.getCo(),
                        (int) (city.getSo2()/2),
                        (int) (city.getPm10()/2),
                        (int) (city.getPm2_5()/2)
                };
                mView.setAQI((int) city.getAqi(), list);
            }
        });
    }

    private void loadWeatherForecast(String city) {
        ForecastModel model = new ForecastModel();
        model.requestData(city, new IContract.OnGetResultListener<ArrayList<ForecastItem>>() {
            @Override
            public void onGetResult(ArrayList<ForecastItem> list) {
                mView.setWeatherForecast(list);
            }
        });
    }

    private void loadWeather(String city) {
        GsonRequest<HeWeather6> request = new GsonRequest<>(HeWeather.getWeatherNow(city), HeWeather6.class,
                new Response.Listener<HeWeather6>() {
                    @Override
                    public void onResponse(HeWeather6 response) {
                        Weather weather = response.getHeWeather6().get(0);
                        Now now = weather.getNow();
                        String[] list = new String[]{
                                now.getVis() + " m",
                                now.getPres() + " Pa",
                                now.getPcpn() + " mm",
                                now.getHum() + "%",
                                now.getWind_dir() + " " + now.getWind_sc() + " km/h",
                                now.getFl() + "℃"
                        };
                        mView.setWeather(now.getTmp(), now.getCond_txt(), now.getCond_code(), list);
                    }
                }, null);
        AppContext.addRequest(request);
        loadLifeStyle(city);
    }

    private void loadLifeStyle(String city) {
        LifeStyleModel model = new LifeStyleModel();
        model.requestData(city, new IContract.OnGetResultListener<List<Item>>() {
            @Override
            public void onGetResult(List<Item> list) {
                //空气、洗车、旅游、流感、运动、紫外线、穿衣指数。舒适度
                ArrayMap<String, String> data = handleDataList(list);
                mView.setLifeStyle(data);
            }
        });
    }

    private ArrayMap<String, String> handleDataList(List<Item> list) {
        ArrayMap<String, String> data = new ArrayMap<>();
        for (int i = 0; i < 8; i++) {
            String type = getType(i);
            for (int j = 0; j < list.size(); j++) {
                if (list.get(j).getType().equals(type)) {
                    data.put(list.get(j).getBrf(), list.get(j).getTxt());
                    list.remove(j);
                }
            }
        }
        return data;
    }

    //空气、洗车、旅游、流感、运动、紫外线、穿衣指数。舒适度
    private String getType(int i) {
        switch (i) {
            case 0: return "air";
            case 1: return "cw";
            case 2: return "trav";
            case 3: return "flu";
            case 4: return "sport";
            case 5: return "uv";
            case 6: return "drsg";
            case 7: return "comf";
        }
        return null;
    }

    private void loadAqiChange(String city) {
        GsonRequest<CityList> request = new GsonRequest<>(AQI.getCityDay(city, DateUtil.getDate()), CityList.class,
                new Response.Listener<CityList>() {
                    @Override
                    public void onResponse(CityList response) {
                        List<City> list = response.getList();
                        ArrayMap<String, Integer> map = new ArrayMap<>();
                        for (int i = 0; i < list.size(); i++) {
                            map.put(i + "", (int) list.get(i).getAqi());
                        }
                        mView.setAQIChange(map);
                    }
                }, null);
        AppContext.addRequest(request);
    }
}
