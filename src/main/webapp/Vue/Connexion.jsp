<%-- 
    Document   : Connexion
    Created on : 21 nov. 2018, 14:27:54
    Author     : Polina
--%>


<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/css/bootstrap.min.css" integrity="sha384-MCw98/SFnGE8fJT3GXwEOngsV7Zt27NXFoaoApmYm81iuXoPkFOJwJ8ERdknLPMO" crossorigin="anonymous">
        <link rel="stylesheet" type="text/css" href="Vue/StyleConnexion.css">
        <title>Page de connexion</title>
    </head>
    <body class="text-center">
        <br>
        <h1>Veuillez vous connecter</h1>
        <br><br>
        <section id="bullesup">
            <form class="form-signin" method="POST">
                <section id="bulle">
                    <div class="form-group">
                        <label for="email">Email</label>
                        <input type="text" class="form-control" id="email" placeholder="Entrez votre email" name="email" required autofocus>  
                    </div>
                    <div class="form-group">
                        <label for="mdp">Mot de passe</label>
                        <input type="password" class="form-control" id="mdp" pattern="[0-9]*" placeholder="Mot de passe" name="mdp" required>
                        <br>
                        <a style="color:red">${erreur}</a>
                        <button type="submit" class="btn btn-dark" name="action" value="Connexion">Connexion</button>
                    </div>    
                <br>
                </section>
            </form>
        </section>
    </body>
</html>

