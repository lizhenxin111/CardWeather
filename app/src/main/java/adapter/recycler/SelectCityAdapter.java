package adapter.recycler;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import com.lzx.cardweather.R;

import java.util.ArrayList;
import java.util.List;

import adapter.recycler.SelectCityAdapter.SelectCityHolder;
import listener.OnListClickListener;

public class SelectCityAdapter extends RecyclerView.Adapter<SelectCityHolder> implements Filterable{

    private List<String> mList = new ArrayList<>();
    private List<String> selectedList = new ArrayList<>();

    private OnListClickListener mOnListClickListener = null;

    public SelectCityAdapter(List<String> list) {
        mList = list;
        selectedList = list;
    }

    public void setOnListClickListener(OnListClickListener onListClickListener) {
        mOnListClickListener = onListClickListener;
    }

    @Override
    public SelectCityHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_single_text, parent,false);
        return new SelectCityHolder(v);
    }

    @Override
    public void onBindViewHolder(SelectCityHolder holder, final int position) {
        holder.text.setText(selectedList.get(position));
        if (mOnListClickListener != null) {
            holder.text.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mOnListClickListener.onClick(view, selectedList.get(position));
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return selectedList.size();
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                if (charSequence.length() == 0) {       //过滤内容为空
                    selectedList = mList;
                } else {                                //有过滤内容
                    ArrayList<String> list = new ArrayList<>();
                    for (String content : mList) {
                        if (content.contains(String.valueOf(charSequence))) {
                            list.add(content);
                        }
                    }
                    selectedList = list;
                }
                FilterResults fr = new FilterResults();
                fr.values = selectedList;
                return fr;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                selectedList = (List<String>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }

    static class SelectCityHolder extends RecyclerView.ViewHolder{
        TextView text;
        public SelectCityHolder(View itemView) {
            super(itemView);
            text = itemView.findViewById(R.id.ist_textview);
        }
    }
}
