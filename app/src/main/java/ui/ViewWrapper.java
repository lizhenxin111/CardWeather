package ui;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

/**
 * 用于属性动画的view包装类
 */
public class ViewWrapper {
    private int width;
    private int height;
    private int weight;     //出错
    private int size;     //长和宽

    private View view;

    public ViewWrapper(View v) {
        view = v;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
        view.getLayoutParams().width = width;
        view.requestLayout();
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
        view.getLayoutParams().height = height;
        view.requestLayout();
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
        ((LinearLayout.LayoutParams) view.getLayoutParams()).weight = weight;
        view.requestLayout();
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
        view.getLayoutParams().width = size;
        view.getLayoutParams().height = size;
        view.requestLayout();
    }
}