package ui.card;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.text.Layout;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.util.AttributeSet;

import utils.AQIUtil;

public class CardAqiTips extends BaseCard {
    private int aqi = -1;


    private TextPaint paint;

    public CardAqiTips(Context context) {
        super(context);
    }

    public CardAqiTips(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CardAqiTips(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setAqi(int aqi) {
        this.aqi = aqi;
        invalidate();
    }

    public int getAqi() {
        return aqi;
    }

    @Override
    protected void drawContent(Canvas canvas) {

        paint = new TextPaint();
        paint.setTextSize(sp(16));
        paint.setTextAlign(Paint.Align.CENTER);
        paint.setAntiAlias(true);
        paint.setColor(aqi == 0 ? Color.BLACK : AQIUtil.getColor(aqi));

        setTitle("建议");
        drawTitle(canvas);


        drawInfluence(canvas);
        drawMeasure(canvas);
    }

    private void drawInfluence(Canvas c) {
        if (aqi != -1) {
            StaticLayout layout = getStaticLayout(AQIUtil.getInfluence(aqi));
            int textHeight = layout.getHeight();
            c.save();
            int x = width / 4;
            int y = (height - textHeight) / 2;
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
            int x = width *3 / 4;
            int y = (height - textHeight) / 2;
            c.translate(x, y);
            layout.draw(c);
            c.restore();
        }
    }

    private StaticLayout getStaticLayout(String text) {
        return new StaticLayout(text,
                paint,
                width / 3,
                Layout.Alignment.ALIGN_CENTER,
                1f, 0f,
                true);
    }
}
