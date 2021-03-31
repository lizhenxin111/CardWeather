package mvp.mainpage;

import android.support.v4.util.ArrayMap;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import bean.card.CardAttrList;
import bean.heweather.forecast.ForecastItem;

public interface IContract {
    interface IView{
        void setAQI(int aqi, int[] data);
        void setAQIChange(ArrayMap<String, Integer> list);      //今日变化
        void setWeather(String tmp, String weather, String weatherCode, String[] data);
        void setWeatherForecast(List<ForecastItem> list);
        void setLifeStyle(ArrayMap<String, String> data);        //生活指数

        void setOrder(CardAttrList list);

        void addLoading(ViewGroup parent);
        void removeLoading(ViewGroup parent);
    }

    interface IPresenter{
        void loadAll();
        void changeOrder();
        void load(String tag);
    }

    interface IModel {
        void requestData(String city, OnGetResultListener<?> onGetResultListener);
    }

    interface OnGetResultListener<T> {
        void onGetResult(T t);
    }
}
