package bean.aqi;

import java.util.List;

public class CityList extends Base {
    private String name;

    private List<City> list;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<City> getList() {
        return list;
    }

    public void setList(List<City> list) {
        this.list = list;
    }
}
