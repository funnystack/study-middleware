<!DOCTYPE html>
<html lang="en" xmlns="http://www.w3.org/1999/html">
<head>
    <meta charset="UTF-8">
    <title>demo1</title>
    <style type="text/css">
        #connect-container {
            float: left;
            width: 400px
        }

        #connect-container div {
            padding: 5px;
        }

        #console-container {
            float: left;
            margin-left: 15px;
            width: 400px;
        }

        #console {
            border: 1px solid #CCCCCC;
            border-right-color: #999999;
            border-bottom-color: #999999;
            height: 380px;
            width: 800px;
            overflow-y: auto;
            padding: 5px;
        }

        #console p {
            padding: 0;
            margin: 0;
        }
    </style>
    <script src="jquery.min.js"></script>
    <script type="application/javascript">

        var socket = null;

        function init() {
            var obj = document.getElementById('ws');
            obj.value = "ws://" + window.location.hostname + ":" + "${serverPort}" +"/call?token=5d63f6ec8ed20b14857f720c7cd5f639268723435eafccd7fc68101e91215f87";
        }

        function setConnected(connected) {
            document.getElementById('connect').disabled = connected;
            document.getElementById('disconnect').disabled = !connected;

            // if (connected) {
            //     echo(document.getElementById('loginText').value);
            // }
        }

        function connect() {
            var target = document.getElementById('ws').value;
            log('websocket url:' + target);
            if (target == '') {
                alert('Please select server side connection implementation.');
                return;
            }

            if (window.WebSocket) {
                socket = new WebSocket(target);
                socket.onmessage = function (event) {
                    log(dateFtt("hh:mm:ss:S",new Date()) + ":" + event.data);
                };
                var timer;
                socket.onopen = function (event) {
                    log('Info: WebSocket connection opened.');
                    var pingTime = 10 * 1000;
                    setConnected(true);
                    timer = window.setInterval(function () {
                        var ping = "{'cmd':'PING' , 'sequence': " + new Date().getTime() + " }";
                        socket.send(ping);
                    }, pingTime);
                };
                socket.onclose = function (event) {
                    setConnected(false);
                    clearInterval(timer);
                    log('Info: WebSocket connection closed, Code: ' + event.code + (event.reason == "" ? "" : ", Reason: " + event.reason));
                };
            } else {
                alert("您的浏览器不支持WebSocket协议！");
            }
        }

        function disconnect() {
            if (socket != null) {
                socket.close();
                socket = null;
            }
            setConnected(false);
        }

        function call() {
            var data = {
                cmd: 'MAKE_CALL',
            }
            var message = JSON.stringify(data);
            if (socket != null) {
                log('Sent: ' + message);
                socket.send(message);
            } else {
                alert('WebSocket connection not established, please connect.');
            }
        }

        /**************************************时间格式化处理************************************/
        function dateFtt(fmt,date)
        { //author: meizz
            let o = {
                "M+" : date.getMonth()+1,                 //月份
                "d+" : date.getDate(),                    //日
                "h+" : date.getHours(),                   //小时
                "m+" : date.getMinutes(),                 //分
                "s+" : date.getSeconds(),                 //秒
                "q+" : Math.floor((date.getMonth()+3)/3), //季度
                "S"  : date.getMilliseconds()             //毫秒
            };
            if(/(y+)/.test(fmt))
                fmt=fmt.replace(RegExp.$1, (date.getFullYear()+"").substr(4 - RegExp.$1.length));
            for(var k in o)
                if(new RegExp("("+ k +")").test(fmt))
                    fmt = fmt.replace(RegExp.$1, (RegExp.$1.length==1) ? (o[k]) : (("00"+ o[k]).substr((""+ o[k]).length)));
            return fmt;
        }

        function log(message) {
            if (message.indexOf('pong') != -1) {
                return;
            }
            var console = document.getElementById('console');
            var p = document.createElement('p');
            p.style.wordWrap = 'break-word';
            p.appendChild(document.createTextNode(message));
            console.appendChild(p);
            // while (console.childNodes.length > 30) {
            //     console.removeChild(console.firstChild);
            // }
            console.scrollTop = console.scrollHeight;
        }

        function clearConsole() {
            var console = document.getElementById('console');
            while (console.childNodes.length > 0) {
                console.removeChild(console.firstChild);
            }
        }


        document.addEventListener("DOMContentLoaded", function () {
            // Remove elements with "noscript" class - <noscript> is not allowed in XHTML
            var noscripts = document.getElementsByClassName("noscript");
            for (var i = 0; i < noscripts.length; i++) {
                noscripts[i].parentNode.removeChild(noscripts[i]);
            }
        }, false);
    </script>
</head>
<body onload="init()">
<div class="noscript"><h2 style="color: #ff0000">Seems your browser doesn't support Javascript! Websockets rely on
        Javascript being enabled. Please enable
        Javascript and reload this page!</h2></div>
<div>
    <div id="connect-container">

        <div>
            <input id="ws" value="" type="text" size="40" style="width: 350px"/>
        </div>
        <div>
            <button id="connect" onclick="connect();">Connect</button>
            <button id="disconnect" disabled="disabled" onclick="disconnect();">Disconnect</button>
        </div>


        <div>
            <label>信息:</label>
            <input type="text" id="called" value="">
        </div>
        <div>
            <button id="callout" onclick="call();"> 外呼</button>
        </div>
    </div>
    <div id="console-container">
        <div id="console"/>
    </div>

    <div>
        <button id="clearConsole" onclick="clearConsole();">clear message</button>
    </div>
</div>
</body>
</html>
