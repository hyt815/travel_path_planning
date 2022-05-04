package Unit;

//用于拼接正确的url
public class DoURL {
    private static final String  KEY="Y3FBZ-EFNW6-4W6SB-E7DFP-CCV7V-LGBZL";
    private static final String  SK="5JTjGtztdiifSDJyeCNOhb17X8PlKBJW";


    //dns 目标网络，position 存放地址,other 其他参数
    public static String getURL(String dns,String position,String other){
        return "";
    }

    //按关键字从小到大排序
    private static String Sort(String other){
        return "";
    }


    //拼接字符串，获得未经过md5计算的原始字符串
    private static String addother(String positon,String other){
        String str=positon;
        if (other.equals("")){
            return str;
        }
        str+="?";
        String[] others= Sort(other).split("&");
        return "";
    }
}
