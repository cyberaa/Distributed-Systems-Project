<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"  pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
    <head>
        <s:include value="header.jsp" />
        <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
        
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
                <li <s:if test = "%{path == '/principal>'">class="current"</s:if>>
                        <a href="./principal"><img src="./img/icons/menu/inbox.png" alt=""/>Painel Principal</a>
                </li>
                <li <s:if test = "%{path == '/meusDados>'">class="current"</s:if>>
                    <a href="./meusDados"><img src="./img/icons/menu/layout.png" alt=""/>Meus Dados</a>
                </li>
                <li <s:if test = "%{path == '/notificacoes'">class="current"</s:if>>
                    <a href="./notificacoes"><img src="./img/icons/menu/layout.png" alt=""/>Notificações</a>
                </li>
                <li <s:if test = "%{path == '/pedidosCompras'">class="current"</s:if>>
                    <a href="./pedidosCompras"><img src="./img/icons/menu/layout.png" alt=""/>Pedidos de Compras</a>
                </li>
                <li <s:if test = "%{path == '/comprarAccao'">class="current"</s:if>>
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
            
            <div class="bloc">
                <div class="title">
                    Dados Pessoais do Utilizador
                </div>
                <div class="content">
                    <div style="float: left; margin-top: 0.8em;">
                        <h5>Nome:</h5>
                    </div>
                    <div class="input" style="margin-left: 8em;">
                        <s:textfield name="user.Nome" readonly="true" cssStyle="width: 13em;" cssClass="input"/>
                    </div>
                    <div style="float: left; margin-top: 0.8em;">
                        <h5>Utilizador:</h5>
                    </div>
                    <div class="input" style="margin-left: 8em;">
                        <s:textfield name="user.username" readonly="true" cssStyle="width: 10em;" cssClass="input"/>
                    </div>
                    <div style="float: left; margin-top: 0.8em;">
                        <h5>Pais:</h5>
                    </div>
                    <div class="input" style="margin-left: 8em;">
                        <s:textfield name="user.pais" readonly="true" cssStyle="width: 11em;" cssClass="input"/>
                    </div>
                    <div style="float: left; margin-top: 0.8em;">
                        <h5>Email:</h5>
                    </div>
                    <div class="input" style="margin-left: 8em;">
                        <s:textfield name="user.email" readonly="true" cssStyle="width: 15em;" cssClass="input"/>
                    </div>
                    <div style="float: left; margin-top: 0.8em;">
                        <h5>Idade:</h5>
                    </div>
                    <div class="input" style="margin-left: 8em;">
                        <s:textfield name="user.idade" readonly="true" cssStyle="width: 3em;" cssClass="input"/>
                    </div>
                </div>
                
            </div>
        </div>        
    </body>
</html>
