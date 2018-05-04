package mvp.compare;

import android.content.Context;
import android.support.v4.util.ArrayMap;
import android.view.ViewGroup;

import java.util.List;

import bean.aqi.City;
import bean.aqi.Site;
import mvp.search.ISearchContract;
import ui.card.CardLineChart;

/**
 * 展示城市对比的结果的view
 * 内部持有一个CardLineChart，获取到结果后传入card中并将card添加至container
 */
public class CompareItemView implements ISearchContract.ISearchView<CardLineChart> {

    private CardLineChart chart;

    @Override
    public void init(Context context) {
        chart = new CardLineChart(context);
        ViewGroup.LayoutParams lp = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        chart.setLayoutParams(lp);
        chart.setxUnit("天");
        chart.setyUnit("pg/m3");
        chart.setTitle("空气质量");
    }

    @Override
    public void setCityData(List<City> list) {
        ArrayMap<String, Integer> map = new ArrayMap();
        for (City c :
                list) {
            map.put(c.getDate() + "\n" + c.getHour(), (int) c.getAqi());
        }
        chart.requestData(map);
    }

    @Override
    public void setSiteList(List<Site> list) {
        ArrayMap<String, Integer> map = new ArrayMap();
        for (Site s :
                list) {
            map.put(s.getDate() + "\n" + s.getHour(), (int) s.getAqi());
        }
        chart.requestData(map);
    }

    @Override
    public void setCondition(String condition) {
        chart.setTitle(condition);
    }

    @Override
    public CardLineChart getView() {
        return chart;
    }

   /* @Override
    public void releaseData() {
        if (chart != null) {
            chart.releaseData();
        }
    }*/
}
