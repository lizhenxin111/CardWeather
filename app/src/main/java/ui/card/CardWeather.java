package ui.card;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.StringRes;
import android.support.v4.util.ArrayMap;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.ViewGroup;

import com.lzx.cardweather.R;

import utils.BitmapUtil;
import utils.ResourceUtil;


public class CardWeather extends BaseCard {

    private String tmp = null;      //温度
    private String[] items = null;  //各项天气数据
    private String weatherCode;     //天气代码
    private ArrayMap<String, String> data;      //生活指数数据，键值对为：简介--详细描述。
                                                // 顺序为：舒适度、穿衣指数、紫外线、运动、流感、旅游、洗车、空气、

    private String[] itemTags = new String[]{"能见度","气压","降水量","湿度","风速","体感"};
    private String[] itemIcon = new String[]{"ic_visibility", "ic_pressure", "ic_rain",
            "ic_hum", "ic_wind_speed", "ic_human"};
    private String[] liftstyleIcon = new String[]{"ic_conf", "ic_wear", "ic_untraviolet", "ic_sport",
            "ic_flu", "ic_travel", "ic_wash_car", "ic_air"};

    private boolean isExpand = false;
    private int toggleWidth, toggleHeight, toggleX, toggleY;          //toggle的宽、高
    private Paint textPaint = new Paint();

    public CardWeather(Context context) {
        super(context);
        setTitle(getResources().getString(R.string.card_title_weather));
    }

    public CardWeather(Context context, AttributeSet attrs) {
        super(context, attrs);
        setTitle(getResources().getString(R.string.card_title_weather));
    }

    public CardWeather(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setTitle(getResources().getString(R.string.card_title_weather));
    }

    public void requestData(ArrayMap<String, String> data) {
        this.data = data;
    }

    public void setWeathreCode(String weatherCode) {
        this.weatherCode = weatherCode;
        invalidate();
    }


    public void setTmp(String tmp) {
        this.tmp = tmp;
        invalidate();
    }

    public void setItems(String[] items) {
        this.items = items;
        invalidate();
    }

    public String getTmp() {
        return tmp;
    }

    public String[] getItems() {
        return items;
    }

    public String getWeatherCode() {
        return weatherCode;
    }

    public ArrayMap<String, String> getData() {
        return data;
    }


    @Override
    protected void drawContent(Canvas canvas) {
        Log.d("FORECAST", "draw weather");
        canvas.save();
        drawToggle(canvas);
        drawTitle(canvas);
        drawCircle(canvas);
        drawItem(canvas);
        if (isExpand) {
            drawTexts(canvas);
        }
    }

    private void drawToggle(Canvas c) {
        int id = ResourceUtil.getId(isExpand ? "ic_expand_less" : "ic_expand_more", "drawable");
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), id);
        toggleX = (width - getLeft() * 2) / 2;
        toggleY = height - dp(30);
        c.drawBitmap(bitmap, toggleX, toggleY, null);
        toggleWidth = bitmap.getWidth();
        toggleHeight = bitmap.getHeight();
    }

    /**
     * 画左半部分的圆圈和里面的文字
     * @param c
     */
    private void drawCircle(Canvas c) {
        if (tmp != null && weatherCode != null) {
            Paint textPaint = new Paint();
            textPaint.setColor(Color.BLACK);
            textPaint.setTextAlign(Paint.Align.CENTER);
            textPaint.setTextSize(sp(16));
            textPaint.setColor(Color.GRAY);

            int circlrX = width / 4;

            //气温
            c.drawText(tmp + "℃",
                    circlrX,
                    dp(55),
                    textPaint);

            Bitmap bitmap = BitmapUtil.fromRes("p" + weatherCode);
            c.drawBitmap(bitmap, circlrX - bitmap.getWidth() / 2, dp(65), null);
        }
    }

    /**
     * 画右半部分的饿污染数据详情
     * @param c
     */
    private void drawItem(Canvas c) {
        if (items!=null && items.length!=0) {
            Paint textPaint = new Paint();
            textPaint.setColor(Color.BLACK);
            textPaint.setTextAlign(Paint.Align.LEFT);
            textPaint.setTextSize(sp(10));

            int x = width * 2 / 3;  //左侧横坐标
            int xBase = dp(55);     //最下面一行的纵坐标
            int interval = dp(21);

            for (int i = 0; i < items.length; i++) {
                String value = items[i];
                int y = xBase + i * interval;
                Bitmap bmp = BitmapUtil.fromRes(itemIcon[i]);
                c.drawBitmap(bmp,
                        x - dp(45), y - bmp.getHeight() * 3 / 4,
                        textPaint);
                //textPaint.setTextAlign(Paint.Align.RIGHT);
                c.drawText(itemTags[i],
                        x - dp(25), y,
                        textPaint);
                //textPaint.setTextAlign(Paint.Align.LEFT);
                c.drawText(value + "",
                        x + dp(16), y,
                        textPaint);
            }
        }
    }


    private void drawTexts(Canvas c) {
        textPaint.setTextSize(sp(10));
        if (data != null && data.size() != 0) {
            int lx = width*2/10, rx = width * 3 / 5;
            for (int i = 0; i < 4; i++) {
                Bitmap bmpL = BitmapUtil.fromRes(liftstyleIcon[i*2]);
                c.drawBitmap(bmpL, lx - dp(25), dp(200) + dp(30) * (i + 2) - bmpL.getHeight() * 3 / 4, null);
                c.drawText(getType(i * 2) + ":   " + data.keyAt(i * 2),
                        lx, dp(200) + dp(30) * (i + 2),
                        textPaint);
                Bitmap bmpR = BitmapUtil.fromRes(liftstyleIcon[i*2 + 1]);
                c.drawBitmap(bmpR, rx - dp(25), dp(200) + dp(30) * (i + 2) - bmpL.getHeight() * 3 / 4, null);
                c.drawText(getType(i * 2 + 1) + ":   " + data.keyAt(i * 2 + 1),
                        rx, dp(200) + dp(30) * (i + 2),
                        textPaint);
            }
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            if (calculatePoint(event.getX(), event.getY()) == 1) {
                if (isExpand) collapsed();
                else expand();
            }
        }
        return super.onTouchEvent(event);
    }

    private void expand() {
        isExpand = true;
        ViewGroup.LayoutParams lp = getLayoutParams();
        lp.height = dp(400);
        setLayoutParams(lp);
        invalidate();
        requestLayout();
    }

    private void collapsed() {
        isExpand = false;
        ViewGroup.LayoutParams lp = getLayoutParams();
        lp.height = dp(200);
        setLayoutParams(lp);
        invalidate();
        requestLayout();
    }

    /**
     * 根据点的坐标计算点击区域
     * @param pointX
     * @param pointY
     * @return          1：AQI，  2：Toggle,   0:其他
     */
    private int calculatePoint(float pointX, float pointY) {
       if (pointX > toggleX - dp(5) && pointX < toggleX + toggleWidth &&
                pointY > toggleY && pointY < height) {
            return 1;
        }
        return 0;
    }

    //舒适度、穿衣指数、紫外线、运动、流感、旅游、洗车、空气、
    private String getType(int i) {
        switch (i) {
            case 7: return getString(R.string.life_air);
            case 6: return getString(R.string.life_cw);
            case 5: return getString(R.string.life_trav);
            case 4: return getString(R.string.life_flu);
            case 3: return getString(R.string.life_sport);
            case 2: return getString(R.string.life_uv);
            case 1: return getString(R.string.life_drsg);
            case 0: return getString(R.string.life_comf);
        }
        return null;
    }


    private String getString(@StringRes int id) {
        return getContext().getString(id);
    }
}
