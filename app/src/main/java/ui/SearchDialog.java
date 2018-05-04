package ui;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatDialog;
import android.view.View;

import com.lzx.cardweather.R;

public class SearchDialog extends AppCompatDialog {

    private SelectorItem selectorItem;
    private FloatingActionButton fab;

    private OnSearchClickListener onSearchClickListener;

    public void setOnSearchClickListener(OnSearchClickListener onSearchClickListener) {
        this.onSearchClickListener = onSearchClickListener;
    }

    public SearchDialog(Context context) {
        super(context);
    }

    public SearchDialog(Context context, int theme) {
        super(context, theme);
    }

    protected SearchDialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_search);

        selectorItem = findViewById(R.id.selector_item);
        fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onSearchClickListener != null) {
                    onSearchClickListener.onSearchClick(selectorItem.getType(), selectorItem.getInput(), selectorItem.getDate(), selectorItem.getHour());
                }
            }
        });
    }

    public interface OnSearchClickListener {
        void onSearchClick(String type, String location, String date, String hour);
    }
}
