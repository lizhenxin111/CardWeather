package ui.card;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.v7.widget.CardView;
import android.util.AttributeSet;

import com.blankj.utilcode.util.SizeUtils;

public abstract class BaseCard extends CardView {

    public static final String TAG_AQI = "AQI";
    public static final String TAG_AQI_TIPS = "AQITIPS";
    public static final String TAG_WEATHER = "WEATHER";
    public static final String TAG_LIFESTYLE = "LIFESTYLE";
    public static final String TAG_AQICHANGE = "AQICHANGE";
    public static final String TAG_WEATHERFORECAST = "WEATHERFORECAST";


    protected String title = null;
    private int cardPriority = 0;
    private boolean cardVisibility = true;

    protected int width, height;

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

        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);

        width = widthSize;
        if (heightMode == MeasureSpec.EXACTLY) {
            height = heightSize;
        } else if (heightMode == MeasureSpec.AT_MOST){
            height = dp(200);
        } else if (heightMode == MeasureSpec.UNSPECIFIED) {
            height = dp(200);
        }
        setMeasuredDimension(widthSize, height);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //setBackgroundColor(Color.TRANSPARENT);
        drawTitle(canvas);
        drawContent(canvas);
    }

    protected abstract void drawContent(Canvas canvas);

    public void drawTitle(Canvas c) {
        if (title != null) {
            Paint p = new Paint();
            p.setColor(Color.BLACK);
            p.setTextSize(sp(8));
            p.setTextAlign(Paint.Align.LEFT);
            //画标题
            c.drawText(title, dp(4), dp(12), p);
        }
    }

    protected int dp(int dp) {
        return SizeUtils.dp2px(dp);
    }

    protected int sp(int sp) {
        return SizeUtils.sp2px(sp);
    }
}
