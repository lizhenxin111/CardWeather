package ui;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.blankj.utilcode.util.ToastUtils;
import com.lzx.cardweather.R;

import java.util.Arrays;

import ui.picker.DateSelecter;
import ui.picker.TimeSelecter;
import utils.DateUtil;
import utils.LocationUtil;
import utils.StringUtil;

public class SelectorItem extends LinearLayout {

    private Spinner spinner;
    private TextView inputView, dateView, hourView;

    public SelectorItem(Context context) {
        super(context);
        init();
    }

    public SelectorItem(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public SelectorItem(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public SelectorItem(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    private void init() {
        inflate(getContext(), R.layout.item_search, this);
        spinner = findViewById(R.id.selector_type);
        inputView = findViewById(R.id.selector_input);
        dateView = findViewById(R.id.selector_date);
        hourView = findViewById(R.id.selector_hour);

        dateView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                onDateClick();
            }
        });

        hourView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                onHourClick();
            }
        });

        inputView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                onInputClick();
            }
        });
    }

    public String getType() {
        return (String) spinner.getSelectedItem();
    }
    public String getInput() {
        return StringUtil.getText(inputView);
    }

    public String getDate() {
        return StringUtil.getText(dateView);
    }

    public String getHour() {
        return StringUtil.getText(hourView);
    }


    private void onDateClick() {
        DateSelecter ds = new DateSelecter(getContext());
        ds.setOnDateSelected(new DateSelecter.OnDateSelected() {
            @Override
            public void onDateselect(int year, int month, int day) {
                setDate(year + DateUtil.add0(month) + DateUtil.add0(day));
            }
        });
        ds.show();
        ds.setOnCancelClickListener(new DateSelecter.OnDateSelected() {
            @Override
            public void onDateselect(int year, int month, int day) {
                setDate(year + DateUtil.add0(month));
                setHour(-1);
            }
        });
    }

    private void onHourClick() {
        if (StringUtil.getText(dateView).length() <= 6) {
            ToastUtils.showShort("已经选择本月数据，无法再选择小时");
            return;
        }
        TimeSelecter ts = new TimeSelecter(getContext());
        ts.setOnHourSelectListener(new TimeSelecter.OnHourSelectListener() {
            @Override
            public void onHourSelect(int hour) {
                setHour(hour);
            }
        });
        ts.show();
        ts.setOnCancelClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setHour(-1);
            }
        });
    }

    private void onInputClick() {
        SelectList s = null;
        if (spinner.getSelectedItem().toString().equals("城市")) {
            s = new SelectList(this, Arrays.asList(LocationUtil.CITY_ARRAY), new SelectList.OnCitySelectedListener() {
                @Override
                public void onSelected(String city) {
                    inputView.setText(city);
                }
            });
        } else {
            s = new SelectList(this, Arrays.asList(LocationUtil.SITE_ARRAY), new SelectList.OnCitySelectedListener() {
                @Override
                public void onSelected(String city) {
                    inputView.setText(city);
                }
            });
        }
        s.show();
    }

    private void setHour(int hour) {
        String text = (hour == -1 ? getContext().getString(R.string.time_picker_day) : DateUtil.add0(hour) );
        hourView.setText(text);
    }

    private void setDate(String date) {
        dateView.setText(date);
    }
}
