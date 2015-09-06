

<%@page contentType="text/html" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
    <s:include value="header.jsp" />
    <script type="text/javascript">

        var websocket;

        window.onload = function() { // execute once the page loads
            initialize();
        };

        function initialize() { // URI = ws://10.16.0.165:8080/chat/chat
            connect('ws://' + window.location.host + '/IdeaBroker/Notificacoes');
        }

        function connect(host) { // connect to the host websocket servlet
            if ('WebSocket' in window)
                websocket = new WebSocket(host);
            else if ('MozWebSocket' in window)
                websocket = new MozWebSocket(host);
            else {
                writeToHistory('Get a real browser which supports WebSocket.');
                return;
            }

            websocket.onopen = onOpen; // set the event listeners below
            websocket.onclose = onClose;
            websocket.onmessage = onMessage;
            websocket.onerror = onError;
        }

        function onOpen(event) {
            doSend(); // call doSend() on enter key     
        }

        function onClose(event) {
            writeToHistory('WebSocket closed.');
        }

        function onMessage(message) { // print the received message
            writeToHistory(message.data);
        }

        function onError(event) {
            writeToHistory('WebSocket error (' + event.data + ').');
        }

        function doSend() {
            message = "";
            websocket.send(message); // send the message
        }

        function writeToHistory(text) {
            var history = document.getElementById('history');
            var p = document.createElement('p');
            p.style.wordWrap = 'break-word';
            p.innerHTML = text;
            history.appendChild(p);
            while (history.childNodes.length > 25)
                history.removeChild(console.firstChild);
            history.scrollTop = history.scrollHeight;
        }
    </script>
</head>
<body>

    <noscript>JavaScript must be enabled for WebSockets to work.</noscript>
    <div>
        <label style="border:0" id="history">bla bla bla</label> 
    </div>
</body>
</html>
