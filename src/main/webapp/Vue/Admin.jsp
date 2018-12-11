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
            <c:forEach var="ca" items="${chiffres_produit}">
                    ['${ca.nom}', ${ca.total}],
            </c:forEach>
                ]);
                var options3 = {title: "Chiffre d'affaire selon les produits"};

                // Instantiate and draw the chart.
                var chart = new google.visualization.PieChart(document.getElementById('gProd'));
                chart.draw(data, options3);
            }
        </script>

        <p <span id="gState"></span></p>
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
                    ['State', 'Foo Factor'],
            <c:forEach var="ca" items="${chiffres_etat}">
                    ['${ca.nom}', ${ca.total}],
            </c:forEach>
                ]);

                var options2 = {title: "Chiffre d'affaire selon les Etats",width: 556, height: 347, region: "US", resolution: "provinces",backgroundColor: '#81d4fa'};

                var chart = new google.visualization.GeoChart(document.getElementById('gState'));

                chart.draw(data, options2);
            }
        </script>

        <p <span id="gCli"></span></p>
        <script type="text/javascript" src="https://www.gstatic.com/charts/loader.js"></script>
        <script type="text/javascript">
            google.charts.load('current', {packages: ['corechart', 'bar']});
            google.charts.setOnLoadCallback(drawBasic);

            function drawBasic() {

                var data = google.visualization.arrayToDataTable([
                    ['City', '2010 Population', ],
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

                var chart = new google.visualization.BarChart(document.getElementById('gCli'));

                chart.draw(data, options);
            }
        </script>


        <input type="date" id="start" name="name"
               value="selectDate"
               min="${minDate}" max="${maxDate}">


        <form method="POST">
            <input name="action" value="Déconnexion" type="SUBMIT">
        </form> 
    </body>
</html>
