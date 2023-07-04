package Demo.Service;

import Demo.Dao.Redis.Redistest;
import Demo.Pojo.*;
import Demo.Unit.ProcessingTool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service("getroute")
public class Getroute {

    //数据库连接类
    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private Redistest red;

    public String table="area";

    public String getroute1(Point start, Point end){
        TreeMap<String,String> map=new TreeMap<String, String>();
        Area[] areas=getArea(start,end);
        StringBuilder po=new StringBuilder("");
        //完成--格式匹配--从[a1,b1,c1,d1,e1,f1],[a2,b2,c2,d2,e2,f2],[a3,b3,c3,d3,e3,f3]到a1,b1;c1,d1;e1,f1|a2,b2,c2,d2,e2,f2|a3,b3,c3,d3,e3,f3
        for(Area a:areas){
            String tmp=spiltPolygon(a.getPolygon());
            po.append(tmp);
            po.append("|");
        }
        if (!po.toString().equals("")){
            po.deleteCharAt(po.length()-1);
            map.put("avoid_polygons",po.toString());
        }
        map.put("key",ProcessingTool.KEY);
        map.put("from",start.toString());
        map.put("to",end.toString());
        String url=ProcessingTool.getURL("https://apis.map.qq.com","/ws/direction/v1/driving",map);
        System.out.println(url);
        return ProcessingTool.sendurl(url);
    }

    public Area[] getArea(Point start,Point end){

        String sql = "select * from "+table;
        List<Map<String, Object>> list =  jdbcTemplate.queryForList(sql);
        LinkedList<Area> areaLinkedList = new LinkedList<Area>();
        for (Map<String, Object> map : list) {
            Set<Entry<String, Object>> entries = map.entrySet( );
            if(entries != null) {
                Iterator<Entry<String, Object>> iterator = entries.iterator( );
                Area area;
                String[] str=new String[3];
                int i=0;
                while(iterator.hasNext( )) {
                    Entry<String, Object> entry =(Entry<String, Object>) iterator.next( );
                    Object key = entry.getKey( );
                    Object value = entry.getValue();
                    str[i]=(String) value;
                    i++;
                }
                area=new Area(str[0],str[1],str[2]);
                areaLinkedList.add(area);
            }
        }
        Area[] areas=areaLinkedList.toArray(new Area[areaLinkedList.size()]);
        return areas;
    }

    private String spiltPolygon(String S){
        //使用正则表达式进行匹配、分割字符串
        String regex="\\[(.*?)]";
        Pattern pattern=Pattern.compile(regex);
        Matcher matcher= pattern.matcher(S);
        StringBuilder stringBuilder=new StringBuilder("");
        while (matcher.find()){
            String tmp=matcher.group(1)+",";
            tmp=spiltpoint(tmp);
            stringBuilder.append(tmp);
            stringBuilder.append("|");
        }
        stringBuilder.deleteCharAt(stringBuilder.length()-1);
        return stringBuilder.toString();
    }

    //完成--最多16个顶点
    private String spiltpoint(String s){
        String regex="([^}]*?\\,[^}]*?)\\,";
        Pattern pattern=Pattern.compile(regex);
        Matcher matcher= pattern.matcher(s);
        StringBuilder stringBuffer=new StringBuilder("");

        while (matcher.find()){
            String tmp=matcher.group(1);
            stringBuffer.append(tmp);
            stringBuffer.append(";");
        }
        stringBuffer.deleteCharAt(stringBuffer.length()-1);

        String[] less=stringBuffer.toString().split(";");
        int all=less.length;
        System.out.println(all);
        if (all>16) {
            stringBuffer = new StringBuilder("");
            int a=all/16;
            for (int i = 0; i < 16; i++) {
                stringBuffer.append(less[i*16]);
                stringBuffer.append(";");
                System.out.println(i);
            }
            stringBuffer.deleteCharAt(stringBuffer.length()-1);
        }
        return stringBuffer.toString();
    }



}
