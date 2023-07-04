        function chooseurl(url,arr){
            if(null == arr ){
                return url;
            }
            url+="?";
            for (let index = 0; index < arr.length-1; index++) {
                url+=arr[index].key+"="+arr[index].value+"&";
                
            }
            url+=arr[arr.length-1].key+"="+arr[arr.length-1].value;
            return url;
        }

        function Getmd5(url,sk){
            var md=md5(url+sk);
            return md;
        }
    
        function Getsig(url,md5){
            url+="&sig="+md5;
            return url;
        }

        function sortBy(props) {
            return function(a,b) {
                return a[props].localeCompare(b[props]);
            }
        }

        function Geturl(dns,url,arr,sk){
            arr.sort(sortBy("key"));
            url=chooseurl(url,arr);

            var md5=Getmd5(url,sk);
            dns+=url;
            url=Getsig(dns,md5);

            return url;
        }

        function Sendurl(src) {
            var script=document.createElement('script');
            script.setAttribute("type","text/javascript");
            script.src = src;
            document.body.appendChild(script);
        }

