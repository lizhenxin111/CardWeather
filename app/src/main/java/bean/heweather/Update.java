package bean.heweather;

public class Update {
    private String loc;     //当前时间
    private String utc;     //UTC时间

    public String getLoc() {
        return loc;
    }

    public void setLoc(String loc) {
        this.loc = loc;
    }

    public String getUtc() {
        return utc;
    }

    public void setUtc(String utc) {
        this.utc = utc;
    }
}
