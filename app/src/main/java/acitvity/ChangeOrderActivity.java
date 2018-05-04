package acitvity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;

import com.google.gson.Gson;
import com.lzx.cardweather.R;

import java.util.Collections;
import java.util.List;

import adapter.recycler.ChangeOrderAdapter;
import base.BaseActivity;
import bean.card.CardAttr;
import bean.card.CardAttrList;
import utils.AppContext;
import utils.AppPref;

public class ChangeOrderActivity extends BaseActivity {

    private RecyclerView recyclerView;
    private ChangeOrderAdapter adapter;

    private LocalBroadcastManager localBroadcastManager = null;
    private Intent broadcastIntent = null;

    private LoadData loadData;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_change_order;
    }

    @Override
    protected void beforeSetContentView() {
        setAllowScreenRoate(false);
        setCanFullScreen(false);
        setSlideBack(true);
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        recyclerView = findViewById(R.id.fco_list);
        adapter = new ChangeOrderAdapter();
        recyclerView.setLayoutManager(new LinearLayoutManager(ChangeOrderActivity.this));
        recyclerView.setAdapter(adapter);
        ItemTouchHelper helper = new ItemTouchHelper(new ItemTouchCallback());
        helper.attachToRecyclerView(recyclerView);
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        loadData = new LoadData();
        loadData.execute();
    }

    @Override
    protected void initOtherComponent() {
        localBroadcastManager = LocalBroadcastManager.getInstance(this);
        broadcastIntent = new Intent(AppContext.ACTION_CHANGE_ORDER);
    }

    @Override
    protected void onPause() {
        super.onPause();
        CardAttrList list = new CardAttrList(adapter.getList());
        String json = new Gson().toJson(list);
        AppPref.setCardList(json);

        localBroadcastManager.sendBroadcast(broadcastIntent);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (!loadData.isCancelled()) {
            loadData.cancel(true);
        }
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
            Collections.swap(adapter.getList(), fromPosition, toPosition);
            //changePriority(adapter.getList());
            adapter.notifyItemMoved(fromPosition, toPosition);
            return true;
        }

        @Override
        public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {

        }
    }

    class LoadData extends AsyncTask<Void, Void, List<CardAttr>> {

        @Override
        protected List<CardAttr> doInBackground(Void... voids) {
            String json = AppPref.getCardList();
            CardAttrList list = new Gson().fromJson(json, CardAttrList.class);
            return list.getList();
        }

        @Override
        protected void onPostExecute(List<CardAttr> list) {
            super.onPostExecute(list);
            adapter.requestData(list);
        }
    }
}
