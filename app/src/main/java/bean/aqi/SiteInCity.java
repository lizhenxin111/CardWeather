package bean.aqi;

import java.util.List;

public class SiteInCity extends Base{
    private String city;
    private List<SiteInfo> sites;

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public List<SiteInfo> getSites() {
        return sites;
    }

    public void setSites(List<SiteInfo> sites) {
        this.sites = sites;
    }
}
