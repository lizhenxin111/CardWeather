package bean.heweather.forecast;

import java.util.List;

import bean.heweather.Basic;
import bean.heweather.Update;

public class Forecast {
    private Basic basic;
    private List<ForecastItem> daily_forecast;
    private String status;
    private Update update;

    public Basic getBasic() {
        return basic;
    }

    public void setBasic(Basic basic) {
        this.basic = basic;
    }

    public List<ForecastItem> getDaily_forecast() {
        return daily_forecast;
    }

    public void setDaily_forecast(List<ForecastItem> daily_forecast) {
        this.daily_forecast = daily_forecast;
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
