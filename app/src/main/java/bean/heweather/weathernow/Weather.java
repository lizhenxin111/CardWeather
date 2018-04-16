package bean.heweather.weathernow;

import bean.heweather.Basic;
import bean.heweather.Update;

public class Weather {
    private Basic basic;
    private Now now;
    private String statuc;
    private Update update;

    public Basic getBasic() {
        return basic;
    }

    public void setBasic(Basic basic) {
        this.basic = basic;
    }

    public Now getNow() {
        return now;
    }

    public void setNow(Now now) {
        this.now = now;
    }

    public String getStatuc() {
        return statuc;
    }

    public void setStatuc(String statuc) {
        this.statuc = statuc;
    }

    public Update getUpdate() {
        return update;
    }

    public void setUpdate(Update update) {
        this.update = update;
    }
}
