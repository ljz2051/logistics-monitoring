<!doctype html>
<html>
<head> 
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="initial-scale=1.0, user-scalable=no, width=device-width">
    <title>Ajax测试</title>   
</head>
<body>
  <button id="ajaxButton" >Ajax获取</button>
    <script type="text/javascript">
        (function () {
            var httpRequest;
            document.getElementById("ajaxButton").onclick = function () { makeRequest('http://restapi.amap.com/v3/place/text?key=BSfLmgG7cMYzjRukm8Xjc8v6hYOZT0lh&children=1&offset=20&page=1&extensions=base'); };

            function makeRequest(url) {
                httpRequest = new XMLHttpRequest();

                if (!httpRequest) {
                    alert('Giving up :( Cannot create an XMLHTTP instance');
                    return false;
                }
                httpRequest.onreadystatechange = alertContents;
                httpRequest.open('GET', url);
                httpRequest.send();
            }
            function alertContents() {
                if (httpRequest.readyState === XMLHttpRequest.DONE) {
                    if (httpRequest.status === 200) {
                        alert(httpRequest.responseText);
                    } else {
                        alert('There was a problem with the request.');
                    }
                }
            }
        })();
  </script> 
</body>
</html>