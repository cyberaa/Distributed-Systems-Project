<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"  pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
    <head>
        <s:include value="header.jsp" />
        <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
        <script type="text/javascript">
            window.onload = setupRefresh;

            function setupRefresh() {
                setTimeout("refreshPage();", 60000); // milliseconds
            }
            function refreshPage() {
                window.location = location.href;
            }
        </script>
        <script type="text/javascript">
            var div = document.getElementById("idNotificacao");
            setTimeout('div.innerHTML=""', 10000);
        </script>
        <script type="text/javascript">
            var websocket;

            window.onload = function() { // execute once the page loads
                initialize();
                document.getElementById("idNotificacao").focus();
            }

            function initialize() { // URI = ws://192.168.1.68:8080/mainPage
                connect('ws://' + window.location.host + '/IdeaBroker/principal');
            }

            function connect(host) { // connect to the host websocket servlet
                if ('WebSocket' in window) {
                    websocket = new WebSocket(host);

                } else if ('MozWebSocket' in window)
                    websocket = new MozWebSocket(host);
                else {
                    //writeToHistory('Get a real browser which supports WebSocket.');
                    return;
                }
                // set the event listeners below
                websocket.onopen = onOpen;
                websocket.onclose = onClose;
                websocket.onmessage = onMessage;
                websocket.onerror = onError;
            }
            function onOpen(event) {

                //writeToHistory('Connected to ' + window.location.host + '.');
                //document.getElementById('msg').onkeydown = function(key) {
                //if (key.keyCode == 13)
                //doSend(); // call doSend() on enter key
                //};
            }
            function sendMessage() {
                doSend();
            }
            function onClose(event) {
                //writeToHistory('WebSocket closed.');
                //document.getElementById('msg').onkeydown = null;
            }

            function onMessage(message) { // print the received message
                writeToHistory(message.data);
            }

            function onError(event) {
                //writeToHistory('WebSocket error (' + event.data + ').');
                //document.getElementById('msg').onkeydown = null;
            }

            function doSend() {
                //var message = document.getElementById('msg').value;
                //document.getElementById('msg').value = '';
                //if (message != '')
                //websocket.send(message); // send the message
            }

// 	var auto_refresh2 = setInterval(function() {
//  		$('#conteudoEsq').load('posts.jsp');
//  	}, 10000);

            function writeToHistory(text) {
                document.getElementById('idNotificacao').innerHTML = text;
                //var history = document.getElementById('idNotificacao');
                //var p = document.createElement('p');
                //p.style.wordWrap = 'break-word';
                //p.innerHTML = text;
                //history.appendChild(p);
                //while (history.childNodes.length > 25) {
                //history.removeChild(console.firstChild);
                //}
                //history.scrollTop = history.scrollHeight;
            }
        </script>
    </head>
    <body>
        <noscript> JavaScript must be enabled for WebSockets to work . </noscript>
            <div id="head">
                <div class="right">
                    <a href="./notificacoes" id="idNotificacao" title="Notificações">${numNoticacao}</a>
                    &nbsp;&nbsp;&nbsp;&nbsp;
                    |
                    ${name}
                    |
                    <a href="./logout">Sair</a>
                </div>
            </div>
            <div id="sidebar">
                <ul>
                    <li <s:if test = "%{path == './principal>'">class="current"</s:if>>
                            <a href="./principal"><img src="./img/icons/menu/inbox.png" alt=""/>Painel Principal</a>
                        </li>
                         <li <s:if test = "%{path == './meuHistorico>'">class="current"</s:if>>
                            <a href="./meuHistorico"><img src="./img/icons/menu/inbox.png" alt=""/>Meu Histórico</a>
                        </li>
                        <li <s:if test = "%{path == './meusDados>'">class="current"</s:if>>
                            <a href="./meusDados"><img src="./img/icons/menu/layout.png" alt=""/>Meus Dados</a>
                        </li>
                        <li <s:if test = "%{path == './notificacoes'">class="current"</s:if>>
                            <a href="./notificacoes"><img src="./img/icons/menu/layout.png" alt=""/>Notificações</a>
                        </li>
                        <li <s:if test = "%{path == './pedidosCompras'">class="current"</s:if>>
                            <a href="./pedidosCompras"><img src="./img/icons/menu/layout.png" alt=""/>Pedidos de Compras</a>
                        </li>
                        <li <s:if test = "%{path == './comprarAccao'">class="current"</s:if>>
                            <a href="./comprarAccao"><img src="./img/icons/menu/layout.png" alt=""/>Comprar acções</a>
                        </li>
                        <li <s:if test = "%{path == '/topico' || path == '/topico/criartopico'  || path == '/topico/listartopicos' || path == '/topico/listarmeustopicos'}">class="current"</s:if>>
                            <a><img src="./img/icons/menu/layout.png" alt=""/>Tópico</a>
                            <ul>
                                <li <s:if test = "%{path == '/topico/criartopico'}">class="current"</s:if>>
                                    <a href="./topico/criartopico" title="Criar um tópico">Criar Tópico</a></li>
                                <li <s:if test = "%{path == '/topico/listartopicos'}">class="current"</s:if>>
                                    <a href="./topico/listartopicos" title="Listar tópicos">Listar Tópicos</a></li>
                                <li <s:if test = "%{path == '/topico/listarmeustopicos'}">class="current"</s:if>>
                                    <a href="./topico/listarmeustopicos" title="Listar Meus tópicos">Listar Meus Tópicos</a></li>
                            </ul>
                        </li>
                        <li <s:if test = "%{path == '/ideia' || path == '/ideia/criarideia' || path == '/ideia/apagarideia' || path == '/ideia/listarideiastopicos'}">class="current"</s:if>>
                            <a><img src="./img/icons/menu/layout.png" alt=""/>Ideia</a>
                            <ul>
                                <li <s:if test = "%{path == '/ideia/criarideia'}">class="current"</s:if>>
                                    <a href="./ideia/criarideia" title="Criar uma Ideia">Criar Ideia</a></li>
                                <li <s:if test = "%{path == '/ideia/apagarideia'}">class="current"</s:if>>
                                    <a href="./ideia/apagarideia" title="Apagar uma Ideia">Apagar Ideia</a></li>
                                <li <s:if test = "%{path == '/ideia/listarideiastopicos'}">class="current"</s:if>>
                                <a href="./ideia/listarideiastopicos" title="Listar Ideias Tópico">Listar Ideias Tópico</a></li>
                        </ul>

                    </li>
                      <li <s:if test = "%{path == '/transaccao' || path == '/transaccao/compraraccoesautomatico' || path == '/transaccao/pedidocompra' || path == '/transaccao/ordemvenda'}">class="current"</s:if>>
                        <a><img src="./img/icons/menu/layout.png" alt=""/>Transacções</a>
                        <ul>
                            <li <s:if test = "%{path == '/transaccao/compraraccoesautomatico'}">class="current"</s:if>>
                                <a href="./transaccao/compraraccoesautomatico" title="Comprar Acções Automatico">Compra Acções Automatica</a></li>
                            <li <s:if test = "%{path == '/transaccao/pedidocompra'}">class="current"</s:if>>
                                <a href="./transaccao/pedidocompra" title="Pedido Compra Acções Uma Ideia">Oferta Compra Acção</a></li>
                            <li <s:if test = "%{path == '/transaccao/ordemvenda'}">class="current"</s:if>>
                            <a href="./transaccao/ordemvenda" title="Ordem Venda Accoes">Ordem Venda Acções</a></li>
                        </ul>

                    </li>

                </ul>
            </div>
            <div id="content" class="white" xmlns="http://www.w3.org/1999/html">
                <h1 style="float: right;"><img src="./img/ideaBroker.gif" alt=""/></h1>

                <h1>
                </h1>
                <div style="height: 56px;">                
                </div>


            </div>        
    </body>
</html>
