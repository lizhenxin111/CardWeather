package bean.heweather.forecast;

import android.os.Parcel;
import android.os.Parcelable;

public class ForecastItem implements Parcelable {

    /**
     * cond_code_d : 101
     * cond_code_n : 101
     * cond_txt_d : 多云
     * cond_txt_n : 多云
     * date : 2018-04-28
     * hum : 76
     * mr : 17:58
     * ms : 05:25
     * pcpn : 0.2
     * pop : 58
     * pres : 1014
     * sr : 06:19
     * ss : 19:39
     * tmp_max : 27
     * tmp_min : 17
     * uv_index : 8
     * vis : 19
     * wind_deg : 0
     * wind_dir : 无持续风向
     * wind_sc : 1-2
     * wind_spd : 10
     */

    private String cond_code_d;
    private String cond_code_n;
    private String cond_txt_d;
    private String cond_txt_n;
    private String date;
    private String hum;
    private String mr;
    private String ms;
    private String pcpn;                //降水量
    private String pop;                 //降水概率
    private String pres;                //大气压强
    private String sr;
    private String ss;
    private String tmp_max;
    private String tmp_min;
    private String uv_index;
    private String vis;                 //能见度
    private String wind_deg;
    private String wind_dir;
    private String wind_sc;
    private String wind_spd;

    public String getCond_code_d() {
        return cond_code_d;
    }

    public void setCond_code_d(String cond_code_d) {
        this.cond_code_d = cond_code_d;
    }

    public String getCond_code_n() {
        return cond_code_n;
    }

    public void setCond_code_n(String cond_code_n) {
        this.cond_code_n = cond_code_n;
    }

    public String getCond_txt_d() {
        return cond_txt_d;
    }

    public void setCond_txt_d(String cond_txt_d) {
        this.cond_txt_d = cond_txt_d;
    }

    public String getCond_txt_n() {
        return cond_txt_n;
    }

    public void setCond_txt_n(String cond_txt_n) {
        this.cond_txt_n = cond_txt_n;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getHum() {
        return hum;
    }

    public void setHum(String hum) {
        this.hum = hum;
    }

    public String getMr() {
        return mr;
    }

    public void setMr(String mr) {
        this.mr = mr;
    }

    public String getMs() {
        return ms;
    }

    public void setMs(String ms) {
        this.ms = ms;
    }

    public String getPcpn() {
        return pcpn;
    }

    public void setPcpn(String pcpn) {
        this.pcpn = pcpn;
    }

    public String getPop() {
        return pop;
    }

    public void setPop(String pop) {
        this.pop = pop;
    }

    public String getPres() {
        return pres;
    }

    public void setPres(String pres) {
        this.pres = pres;
    }

    public String getSr() {
        return sr;
    }

    public void setSr(String sr) {
        this.sr = sr;
    }

    public String getSs() {
        return ss;
    }

    public void setSs(String ss) {
        this.ss = ss;
    }

    public String getTmp_max() {
        return tmp_max;
    }

    public void setTmp_max(String tmp_max) {
        this.tmp_max = tmp_max;
    }

    public String getTmp_min() {
        return tmp_min;
    }

    public void setTmp_min(String tmp_min) {
        this.tmp_min = tmp_min;
    }

    public String getUv_index() {
        return uv_index;
    }

    public void setUv_index(String uv_index) {
        this.uv_index = uv_index;
    }

    public String getVis() {
        return vis;
    }

    public void setVis(String vis) {
        this.vis = vis;
    }

    public String getWind_deg() {
        return wind_deg;
    }

    public void setWind_deg(String wind_deg) {
        this.wind_deg = wind_deg;
    }

    public String getWind_dir() {
        return wind_dir;
    }

    public void setWind_dir(String wind_dir) {
        this.wind_dir = wind_dir;
    }

    public String getWind_sc() {
        return wind_sc;
    }

    public void setWind_sc(String wind_sc) {
        this.wind_sc = wind_sc;
    }

    public String getWind_spd() {
        return wind_spd;
    }

    public void setWind_spd(String wind_spd) {
        this.wind_spd = wind_spd;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.cond_code_d);
        dest.writeString(this.cond_code_n);
        dest.writeString(this.cond_txt_d);
        dest.writeString(this.cond_txt_n);
        dest.writeString(this.date);
        dest.writeString(this.hum);
        dest.writeString(this.mr);
        dest.writeString(this.ms);
        dest.writeString(this.pcpn);
        dest.writeString(this.pop);
        dest.writeString(this.pres);
        dest.writeString(this.sr);
        dest.writeString(this.ss);
        dest.writeString(this.tmp_max);
        dest.writeString(this.tmp_min);
        dest.writeString(this.uv_index);
        dest.writeString(this.vis);
        dest.writeString(this.wind_deg);
        dest.writeString(this.wind_dir);
        dest.writeString(this.wind_sc);
        dest.writeString(this.wind_spd);
    }

    public ForecastItem() {
    }

    protected ForecastItem(Parcel in) {
        this.cond_code_d = in.readString();
        this.cond_code_n = in.readString();
        this.cond_txt_d = in.readString();
        this.cond_txt_n = in.readString();
        this.date = in.readString();
        this.hum = in.readString();
        this.mr = in.readString();
        this.ms = in.readString();
        this.pcpn = in.readString();
        this.pop = in.readString();
        this.pres = in.readString();
        this.sr = in.readString();
        this.ss = in.readString();
        this.tmp_max = in.readString();
        this.tmp_min = in.readString();
        this.uv_index = in.readString();
        this.vis = in.readString();
        this.wind_deg = in.readString();
        this.wind_dir = in.readString();
        this.wind_sc = in.readString();
        this.wind_spd = in.readString();
    }

    public static final Parcelable.Creator<ForecastItem> CREATOR = new Parcelable.Creator<ForecastItem>() {
        @Override
        public ForecastItem createFromParcel(Parcel source) {
            return new ForecastItem(source);
        }

        @Override
        public ForecastItem[] newArray(int size) {
            return new ForecastItem[size];
        }
    };
}
