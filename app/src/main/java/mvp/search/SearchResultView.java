package mvp.search;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import java.util.List;

import adapter.recycler.CityListAdapter;
import adapter.recycler.SiteListAdapter;
import bean.aqi.City;
import bean.aqi.Site;

public class SearchResultView implements ISearchContract.ISearchView<RecyclerView> {
    private RecyclerView view = null;
    private RecyclerView.Adapter adapter = null;

    @Override
    public void init(Context context) {
        view = new RecyclerView(context);
        ViewGroup.LayoutParams lp = new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        view.setLayoutParams(lp);
        view.setLayoutManager(new LinearLayoutManager(context));
    }

    @Override
    public void setCityData(List<City> list) {
        if (list !=null && list.size() != 0) {
            adapter = new CityListAdapter(list);
            view.setAdapter(adapter);
        }
    }

    @Override
    public void setSiteList(List<Site> list) {
        adapter = new SiteListAdapter(list);
        view.setAdapter(adapter);
    }

    @Override
    public void setCondition(String condition) {

    }

    @Override
    public RecyclerView getView() {
        return view;
    }

}
