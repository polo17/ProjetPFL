<%-- 
    Document   : Client
    Created on : 21 nov. 2018, 14:28:08
    Author     : Polina
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/css/bootstrap.min.css" integrity="sha384-MCw98/SFnGE8fJT3GXwEOngsV7Zt27NXFoaoApmYm81iuXoPkFOJwJ8ERdknLPMO" crossorigin="anonymous">
        <link rel="stylesheet" type="text/css" href="StyleClient.css">
        
        <title>Page Client</title>
    </head>
    <body>
        <h1>Mon espace Client</h1>
        <br>

        <button type="button" class="btn btn-dark" data-toggle="modal" data-target="#modifClient" id="btnModif">
            Modifier mes données
        </button>
        <br><br>
        
        <div id="modifClient" class="modal" tabindex="-1" role="dialog"  >
            <div class="modal-dialog" role="document">
              <div class="modal-content">
                <div class="modal-header">
                  <h5 class="modal-title">Modifier mes données</h5>
                  <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true" id="close">&times; </span>
                  </button>
                </div>
                  <form method="POST">
                    
                    <div class="modal-body">

                          <div class="form-group">
                                <label>Nom : </label><input type="text" name="nom" value="pasclick" readonly></div>
                          <div class="form-group">
                                <label>Adresse : </label><input type="text" name="adresse" value="click"></div>  
                          <div class="form-group">
                                <label>Complément : </label><input type="text" name="compadresse" value=""></div>  
                          <div class="form-group">
                                <label>Ville : </label><input type="text" name="ville" value=""></div>  
                          <div class="form-group">
                                <label>Code Postal : </label><input type="text" name="cp" value=""></div>  
                          <div class="form-group">
                                <label>Etat : </label><input type="text" name="etat" value=""></div>  
                          <div class="form-group">
                                <label>Téléphone : </label><input type="text" name="telephone" value=""></div>  
                          <div class="form-group">
                                <label>Fax : </label><input type="text" name="fax" value=""></div>  
                          <div class="form-group">
                                <label>Email : </label><input type="text" name="email" value=""></div>

                    </div>
                    <div class="modal-footer">
                      <button type="button" class="btn btn-secondary" data-dismiss="modal" id="close2">Fermer</button>
                      <button type="button" class="btn btn-dark" name="action" value="Modif_cli">Sauvegarder</button>
                    </div>
                  </form>
              </div>
            </div>
        </div>
        
    <script>
        var modal = document.getElementById('modifClient');
        var btn = document.getElementById("btnModif");
        var fermer = document.getElementById("close");
        var fermer2 = document.getElementById("close2");
    //ouverture quand on clique sur le bouton
        btn.onclick = function() {
            modal.style.display = "block";
        }
    //fermeture quand on clique sur la croix   
        fermer.onclick = function() {
            modal.style.display = "none";
        }
    //fermeture quand on clique sur fermer   
        fermer2.onclick = function() {
            modal.style.display = "none";
        }
    </script>
    <br><br>    
    <h2>Mes commandes</h2>    
    <br>
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
                <td>${descriptions}</td>
                <td>${quantites}</td>
                <td>${prix}</td>
                <td>${totaux}</td>
                <td>${dates}</td>
                <td>${companies}</td>
                <td><button type="button" class="btn btn-dark" data-toggle="modal" data-target="#modifCom" id="btnModifc">modifier</button>
                <td><a href="?action=DELETE">supprimer</a></td>
            </tr>
        </table>
        <br>
        
        <div id="modifCom" class="modal" tabindex="-1" role="dialog"  >
            <div class="modal-dialog" role="document">
              <div class="modal-content">
                <div class="modal-header">
                  <h5 class="modal-title">Modifier ma commande</h5>
                  <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true" id="close3">&times; </span>
                  </button>
                </div>
                <div class="modal-body">
                  <form>
                      <div class="form-group">
                            <label>Nom produit : </label><input type="text" name="nompro" value="nom" readonly></div>
                      <div class="form-group">
                            <label>Quantité : </label><input type="text" name="quantite" value="5"></div>  
                      <div class="form-group">
                            <label>Prix unit. : </label><input type="text" name="prixunit" value="10" readonly></div>  
                      <div class="form-group">
                            <label>Total : </label><input type="text" name="total" value="50" readonly></div>  
                      <div class="form-group">
                            <label>Date d'achat : </label><input type="text" name="dateachat" value="12/04/15" readonly></div>  
                      <div class="form-group">
                            <label>Nom compagnie : </label><input type="text" name="nomcomp" value="comp" readonly></div>  
                  </form>
                </div>
                <div class="modal-footer">
                  <button type="button" class="btn btn-secondary" data-dismiss="modal" id="close4">Fermer</button>
                  <button type="button" class="btn btn-dark">Sauvegarder</button>
                </div>
              </div>
            </div>
        </div>        
        <script>
            var modalc = document.getElementById('modifCom');
            var btnc = document.getElementById("btnModifc");
            var fermer3 = document.getElementById("close3");
            var fermer4 = document.getElementById("close4");
        //ouverture quand on clique sur le bouton
            btnc.onclick = function() {
                modalc.style.display = "block";
            }
        //fermeture quand on clique sur la croix   
            fermer3.onclick = function() {
                modalc.style.display = "none";
            }
        //fermeture quand on clique sur fermer   
            fermer4.onclick = function() {
                modalc.style.display = "none";
            }
        </script>
        <br><br>
        <form method="POST">
            <input class="btn btn-dark" name="action" value="Déconnexion" type="SUBMIT">
        </form>
    </body>
</html>

