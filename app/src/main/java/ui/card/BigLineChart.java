package ui.card;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.v4.util.ArrayMap;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import com.blankj.utilcode.util.SizeUtils;

import java.util.Collections;

public class BigLineChart extends BaseCard {
    private String title = null;

    private String xUnit = null;
    private String yUnit = null;

    private ArrayMap<Integer, Integer> data = new ArrayMap<>();

    private Paint textPaint = new Paint();
    private Paint linePaint = new Paint();

    private int mDis = 0;
    int pl = 0;

    public BigLineChart(Context context) {
        super(context);
    }

    public BigLineChart(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public BigLineChart(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public String getTitle() {
        return title;
    }

    @Override
    public void setTitle(String title) {
        this.title = title;
    }

    public String getxUnit() {
        return xUnit;
    }
    public void setxUnit(String unit) {
        this.xUnit = unit;
        invalidate();
    }

    public String getyUnit() {
        return yUnit;
    }

    public void setyUnit(String yUnit) {
        this.yUnit = yUnit;
        invalidate();
    }

    public ArrayMap<Integer, Integer> getData() {
        return data;
    }

    public void setData(ArrayMap<Integer, Integer> data) {
        this.data = data;
        invalidate();       //重新绘制（onDraw）
        requestLayout();    //刷新数据
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(data.size() * 80 + 200, 200);
    }

    @Override
    protected void drawContent(Canvas canvas) {
        int selfWidth = getMeasuredWidth();
        int parentWidth = ((View) getParent()).getMeasuredWidth();
        pl = ((View) getParent()).getPaddingLeft();
        int pR = ((View) getParent()).getPaddingRight();
        Log.d("SCROLL", "自己：" + selfWidth + "   父控件：" + parentWidth + "   左：" + SizeUtils.px2dp(pl) + "   右：" + pR);

        mDis = selfWidth - (parentWidth - pl - pR);

        canvas.save();
        canvas.translate(50, 170);

        drawCoordinateSystem(canvas);
        drawLine(canvas);
        //drawLines(canvas);
        canvas.restore();
    }

    int lastX = 0;
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int x = (int) event.getX();
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            lastX = x;
        } else if (event.getAction() == MotionEvent.ACTION_MOVE) {
            int offX = (int) event.getX() - lastX;
            int xNow = (int) getX();
            Log.d("SCROLL", offX + "  " + (-mDis) + "  " + getX());
            if ((offX>0 && xNow<pl) || (offX<0 && xNow>-mDis)) {
                offsetLeftAndRight(offX);
            }
        } else if (event.getAction() == MotionEvent.ACTION_UP || event.getAction() == MotionEvent.ACTION_CANCEL) {
            int xNow = (int) getX();
            if (xNow >= pl) {
                setX(pl);
            } else if (xNow <= -mDis) {
                setX(-mDis);
            }
        }
        return true;
    }

    /**
     * 画坐标系
     * @param c
     */
    private void drawCoordinateSystem(Canvas c) {
        linePaint.setColor(Color.BLACK);
        linePaint.setStrokeWidth(5);
        linePaint.setStyle(Paint.Style.STROKE);
        int x = getMeasuredWidth() - 30;
        int y = -getMeasuredHeight() + 70;
        //横轴
        c.drawLine(0, 0,
                x - 100, 0,
                linePaint);
        c.drawLine(x - 30, 0.5f,
                x+100, 3,
                linePaint);
        c.drawLine(x - 30, -0.5f,
                x-34, -3,
                linePaint);
        if (xUnit != null) {
            textPaint.setTextSize(10);
            textPaint.setTextAlign(Paint.Align.LEFT);
            textPaint.setColor(Color.BLACK);
            c.drawText(xUnit,
                    x - 30, 13,
                    textPaint);
        }

        //纵轴
        c.drawLine(0, 0,
                0, y,
                linePaint);
        c.drawLine(-0.5f, y,
                -3, y+4,
                linePaint);
        c.drawLine(0.5f, y,
                3, y+4,
                linePaint);
        if (yUnit != null) {
            textPaint.setTextSize(10);
            textPaint.setTextAlign(Paint.Align.RIGHT);
            textPaint.setColor(Color.BLACK);
            c.drawText(yUnit,
                    -4, y+8,
                    textPaint);
        }
    }

    private void drawLine(Canvas c) {
        if (data!=null && data.size()!=0) {
            linePaint.setStrokeWidth(4);
            linePaint.setColor(Color.RED);
            textPaint.setColor(Color.GREEN);
            textPaint.setTextSize(15);
            for (int i = 1; i < data.size(); i++) {
                int x = i;
                int y = -data.valueAt(x);

                int dx = i - 1;
                int dy = -data.valueAt(x);

                c.drawLine(dx * 80, dy,
                        x * 80, y,
                        linePaint);
                c.drawText(i + "",
                        dx * 80, 5,
                        textPaint);
            }
        }
    }

    /**
     * 画里面的线
     * @param c
     */
    private void drawLines(Canvas c) {
        if (data != null && data.size() != 0) {
            int num = data.size();
            linePaint.setColor(Color.BLACK);
            linePaint.setStrokeWidth(2);
            linePaint.setStyle(Paint.Style.STROKE);
            //每个刻度的间隔
            int xDis = (getMeasuredWidth() - 100) / (num  - 1);
            //起始刻度
            int xBase = 20;
            int yBase = -10;

            int scale = getScale();


            //起点
            textPaint.setTextAlign(Paint.Align.CENTER);
            textPaint.setTextSize(15);
            //值
            c.drawText(data.valueAt(0) + "",
                    xBase, yBase - (data.valueAt(0))/scale,
                    textPaint);
            //横
            c.drawText(data.keyAt(0)+"",
                    xBase, 15,
                    textPaint);

            //当前点---前一个点 画直线
            for (int i = 1; i < num; i++) {
                int x = data.keyAt(i);
                int y = data.valueAt(i);

                int px = data.keyAt(i-1);
                int py = data.valueAt(i-1);

                //画线
                c.drawLine(xBase + (i-1)*xDis, -py/scale,
                        15 * y,  - y/scale,
                        linePaint);
                textPaint.setTextAlign(Paint.Align.CENTER);
                //点的值
                c.drawText(y + "",
                        15 * y, (yBase - y)/scale,
                        textPaint);
                //横坐标
                c.drawText(x+"",
                        15 * y, 15,
                        textPaint);
                textPaint.setTextAlign(Paint.Align.RIGHT);

            }
        }
    }

    /**
     * 卡片的宽有限，所以当纵坐标过大时需要将其按比例缩小
     * @return
     */
    private int getScale() {
        int max = Collections.max(data.values());
        return max / 100 > 1 ? max / 100 : 1;
    }
}
