package acitvity;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.NestedScrollView;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.blankj.utilcode.util.SizeUtils;
import com.lzx.cardweather.R;

import base.BaseActivity;
import ui.ViewWrapper;
import ui.picker.DateSelecter;
import ui.picker.TimeSelecter;
import utils.DateUtil;

public class SearchActivity extends BaseActivity {

    private boolean isFirstOpen = true;
    private boolean isFabClicked = false;

    private LinearLayout dateLayout, cityLayout, root;
    private NestedScrollView scrollView;
    private FloatingActionButton fab;
    private TextView yearView, monthView, dayView, hourView;
    private Spinner spinner;
    private EditText cityInput;

    //首次点击搜索的动画
    private ObjectAnimator dateXAnim, dateYAnim, dateWidthAnim;
    private ObjectAnimator cityXAnim, cityYAnim, cityWidthAnim;
    private ObjectAnimator fabXAnim, fabYAnim, fabRotateAnim;
    private ObjectAnimator scrollViewFadeInAnim, scrollViewFadeOutAnim, zoomIn, zoomOut;
    private AnimatorSet dateAnimSet, cityAnimSet, fabPosAnimSet, svIn, svOut;

    //非首次搜索、首次搜索结束之后的动画
    private ObjectAnimator dateXAnim1, dateYAnim1, dateWidthAnim1;
    private ObjectAnimator cityXAnim1, cityYAnim1, cityWidthAnim1;
    private ObjectAnimator fabXAnim1, fabYAnim1;

    private AnimatorSet dateAnimSet1, cityAnimSet1, fabPosAnimSet1;

    private int padding = SizeUtils.dp2px(10);
    private int dis = padding;
    private int fabSize = SizeUtils.dp2px(50);


    private Handler handler = new Handler();

    @Override
    protected int getLayoutId() {
        return R.layout.activity_search;
    }

    @Override
    protected void beforeSetContentView() {
        setCanFullScreen(false);
        setSlideBack(true);
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        root = findView(R.id.search_root);
        scrollView = findView(R.id.scrollview);
        fab = findView(R.id.btn_search);
        yearView = findView(R.id.year);
        monthView = findView(R.id.month);
        dayView = findView(R.id.day);
        hourView =  findView(R.id.hour);
        dateLayout = findView(R.id.date_layout);
        cityLayout = findView(R.id.city_layout);
        cityInput = findView(R.id.input);
        spinner = findView(R.id.type);
    }



    @Override
    protected void initData(Bundle savedInstanceState) {
        setDate(DateUtil.getYear(), DateUtil.getMonth(), DateUtil.getDay());
        setHour(DateUtil.getHour());
    }

    @Override
    protected void initOtherComponent() {
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onSearchClick(v);
            }
        });

        yearView.setOnClickListener(mOnDateClick);
        monthView.setOnClickListener(mOnDateClick);
        dayView.setOnClickListener(mOnDateClick);

        hourView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onHourClick();
            }
        });

        cityInput.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus == true) {

                } else {

                }
            }
        });
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus && isFirstOpen) {
            isFirstOpen = false;
            initAnimator();
        }
    }

    private void setDate(int y, int m, int d) {
        yearView.setText(y + " 年");
        monthView.setText(m + " 月");
        if (d == -1 ){
            dayView.setText(getString(R.string.date_picker_month));
            hourView.setText(getString(R.string.time_picker_day));
        } else {
            dayView.setText(d + " 日");
        }
    }

    private void setHour(int h) {
        hourView.setText(h == -1 ? getString(R.string.time_picker_day) : h + " 时");
    }



    private void onSearchClick(View v) {
        if (!isFabClicked) {
            animOnFirstClick();
        } else {
            animOnClick();
        }

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(6000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        onSearchComplete();
                    }
                });
            }
        }).start();
    }

    private void onSearchComplete() {

        animSearchComplete();
    }

    private View.OnClickListener mOnDateClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            DateSelecter ds = new DateSelecter(SearchActivity.this);
            ds.setOnDateSelected(new DateSelecter.OnDateSelected() {
                @Override
                public void onDateselect(int year, int month, int day) {
                    setDate(year, month, day);
                }
            });
            ds.show();
            ds.setOnCancelClickListener(new DateSelecter.OnDateSelected() {
                @Override
                public void onDateselect(int year, int month, int day) {
                    setDate(year, month, day);
                }
            });
        }
    };

    private void onHourClick() {
        if (getString(R.string.date_picker_month).equals(dayView.getText().toString())) {
            toastShort(getString(R.string.notice_pick_hour));
        } else {
            TimeSelecter ts = new TimeSelecter(this);
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
    }



    private void initAnimator() {
        int rootWidth = root.getMeasuredWidth();

        dateXAnim = ObjectAnimator.ofFloat(dateLayout, "X", dateLayout.getX(), padding).setDuration(2000);
        dateYAnim = ObjectAnimator.ofFloat(dateLayout, "Y", dateLayout.getY(), padding).setDuration(2000);
        dateWidthAnim = ObjectAnimator.ofInt(new ViewWrapper(dateLayout), "width",
                dateLayout.getMeasuredWidth(), rootWidth / 3).setDuration(2000);
        dateAnimSet = new AnimatorSet();
        dateAnimSet.play(dateXAnim).with(dateYAnim).with(dateWidthAnim);

        cityXAnim = ObjectAnimator.ofFloat(cityLayout, "X", cityLayout.getX(), rootWidth / 3 + padding + dis).setDuration(2000);
        cityYAnim = ObjectAnimator.ofFloat(cityLayout, "Y", cityLayout.getY(), padding).setDuration(2000);
        cityWidthAnim = ObjectAnimator.ofInt(new ViewWrapper(cityLayout), "width",
                cityLayout.getMeasuredWidth(), rootWidth - rootWidth / 3 - fabSize - padding * 2 - dis * 2).setDuration(2000);
        cityAnimSet = new AnimatorSet();
        cityAnimSet.play(cityXAnim).with(cityYAnim).with(cityWidthAnim);


        fabRotateAnim = ObjectAnimator.ofFloat(fab, "rotation", 0, 359).setDuration(4000);
        fabRotateAnim.setRepeatCount(-1);

        fabXAnim = ObjectAnimator.ofFloat(fab, "X", fab.getX(), rootWidth - fabSize - padding).setDuration(2000);
        fabYAnim = ObjectAnimator.ofFloat(fab, "Y", fab.getY(), padding).setDuration(2000);
        fabPosAnimSet = new AnimatorSet();
        fabPosAnimSet.play(fabXAnim).with(fabYAnim);

        scrollViewFadeInAnim = ObjectAnimator.ofFloat(scrollView, "alpha", 0f, 1f).setDuration(2000);
        scrollViewFadeOutAnim = ObjectAnimator.ofFloat(scrollView, "alpha", 1f, 0f).setDuration(2000);


        dateXAnim1 = ObjectAnimator.ofFloat(dateLayout, "X", padding, padding).setDuration(1);
        dateYAnim1 = ObjectAnimator.ofFloat(dateLayout, "Y", padding, padding).setDuration(1);
        dateWidthAnim1 = ObjectAnimator.ofInt(new ViewWrapper(dateLayout), "width",
                rootWidth / 3, rootWidth / 3).setDuration(1);
        dateAnimSet1 = new AnimatorSet();
        dateAnimSet1.play(dateXAnim1).with(dateYAnim1).with(dateWidthAnim1);

        cityXAnim1 = ObjectAnimator.ofFloat(cityLayout, "X", rootWidth / 3 + padding + dis, rootWidth / 3 + padding + dis).setDuration(1);
        cityYAnim1 = ObjectAnimator.ofFloat(cityLayout, "Y", padding, padding).setDuration(1);
        cityWidthAnim1 = ObjectAnimator.ofInt(new ViewWrapper(cityLayout), "width",
                rootWidth - rootWidth / 3 - fabSize - padding * 2 - dis * 2, rootWidth - rootWidth / 3 - fabSize - padding * 2 - dis * 2).setDuration(1);
        cityAnimSet1 = new AnimatorSet();
        cityAnimSet1.play(cityXAnim1).with(cityYAnim1).with(cityWidthAnim1);


        fabXAnim1 = ObjectAnimator.ofFloat(fab, "X", rootWidth - fabSize - padding, rootWidth - fabSize - padding).setDuration(1);
        fabYAnim1 = ObjectAnimator.ofFloat(fab, "Y", padding, padding).setDuration(1);
        fabPosAnimSet1 = new AnimatorSet();
        fabPosAnimSet1.play(fabXAnim1).with(fabYAnim1);

        zoomIn = ObjectAnimator.ofInt(new ViewWrapper(scrollView), "height", 0, 200).setDuration(2000);
        zoomOut = ObjectAnimator.ofInt(new ViewWrapper(scrollView), "height", 200, 0).setDuration(2000);

        svIn = new AnimatorSet();
        svIn.play(scrollViewFadeInAnim).with(zoomIn);
        svOut = new AnimatorSet();
        svOut.play(scrollViewFadeOutAnim).with(zoomOut);
    }

    private void animOnFirstClick() {
        dateAnimSet.start();
        cityAnimSet.start();
        fabRotateAnim.start();
    }

    private void animOnClick() {
        fabRotateAnim.start();
        svOut.start();
    }

    /**
     * 当动画首次运行结束、scrollview显示时，date框和城市框会被移动回原来位置，所以需要利用动画确定其位置
     */
    private void animSearchComplete() {
        if (!isFabClicked) {
            isFabClicked = true;
            dateAnimSet1.start();
            cityAnimSet1.start();
            fabPosAnimSet.start();
            scrollView.setVisibility(View.VISIBLE);
            root.setGravity(Gravity.NO_GRAVITY);
        }
        svIn.start();
        fabRotateAnim.end();
    }
}
