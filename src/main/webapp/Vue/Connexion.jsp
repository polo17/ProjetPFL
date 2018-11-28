<%-- 
    Document   : Connexion
    Created on : 21 nov. 2018, 14:27:54
    Author     : pedago
--%>


<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Page de connexion</title>
    </head>
    <body>
        <h1>Veuillez vous connecter</h1>
        <br>
        <form method="POST">
            <label>Nom d'utilisateur : <input name="nomUtili"></label>
            <br>
            <label>Mot de passe : <input name="mdp" type="password"></label>
            <input name="action" value="Connexion" type="SUBMIT">
        </form>
    </body>
</html>

