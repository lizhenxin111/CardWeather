package bean.card;

import java.util.ArrayList;
import java.util.List;

public class CardAttrList {
    private ArrayList<CardAttr> list = null;
    public int size() {
        return list.size();
    }


    public CardAttrList() {
        list = new ArrayList<>();
    }

    public CardAttrList(List<CardAttr> list) {
        this.list = new ArrayList<>(list);
    }


    public void add(CardAttr attr) {
        list.add(attr);
    }

    public CardAttr get(String tag) {
        for (CardAttr attr : list) {
            if (attr.getTag().equals(tag)) {
                return attr;
            }
        }
        return null;
    }

    public int getIndex(String tag) {
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getTag().equals(tag)) {
                return i;
            }
        }
        return list.size();
    }

    public CardAttr get(int index) {
        return list.get(index);
    }

    public ArrayList<CardAttr> getList() {
        return list;
    }


}
