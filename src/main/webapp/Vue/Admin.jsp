<%-- 
    Document   : Admin
    Created on : 21 nov. 2018, 14:28:22
    Author     : Polina
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/css/bootstrap.min.css" integrity="sha384-MCw98/SFnGE8fJT3GXwEOngsV7Zt27NXFoaoApmYm81iuXoPkFOJwJ8ERdknLPMO" crossorigin="anonymous">
        <link rel="stylesheet" type="text/css" href="Vue/StyleAdmin.css">
        <title>Page Administrateur</title>
    </head>
    <body>
        <h1>Statistiques des commandes</h1>
        <br>
        <h4>Chiffre d'affaire selon les produits</h4>
        <p <span id="gProd"></span></p>
        <script type="text/javascript" src="https://www.gstatic.com/charts/loader.js"></script>
        <script type="text/javascript">
            google.charts.load('current', {packages: ['corechart']});
            google.charts.setOnLoadCallback(drawChart);

            function drawChart() {
                // Define the chart to be drawn.
                var data = new google.visualization.DataTable();
                data.addColumn('string', 'Element');
                data.addColumn('number', 'Percentage');
                data.addRows([
            <c:forEach var="ca" items="${chiffres_prod}">
                    ['${ca.nom}', ${ca.total}],
            </c:forEach>
                ]);
                var options3 = {backgroundColor: "#303030", legend: {textStyle: {color: 'white'}}};

                // Instantiate and draw the chart.
                var chart = new google.visualization.PieChart(document.getElementById('gProd'));
                chart.draw(data, options3);
            }
        </script>

        <button type="button" class="btn btn-dark" data-toggle="modal" data-target="#ajoutProd" id="btnProd">Ajouter un produit</button>
        <br>
        <div id="ajoutProd" class="modal" tabindex="-1" role="dialog"  >
            <div class="modal-dialog" role="document">
                <div class="modal-content">
                    <div class="modal-header">
                        <h5 class="modal-title">Ajouter un produit</h5>
                        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                            <span aria-hidden="true" id="close0">&times; </span>
                        </button>
                    </div>
                    <form method="POST">
                        <div class="modal-body">
                            <div class="input-group mb-3">
                                <div class="input-group-prepend">
                                    <label class="input-group-text" for="inputNom">Nom</label> </div>
                                <input type="text" class="form-control" name="nom" aria-describedby="basic-addon1" required></div>
                            <div class="input-group mb-3">
                                <div class="input-group-prepend">
                                    <label class="input-group-text" for="inputQ">Quantité</label> </div>
                                <input type="text" class="form-control" name="quantite" aria-describedby="basic-addon1" pattern="[0-9]*" required></div>
                            <div class="input-group mb-3">
                                <div class="input-group-prepend">
                                    <label class="input-group-text" for="inputPrix">Prix</label> </div>
                                <input type="text" class="form-control" name="prix" aria-describedby="basic-addon1" pattern="[0-9]*" required></div>
                            <div class="input-group mb-3">
                                <div class="input-group-prepend">
                                    <label class="input-group-text" for="inputMarkup">Markup</label> </div>
                                <input type="text" class="form-control" name="markup" aria-describedby="basic-addon1" pattern="[0-9]*" required></div>
                            <div class="input-group mb-3">
                                <div class="input-group-prepend">
                                    <label class="input-group-text" for="inputCode">Code</label> </div>
                                <select class="custom-select" id="choixCode" name="code" required>
                                    <option selected>Choisir...</option>
                                    <c:forEach var="ch" items="${choixCode}">
                                        <option name="code" value="${ch}">${ch}</option>
                                    </c:forEach>
                                </select>
                            </div>
                            <div class="input-group mb-3">
                                <div class="input-group-prepend">
                                    <label class="input-group-text" for="inputF">Fabricant</label> </div>   
                                <select class="custom-select" id="choixCode" name="manu" required>
                                    <option selected>Choisir...</option>
                                    <c:forEach var="ch" items="${choixManu}">
                                        <option name="manu" value="${ch}">${ch}</option>
                                    </c:forEach>
                                </select>                                   
                            </div>         
                            <div class="modal-footer">
                                <button type="button" class="btn btn-secondary" data-dismiss="modal" id="close1">Fermer</button>
                                <button type="SUBMIT" class="btn btn-dark" name="action" value="AjouterProd" >Ajouter</button>
                            </div>
                    </form>
                </div>
            </div>
        </div> 
    </div>

    <script>
        var modal = document.getElementById('ajoutProd');
        var btn = document.getElementById("btnProd");
        var fermer0 = document.getElementById("close0");
        var fermer1 = document.getElementById("close1");
        //ouverture quand on clique sur le bouton
        btn.onclick = function () {
            modal.style.display = "block";
        }
        //fermeture quand on clique sur la croix   
        fermer0.onclick = function () {
            modal.style.display = "none";
        }
        //fermeture quand on clique sur fermer   
        fermer1.onclick = function () {
            modal.style.display = "none";
        }
    </script>         

    <br>
    <h4>Chiffre d'affaire selon les Etats</h4>
    <br>        
    <p <span id="gState" ></span></p>
    <script type="text/javascript" src="https://www.gstatic.com/charts/loader.js"></script>
    <script type="text/javascript">
        google.charts.load('current', {
            'packages': ['geochart'],
            // Note: you will need to get a mapsApiKey for your project.
            // See: https://developers.google.com/chart/interactive/docs/basic_load_libs#load-settings
            'mapsApiKey': 'AIzaSyD-9tSrke72PouQMnMX-a7eZSW0jkFMBWY'
        });
        google.charts.setOnLoadCallback(drawRegionsMap);

        function drawRegionsMap() {
            var data = google.visualization.arrayToDataTable([
                ['State', 'Chiffre'],
        <c:forEach var="ca" items="${chiffres_etat}">
                ['${ca.nom}', ${ca.total}],
        </c:forEach>
            ]);

            var options2 = {width: 600, height: 400, region: "US",
                resolution: "provinces", backgroundColor: "#303030", defaultColor: '#81d4fa', colorAxis: {colors: ['red', 'yellow', 'green']}};

            var chart = new google.visualization.GeoChart(document.getElementById('gState'));

            chart.draw(data, options2);
        }
    </script>
    <br>
    <br>
    <br>
    <h4>Chiffre d'affaire selon les clients</h4>

    <p <span id="gCli"></span></p>
    <script type="text/javascript" src="https://www.gstatic.com/charts/loader.js"></script>
    <script type="text/javascript">
        google.charts.load('current', {packages: ['corechart', 'bar']});
        google.charts.setOnLoadCallback(drawBasic);

        function drawBasic() {

            var data = google.visualization.arrayToDataTable([
                ['Client', "Chiffre d'affaire", ],
        <c:forEach var="ca" items="${chiffres}">
                ['${ca.nom}', ${ca.total}],
        </c:forEach>
            ]);

            var options = {
                title: "Chiffre d'affaire selon les clients",
                chartArea: {width: '50%'},
                hAxis: {
                    title: 'Total Population',
                    minValue: 0
                },
                vAxis: {
                    title: 'City'
                }
            };
            var options3 = {backgroundColor: "#303030", legend: {textStyle: {color: 'white'}}, hAxis: {textStyle: {color: 'white'}}, vAxis: {textStyle: {color: 'white'}}};
            var chart = new google.visualization.BarChart(document.getElementById('gCli'));

            chart.draw(data, options3);
        }
    </script>
    <br>
    <footer>
        <input type="date" id="start" name="name"
               value="selectDate"
               min="${minDate}" max="${maxDate}">
        <br>
        <br>
        <form method="POST">
            <input class="btn btn-dark" id="decon" name="action" value="Déconnexion" type="SUBMIT">
        </form> 
    </footer>
</body>
</html>
