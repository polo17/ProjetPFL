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
        <title>Page Client</title>
    </head>
    <body>
        <h1>Mes espace Client</h1>
        <br>
        <input href="ModifClient.jsp" name="action" value="Modifier mes données" type="SUBMIT" onclick="window.open('ModifClient.jsp','popup','width=700,height=400,left=200,top=200,scrollbars=1');">
        
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

