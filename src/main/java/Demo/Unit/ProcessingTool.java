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
    public static final String KEY = "Y3FBZ-EFNW6-4W6SB-E7DFP-CCV7V-LGBZL";
    private static final String SK = "5JTjGtztdiifSDJyeCNOhb17X8PlKBJW";

    //使用完整的url，调用api并返回内容
    public static String sendurl(String url) {

        //发送向腾讯地图GET请求流程
        CloseableHttpClient httpClient = HttpClients.createDefault();

        URI uri = null;
        try {
            uri = new URIBuilder(url).build();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        HttpGet httpGet = new HttpGet(uri);
        CloseableHttpResponse response = null;
        String content = null;

        try {
            //发送请求并将响应存入response
            response = httpClient.execute(httpGet);
            if (response.getStatusLine().getStatusCode() == 200) {
                content = EntityUtils.toString(response.getEntity(), "UTF-8");
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (response != null) {
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
    public static String getURL(String dns, String position, TreeMap<String, String> other) {
        String md5 = md5count(position, other);
        String url = dns + md5;
        return url;
    }

    private static String md5count(String position, TreeMap<String, String> other) {
        StringBuilder tmp = new StringBuilder(position);
        String end = "";
        //至少有"key"参数（秘钥）
        tmp.append("?");
        for (String key : other.keySet()) {
            tmp.append(key);
            tmp.append("=");
            tmp.append(other.get(key));
            tmp.append("&");
        }
        end = tmp.toString();
        //去除末尾的“&”
        tmp.deleteCharAt(tmp.length() - 1);
        tmp.append(SK);

        String md5 = DigestUtils.md5DigestAsHex(tmp.toString().getBytes());
        end += "sig=" + md5;
        return end;
    }

}

