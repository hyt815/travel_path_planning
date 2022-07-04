package Demo.Pojo;

public class Region {
    private long id;
    private String title;
    private String address;
    private double[] location;
    private int adcode;
    private String province;
    private String city;
    private String district;


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public double[] getLocation() {
        return location;
    }

    public void setLocation(double[] location) {
        this.location = location;
    }

    public int getAdcode() {
        return adcode;
    }

    public void setAdcode(int adcode) {
        this.adcode = adcode;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }
}

/*
        {
                "id": "3791814133059418384",唯一标识
                "title": "马莲园", 名称
                "address": "北京市海淀区永丰路马莲园(中国银行信息中心北50米)",地址
                "tel": "",
                "category": "旅游景点:公园",
                "type": 0,
                "location": {
                "lat": 40.03645,
                "lng": 116.269695
                },
                "_distance": 565.78,
                "ad_info": {            	行政区划信息
                "adcode": 110108,           行政区划代码，具体见 https://lbs.qq.com/service/webService/webServiceGuide/webServiceDistrict#6
                "province": "北京市",
                "city": "北京市",
                "district": "海淀区"
                }
                }
*/
