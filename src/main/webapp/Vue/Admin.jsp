<%-- 
    Document   : Admin
    Created on : 21 nov. 2018, 14:28:22
    Author     : Polina
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
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
        ['Nitrogen', 0.78],
        ['Oxygen', 0.21],
        ['Other', 0.01]
      ]);

      // Instantiate and draw the chart.
      var chart = new google.visualization.PieChart(document.getElementById('gProd'));
      chart.draw(data, null);
    }
  </script>
    
        
        
        <form method="POST">
            <input name="action" value="Déconnexion" type="SUBMIT">
        </form> 
    </body>
</html>
