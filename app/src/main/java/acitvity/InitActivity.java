package acitvity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.blankj.utilcode.util.SizeUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.google.gson.Gson;
import com.lzx.cardweather.R;
import com.orhanobut.logger.Logger;

import java.util.Arrays;

import base.BaseActivity;
import bean.card.CardAttr;
import bean.card.CardAttrList;
import ui.SelectList;
import ui.card.BaseCard;
import utils.AppContext;
import utils.AppPref;
import utils.DelayUtil;
import utils.LocationUtil;

/**
 * 初始化位置信息
 */
public class InitActivity extends BaseActivity implements AMapLocationListener {

    public static final int RESULT_CODE = -1;
    public static final String RESULT_TAG = "_CITY";


    private CoordinatorLayout root;

    //声明AMapLocationClient类对象
    public AMapLocationClient mLocationClient = null;
    //声明AMapLocationClientOption对象
    public AMapLocationClientOption mLocationOption = null;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_init;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        root = findView(R.id.init_root);
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        String permission = Manifest.permission.ACCESS_FINE_LOCATION;
        if (ContextCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{permission}, 1);
        } else {
            locateFromGPS();
        }
    }

    @Override
    public void onBackPressed() {
        AppContext.exitApp(this);       //点击返回按钮，说明没有选择城市，则不会显示主页，直接退出app
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == 1) {
            if (grantResults.length > 0 &&
                    grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                locateFromGPS();
            } else {
                selectCity();
            }
        }
    }

    /**
     * 使用定位选择城市
     */
    private void locateFromGPS() {
        ToastUtils.showShort("正在进行网络定位");
        //初始化定位
        mLocationClient = new AMapLocationClient(getApplicationContext());
        //设置定位回调监听
        mLocationClient.setLocationListener(this);
        //初始化AMapLocationClientOption对象
        mLocationOption = new AMapLocationClientOption();

        AMapLocationClientOption option = new AMapLocationClientOption();
        /**
         * 设置定位场景，目前支持三种场景（签到、出行、运动，默认无场景）
         */
        option.setLocationPurpose(AMapLocationClientOption.AMapLocationPurpose.SignIn);
        if(null != mLocationClient){
            mLocationClient.setLocationOption(option);
            //设置场景模式后最好调用一次stop，再调用start以保证场景模式生效
            mLocationClient.stopLocation();
            mLocationClient.startLocation();
        }
        mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
        mLocationOption.setOnceLocation(true);      //一次定位
        //给定位客户端对象设置定位参数
        mLocationClient.setLocationOption(mLocationOption);
        //启动定位
        mLocationClient.startLocation();

    }

    @Override
    public void onLocationChanged(AMapLocation amapLocation) {
        if (amapLocation != null) {
            if (amapLocation.getErrorCode() == 0) {
                String c = amapLocation.getCity();
                for (final String s : Arrays.asList(LocationUtil.CITY_ARRAY)) {
                    if (c.contains(s)) {
                        onCitySelect(s);
                        return;
                    }
                }
                selectCity();
            } else {
                //显示错误信息ErrCode是错误码，errInfo是错误信息，详见错误码表。
                Logger.e("location Error, ErrCode:"
                        + amapLocation.getErrorCode() + ", errInfo:"
                        + amapLocation.getErrorInfo());
            }
        }
    }


    /**
     * 手动选择城市
     */
    private void selectCity() {
        TextView textView = new TextView(this);
        textView.setText(getString(R.string.select_location));
        textView.setTextSize(SizeUtils.sp2px(10));
        CoordinatorLayout.LayoutParams layoutParams = new CoordinatorLayout.LayoutParams(450, 100);
        layoutParams.gravity = Gravity.CENTER;
        textView.setLayoutParams(layoutParams);
        root.addView(textView);

        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showPopopWindow();
            }
        });
        showPopopWindow();
    }

    /**
     * 显示城市列表
     */
    private void showPopopWindow() {
        SelectList selectList = new SelectList(root, Arrays.asList(LocationUtil.CITY_ARRAY), new SelectList.OnCitySelectedListener() {
            @Override
            public void onSelected(final String city) {
                onCitySelect(city);
            }
        });
        selectList.show();
    }

    /**
     * 选择城市后的操作
     * @param city
     */
    private void onCitySelect(final String city) {
        ToastUtils.showShort("已经定位到当前位置为：" + city);
        AppPref.setCity(city);
        initCardList();
        root.removeAllViews();
        new DelayUtil(100, new Handler(), new DelayUtil.OnDelayExecuteInterface() {
            @Override
            public void delay() {
                returnWithCity(city);
            }
        });
    }

    /**
     * 退出并将选择的城市返回给MainActivity
     * @param city
     */
    private void returnWithCity(String city) {
        Intent intent = new Intent();
        intent.putExtra(RESULT_TAG, city);
        setResult(RESULT_CODE, intent);
        finish();
    }

    /**
     * 选择城市之后，初始化配置信息
     */
    private void initCardList() {
        CardAttr attrAqi = new CardAttr(BaseCard.TAG_AQI, 1, true);
        //CardAttr attrAqiTips = new CardAttr(BaseCard.TAG_AQI_TIPS, 2, true);
        CardAttr attrWeather = new CardAttr(BaseCard.TAG_WEATHER, 3, true);
        //CardAttr attrLifeStyle = new CardAttr(BaseCard.TAG_LIFESTYLE, 4, true);
        CardAttr attrAqiChange = new CardAttr(BaseCard.TAG_AQICHANGE, 5, true);
        CardAttr attrWeatherForecast = new CardAttr(BaseCard.TAG_WEATHERFORECAST, 6, true);

        CardAttrList list = new CardAttrList();
        list.add(attrAqi);
        //list.add(attrAqiTips);
        list.add(attrWeather);
        //list.add(attrLifeStyle);
        list.add(attrAqiChange);
        list.add(attrWeatherForecast);

        String json = new Gson().toJson(list);
        AppPref.setCardList(json);
    }
}
