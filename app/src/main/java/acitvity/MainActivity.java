package acitvity;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.blankj.utilcode.util.FragmentUtils;
import com.lzx.cardweather.R;
import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.Logger;

import cache.DiskCache;
import mvp.mainpage.MainFragment;
import utils.AppPref;
import utils.SelectCity;

public class MainActivity extends AppCompatActivity {

    private View mParent = null;

    private Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Logger.addLogAdapter(new AndroidLogAdapter());
        setContentView(R.layout.activity_main);
        mParent = (CoordinatorLayout) findViewById(R.id.activity_main_base);

        /*new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        BigLineChart blc = findViewById(R.id.blc);
                        ArrayMap<Integer, Integer> map = new ArrayMap<>();
                        for (int i = 0; i < 24; i++) {
                            map.put(i, i*2);
                        }
                        blc.setData(map);
                    }
                });
            }
        });*/
        if (AppPref.getCity() == null) {
            selectCity();
            AppPref.setFirstOpen();
        } else {
            loadMainFragment();
        }
    }

    private void loadMainFragment() {
        MainFragment mf = new MainFragment();
        FragmentUtils.add(getSupportFragmentManager(), mf, R.id.activity_main_base);
    }


    private void selectCity() {
        TextView textView = new TextView(this);
        textView.setText("请选择城市");
        textView.setBackgroundColor(Color.BLUE);
        CoordinatorLayout.LayoutParams layoutParams = new CoordinatorLayout.LayoutParams(400, 100);
        layoutParams.gravity = Gravity.CENTER;
        textView.setLayoutParams(layoutParams);
        ((ViewGroup)findViewById(R.id.activity_main_base)).addView(textView);

        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showPopopWindow();
            }
        });
    }

    private void showPopopWindow() {
        SelectCity selectCity = new SelectCity(mParent, new SelectCity.OnPopupDismissListener() {
            @Override
            public void onDismiss(String city) {
                loadMainFragment();
            }
        });
        selectCity.show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        DiskCache.flush();
        DiskCache.close();
    }
}
