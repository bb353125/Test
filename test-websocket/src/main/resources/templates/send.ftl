<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>keeko</title>
    <script type="text/javascript" src="/js/plugins/jquery.form.js"></script>
    <script type="text/javascript" src="/js/plugins/jquery.twbsPagination.min.js"></script>
    <script type="text/javascript" src="/js/My97DatePicker/WdatePicker.js" ></script>
    <script type="text/javascript">

    </script>
</head>
<body>

<div class="container-fluid cm-container-white">
    <h2 style="margin-top:0;">发送消息</h2>
    <input type="text" class="logisticCode" value="" disabled="disabled">
    <p></p>


</div>

<input type="text" id="content" name="content" maxlength="50" style="width: 500px; height: 200px"/>
<li>
    <button onclick="send()" onmouseover="this.style.backgroundColor='#00BB00';"
            onmouseout="this.style.backgroundColor='';" >发送</button>
</li>

</body>
<script src="/static/js/jquery.min.js" type="text/javascript"></script>
<script type="text/javascript">
    var websocket = null; //声明一个socket

    //判断当前浏览器是否支持WebSocket
    if ('WebSocket' in window) {
        websocket = new WebSocket("ws://localhost:8080/websocket?mac=456");
    }
    else {
        alert('Not support websocket')
    }

    //发送消息
    function send() {
        var message = document.getElementById('content').value;
        websocket.send(message);
    }

    //连接发生错误的回调方法
    websocket.onerror = function () {
        setMessageInnerHTML("error");
    };

    //连接成功建立的回调方法
    websocket.onopen = function (event) {
        setMessageInnerHTML("open");
    };

    //接收到消息的回调方法
    websocket.onmessage = function (event) {
        console.log(event);
        setMessageInnerHTML(event.data);
    };

    //连接关闭的回调方法
    websocket.onclose = function () {
        setMessageInnerHTML("close");
    };

    //监听窗口关闭事件，当窗口关闭时，主动去关闭websocket连接，防止连接还没断开就关闭窗口，server端会抛异常。
    window.onbeforeunload = function () {
        websocket.close();
    };

    //将消息显示在网页上
    function setMessageInnerHTML(innerHTML) {
        //console.log(innerHTML);
        document.getElementById('message').innerHTML += innerHTML + '<br/>';
    }

    //关闭连接
    function closeWebSocket() {
        websocket.close();
    }
</script>
</html>