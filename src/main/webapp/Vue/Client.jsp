<%-- 
    Document   : Client
    Created on : 21 nov. 2018, 14:28:08
    Author     : pedago
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/css/bootstrap.min.css" integrity="sha384-MCw98/SFnGE8fJT3GXwEOngsV7Zt27NXFoaoApmYm81iuXoPkFOJwJ8ERdknLPMO" crossorigin="anonymous">

        <title>Page Client</title>
    </head>
    <body>
        <h1>Mes espace Client</h1>
        <br>

        <button type="button" class="btn btn-dark" data-toggle="modal" data-target="#modifClient">
            Modifier mes données
        </button>
        
        <div id="modifClient" class="modal fade" tabindex="-1" role="dialog"  >
            <div class="modal-dialog" role="document">
              <div class="modal-content">
                <div class="modal-header">
                  <h5 class="modal-title">Modifier mes données</h5>
                  <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                  </button>
                </div>
                <div class="modal-body">
                  <p>Modal body text goes here.</p>
                </div>
                <div class="modal-footer">
                  <button type="button" class="btn btn-secondary" data-dismiss="modal">Fermer</button>
                  <button type="button" class="btn btn-dark">Sauvegarder</button>
                </div>
              </div>
            </div>
        </div>
        
    <h2>Mes commandes</h2>    
        <table border="1">
            <thead>
                <tr>
                    <th>Nom produit</th>
                    <th>Quantité</th>
                    <th>Prix unit.</th>
                    <th>Total</th>
                    <th>Date d'achat</th>
                    <th>Nom compagnie</th>
                    <th colspan="2">Action</th>
                </tr>
            </thead>
            <tr>
                <td>1</td>
                <td>2</td>
                <td>3</td>
                <td>4</td>
                <td>5</td>
                <td>6</td>
                <td><a href="ModifCommande.jsp" target="popup" onclick="window.open('','popup','width=700,height=400,left=200,top=200,scrollbars=1');">modifier</a></td>
                <td><a href="?action=DELETE">supprimer</a></td>
            </tr>
        </table>
        <br>
        
        
        
        
        <input name="action" value="Déconnexion" type="SUBMIT">
    </body>
</html>

