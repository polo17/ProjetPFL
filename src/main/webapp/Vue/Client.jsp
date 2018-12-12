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
        <button type="SUBMIT" class="btn btn-light" data-toggle="modal" data-target="#modifClient" id="btnModif">Modifier mes données</button>
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
                                <label>Nom : </label><input type="text" name="nom" value="${nom}"></div>
                            <div class="form-group">
                                <label>Adresse : </label><input type="text" name="adresse" value="${adresse}"></div>  
                            <div class="form-group">
                                <label>Complément : </label><input type="text" name="compadresse" value="${compadresse}"></div>  
                            <div class="form-group">
                                <label>Ville : </label><input type="text" name="ville" value="${ville}"></div>  
                            <div class="form-group">
                                <label>Code Postal : </label><input type="text" name="cp" value="${cp}"></div>  
                            <div class="form-group">
                                <label>Etat : </label><input type="text" name="etat" value="${etat}"></div>  
                            <div class="form-group">
                                <label>Téléphone : </label><input type="text" name="telephone" value="${telephone}"></div>  
                            <div class="form-group">
                                <label>Fax : </label><input type="text" name="fax" value="${fax}"></div>  
                            <div class="form-group">
                                <label>Email : </label><input type="text" name="email" value="${email}"></div>

                        </div>
                        <div class="modal-footer">
                            <button type="button" class="btn btn-secondary" data-dismiss="modal" id="close2">Fermer</button>
                            <button type="SUBMIT" class="btn btn-dark" name="action" value="Modif_cli" >Sauvegarder</button>
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
            btn.onclick = function () {
                modal.style.display = "block";
            }
            //fermeture quand on clique sur la croix   
            fermer.onclick = function () {
                modal.style.display = "none";
            }
            //fermeture quand on clique sur fermer   
            fermer2.onclick = function () {
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
            <c:forEach var="prod" items="${produits}" varStatus="status">
                <tr>
                    <td>${prod.description}</td>
                    <td>
                        <form method = "POST">
                            <input type="text" class="form-control" name="quantite" size="4" aria-describedby="basic-addon1" pattern="[0-9]*" value="${prod.quantite}">
                        </form>
                    </td>
                    <td>${prod.prix}</td>
                    <td>${prod.total}</td>
                    <td>${prod.date}</td>
                    <td>${prod.companie}</td>
                    <td>
                        <form method = "POST">
                            <button type="submit" class="btn btn-dark" id="${status.getIndex()}" name="action" value="modifier">modifier</button>
                            <input id="prodId" name="orderm" type="hidden" value="${prod.order}">
                            <input id="prodQu" name="quantite" type="hidden" value="${prod.quantite}">
                        </form>
                    <td>
                        <form method = "POST">
                            <button type="SUBMIT" class="btn btn-dark" name="action" value="SupprimCommande" >supprimer</button>
                            <input id="prodId" name="order" type="hidden" value="${prod.order}">
                        </form>
                    </td> 
                </tr>
            </c:forEach>

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

                                <select class="custom-select" id="choixProd" name="nom" required>
                                    <option selected>Choisir...</option>
                                    <c:forEach var="ch" items="${choixProd}">
                                        <option name="nom" value="${ch}">${ch}</option>
                                    </c:forEach>
                                </select>
                            </div>
                            <div class="input-group mb-3">
                                <div class="input-group-prepend">
                                    <span class="input-group-text" id="choixQuant">Quantité</span>
                                </div>
                                <input type="text" class="form-control" name="quantite" placeholder="Veuillez entrer un quantité" aria-describedby="basic-addon1" pattern="[0-9]*" required>
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
            btna.onclick = function () {
                modala.style.display = "block";
            }
            //fermeture quand on clique sur la croix   
            fermer5.onclick = function () {
                modala.style.display = "none";
            }
            //fermeture quand on clique sur fermer   
            fermer6.onclick = function () {
                modala.style.display = "none";
            }
        </script>        


        <br><br>
        <form method="POST">
            <input class="btn btn-light" name="action" value="Déconnexion" type="SUBMIT">
        </form>
    </body>
</html>

