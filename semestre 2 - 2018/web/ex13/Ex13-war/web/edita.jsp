<%-- 
    Document   : edita
    Created on : 12/11/2018, 15:53:20
    Author     : 201319070221
--%>

<%@page contentType="text/html" pageEncoding="ISO-8859-1"%>
<!DOCTYPE HTML>

<html lang="pt-br">
    <head>
        <meta charset="UTF-8">
        <link rel="stylesheet" type="text/css" href="estilo.css"/>
        <title>Cadastro de Usuários</title>
    </head>
    <body>
        <h1>Formulário de edição de Usuário</h1>
        <form method="POST" action="${pageContext.request.contextPath}/editar">
            <input type="hidden" name="id" size="40" value="${user.id}">
            <p>Nome: <input type="text" name="nome" size="40" value="${user.nome}"></p>
            <p>Login: <input type="text" name="login" size="40" value="${user.login}"></p>
            <p>Senha: <input type="password" name="senha" size="10"></p>
            <p><input type="submit" value="Salvar">
                <input type="reset" value="Limpar"></p>
        </form>
    </body>
</html>