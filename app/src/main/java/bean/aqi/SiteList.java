package bean.aqi;

import java.util.List;

public class SiteList extends Base{
    private String name;
    private String code;
    private List<Site> list;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Site> getList() {
        return list;
    }

    public void setList(List<Site> list) {
        this.list = list;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
