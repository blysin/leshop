<!DOCTYPE HTML>
<html>
<head>
    <title>My WebSocket</title>
    <style>
        .count {
            background-color: #8D8D8D;
        }
    </style>
</head>

<body onkeydown="window.event.keyCode == 13? send():''">

Spring-WebSocket Demo<br/>
<input id="text" type="text"/>
<button onclick="send()">发送</button>
<button onclick="closeWebSocket()">退出</button>
<div id="userCount" class="count"></div>
<div id="message">
</div>
</body>

<script type="text/javascript">
    var websocket = null;

    //判断当前浏览器是否支持WebSocket
    if ('WebSocket' in window) {
        websocket = new WebSocket("ws://localhost:8071/socket/chat?uid=blysin");
    }
    else {
        alert('当前浏览器不支持websocket')
    }

    //连接发生错误的回调方法
    websocket.onerror = function () {
        setMessageInnerHTML("链接失败");
    };

    //连接成功建立的回调方法
    websocket.onopen = function (event) {
        setMessageInnerHTML("链接开启");
    }

    //接收到消息的回调方法
    websocket.onmessage = function (event) {
        setMessageInnerHTML(event.data);
    }

    //连接关闭的回调方法
    websocket.onclose = function () {
        setMessageInnerHTML("关闭中。。。");
    }

    //监听窗口关闭事件，当窗口关闭时，主动去关闭websocket连接，防止连接还没断开就关闭窗口，server端会抛异常。
    window.onbeforeunload = function () {
        websocket.close();
    }

    //将消息显示在网页上
    function setMessageInnerHTML(innerHTML) {
        if (innerHTML.indexOf('当前在线人数') == 0) {
            document.getElementById('userCount').innerHTML = innerHTML
        } else {
            document.getElementById('message').innerHTML += innerHTML + '<br/>';
        }
    }

    //关闭连接
    function closeWebSocket() {
        websocket.close();
    }

    //发送消息
    function send() {
        var message = document.getElementById('text').value;
        websocket.send(message);
        document.getElementById('text').value = '';
    }
</script>
</html>