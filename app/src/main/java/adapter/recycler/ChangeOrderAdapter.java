package adapter.recycler;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

import com.lzx.cardweather.R;

import java.util.ArrayList;
import java.util.List;

import bean.card.CardAttr;

public class ChangeOrderAdapter extends RecyclerView.Adapter<ChangeOrderAdapter.Holder> {

    private List<CardAttr> list = new ArrayList<>();

    public ChangeOrderAdapter() {
    }

    public void requestData(List<CardAttr> list) {
        this.list = list;
        notifyDataSetChanged();
    }
    public List<CardAttr> getList() {
        return list;
    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_order_item, parent, false);
        return new Holder(v);
    }

    @Override
    public void onBindViewHolder(final Holder holder, final int position) {
        CardAttr attr = list.get(position);
        holder.title.setText(attr.getTag());
        holder.aSwitch.setChecked(attr.getVisibility());
        holder.aSwitch.setTag(attr.getTag());
        holder.aSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                onSwitchCheckChange((String) holder.aSwitch.getTag());
            }
        });
    }

    private void onSwitchCheckChange(String tag) {
        for (CardAttr attr :
                list) {
            if (attr.getTag().equals(tag)) {
                attr.setVisibility(!attr.getVisibility());
            }
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    static class Holder extends RecyclerView.ViewHolder{
        TextView title;
        Switch aSwitch;
        public Holder(View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.fco_item_title);
            aSwitch = itemView.findViewById(R.id.fco_item_switch);
        }
    }
}
