package bean.aqi;

public class SiteInfo extends Base{

    private String site;
    private String city;
    private String site_code;

    public String getSite_code() {
        return site_code;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getSite() {
        return site;
    }

    public void setSite_code(String site_code) {
        this.site_code = site_code;
    }

    public String getCity() {
        return city;
    }

    public void setSite(String site) {
        this.site = site;
    }
}
