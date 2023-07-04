var xhr=new XMLHttpRequest();
var gps="39.90469,116.40717";
var map;
function initMap(a,b) {
    //定义地图中心点坐标
    var center = new TMap.LatLng(a, b);
    //定义map变量，调用 TMap.Map() 构造函数创建地图
    map = new TMap.Map(document.getElementById('container'), {
        center: center,//设置地图中心点坐标
        viewMode:'2D',//设置显示模式 2D 3D可以自己修改
        zoom: 17.2,   //设置地图缩放级别
        pitch: 43.5,  //设置俯仰角
        rotation: 45    //设置地图旋转角度
    });
}

function addpolymap(map) {
    var polylineLayer = new TMap.MultiPolyline({
        id: 'polyline-map', //图层唯一标识
        map: map,//设置折线图层显示到哪个地图实例中
        //折线样式预设，可以从文档查
        styles: {
            'style_green': new TMap.PolylineStyle({
                'color': '#37ff69', //线填充色
                'width': 6, //折线宽度
                'borderWidth': 5, //边线宽度
                'borderColor': '#FFF', //边线颜色
                'lineCap': 'butt' //线端头方式
            }),
            'style_red': new TMap.PolylineStyle({
                'color': '#CC0000', //线填充色
                'width': 6, //折线宽度
                'borderWidth': 5, //边线宽度
                'borderColor': '#CCC', //边线颜色
                'lineCap': 'round' //线端头方式
            })
        },
        //折线数据定义
        geometries: []
    });
    return polylineLayer;
}


function addpolyline(polymap,id,styleId,paths) {
    polymap.add(
        {    'id': id,
            'styleId': styleId,
            'paths': paths
        }
    );
}

function removepolyline(polymap,id) {
    polymap.remove(id);
}

function updatepolyline(polymap,id,styleId,paths) {
    paths = paths.map(p => {
        return new TMap.LatLng(p[0], p[1]);
    });
    polymap.updateGeometries(
        {   'id': id,
            'styleId': styleId,
            'paths': paths
        })
}

function drawroute(pol){
    var coors=pol;
    for (var i = 2; i < coors.length ; i++)
    {coors[i] = coors[i-2] + coors[i]/1000000}
    var pl = [];
    for (var i = 0; i < coors.length; i += 2) {
        pl.push(new TMap.LatLng(coors[i], coors[i+1]));
    }
    var polylineLayer =addpolymap(map);
    for (var i=0;i<coors.length;i+=2){
        addpolyline(polylineLayer,i,
            'style_green'
        ,pl);
    }
}


function getfind() {
    if (xhr.readyState==4){
        if (xhr.status==200){
            var arry = JSON.parse(xhr.responseText);
            var pol=arry.result.routes[0].polyline;
            drawroute(pol);
        }
    }
}

function getroute(){
    var start=document.getElementById("from").innerText;
    var end=document.getElementById("to").innerText;
    xhr.open("get","/getroute?start=39.932026,116.380867&end=39.9122,116.557367");
    xhr.onreadystatechange=getfind;
    xhr.send(null);
}
