package mvp.search.SiteModel;

import com.android.volley.Response;

import net.GsonRequest;
import net.VolleyErrorListener;

import java.util.ArrayList;
import java.util.List;

import aqi.AQI;
import bean.aqi.Site;
import bean.aqi.SiteList;
import mvp.search.ISearchContract;
import utils.AppContext;

public class SearchSiteHourModel implements ISearchContract.IModel<SiteList> {
    @Override
    public void requestData(String city, String date, final ISearchContract.OnGetResultListener<SiteList> onGetResultListener) {
        String d = date.substring(0, 8);
        String h = date.substring(8, date.length());
        GsonRequest<Site> request = new GsonRequest<>(AQI.getSiteHour(city, d, h), Site.class,
                new Response.Listener<Site>() {
                    @Override
                    public void onResponse(Site response) {
                        List<Site> list = new ArrayList<>();
                        list.add(response);
                        SiteList sityList = new SiteList();
                        sityList.setList(list);
                        onGetResultListener.onGetResult(sityList);
                    }
                },new VolleyErrorListener());
        AppContext.addRequest(request);
    }
}
