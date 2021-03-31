package ui.card;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.text.Layout;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ViewGroup;

import com.lzx.cardweather.R;
import com.orhanobut.logger.Logger;

import utils.AQIUtil;
import utils.ResourceUtil;

public class CardAQI extends BaseCard {

    private int aqi = -1;
    private int[] items = null;                     //各项空气污染物的数据
    private boolean drawToggle = false;             //是否绘制展开按钮
    private OnClickListener onAqiClick = null;      //AQI圆环的点击事件

    private String[] itemTags = new String[]{"O3","NO2","CO","SO2","PM10","PM2.5"};     //空气污染物绘制的顺序

    private boolean isExpand = false;               //卡片是否展开

    private int circelX, circelY, circelRadius;                       //AQI圆环的横纵坐标、半径
    private int toggleWidth, toggleHeight, toggleX, toggleY;          //toggle的宽、高

    Paint bigPaint;
    Paint smallPaint;
    Paint textPaint;
    Paint pillarPaint;
    Paint linePaint;
    int pillarWidth = dp(16);       //横柱的高度

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

    public boolean isDrawToggle() {
        return drawToggle;
    }

    public void setDrawToggle(boolean drawToggle) {
        this.drawToggle = drawToggle;
        invalidate();
    }

    public OnClickListener getOnAqiClick() {
        return onAqiClick;
    }

    public void setOnAqiClick(OnClickListener onAqiClick) {
        this.onAqiClick = onAqiClick;
    }

    @Override
    protected void drawContent(Canvas canvas) {
        initPaint();
        drawTitle(canvas);
        if (drawToggle) {
            drawToggle(canvas);
        }

        drawCircle(canvas);
        drawItem(canvas);
        if (isExpand) {
            drawTips(canvas);
        }
    }

    private void initPaint() {
        bigPaint = new Paint();
        bigPaint.setStyle(Paint.Style.FILL);
        bigPaint.setColor(AQIUtil.getColor(aqi));
        smallPaint = new Paint();
        smallPaint.setStyle(Paint.Style.FILL);
        smallPaint.setColor(Color.WHITE);

        textPaint = new Paint();
        textPaint.setColor(Color.BLACK);
        textPaint.setTextAlign(Paint.Align.CENTER);
        textPaint.setTextSize(sp(16));
        textPaint.setColor(AQIUtil.getColor(aqi));

        pillarPaint = new Paint();
        pillarPaint.setStyle(Paint.Style.FILL);
        pillarPaint.setStrokeWidth(pillarWidth);

        linePaint = new Paint();
        linePaint.setStyle(Paint.Style.FILL);
        linePaint.setColor(Color.DKGRAY);
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
        if (aqi != -1) {
            circelX = width / 4;
            circelY = dp(100);
            circelRadius = dp(60);
            //外圈
            c.drawCircle(circelX,
                    circelY,
                    circelRadius,
                    bigPaint);
            //内圈。和外圈组成一个圆环
            c.drawCircle(circelX,
                    circelY, circelRadius - dp(8),
                    smallPaint);
            //空气质量
            c.drawText(AQIUtil.getLevel(aqi),
                    circelX, circelY + dp(16),
                    textPaint);
            //aqi值
            c.drawText(aqi + "",
                    circelX, circelY - dp(16),
                    textPaint);

        }
    }

    /**
     * 画右半部分的污染数据详情
     * @param c
     */
    private void drawItem(Canvas c) {
        if (items!=null && items.length!=0) {

            int textSize = sp(10);
            textPaint.setColor(Color.DKGRAY);
            textPaint.setTextAlign(Paint.Align.CENTER);
            textPaint.setTextSize(textSize);

            int x = width * 2 / 3;  //左侧横坐标
            int xBase = dp(55);     //第一行的纵坐标
            int interval = dp(21);  //每个横柱的间隔
            int textYOffset = (pillarWidth - textSize) / 2;

            for (int i = 0; i < items.length; i++) {
                int value = items[i];
                int y = xBase + i * interval;
                //textPaint.setColor(AQIUtil.getColor(value));
                textPaint.setTextAlign(Paint.Align.RIGHT);
                //污染物类别
                c.drawText(itemTags[i],
                        x - dp(2),
                        y + textYOffset,
                        textPaint);
                pillarPaint.setColor(AQIUtil.getColor(value));
                if (value > 300) {
                    value = value * 2 /3;
                }
                //横柱
                c.drawLine(x, y,
                        x + value, y,
                        pillarPaint);
                textPaint.setTextAlign(Paint.Align.LEFT);
                //污染物数据
                c.drawText(value + "",
                        x + value + dp(2),
                        y + textYOffset,
                        textPaint);
            }

            c.drawLine(x, dp(45),
                    x, dp(126 + 45),
                    linePaint);
        }
    }


    private void drawTips(Canvas canvas) {
        drawInfluence(canvas);
        drawMeasure(canvas);
    }


    private void drawInfluence(Canvas c) {
        if (aqi != -1) {
            StaticLayout layout = getStaticLayout(AQIUtil.getInfluence(aqi));
            int textHeight = layout.getHeight();
            c.save();
            int x = width / 4;
            int y = (dp(200) - textHeight) / 2 + dp(200);
            c.translate(x, y);
            layout.draw(c);
            c.restore();
        }
    }

    private void drawMeasure(Canvas c) {
        if (aqi != -1) {
            StaticLayout layout = getStaticLayout(AQIUtil.getMeasure(aqi));
            int textHeight = layout.getHeight();
            c.save();
            int x = width * 3 / 4;
            int y = (dp(200) - textHeight) / 2 + dp(200);
            c.translate(x, y);
            layout.draw(c);
            c.restore();
        }
    }

    private boolean aqiClicked = false;

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            Logger.d(calculatePoint(event.getX(), event.getY()));
            if (calculatePoint(event.getX(), event.getY()) == 1) {
                if (onAqiClick != null) onAqiClick.onClick(this);
                aqiClicked = true;
            } else if (calculatePoint(event.getX(), event.getY()) == 2) {
                if (isExpand) collapsed();
                else expand();
            }
        } else if (event.getAction() == MotionEvent.ACTION_CANCEL || event.getAction() == MotionEvent.ACTION_UP) {
            if (calculatePoint(event.getX(), event.getY()) == 1 && aqiClicked) {
                Logger.d("detail");
                if (onAqiClick != null) onAqiClick.onClick(this);
                aqiClicked = false;
            }
        }

        aqiClicked = false;
        return super.onTouchEvent(event);
    }

    private void expand() {
        isExpand = true;
        //expandAnim.start();
        ViewGroup.LayoutParams lp = getLayoutParams();
        lp.height = dp(400);
        setLayoutParams(lp);
        invalidate();
        requestLayout();
    }

    private void collapsed() {
        isExpand = false;
        //collapsedAnim.start();
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
        Logger.d(pointX + " " + pointY + "     \n" +
                (circelX - circelRadius)+" "+(circelX + circelRadius) + " "+(circelY - circelRadius)+" "+(circelY + circelRadius)+"         \n"+
                toggleX +" "+(toggleX + toggleWidth)+" " +(height - toggleY) + " "+height);
        if (pointX > circelX - circelRadius && pointX < circelX + circelRadius &&
                pointY > circelY - circelRadius && pointY < circelY + circelRadius) {
            return 1;
        } else if (pointX > toggleX && pointX < toggleX + toggleWidth &&
                pointY > toggleY && pointY < height) {
            return 2;
        }
        return 0;
    }

    private StaticLayout getStaticLayout(String text) {
        TextPaint paint = new TextPaint();
        paint = new TextPaint();
        paint.setTextSize(sp(16));
        paint.setTextAlign(Paint.Align.CENTER);
        paint.setAntiAlias(true);
        paint.setColor(aqi == 0 ? Color.BLACK : AQIUtil.getColor(aqi));
        return new StaticLayout(text,
                paint,
                width / 3,
                Layout.Alignment.ALIGN_NORMAL,
                1f, 0f,
                true);
    }

}
