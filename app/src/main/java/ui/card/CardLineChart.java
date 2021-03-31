package ui.card;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.v4.util.ArrayMap;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.blankj.utilcode.util.SizeUtils;
import com.orhanobut.logger.Logger;

import java.util.Collections;

public class CardLineChart extends BaseCard {


    private String xUnit, yUnit = null;                 //横纵坐标的单位
    private ArrayMap<String, Integer> data = null;      //横--纵坐标集合。横坐标为时间，纵坐标为aqi


    private int mDis = 0;   //子控件宽度 和 父控件可显示区域 的差值
    private int pl = 0;     //父控件左边padding
    private int margin = 0;

    private Paint textPaint = new Paint();
    private Paint linePaint = new Paint();

    public CardLineChart(Context context) {
        super(context);
    }

    public CardLineChart(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CardLineChart(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
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

    public ArrayMap<String, Integer> getData() {
        return data;
    }

    public void requestData(ArrayMap<String, Integer> data) {
        this.data = data;
        invalidate();       //重新绘制（onDraw）
        requestLayout();    //刷新数据
    }

    public void releaseData() {
        if (data != null) {
            data.clear();
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        if (data!=null && data.size()>12) {
            width = data.size() * dp(80);
            setMeasuredDimension(width, height);
        }
    }

    @Override
    protected void drawContent(Canvas canvas) {
        margin = getLeft();
        int selfWidth = width;
        int parentWidth = ((View) getParent()).getMeasuredWidth();
        pl = ((View) getParent()).getPaddingLeft();
        int pR = ((View) getParent()).getPaddingRight();

        mDis = selfWidth - (parentWidth - pl - pR - margin);


        canvas.save();
        canvas.translate(dp(40), height - dp(30));

        drawCoordinateSystem(canvas);
        drawLines(canvas);
        canvas.restore();
    }

    /**
     * 画坐标系
     * @param c
     */
    private void drawCoordinateSystem(Canvas c) {
        linePaint.setColor(Color.BLACK);
        linePaint.setStrokeWidth(dp(1));
        linePaint.setStyle(Paint.Style.STROKE);
        int x = width - dp(40) - dp(40);
        int y = -height + dp(50) + dp(8);
        //横轴
        c.drawLine(0, 0,
                x, 0,
                linePaint);
        c.drawLine(x, 0.5f,
                x-5, 3,
                linePaint);
        c.drawLine(x, -0.5f,
                x-4, -3,
                linePaint);
        if (xUnit != null) {
            textPaint.setTextSize(SizeUtils.sp2px(8));
            textPaint.setTextAlign(Paint.Align.LEFT);
            textPaint.setColor(Color.BLACK);
            c.drawText(xUnit,
                    x - 3, dp(15),
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
            textPaint.setTextSize(SizeUtils.sp2px(8));
            textPaint.setTextAlign(Paint.Align.RIGHT);
            textPaint.setColor(Color.BLACK);
            c.drawText(yUnit,
                    -4, y+8,
                    textPaint);
        }
    }

    /**
     * 画里面的线
     * @param c
     */
    private void drawLines(Canvas c) {

        if (data == null || data.size() == 0) {
            return;
        }

        linePaint.setStrokeWidth(4);
        linePaint.setColor(Color.RED);
        textPaint.setColor(Color.BLACK);
        textPaint.setTextSize(sp(10));

        int num = data.size();

        if (num == 1) {
            //画线
            /*c.drawLine(xBase + (i-1)*xDis, -py/scale,
                    xBase + i*xDis,  - y/scale,
                    linePaint);*/
            textPaint.setTextAlign(Paint.Align.CENTER);
            //点的值
            c.drawText(data.valueAt(0) + "",
                    width / 2, -(data.valueAt(0) + dp(5))/getScale(),
                    textPaint);
            linePaint.setColor(Color.LTGRAY);
            c.drawLine(width / 2, -(data.valueAt(0) + dp(5))/getScale(),
                    width / 2, 0,
                    linePaint);
            //横坐标
            c.drawText(data.keyAt(0),
                    width / 2, dp(15),
                    textPaint);
            return;
        }

        int xDis;       //每个刻度的间隔: 总长度-两边padding / 数据数量
        if (num < 12) {
            xDis = (width - dp(40) - dp(40)) / num;
        } else {
            xDis = (width - dp(40) - dp(40)) / num;
        }



        //起始刻度
        int xBase = dp(10);

        //当前点的信息
        String x = data.keyAt(0);
        int y = data.valueAt(0);
        int valueY = -(y + dp(5))/getScale();
        //起点
        //值
        c.drawText(y+"",
                xBase + xBase, valueY,
                textPaint);
        //横
        c.drawText(x,
                xBase + xBase, dp(15),
                textPaint);
        int scale = getScale();
        for (int i = 1; i < num; i++) {
            x = data.keyAt(i);
            y = data.valueAt(i);
            valueY = -(y + dp(5))/getScale();

            String px = data.keyAt(i-1);
            int py = data.valueAt(i-1);

            //画线
            c.drawLine(xBase + (i-1)*xDis, -py/scale,
                    xBase + i*xDis,  - y/scale,
                    linePaint);
            textPaint.setTextAlign(Paint.Align.CENTER);
            //点的值
            c.drawText(y + "",
                    xBase + i*xDis, valueY,
                    textPaint);
            //横坐标
            c.drawText(x,
                    xBase + i*xDis, dp(15),
                    textPaint);
            textPaint.setTextAlign(Paint.Align.RIGHT);

        }
    }


    float lastX = 0;
    float lastY = 0;

    /*@Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        boolean intercept = false;

        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                getParent().requestDisallowInterceptTouchEvent(true);       //禁止父布局处理事件
                if (data!=null && data.size() > 12) {
                    lastX = ev.getX();
                    lastY = ev.getY();
                }
                Logger.d("down");
                break;
            case MotionEvent.ACTION_MOVE:
                Logger.d("move");
                if (data!=null && data.size() > 12){
                    float offX = ev.getX() - lastX;      //滑动的距离
                    float offy = ev.getY() - lastY;
                    int xNow = (int) getX();                    //当前时刻控件左边缘x值

                    *//*if (offX > offy && ((offX>0 && xNow<pl) || (offX<0 && xNow>-mDis))) {
                        offsetLeftAndRight((int) offX);
                    }*//*
                    if (offX > offy) {
                        Logger.d("横移");
                        intercept = true;
                        offsetLeftAndRight((int) offX);
                    } else {
                        Logger.d("竖移");
                        intercept = false;
                    }
                }
                break;
            case MotionEvent.ACTION_UP:
                Logger.d("up");
                intercept = false;
                break;
            default: break;
        }
        return intercept;
    }*/


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        /*if (data!=null && data.size() > 12){
            int x = (int) event.getX();
            int y = (int) event.getY();
            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                lastX = x;
                lastY = event.getY();
            } else if (event.getAction() == MotionEvent.ACTION_MOVE) {
                float offX = event.getX() - lastX;      //滑动的距离
                int xNow = (int) getX();                    //当前时刻控件左边缘x值
                //((NestedScrollView) (getParent().getParent())).setNestedScrollingEnabled(false);
                if (Math.abs(event.getY() - y) > 10) {
                    //return false;
                }
                if ((offX>0 && xNow<pl) || (offX<0 && xNow>-mDis)) {
                    offsetLeftAndRight((int) offX);
                }
            } else if (event.getAction() == MotionEvent.ACTION_UP || event.getAction() == MotionEvent.ACTION_CANCEL) {
                lastY = lastY  =0;
                //view超出预定范围，将view归位
                int xNow = (int) getX();
                if (xNow >= pl) {
                    setX(pl + margin);
                } else if (xNow <= -mDis) {     //setX()的基准是屏幕y轴， 而不是父控件的左边，所以在设置边界是应当以屏幕边缘为基准
                    setX(-mDis + pl);
                }
                //((NestedScrollView) (getParent().getParent())).setNestedScrollingEnabled(true);
            }
            return true;
        }

        if (Math.abs(getX() - lastX) > 20) {
            return false;
        } else {
        }*/

        if (data!=null && data.size() > 12){
            int x = (int) event.getX();
            int y = (int) event.getY();
            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                lastX = x;
                lastY = y;
            } else if (event.getAction() == MotionEvent.ACTION_MOVE) {
                float offX = event.getX() - lastX;      //滑动的距离
                float offy = event.getY() - lastY;
                int xNow = (int) getX();                    //当前时刻控件左边缘x值
                //((NestedScrollView) (getParent().getParent())).setNestedScrollingEnabled(false);
                /*if (Math.abs(event.getY() - y) > 10) {
                    //return false;
                }*/
                if (offX > offy && (offX>0 && xNow<pl) || (offX<0 && xNow>-mDis)) {
                    getParent().requestDisallowInterceptTouchEvent(true);
                    offsetLeftAndRight((int) offX);
                }
            } else if (event.getAction() == MotionEvent.ACTION_UP || event.getAction() == MotionEvent.ACTION_CANCEL) {
                lastY = lastY  =0;
//                getParent().requestDisallowInterceptTouchEvent(false);
                //view超出预定范围，将view归位
                int xNow = (int) getX();
                if (xNow >= pl) {
                    setX(pl + margin);
                } else if (xNow <= -mDis) {     //setX()的基准是屏幕y轴， 而不是父控件的左边，所以在设置边界是应当以屏幕边缘为基准
                    setX(-mDis + pl);
                }
                //((NestedScrollView) (getParent().getParent())).setNestedScrollingEnabled(true);
            }
            return true;
        }

        if (Math.abs(getX() - lastX) > 20) {
            return false;
        } else {
        }

        return super.onTouchEvent(event);
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
