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
import bean.card.CardAttrList;
import bean.heweather.forecast.ForecastItem;
import bean.heweather.forecast.ForecastList;
import bean.heweather.lifestyle.Item;
import bean.heweather.lifestyle.LifeStyle;
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


    Gson mGson = new Gson();
    private String jsonLifeStyle = "{\"HeWeather6\":[{\"basic\":{\"cid\":\"CN101280101\",\"location\":\"广州\",\"parent_city\":\"广州\",\"admin_area\":\"广东省\",\"cnty\":\"中国\",\"lat\":\"23.12517738\",\"lon\":\"113.28063965\",\"tz\":\"+8.00\"},\"update\":{\"loc\":\"2021-03-29 21:36\",\"utc\":\"2021-03-29 13:36\"},\"status\":\"ok\",\"lifestyle\":[{\"type\":\"comf\",\"brf\":\"不舒适\",\"txt\":\"白天多云，并且空气湿度偏大，在这种天气条件下，您会感到有些闷热，不舒适。\"},{\"type\":\"drsg\",\"brf\":\"热\",\"txt\":\"天气热，建议着短裙、短裤、短薄外套、T恤等夏季服装。\"},{\"type\":\"flu\",\"brf\":\"少发\",\"txt\":\"各项气象条件适宜，发生感冒机率较低。但请避免长期处于空调房间中，以防感冒。\"},{\"type\":\"sport\",\"brf\":\"较适宜\",\"txt\":\"天气较好，但因气温较高且风力较强，请适当降低运动强度并注意户外防风。推荐您进行室内运动。\"},{\"type\":\"trav\",\"brf\":\"适宜\",\"txt\":\"天气较好，温度稍高，幸好风稍大，会缓解稍热的天气。适宜旅游，可不要错过机会呦！\"},{\"type\":\"uv\",\"brf\":\"弱\",\"txt\":\"紫外线强度较弱，建议出门前涂擦SPF在12-15之间、PA+的防晒护肤品。\"},{\"type\":\"cw\",\"brf\":\"较适宜\",\"txt\":\"较适宜洗车，未来一天无雨，风力较小，擦洗一新的汽车至少能保持一天。\"},{\"type\":\"air\",\"brf\":\"良\",\"txt\":\"气象条件有利于空气污染物稀释、扩散和清除，可在室外正常活动。\"}]}]}";
    private String jsonWeatherNow = "{\"HeWeather6\":[{\"basic\":{\"cid\":\"CN101280101\",\"location\":\"广州\",\"parent_city\":\"广州\",\"admin_area\":\"广东省\",\"cnty\":\"中国\",\"lat\":\"23.12517738\",\"lon\":\"113.28063965\",\"tz\":\"+8.00\"},\"update\":{\"loc\":\"2021-03-29 21:52\",\"utc\":\"2021-03-29 13:52\"},\"status\":\"ok\",\"now\":{\"cloud\":\"91\",\"cond_code\":\"100\",\"cond_txt\":\"晴\",\"fl\":\"28\",\"hum\":\"81\",\"pcpn\":\"0.0\",\"pres\":\"1001\",\"tmp\":\"26\",\"vis\":\"16\",\"wind_deg\":\"180\",\"wind_dir\":\"南风\",\"wind_sc\":\"2\",\"wind_spd\":\"8\"}}]}";
    private String jsonWeatherForecast = "{\"HeWeather6\":[{\"basic\":{\"cid\":\"CN101280101\",\"location\":\"广州\",\"parent_city\":\"广州\",\"admin_area\":\"广东省\",\"cnty\":\"中国\",\"lat\":\"23.12517738\",\"lon\":\"113.28063965\",\"tz\":\"+8.00\"},\"update\":{\"loc\":\"2021-03-29 22:46\",\"utc\":\"2021-03-29 14:46\"},\"status\":\"ok\",\"daily_forecast\":[{\"cond_code_d\":\"104\",\"cond_code_n\":\"101\",\"cond_txt_d\":\"阴\",\"cond_txt_n\":\"多云\",\"date\":\"2021-03-29\",\"hum\":\"78\",\"mr\":\"19:20\",\"ms\":\"06:46\",\"pcpn\":\"0.0\",\"pop\":\"25\",\"pres\":\"1003\",\"sr\":\"06:20\",\"ss\":\"18:41\",\"tmp_max\":\"30\",\"tmp_min\":\"23\",\"uv_index\":\"3\",\"vis\":\"25\",\"wind_deg\":\"195\",\"wind_dir\":\"西南风\",\"wind_sc\":\"3-4\",\"wind_spd\":\"16\"},{\"cond_code_d\":\"101\",\"cond_code_n\":\"101\",\"cond_txt_d\":\"多云\",\"cond_txt_n\":\"多云\",\"date\":\"2021-03-30\",\"hum\":\"75\",\"mr\":\"20:24\",\"ms\":\"07:27\",\"pcpn\":\"0.0\",\"pop\":\"24\",\"pres\":\"1004\",\"sr\":\"06:19\",\"ss\":\"18:42\",\"tmp_max\":\"31\",\"tmp_min\":\"24\",\"uv_index\":\"10\",\"vis\":\"24\",\"wind_deg\":\"180\",\"wind_dir\":\"南风\",\"wind_sc\":\"3-4\",\"wind_spd\":\"16\"},{\"cond_code_d\":\"104\",\"cond_code_n\":\"104\",\"cond_txt_d\":\"阴\",\"cond_txt_n\":\"阴\",\"date\":\"2021-03-31\",\"hum\":\"76\",\"mr\":\"21:30\",\"ms\":\"08:09\",\"pcpn\":\"0.0\",\"pop\":\"25\",\"pres\":\"1005\",\"sr\":\"06:19\",\"ss\":\"18:42\",\"tmp_max\":\"29\",\"tmp_min\":\"24\",\"uv_index\":\"7\",\"vis\":\"24\",\"wind_deg\":\"180\",\"wind_dir\":\"南风\",\"wind_sc\":\"3-4\",\"wind_spd\":\"16\"}]}]}";



    public MainPreferencr(IContract.IView view, String city) {
        mView = view;
        this.city = city;
    }

    @Override
    public void loadAll() {
        //String json = AppPref.getCardList();
        /*String json = getFromFile();
        CardAttrList list = new Gson().fromJson(json, CardAttrList.class);
        for (CardAttr attr:
             list.getList()) {
            if (attr.getVisibility() == true) {
                load(attr.getTag());
            }
        }*/

        //不发起网络请求，直接用假数据
        loadStaticAqi();
        loadStaticWeather();
        loadStaticWeatherForecast();
        loadStaticLifestyle();
        loadStaticAqiChange();
    }

    /**
     * 实时AQI的假数据
     * */
    private void loadStaticAqi() {
        City city = new City();
        city.setO3(9);
        city.setNo2(68);
        city.setCo(2);
        city.setSo2(12);
        city.setPm10(254);
        city.setPm2_5(108);
        city.setAqi(152);

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

    private void loadStaticWeatherForecast() {
        ForecastList forecastList = mGson.fromJson(jsonWeatherForecast, ForecastList.class);
        List<ForecastItem> list = forecastList.getHeWeather6().get(0).getDaily_forecast();
        mView.setWeatherForecast(list);
    }

    private void loadStaticWeather() {

        HeWeather6 weather6 = mGson.fromJson(jsonWeatherNow, HeWeather6.class);
        Now now = weather6.getHeWeather6().get(0).getNow();

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

    private void loadStaticLifestyle() {
        LifeStyle ls = mGson.fromJson(jsonLifeStyle, LifeStyle.class);

        List<Item> list = ls.getHeWeather6().get(0).getLifestyle();

        ArrayMap<String, String> data = handleDataList(list);
        mView.setLifeStyle(data);
    }

    private void loadStaticAqiChange() {
        List<City> list = new ArrayList<>();

        float aqis[] = new float[]{79f, 53f, 53f, 53f, 51f, 52f, 51f, 52f, 51f, 51f, 50f, 51f, 58f, 60f, 62f, 65f, 67f, 68f, 67f, 69f, 68f};
        for (int i = 0; i < aqis.length; i++) {
            City city = new City(aqis[i]);
            list.add(city);
        }
        ArrayMap<String, Integer> map = new ArrayMap<>();
        for (int i = 0; i < list.size(); i++) {
            map.put(i + "", (int) list.get(i).getAqi());
        }
        mView.setAQIChange(map);
    }




    /*private String getFromFile() {
        StringBuilder sb = new StringBuilder();
        InputStream is = null;
        BufferedReader br = null;
        try {
            is = AppContext.getAppContext().getResources().getAssets().open("city&date");
            br = new BufferedReader(new InputStreamReader(is));
            String line;
            while ((line = br.readLine()) != "") {
                sb.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            CloseUtil.close(is);
            CloseUtil.close(br);
        }
        return sb.toString();
    }*/

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
