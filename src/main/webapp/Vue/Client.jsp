<%-- 
    Document   : Client
    Created on : 21 nov. 2018, 14:28:08
    Author     : Polina
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/css/bootstrap.min.css" integrity="sha384-MCw98/SFnGE8fJT3GXwEOngsV7Zt27NXFoaoApmYm81iuXoPkFOJwJ8ERdknLPMO" crossorigin="anonymous">
        <link rel="stylesheet" type="text/css" href="Vue/StyleClient.css">
        
        <title>Page Client</title>
    </head>
    <body>
        <h1>Mon espace Client</h1>
        <br>

        <button type="button" class="btn btn-light" data-toggle="modal" data-target="#modifClient" id="btnModif">
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
                      <button type="button" class="btn btn-dark" name="action" value="Modif_cli" >Sauvegarder</button>
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
                <c:forEach var="prod" items="${produits}">
                <tr>
                    <td>${prod.description}</td>
                    <td>${prod.quantite}</td>
                    <td>${prod.prix}</td>
                    <td>${prod.total}</td>
                    <td>${prod.date}</td>
                    <td>${prod.companie}</td>
                    <td><button type="button" class="btn btn-dark" data-toggle="modal" data-target="#modifCom" id="btnModifc" >modifier</button>
                    <td><a href="?action=DELETE">supprimer</a></td>
                </c:forEach>
            </tr>
        </table>
        <br>
        
        <button type="button" class="btn btn-light" data-toggle="modal" data-target="#ajoutCom" id="btnAjout">Passer une commande</button>
        
         <div id="ajoutCom" class="modal" tabindex="-1" role="dialog"  >
            <div class="modal-dialog" role="document">
              <div class="modal-content">
                <div class="modal-header">
                  <h5 class="modal-title">Passer commande</h5>
                  <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true" id="close5">&times; </span>
                  </button>
                </div>
                <form method="POST">
                    <div class="modal-body">

                        <div class="input-group mb-3">
                          <div class="input-group-prepend">
                            <label class="input-group-text" for="inputProd">Produit</label>
                          </div>
                          <select class="custom-select" id="choixProd" required>
                            <option selected>Choisir...</option>
                            <option value="1">One</option>
                            <option value="2">Two</option>
                            <option value="3">Three</option>
                          </select>
                        </div>
                        <div class="input-group mb-3">
                          <div class="input-group-prepend">
                            <span class="input-group-text" id="choixQuant">Quantité</span>
                          </div>
                          <input type="text" class="form-control" placeholder="Veuillez entrer un quantité" aria-describedby="basic-addon1" pattern="[0-9]*" required>
                        </div>
                    </div>         
                    <div class="modal-footer">
                      <button type="button" class="btn btn-secondary" data-dismiss="modal" id="close6">Fermer</button>
                      <input class="btn btn-dark" name="action" value="Ajouter" type="SUBMIT">
                    </div>
                </form>
              </div>
            </div>
        </div>       
        
        <script>
            var modala = document.getElementById('ajoutCom');
            var btna = document.getElementById("btnAjout");
            var fermer5 = document.getElementById("close5");
            var fermer6 = document.getElementById("close6");
        //ouverture quand on clique sur le bouton
            btna.onclick = function() {
                modala.style.display = "block";
            }
        //fermeture quand on clique sur la croix   
            fermer5.onclick = function() {
                modala.style.display = "none";
            }
        //fermeture quand on clique sur fermer   
            fermer6.onclick = function() {
                modala.style.display = "none";
            }
        </script>        
        
        <div id="modifCom" class="modal" tabindex="-1" role="dialog"  >
            <div class="modal-dialog" role="document">
              <div class="modal-content">
                <div class="modal-header">
                  <h5 class="modal-title">Modifier ma commande</h5>
                  <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true" id="close3">&times; </span>
                  </button>
                </div>
                  <form method="POST">
                <div class="modal-body">
                  
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
                </div>         
                <div class="modal-footer">
                  <button type="button" class="btn btn-secondary" data-dismiss="modal" id="close4">Fermer</button>
                  <button type="button" class="btn btn-dark" value="SauvegarderProduit" name="action">Sauvegarder</button>
                </div>
                      </form>
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
            <input class="btn btn-light" name="action" value="Déconnexion" type="SUBMIT">
        </form>
    </body>
</html>

