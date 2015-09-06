<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"  pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
        <title>IdeaBroker</title>

        <link rel="stylesheet" href="../css/style.css"/>
        <link rel="stylesheet" href="../js/jwysiwyg/jquery.wysiwyg.old-school.css"/>

        <!-- jQuery AND jQueryUI -->
        <script type="text/javascript" src="../js/jquery.min.js"></script>
        <script type="text/javascript" src="../js/jquery-ui.min.js"></script>
        <script type="text/javascript" src="../js/min.js"></script>
        <script language="Javascript">
            function isNumber(evt){
                var charCode = (evt.which) ? evt.which : event.keyCode;
                if(charCode > 31 && (charCode < 48 || charCode > 57)){
                    return false;
                }
                return true;
            }
        </script>
    </head>
    <body>
        <noscript>JavaScript must be enabled for WebSockets to work .</noscript> 
        <div id="head">
            <div class="right">
                |
                ${name}
                |
                <a href="../logout">Sair</a>
            </div>
        </div>
        <div id="sidebar">
            <ul>
                <li <s:if test = "%{path == '/principal>'">class="current"</s:if>>
                        <a href="../principal"><img src="../img/icons/menu/inbox.png" alt=""/>Painel Principal</a>
                    </li>
                    <li <s:if test = "%{path == '/meusDados>'">class="current"</s:if>>
                        <a href="../meusDados"><img src="../img/icons/menu/layout.png" alt=""/>Meus Dados</a>
                    </li>
                    <li <s:if test = "%{path == '/notificacoes'">class="current"</s:if>>
                        <a href="../notificacoes"><img src="../img/icons/menu/layout.png" alt=""/>Notificações</a>
                    </li>
                    <li <s:if test = "%{path == '/pedidosCompras'">class="current"</s:if>>
                        <a href="../pedidosCompras"><img src="../img/icons/menu/layout.png" alt=""/>Pedidos de Compras</a>
                    </li>
                    <li <s:if test = "%{path == '/comprarAccao'">class="current"</s:if>>
                        <a href="../comprarAccao"><img src="../img/icons/menu/layout.png" alt=""/>Comprar acções</a>
                    </li>
                    <li <s:if test = "%{path == '/topico' || path == '/topico/criartopico' || path == '/topico/listartopicos' || path == '/topico/listarmeustopicos'}">class="current"</s:if>>
                        <a><img src="../img/icons/menu/layout.png" alt=""/>Tópico</a>
                        <ul>
                            <li <s:if test = "%{path == '/topico/criartopico'}">class="current"</s:if>>
                                <a href="../topico/criartopico" title="Criar um tópico">Criar Tópico</a></li>
                            <li <s:if test = "%{path == '/topico/listartopicos'}">class="current"</s:if>>
                                <a href="../topico/listartopicos" title="Listar tópicos">Listar Tópicos</a></li>
                            <li <s:if test = "%{path == '/topico/listarmeustopicos'}">class="current"</s:if>>
                                <a href="../topico/listarmeustopicos" title="Listar Meus tópicos">Listar Meus Tópicos</a></li>
                        </ul>
                    </li>
                    <li <s:if test = "%{path == '/ideia' || path == '/ideia/criarideia' || path == '/ideia/apagarideia' || path == '/ideia/listarideiastopicos'}">class="current"</s:if>>
                        <a><img src="../img/icons/menu/layout.png" alt=""/>Ideia</a>
                        <ul>
                            <li <s:if test = "%{path == '/ideia/criarideia'}">class="current"</s:if>>
                                <a href="../ideia/criarideia" title="Criar uma Ideia">Criar Ideia</a></li>
                            <li <s:if test = "%{path == '/ideia/apagarideia'}">class="current"</s:if>>
                                <a href="../ideia/apagarideia" title="Apagar uma Ideia">Apagar Ideia</a></li>
                            <li <s:if test = "%{path == '/ideia/listarideiastopicos'}">class="current"</s:if>>
                                <a href="../ideia/listarideiastopicos" title="Listar Ideias Tópico">Listar Ideias Tópico</a></li>
                        </ul>

                    </li>
                    <li <s:if test = "%{path == '/transaccao' || path == '/transaccao/compraraccoesautomatico' || path == '/transaccao/pedidocompra' || path == '/transaccao/ordemvenda'}">class="current"</s:if>>
                        <a><img src="../img/icons/menu/layout.png" alt=""/>Transacções</a>
                        <ul>
                            <li <s:if test = "%{path == '/transaccao/compraraccoesautomatico'}">class="current"</s:if>>
                                <a href="../transaccao/compraraccoesautomatico" title="Comprar Acções Automatico">Compra Acções Automatica</a></li>
                            <li <s:if test = "%{path == '/transaccao/pedidocompra'}">class="current"</s:if>>
                                <a href="../transaccao/pedidocompra" title="Pedido Compra Acções Uma Ideia">Oferta Compra Acção</a></li>
                            <li <s:if test = "%{path == '/transaccao/ordemvenda'}">class="current"</s:if>>
                            <a href="../transaccao/ordemvenda" title="Ordem Venda Accoes">Ordem Venda Acções</a></li>
                        </ul>

                    </li>
                </ul>
            </div>
            <div id="content" class="white" xmlns="http://www.w3.org/1999/html">
                <h1 style="float: right;"><img src="../img/ideaBroker.gif" alt=""/></h1>

                <h1>
                </h1>
                <div style="height: 56px;"> 
                    <iframe align = "left" width="50%" height="10%" frameborder="1" scrolling="no" src="./showNotification.jsp"></iframe>
                    <!--<span id="notificationsArea"> teste de notificação</span>-->
                </div>
            <s:if test="hasActionErrors()">
                <div id="labelError" class="notif error" style="margin-top: 3em">
                    <strong><s:actionerror/></strong>
                    <script type="text/javascript">
                        var div = document.getElementById("labelError");
                        setTimeout('div.parentNode.removeChild(div)', 10000);
                    </script>
                </div>    
            </s:if>
            <s:if test="hasActionMessages()">
                <div id="labelSucess" class="notif success" style="margin-top: 3em">
                    <strong><s:actionmessage/></strong>
                    <script type="text/javascript">
                        var div = document.getElementById("labelSucess");
                        setTimeout('div.parentNode.removeChild(div)', 10000);
                    </script>
                </div>    
            </s:if>
            <div class="bloc">
                <div class="title">Criar uma Ideia de um Tópico</div>
                <div class="content">
                    <form action="criarideia" id="form1" method="post" autocomplete="off">
                        <div style="float: left; margin-top: 0.8em;">
                            <h5>Tópico:</h5>
                        </div>
                        <div class="input" style="margin-left: 15em;">
                        <s:textfield name="nome" cssStyle="width: 13em;" cssClass="input"/>
                        </div>
                        <div style="float: left; margin-top: 0.8em;">
                            <h5>Titulo a Ideia:</h5>
                        </div>
                        <div class="input" style="margin-left: 15em;">
                            <s:textfield name="ideiaBean.titulo" cssStyle="width: 18em;" maxlength="60" cssClass="input"/>
                        </div>
                        <div style="float: left; margin-top: 0.8em;">
                            <h5>Descricao da Ideia:</h5>
                        </div>
                        <div class="input" style="margin-left: 15em;">
                            <s:textarea name="ideiaBean.descricao" cols="10" rows="4" cssStyle="width: 18em;" cssClass="input"/>
                        </div>
                        <div style="float: left; margin-top: 0.8em;">
                            <h5>Montante a Investir:</h5>
                        </div>
                        <div class="input" style="margin-left: 15em;">
                            <s:textfield name="ideiaBean.montante" cssStyle="width: 18em;" onkeypress="return isNumber(event)" maxlength="15" cssClass="input"/>
                        </div>
                        <div style="float: left; margin-top: 0.8em;">
                            <h5>Percentagem Venda Automatica:</h5>
                        </div>
                        <div class="input" style="margin-left: 15em;">
                            <s:textfield name="ideiaBean.percentagem" cssStyle="width: 18em;" onkeypress="return isNumber(event)" maxlength="4" cssClass="input"/>
                        </div>
                        <div class="submit">
                            <s:submit value="Submeter" method="execute" />
                        </div>
                    </form>
                </div>
            </div>
        </div>        
    </body>
</html>
