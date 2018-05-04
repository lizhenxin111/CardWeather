package ui.picker;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatDialog;
import android.view.View;
import android.widget.TimePicker;

import com.lzx.cardweather.R;

public class TimeSelecter extends AppCompatDialog{

    private TimePicker picker;
    private int hour;
    private OnHourSelectListener onHourSelectListener;
    private View.OnClickListener onCancelClickListener = null;

    public TimeSelecter(Context context) {
        super(context);
    }

    public TimeSelecter(Context context, int theme) {
        super(context, theme);
    }

    protected TimeSelecter(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.time_picker);
        picker = findViewById(R.id.picker);
        picker.setIs24HourView(true);
        picker.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
            @Override
            public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {
                hour = hourOfDay;
            }
        });

        picker = findViewById(R.id.picker);

        findViewById(R.id.done).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
                if (onHourSelectListener != null) {
                    onHourSelectListener.onHourSelect(hour);
                }
            }
        });

        findViewById(R.id.cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
                if (onCancelClickListener != null) {
                    onCancelClickListener.onClick(v);
                }
            }
        });
    }

    public void setOnHourSelectListener(OnHourSelectListener onHourSelectListener) {
        this.onHourSelectListener = onHourSelectListener;
    }

    public void setOnCancelClickListener(View.OnClickListener onCancelClickListener) {
        this.onCancelClickListener = onCancelClickListener;
    }

    public interface OnHourSelectListener {
        void onHourSelect(int hour);
    }
}
