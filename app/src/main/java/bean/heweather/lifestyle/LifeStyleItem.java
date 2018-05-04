package bean.heweather.lifestyle;

import java.util.List;

import bean.heweather.Basic;
import bean.heweather.Update;

public class LifeStyleItem {
    private Basic basic;
    private List<Item> lifestyle;
    private String status;
    private Update update;

    public Basic getBasic() {
        return basic;
    }

    public void setBasic(Basic basic) {
        this.basic = basic;
    }

    public List<Item> getLifestyle() {
        return lifestyle;
    }

    public void setLifestyle(List<Item> lifestyle) {
        this.lifestyle = lifestyle;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Update getUpdate() {
        return update;
    }

    public void setUpdate(Update update) {
        this.update = update;
    }
}
