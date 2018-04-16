package ui.picker;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatDialog;
import android.view.View;
import android.widget.DatePicker;

import com.lzx.cardweather.R;

public class DateSelecter extends AppCompatDialog{

    private DatePicker picker;
    private OnDateSelected onDateSelected = null;
    private OnDateSelected onCancelClickListener = null;

    public DateSelecter(Context context) {
        super(context);
    }

    public DateSelecter(Context context, int theme) {
        super(context, theme);
    }

    protected DateSelecter(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.date_picker);

        picker = findViewById(R.id.picker);
        if (onDateSelected != null) {
            findViewById(R.id.done).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dismiss();
                    onDateSelected.onDateselect(picker.getYear(), picker.getMonth() + 1, picker.getDayOfMonth());
                }
            });
        }

        findViewById(R.id.cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
                if (onCancelClickListener != null) {
                    onDateSelected.onDateselect(picker.getYear(), picker.getMonth() + 1, -1);
                }
            }
        });
    }

    public void setOnDateSelected(OnDateSelected onDateSelected) {
        this.onDateSelected = onDateSelected;
    }

    public void setOnCancelClickListener(OnDateSelected onCancelClickListener) {
        this.onCancelClickListener = onCancelClickListener;
    }

    public interface OnDateSelected {
        void onDateselect(int year, int month, int day);
    }
}
