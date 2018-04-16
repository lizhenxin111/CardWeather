package ui.card;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;

import com.lzx.cardweather.R;

import utils.AQIUtil;

public class CardAQI extends BaseCard {

    private int aqi = -1;
    private int[] items = null;

    private String[] itemTags = new String[]{"O3","NO2","CO","SO2","PM10","PM2.5"};

    public CardAQI(Context context) {
        super(context);
        setTitle(getResources().getString(R.string.card_title_aqi));
    }

    public CardAQI(Context context, AttributeSet attrs) {
        super(context, attrs);
        setTitle(getResources().getString(R.string.card_title_aqi));
    }

    public CardAQI(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setTitle(getResources().getString(R.string.card_title_aqi));
    }

    public int getAqi() {
        return aqi;
    }

    public void setAqi(int aqi) {
        this.aqi = aqi;
        invalidate();
    }

    public int[] getItems() {
        return items;
    }

    public void setItems(int[] items) {
        this.items = items;
        invalidate();
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
        if (aqi != -1) {
            Paint bigPaint = new Paint();
            bigPaint.setStyle(Paint.Style.FILL);
            bigPaint.setColor(AQIUtil.getColor(aqi));
            Paint smallPaint = new Paint();
            smallPaint.setStyle(Paint.Style.FILL);
            smallPaint.setColor(AQIUtil.getColor(0));
            Paint textPaint = new Paint();
            textPaint.setColor(Color.BLACK);
            textPaint.setTextAlign(Paint.Align.CENTER);
            textPaint.setTextSize(25);
            textPaint.setColor(AQIUtil.getColor(aqi));

            int circlrX = getMeasuredWidth() / 4 - 50;
            int circleY = -90;

            //外圈
            c.drawCircle(circlrX,
                    circleY,
                    80,
                    bigPaint);
            //内圈。和外圈组成一个圆环
            c.drawCircle(circlrX,
                    circleY,
                    70,
                    smallPaint);
            //空气质量
            c.drawText(AQIUtil.getLevel(aqi),
                    circlrX,
                    circleY + 30,
                    textPaint);
            textPaint.setTextSize(25);
            //aqi值
            c.drawText(aqi + "",
                    circlrX,
                    circleY - 10,
                    textPaint);

        }
    }

    /**
     * 画右半部分的饿污染数据详情
     * @param c
     */
    private void drawItem(Canvas c) {
        if (items!=null && items.length!=0) {
            Paint pillarPaint = new Paint();
            pillarPaint.setStyle(Paint.Style.FILL);
            pillarPaint.setStrokeWidth(24);

            Paint linePaint = new Paint();
            linePaint.setStyle(Paint.Style.FILL);
            linePaint.setColor(Color.DKGRAY);

            Paint textPaint = new Paint();
            textPaint.setColor(Color.BLACK);
            textPaint.setTextAlign(Paint.Align.CENTER);
            textPaint.setTextSize(15);

            int x = getMeasuredWidth() / 2 + 40;

            for (int i = 0; i < items.length; i++) {
                int value = items[i];
                int y = - (32 + i * 27);
                textPaint.setColor(AQIUtil.getColor(value));
                textPaint.setTextAlign(Paint.Align.RIGHT);
                //污染物类别
                c.drawText(itemTags[i],
                        x - 5,
                        y + 9,
                        textPaint);
                pillarPaint.setColor(AQIUtil.getColor(value));
                //横柱
                c.drawLine(x,
                        y,
                        x + value,
                        y,
                        pillarPaint);
                textPaint.setTextAlign(Paint.Align.LEFT);
                //污染物数据
                c.drawText(value + "",
                        x + value + 5,
                        y + 9,
                        textPaint);
            }

            c.drawLine(x,
                    -20,
                    x,
                    -180,
                    linePaint);
        }
    }
}
