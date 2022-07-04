package Demo.Pojo;

public class Area {
    private String id;
    private String fullname;
    private String polygon;

    public Area(String id, String fullname, String polygon) {
        this.id = id;
        this.fullname = fullname;
        this.polygon = polygon;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getPolygon() {
        return polygon;
    }

    public void setPolygon(String polygon) {
        this.polygon = polygon;
    }
}
/*
            {
                    "id": "110101",
                    "fullname": "东城区",
                    "location": {
                    "lat": "39.928353",
                    "lng": "116.416357"
                    },
                    "polygon": [ //行政区划轮廓点串，search及getchildren接口加get_polygon=1/2时返回
                    [116.809403,39.61482,116.790175,39.610555,116.780286,39.593196....],
                    //其它项省略(若有)...
                    ]
                    },*/
