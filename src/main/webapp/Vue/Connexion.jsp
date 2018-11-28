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
        <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/css/bootstrap.min.css" integrity="sha384-MCw98/SFnGE8fJT3GXwEOngsV7Zt27NXFoaoApmYm81iuXoPkFOJwJ8ERdknLPMO" crossorigin="anonymous">
        <link rel="stylesheet" type="text/css" href="StyleConnexion.css">
        <title>Page de connexion</title>
    </head>
    <body>
        <h1>Veuillez vous connecter</h1>
        <br>
    <form>
      <div class="form-group">
        <label for="email">Email address</label>
        <input type="email" class="form-control" id="email" aria-describedby="emailHelp" placeholder="Enter votre email" name="email">
      </div>
      <div class="form-group">
        <label for="mdp">Password</label>
        <input type="password" class="form-control" id="mdp" placeholder="Mot de passe" name="mdp">
      <button type="submit" class="btn btn-dark" name="action" >Connexion</button>
    </form>
    </body>
</html>

