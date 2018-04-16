package mvp.mainpage;

import android.support.v4.util.ArrayMap;

import java.util.List;

import bean.aqi.Site;
import bean.card.CardAttrList;

public interface IContract {
    interface IView{
        void setAQI(int aqi, int[] data);
        void setWeather(String tmp, String weather, String[] data);
        void setAQIChange(ArrayMap<Integer, Integer> list);
        void addSites(List<Site> list);

        void setOrder(CardAttrList list);
    }
    interface IPresenter{
        void loadAll(String city);
        void changeOrder();
        void load(String tag, String city);
        void loadSites(String city);
    }
}
