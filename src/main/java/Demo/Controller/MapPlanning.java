package Demo.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import Demo.Pojo.*;
import Demo.Service.*;

@Controller
public class MapPlanning {

    @Autowired
    private Route route;

    @ResponseBody
    @RequestMapping(value = "/getroute",method = RequestMethod.GET)
    public String route(String start,String end){
            Point from;
            Point to;
            String[] tmp1=start.split(",");
            String[] tmp2=end.split(",");
            from=new Point(tmp1[0],tmp1[1]);
            to=new Point(tmp2[0],tmp2[1]);
            return route.getroute1(from,to);
    }



    @RequestMapping(value = {"/","/index.html"})
    public String index(){
        return "index";
    }


}
