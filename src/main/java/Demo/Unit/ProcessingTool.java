package Demo.Unit;

import org.springframework.stereotype.Component;
import org.springframework.util.DigestUtils;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.TreeMap;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;




//api处理工具
public class ProcessingTool {
    public static final String  KEY="Y3FBZ-EFNW6-4W6SB-E7DFP-CCV7V-LGBZL";
    private static final String  SK="5JTjGtztdiifSDJyeCNOhb17X8PlKBJW";

    //使用完整的url，调用api并返回内容
    public static String sendurl(String url){

        //发送向腾讯地图GET请求流程
        CloseableHttpClient httpClient = HttpClients.createDefault();
//        String path="https://apis.map.qq.com/ws/district/v1/search";

        URI uri=null;
        try {
            //创建URI并设置参数.setParameter("sig","c9197ed32f6bdf340835714e6d3a56d3")
//            uri = new URIBuilder(path).setParameter("get_polygon","2").setParameter("key",KEY).setParameter("keyword","120000").setParameter("sig","fd83c0be0d006f11cdbc48fcd00ecc7b").build();
            uri=new URIBuilder(url).build();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        System.out.println(uri);
        HttpGet httpGet=new HttpGet(uri);
        CloseableHttpResponse response = null;
        String content=null;

        try {
            //发送请求并将响应存入response
            response = httpClient.execute(httpGet);
            if (response.getStatusLine().getStatusCode() == 200 ){
                content = EntityUtils.toString(response.getEntity(),"UTF-8");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if (response != null ){
                try {
                    response.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            try {
                httpClient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


        return content;
    }
    //得到完整的url，参数说明：dns 域名，position 请求路径（以“/”开头）,other 其他参数
    public static String getURL(String dns,String position,TreeMap<String,String> other){
        String md5=md5count(position,other);
        String url=dns+md5;
        return url;
    }

    private static String md5count(String position,TreeMap<String,String> other){
        StringBuilder tmp= new StringBuilder(position);
        String end="";
        //至少有"key"参数（秘钥）
        tmp.append("?");
        for (String key:other.keySet()) {
            tmp.append(key);
            tmp.append("=");
            tmp.append(other.get(key));
            tmp.append("&");
        }
        end=tmp.toString();
        //去除末尾的“&”
        tmp.deleteCharAt(tmp.length()-1);
        tmp.append(SK);

        String md5=DigestUtils.md5DigestAsHex(tmp.toString().getBytes());
        end+="sig="+md5;
        return end;
    }


/*    public static void main(String[] args) {
        String dns="https://apis.map.qq.com";
        String position="/ws/location/v1/ip";
        TreeMap<String,String> other=new TreeMap<String, String>();
        other.put("key","Y3FBZ-EFNW6-4W6SB-E7DFP-CCV7V-LGBZL");
        other.put("callback","cbfrom");
        other.put("output","jsonp");
        String URL=getURL(dns,position,other);
        System.out.println(URL);
        String de="https://apis.map.qq.com/ws/location/v1/ip?callback=cbfrom&key=Y3FBZ-EFNW6-4W6SB-E7DFP-CCV7V-LGBZL&output=jsonp&sig=540a193da90c355ae7ea08e52bcf4dac";
        System.out.println(URL.equals(de));
        String con=sendurl(URL);
        System.out.println(con);
    }*/

/*    public static void main(String[] args) {
        String dns="https://apis.map.qq.com";
        String position="/ws/district/v1/search";
        TreeMap<String,String> map=new TreeMap<String, String>();
        map.put("key","Y3FBZ-EFNW6-4W6SB-E7DFP-CCV7V-LGBZL");
        map.put("keyword","130681");
        map.put("get_polygon","2");
        String URL=getURL(dns,position,map);
        System.out.println(URL);
    }*/
}
