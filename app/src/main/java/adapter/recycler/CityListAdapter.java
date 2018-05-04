package adapter.recycler;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lzx.cardweather.R;

import java.util.ArrayList;
import java.util.List;

import bean.aqi.City;
import listener.ReleaseDataListener;
import ui.card.CardAQI;

public class CityListAdapter extends RecyclerView.Adapter<CityListAdapter.CityHolder> implements ReleaseDataListener {

    private List<City> list = new ArrayList<>();

    public CityListAdapter(List<City> list) {
        this.list = list;
    }

    @Override
    public CityHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_aqi, parent, false);
        return new CityHolder(v);
    }

    @Override
    public void onBindViewHolder(CityHolder holder, int position) {
        CardAQI aqi = holder.aqi;
        City c = list.get(position);
        aqi.setTitle(c.getCity() + "  " + c.getDate() + " " + c.getHour());
        aqi.setAqi((int) c.getAqi());
        int items[] = new int[]{
                (int) (c.getO3() /2),
                (int) (c.getNo2()/2),
                (int) c.getCo(),
                (int) (c.getSo2()/2),
                (int) (c.getPm10()/2),
                (int) (c.getPm2_5()/2)
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

    static class CityHolder extends RecyclerView.ViewHolder {
        CardAQI aqi;
        public CityHolder(View itemView) {
            super(itemView);
            aqi = itemView.findViewById(R.id.item_aqi);
        }
    }
}
