package ui.card;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;

import java.util.ArrayList;
import java.util.List;

import bean.heweather.forecast.ForecastItem;
import utils.BitmapUtil;

public class CardWeatherForecast extends BaseCard {

    private List<ForecastItem> data = new ArrayList<>();        //预测的天气数据
    private OnForecastClickListener onForecastClickListener = null;

    private Paint textPaint;
    private Paint linePaint;

    private int intervel = 0;       //每个数据之间的间隔


    public CardWeatherForecast(Context context) {
        super(context);
    }

    public CardWeatherForecast(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CardWeatherForecast(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void requestData(List<ForecastItem> data) {
        this.data = data;
        invalidate();
    }

    public void setOnForecastClickListener(OnForecastClickListener onForecastClickListener) {
        this.onForecastClickListener = onForecastClickListener;
    }

    public List<ForecastItem> getData() {
        return data;
    }

    public OnForecastClickListener getOnForecastClickListener() {
        return onForecastClickListener;
    }

    @Override
    protected void drawContent(Canvas canvas) {
        setTitle("天气预测");
        initPaint();
        drawForecast(canvas);
    }

    private void initPaint() {
        textPaint = new Paint();
        textPaint.setTextAlign(Paint.Align.CENTER);
        textPaint.setTextSize(sp(10));
        textPaint.setColor(Color.BLACK);

        linePaint = new Paint();
        linePaint.setColor(Color.LTGRAY);
        linePaint.setStrokeWidth(dp(2));
        linePaint.setStyle(Paint.Style.STROKE);
    }

    private void drawForecast(Canvas canvas) {
        if (data != null && data.size() != 0) {
            int num = data.size();
            int contentWidth = width - dp(40 + 20);     //预测内容的宽
            int startX = dp(50);                        //起始x坐标
            intervel = contentWidth / num;              //每个预测之间的间隔
            int maxTmpY = 0, minTmpY = 0;               //最高温度、最低温度的y值
            int dayIconY = 0, nightIconY = 0;           //白天、夜间图标中心的y值

            for (int i = 0; i < num; i++) {
                int xBase = startX + intervel * i + intervel / 2;
                ForecastItem n = data.get(i);
                //日期
                canvas.drawText(n.getDate().substring(5, n.getDate().length()), xBase, dp(30), textPaint);

                //白天天气情况
                Bitmap dayPic = BitmapUtil.fromRes("p" + n.getCond_code_d(), 0.5f, 0.5f);
                canvas.drawBitmap(dayPic, xBase - dayPic.getWidth() / 2, dp(35), null);
                dayIconY = dp(35) + dayPic.getHeight() / 2;

                //白天最高温度
                canvas.drawText(n.getTmp_max() + "℃", xBase, dp(35) + dayPic.getHeight() + dp(15), textPaint);
                maxTmpY = dp(35) + dayPic.getHeight() + dp(15);

                //夜间最高温度
                canvas.drawText(n.getTmp_min() + "℃", xBase, dp(35) + dayPic.getHeight() + dp(50), textPaint);
                minTmpY = dp(35) + dayPic.getHeight() + dp(50);

                //夜间天气情况
                Bitmap nightPic = BitmapUtil.fromRes("p" + n.getCond_code_n(), 0.5f, 0.5f);
                canvas.drawBitmap(dayPic, xBase - dayPic.getWidth() / 2, height - nightPic.getHeight() - dp(10), null);
                nightIconY = height - nightPic.getHeight() - dp(10) + nightPic.getHeight() / 2;
            }
            /*for (int i = 1; i < num; i++) {
                canvas.drawLine(maxPoints.keyAt(i-1), maxPoints.valueAt(i-1),
                        maxPoints.keyAt(i), maxPoints.valueAt(i),
                        linePaint);
                canvas.drawLine(minPoints.keyAt(i-1), minPoints.valueAt(i-1),
                        minPoints.keyAt(i), minPoints.valueAt(i),
                        linePaint);
            }*/
            //白天图标
            Bitmap dayIcon = BitmapUtil.fromRes("ic_day");
            canvas.drawBitmap(dayIcon, dp(40) - dayIcon.getWidth() / 2, dayIconY - dayIcon.getHeight() / 2, null);
            //夜间图标
            Bitmap nightIcon = BitmapUtil.fromRes("ic_night");
            canvas.drawBitmap(nightIcon, dp(40) - nightIcon.getWidth() / 2, nightIconY - dayIcon.getHeight() / 2, null);
            //分割线
            int lineY = (maxTmpY + minTmpY - dp(10)) / 2;
            canvas.drawLine(startX - nightIcon.getWidth(), lineY,
                    startX + intervel * num, lineY,
                    linePaint);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN && onForecastClickListener != null) {
            int position = calculatePosition(event.getX());
            onForecastClickListener.onClick(position);
        }
        return super.onTouchEvent(event);
    }

    private int calculatePosition(float x) {
        if (intervel == 0) {
            return -1;
        }

        return (int) Math.floor((x - dp(30)) / intervel);
    }

    public interface OnForecastClickListener {
        void onClick(int position);
    }
}
