package Demo.Dao.Redis;

//import org.springframework.boot.autoconfigure.data.redis.RedisProperties.*;
import Demo.Pojo.Area;
import org.springframework.stereotype.Component;
import redis.clients.jedis.*;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.Set;


@Component("red")
public class Redistest {
    public Jedis jedis;
    public Redistest() {
        //连接本地的 Redis 服务
        jedis = new Jedis("localhost",6379);
        // 如果 Redis 服务设置了密码，需要下面这行，没有就不需要
        jedis.auth("875428767");
        System.out.println("连接成功");
        //查看服务是否运行
    }

    public void setArea(Area area){
        jedis.sadd("areas" , area.getId());
    }

    public Area[] getAreas(){
        Set<String> areas=jedis.smembers("areas");
        LinkedList<Area> areaLinkedList=new LinkedList<Area>();
        Iterator<String> it=areas.iterator();
        while (it.hasNext()){
            String tmp=get(it.next());
            String[] put=tmp.split(";");
            areaLinkedList.add(new Area(put[0],put[1],put[2]));
        }
        return (Area[]) areaLinkedList.toArray();
    }

    public String get(String key){
        Set<String> set=jedis.smembers(key);
        StringBuilder sb=new StringBuilder("");
        Iterator<String> it= set.iterator();
        while (it.hasNext()){
            sb.append(it.next());
            sb.append(";");
        }
        sb.deleteCharAt(sb.length()-1);
        return sb.toString();
    }
}
