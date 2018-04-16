package bean.card;

public class CardAttr {
    private String tag;
    private int priority;
    private boolean visibility;

    public CardAttr() {

    }

    public CardAttr(String tag, int priority, boolean visibility) {
        this.tag = tag;
        this.priority = priority;
        this.visibility = visibility;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public boolean getVisibility() {
        return visibility;
    }

    public void setVisibility(boolean visibility) {
        this.visibility = visibility;
    }
}
