package ui.card;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

public abstract class BaseCard extends View {

    public static final String TAG_AQI = "AQI";
    public static final String TAG_WEATHER = "WEATHER";
    public static final String TAG_AQICHANGE = "AQICHANGE";

    private String title = null;
    private int cardPriority = 0;
    private boolean cardVisibility = true;

    public BaseCard(Context context) {
        super(context);
    }

    public BaseCard(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public BaseCard(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setTitle(String title) {
        this.title = title;
        invalidate();
    }

    public String getTitle() {
        return title;
    }

    public int getCardPriority() {
        return cardPriority;
    }

    public void setCardPriority(int cardPriority) {
        this.cardPriority = cardPriority;
    }

    public void setCardVisibility(boolean visibility) {
        this.cardVisibility = visibility;
    }

    public boolean getCardVisibility() {
        return cardVisibility;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);

        /*int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);*/

        setMeasuredDimension(widthSize, 200);
        /*if (widthMode == MeasureSpec.AT_MOST && heightMode == MeasureSpec.AT_MOST) {
        } else if (widthMode == MeasureSpec.AT_MOST) {
            setMeasuredDimension(ScreenUtils.getScreenWidth(), heightSize);
        } else if (heightMode == MeasureSpec.AT_MOST) {
            setMeasuredDimension(widthSize, SizeUtils.dp2px(200));
        }*/
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        setBackgroundColor(Color.WHITE);
        drawTitle(canvas);
        drawContent(canvas);
    }

    protected abstract void drawContent(Canvas canvas);

    public void drawTitle(Canvas c) {
        if (title != null) {
            Paint p = new Paint();
            p.setColor(Color.BLACK);
            p.setTextSize(15);
            p.setTextAlign(Paint.Align.LEFT);
            //画标题
            c.drawText(title, 10, 25, p);
        }
    }
}
