package ui.card;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.StringRes;
import android.support.v4.util.ArrayMap;
import android.support.v7.widget.CardView;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.ViewGroup;
import android.widget.TextView;

import com.lzx.cardweather.R;


/**
 * 生活指数卡片
 * 当触摸某一项指数时，会显示指数的详情（根据触摸点判断位置、添加TextView）
 * 指数总共有8项，排列如下：
 *      0  1
 *      2  3
 *      4  5
 *      6  7
 */
public class CardLifeStyle extends BaseCard {


    private TextPaint paint;

    private TextView detailView;        //详情View

    /*
    * 生活指数数据，键值对为：简介--详细描述，
    * 顺序为：舒适度、穿衣指数、紫外线、运动、流感、旅游、洗车、空气、
    * */
    private ArrayMap<String, String> data;

    public CardLifeStyle(Context context) {
        super(context);
        init();
    }

    public CardLifeStyle(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CardLifeStyle(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public void requestData(ArrayMap<String, String> data) {
        this.data = data;
        invalidate();
    }

    private void init() {
        detailView = new TextView(getContext());
        detailView.setBackgroundColor(Color.YELLOW);
        CardView.LayoutParams lp = new CardView.LayoutParams(width / 4, ViewGroup.LayoutParams.WRAP_CONTENT);
        lp.gravity = Gravity.CENTER_VERTICAL;
        detailView.setLayoutParams(lp);
        detailView.setTextSize(sp(15));
    }

    @Override
    protected void drawContent(Canvas canvas) {
        setTitle("生活指数");

        paint = new TextPaint();
        paint.setTextSize(sp(10));
        paint.setTextAlign(Paint.Align.CENTER);
        paint.setAntiAlias(true);
        paint.setColor(Color.BLACK);
        paint.setTextAlign(Paint.Align.LEFT);


        drawTexts(canvas);
    }

    /**
     * 画8个指数
     * @param c
     */
    private void drawTexts(Canvas c) {
        if (data != null && data.size() != 0) {
            int lx = width/10, rx = width * 3 / 5;
            for (int i = 0; i < 4; i++) {
                c.drawText(getType(i * 2) + ": " + data.keyAt(i * 2),
                        lx, dp(30) * (i + 2),
                        paint);
                c.drawText(getType(i * 2 + 1) + ": " + data.keyAt(i * 2 + 1),
                        rx, dp(30) * (i + 2),
                        paint);
            }
        }
    }

    int position;       //记录位置

    /**
     * 监听触摸事件，根据触摸点的坐标计算位置并显示详情
     * @param event
     * @return
     */
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {             //按下。记录位置
            position = calculatePosition(event.getX(), event.getY());
            //showDetail(position);
        } else if (event.getAction() == MotionEvent.ACTION_UP || event.getAction() == MotionEvent.ACTION_CANCEL) {      //抬起或取消，移除详情
            //removeDetail();
        }
        /*else if (event.getAction() == MotionEvent.ACTION_MOVE) {      //移动时，如果位置有变，则重新计算位置
            if (position != calculatePosition(event.getX(), event.getY())) {
                removeDetail();
                showDetail(position);
            }
        }*/
        return super.onTouchEvent(event);
    }

    /**
     * 计算position
     * @param pointX    触摸点x坐标
     * @param pointY    触摸点y坐标
     * @return
     */
    private int calculatePosition(float pointX, float pointY) {

        //触摸点不在显示范围，返回-1
        float xBase = 0.1f * width;     //卡片宽度 * 0.1，下面再乘分子即为分数
        if (pointX<xBase  || (pointX>xBase*4 && pointX<xBase*6) || pointX>xBase*9 ||
                pointY<dp(40) || pointY>dp(160)) {
            return -1;
        }

        double yIndex = Math.floor((pointY - dp(40)) / dp(40));
        float xIndex = 0;
        if (pointX >= xBase && pointX <= xBase*4) xIndex = 0;
        if (pointX >= xBase*6 && pointX <= xBase*9) xIndex = 1;
        return (int) (2 * yIndex + xIndex);
    }


    /*private void showDetail(int position) {
        this.removeAllViews();
        if (position == -1) {
            return;
        }
        String text = data.valueAt(position);
        detailView.setText(text);

        int x = position % 2 == 0 ? width * 1/2: width / 10;      //position为偶数，显示在右边，否则显示在左边
        detailView.setX(x);
        this.addView(detailView);
    }

    private void removeDetail() {
        this.removeView(detailView);
    }*/


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
