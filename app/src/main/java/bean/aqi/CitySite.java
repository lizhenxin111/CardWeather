package bean.aqi;

import java.util.List;

public class CitySite extends Base{

    private City city;

    private List<Site> sites;

    public City getCity() {
        return city;
    }

    public List<Site> getSites() {
        return sites;
    }

    public void setCity(City city) {
        this.city = city;
    }

    public void setSites(List<Site> sites) {
        this.sites = sites;
    }
}
