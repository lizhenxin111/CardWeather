package bean.aqi;

public class City extends Base{
    private String city;
    private String primary_pollutant;
    private String quality;
    private String date;
    private String hour;
    private String time;
    private float aqi;
    private float pm2_5;
    private float pm2_5_24h;
    private float pm10;
    private float pm10_24h;
    private float so2;
    private float so2_24h;
    private float no2;
    private float no2_24h;
    private float o3;
    private float o3_24h;
    private float o3_8h;
    private float o3_8h_24h;
    private float co;
    private float co_24h;


    public City() {
    }

    public City(float aqi) {
        this.aqi = aqi;
    }

    public String getHour() {
        return hour;
    }

    public void setHour(String hour) {
        this.hour = hour;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public float getAqi() {
        return aqi == 0 ? 0 : aqi;
    }

    public void setAqi(float aqi) {
        this.aqi = aqi;
    }

    public float getCo() {
        return co == 0 ? 0 : co;
    }

    public void setCo(float co) {
        this.co = co;
    }

    public float getCo_24h() {
        return co_24h == 0 ? 0 : co_24h;
    }

    public void setCo_24h(float co_24h) {
        this.co_24h = co_24h;
    }

    public float getNo2() {
        return no2 == 0 ? 0 : no2;
    }

    public void setNo2(float no2) {
        this.no2 = no2;
    }

    public float getNo2_24h() {
        return no2_24h == 0 ? 0 : no2_24h;
    }

    public void setNo2_24h(float no2_24h) {
        this.no2_24h = no2_24h;
    }

    public float getO3() {
        return o3 == 0 ? 0 : o3;
    }

    public void setO3(float o3) {
        this.o3 = o3;
    }

    public float getO3_24h() {
        return o3_24h == 0 ? 0 : o3_24h;
    }

    public void setO3_24h(float o3_24h) {
        this.o3_24h = o3_24h;
    }

    public float getO3_8h() {
        return o3_8h == 0 ? 0 : o3_8h;
    }

    public void setO3_8h(float o3_8h) {
        this.o3_8h = o3_8h;
    }

    public float getO3_8h_24h() {
        return o3_8h_24h == 0 ? 0 : o3_8h_24h;
    }

    public void setO3_8h_24h(float o3_8h_24h) {
        this.o3_8h_24h = o3_8h_24h;
    }

    public float getPm10() {
        return pm10 == 0 ? 0 : pm10;
    }

    public void setPm10(float pm10) {
        this.pm10 = pm10;
    }

    public float getPm10_24h() {
        return pm10_24h == 0 ? 0 : pm10_24h;
    }

    public void setPm10_24h(float pm10_24h) {
        this.pm10_24h = pm10_24h;
    }

    public float getPm2_5() {
        return pm2_5 == 0 ? 0 : pm2_5;
    }

    public void setPm2_5(float pm2_5) {
        this.pm2_5 = pm2_5;
    }

    public float getPm2_5_24h() {
        return pm2_5_24h == 0 ? 0 : pm2_5_24h;
    }

    public void setPm2_5_24h(float pm2_5_24h) {
        this.pm2_5_24h = pm2_5_24h;
    }


    public String getPrimary_pollutant() {
        return primary_pollutant == null ? "" : primary_pollutant;
    }

    public void setPrimary_pollutant(String primary_pollutant) {
        this.primary_pollutant = primary_pollutant;
    }

    public String getQuality() {
        return quality;
    }

    public void setQuality(String quality) {
        this.quality = quality;
    }

    public float getSo2() {
        return so2 == 0 ? 0 : so2;
    }

    public void setSo2(float so2) {
        this.so2 = so2;
    }

    public float getSo2_24h() {
        return so2_24h == 0 ? 0 : so2_24h;
    }

    public void setSo2_24h(float so2_24h) {
        this.so2_24h = so2_24h;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
