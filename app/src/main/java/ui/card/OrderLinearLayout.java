package ui.card;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.LinearLayout;

public class OrderLinearLayout extends LinearLayout {
    public OrderLinearLayout(Context context) {
        super(context);
    }

    public OrderLinearLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public OrderLinearLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public OrderLinearLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    /**
     * 调整顺序
     * 步骤： 1。收集所有card并按优先级排序
     *       2。移除所有view
     *       3。重新添加
     */
    /*public void changeOrder() {
        SparseArray<BaseCard> map = new SparseArray<>();
        for (int i = 0; i < getChildCount(); i++) {
            BaseCard card = (BaseCard) getChildAt(i);
            map.put(card.getCardPriority(), card);
        }

        removeAllViews();

        for (int i = 0; i < map.size(); i++) {
            addView(map.valueAt(i));
        }
    }*/
}
