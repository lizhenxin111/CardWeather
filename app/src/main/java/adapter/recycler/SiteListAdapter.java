package adapter.recycler;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lzx.cardweather.R;

import java.util.ArrayList;
import java.util.List;

import bean.aqi.Site;
import listener.ReleaseDataListener;
import ui.card.CardAQI;

public class SiteListAdapter extends RecyclerView.Adapter<SiteListAdapter.SiteHolder> implements ReleaseDataListener{

    private List<Site> list = new ArrayList<>();

    public SiteListAdapter(List<Site> list) {
        this.list = list;
    }

    @Override
    public SiteHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_aqi, parent, false);
        return new SiteHolder(v);
    }

    @Override
    public void onBindViewHolder(SiteHolder holder, int position) {
        CardAQI aqi = holder.aqi;
        Site site = list.get(position);
        aqi.setTitle(site.getSite() + " " + site.getDate() + " " + site.getHour());
        aqi.setAqi((int) site.getAqi());
        int items[] = new int[]{
                (int) (site.getO3() /2),
                (int) (site.getNo2()/2),
                (int) site.getCo(),
                (int) (site.getSo2()/2),
                (int) (site.getPm10()/2),
                (int) (site.getPm2_5()/2)
        };
        aqi.setItems(items);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    @Override
    public void releaseData() {
        list.clear();
    }

    static class SiteHolder extends RecyclerView.ViewHolder {
        CardAQI aqi;
        public SiteHolder(View itemView) {
            super(itemView);
            aqi = itemView.findViewById(R.id.item_aqi);
        }
    }
}
