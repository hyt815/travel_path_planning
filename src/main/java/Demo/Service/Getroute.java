package Demo.Service;

import Demo.Dao.Jdbc.Jdbc;
import Demo.Dao.Redis.Redistest;
import Demo.Pojo.*;
import Demo.Dao.*;
import Demo.Unit.ProcessingTool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service("getroute")
public class Getroute {

    @Autowired
    private Jdbc jd;
    @Autowired
    private Redistest red;

    public String getroute1(Point start, Point end){
        TreeMap<String,String> map=new TreeMap<String, String>();
        Area[] areas= jd.getArea(start,end);
        StringBuilder po=new StringBuilder("");
        //完成--格式匹配--从[a1,b1,c1,d1,e1,f1],[a2,b2,c2,d2,e2,f2],[a3,b3,c3,d3,e3,f3]到a1,b1;c1,d1;e1,f1|a2,b2,c2,d2,e2,f2|a3,b3,c3,d3,e3,f3
        //未完成-每个区域最多16个点
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

    //未完成--最多16个
    private String spiltpoint(String s){
        String regex="([^}]*?\\,[^}]*?)\\,";
        Pattern pattern=Pattern.compile(regex);
        Matcher matcher= pattern.matcher(s);
        StringBuilder stringBuffer=new StringBuilder("");

        int all=matcher.groupCount();


        while (matcher.find()){
            String tmp=matcher.group(1);
            stringBuffer.append(tmp);
            stringBuffer.append(";");
        }
        stringBuffer.deleteCharAt(stringBuffer.length()-1);
        return stringBuffer.toString();
    }

}
