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
