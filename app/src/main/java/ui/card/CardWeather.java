package ui.card;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;

import com.lzx.cardweather.R;


public class CardWeather extends BaseCard {

    private String tmp = null;
    private String weather = null;
    private String[] items = null;

    private String[] itemTags = new String[]{"能见度","气压","降水量","相对湿度","风速","体感温度"};

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

    public String getTmp() {
        return tmp;
    }

    public void setTmp(String tmp) {
        this.tmp = tmp;
        invalidate();
    }

    public void setWeather(String weather) {
        this.weather = weather;
        invalidate();
    }

    public String getWeather() {
        return weather;
    }

    public void setItems(String[] items) {
        this.items = items;
        invalidate();
    }

    public String[] getItems() {
        return items;
    }

    @Override
    protected void drawContent(Canvas canvas) {
        canvas.save();
        drawTitle(canvas);

        canvas.translate(0, 200);

        drawCircle(canvas);
        drawItem(canvas);
        canvas.restore();
    }


    /**
     * 画左半部分的圆圈和里面的文字
     * @param c
     */
    private void drawCircle(Canvas c) {
        if (tmp != null && weather != null) {
            Paint textPaint = new Paint();
            textPaint.setColor(Color.BLACK);
            textPaint.setTextAlign(Paint.Align.CENTER);
            textPaint.setTextSize(30);
            textPaint.setColor(Color.GRAY);

            int circlrX = getMeasuredWidth() / 4 - 50;
            int circleY = -90;

            //气温
            c.drawText(tmp + "℃",
                    circlrX,
                    circleY - 10,
                    textPaint);
            //天气状况
            c.drawText(weather,
                    circlrX,
                    circleY + 30,
                    textPaint);

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
            textPaint.setTextAlign(Paint.Align.CENTER);
            textPaint.setTextSize(15);

            int x = getMeasuredWidth() / 2 + 40;

            for (int i = 0; i < items.length; i++) {
                String value = items[i];
                int y = - (32 + i * 27);
                textPaint.setTextAlign(Paint.Align.RIGHT);
                c.drawText(itemTags[i],
                        x + 30,
                        y + 9,
                        textPaint);
                textPaint.setTextAlign(Paint.Align.LEFT);
                c.drawText(value + "",
                        x + 50,
                        y + 9,
                        textPaint);
            }
        }
    }
}
