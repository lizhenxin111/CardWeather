package ui;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatDialog;
import android.view.View;

import com.lzx.cardweather.R;

public class CompareDialog extends AppCompatDialog {

    private SelectorItem selectorItemA, selectorItemB;
    private FloatingActionButton fab;

    private OnSearchClickListener onSearchClickListener = null;

    public CompareDialog(Context context) {
        super(context);
    }

    public CompareDialog(Context context, int theme) {
        super(context, theme);
    }

    protected CompareDialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.compare_option);

        selectorItemA = findViewById(R.id.selector_a);
        selectorItemB = findViewById(R.id.selector_b);

        fab = findViewById(R.id.selecter_search);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onSearchClick();
            }
        });
    }

    public void setOnSearchClickListener(OnSearchClickListener onSearchClickListener) {
        this.onSearchClickListener = onSearchClickListener;
    }



    private void onSearchClick() {
        if (onSearchClickListener != null) {
            onSearchClickListener.onSearchClick(selectorItemA.getType(), selectorItemA.getInput(), selectorItemA.getDate(), selectorItemA.getHour(),
                    selectorItemB.getType(), selectorItemB.getInput(), selectorItemB.getDate(), selectorItemB.getHour());
        }
    }

    public interface OnSearchClickListener {
        void onSearchClick(String aType, String aLocation, String aDate, String aHour, String bType, String bLocation, String bDate, String bHour);
    }
}
