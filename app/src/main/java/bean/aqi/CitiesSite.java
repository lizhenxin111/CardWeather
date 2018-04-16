package bean.aqi;

import java.util.List;

public class CitiesSite extends Base {
    private List<City> cities;
    private List<Site> sites;

    public List<City> getCities() {
        return cities;
    }

    public void setCities(List<City> cities) {
        this.cities = cities;
    }

    public void setSites(List<Site> sites) {
        this.sites = sites;
    }

    public List<Site> getSites() {
        return sites;
    }
}
