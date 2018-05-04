package ui;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatDialog;

import com.lzx.cardweather.R;

public class ForecastDialog extends AppCompatDialog {
    public ForecastDialog(Context context) {
        super(context);
    }

    public ForecastDialog(Context context, int theme) {
        super(context, theme);
    }

    protected ForecastDialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_forecast);
    }


}
