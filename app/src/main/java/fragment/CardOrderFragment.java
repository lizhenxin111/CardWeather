package fragment;

import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;

import com.google.gson.Gson;
import com.lzx.cardweather.R;
import com.orhanobut.logger.Logger;

import java.util.Collections;
import java.util.List;

import adapter.recycler.ChangeOrderAdapter;
import base.BaseFragment;
import bean.card.CardAttr;
import bean.card.CardAttrList;
import utils.AppPref;

public class CardOrderFragment extends BaseFragment {

    private RecyclerView recyclerView;
    private ChangeOrderAdapter adapter;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_card_order;
    }

    @Override
    protected void initView(View root, @Nullable Bundle savedInstanceState) {
        recyclerView = root.findViewById(R.id.fco_list);
        adapter = new ChangeOrderAdapter();
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        ItemTouchHelper helper = new ItemTouchHelper(new ItemTouchCallback());
        helper.attachToRecyclerView(recyclerView);

        new LoadData().execute();
    }


    @Override
    protected void initData(@Nullable Bundle savedInstanceState) {

    }

    class ItemTouchCallback extends ItemTouchHelper.Callback {

        @Override
        public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
            int dragFlag = 0;
            int swipeFlag = 0;
            RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
            if (layoutManager instanceof LinearLayoutManager) {
                dragFlag = ItemTouchHelper.UP | ItemTouchHelper.DOWN;
                swipeFlag = 0;
            }
            return makeMovementFlags(dragFlag, swipeFlag);
        }

        @Override
        public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
            int fromPosition = viewHolder.getAdapterPosition();
            int toPosition = target.getAdapterPosition();
            if (fromPosition < toPosition) {
                for (int i = fromPosition; i < toPosition; i++) {
                    Collections.swap(adapter.getList(), i, i+1);
                }
            } else {
                for (int i = fromPosition; i > toPosition; i--) {
                    Collections.swap(adapter.getList(), i, i-1);
                }
            }
            adapter.notifyItemChanged(fromPosition, toPosition);
            return true;
        }

        @Override
        public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {

        }

        @Override
        public void onSelectedChanged(RecyclerView.ViewHolder viewHolder, int actionState) {
            super.onSelectedChanged(viewHolder, actionState);
            if (actionState == ItemTouchHelper.ACTION_STATE_DRAG) {
                viewHolder.itemView.setBackgroundColor(Color.LTGRAY);
            }
        }

        @Override
        public void clearView(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
            super.clearView(recyclerView, viewHolder);
            viewHolder.itemView.setBackgroundColor(0);
        }
    }

    class LoadData extends AsyncTask<Void, Void, List<CardAttr>> {

        @Override
        protected List<CardAttr> doInBackground(Void... voids) {
            String json = AppPref.getCardList();
            Logger.json(json);
            CardAttrList list = new Gson().fromJson(json, CardAttrList.class);
            return list.getList();
        }

        @Override
        protected void onPostExecute(List<CardAttr> list) {
            super.onPostExecute(list);
            adapter.setData(list);
        }
    }
}
