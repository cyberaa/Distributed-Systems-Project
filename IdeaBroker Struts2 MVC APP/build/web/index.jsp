
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
            <h1><img src="./img/icons/lock-closed.png" alt=""/>IdeaBroker</h1>

            <div class="notif tip" style="margin-top: 3em">
                <s:if test="hasActionErrors()">
                    <s:actionerror/>
                </s:if>
                <s:else>
                    Insira as suas credenciais!
                </s:else>
                
            </div>

            <form action="login" id="form1" method="post" autocomplete="off">
                <div class="input placeholder">
                    <s:textfield name="user.username" label="Utilizador" cssClass="input"/>
                </div>
                <div class="input placeholder">
                    <s:password name="user.passw" label="Palavra-chave" cssClass="input"/>
                </div>
                <div class="submit">
                    <s:submit value="Entrar" method="execute"/>
                </div>
            </form>
            <form action="registo" id="form1" method="post">
                <div class="submit">
                    <s:submit value="Registar" method="execute"/>
                </div>
            </form>    
        </div>
    </body>
</html>
