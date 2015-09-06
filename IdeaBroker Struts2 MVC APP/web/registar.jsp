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
        <div id="content" class="login">
            <h1><img src="./img/icons/lock-closed.png" alt=""/>IdeaBroker - Registo</h1>

            <div class="notif tip" style="margin-top: 3em">
                <s:if test="hasActionErrors()">
                    <s:actionerror/>
                </s:if>
                <s:else>
                    Insira os seus dados de registo!
                </s:else>

            </div>

            <form action="registo" id="form1" method="post" autocomplete="off">
                <div class="input placeholder">
                    <s:textfield name="username" label="Utilizador" cssClass="input"/>
                </div>
                <div class="input placeholder">
                    <s:password name="passw" label="Palavra-chave" cssClass="input"/>
                </div>
                <div class="input placeholder">
                    <s:textfield name="nome" label="Nome" cssClass="input"/>
                </div>
                <div class="input placeholder">
                    <s:textfield name="idade" label="Idade" cssClass="input"/>
                </div>
                <div class="input placeholder">
                    <s:textfield name="pais" label="Pais" cssClass="input"/>
                </div>
                <div class="input placeholder">
                    <s:textfield name="email" label="email" cssClass="input"/>
                </div>
                <div class="submit">
                    <s:submit value="Submeter" method="execute" />
                </div>
            </form>
            <form action="cancelarRegisto" method="post">
                <div class="submit">
                    <s:submit value="Cancelar" method="execute"/>
                </div>
            </form>  
        </div>
    </body>
</html>
