/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modele;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import javax.sql.DataSource;

/**
 *
 * @author pedago
 */
public class DAO {

    private final DataSource myDataSource;

    public DAO(DataSource dataSource) {
        this.myDataSource = dataSource;
    }

    // getEmails permet d'obtenir la liste de tous les emails des clients
    public List<String> getEmails() throws SQLException {

        List<String> result = new LinkedList<>();
        String sql = "SELECT EMAIL FROM CUSTOMER";

        try (Connection connection = myDataSource.getConnection();
                PreparedStatement stmt = connection.prepareStatement(sql)) {
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                String email = rs.getString("EMAIL");
                result.add(email);
            }
        }

        return result;

    }

    // getCustomerIds permet d'obtenir la liste de tous les customer_ids des clients
    public List<Integer> getCustomerIds() throws SQLException {

        List<Integer> result = new LinkedList<>();
        String sql = "SELECT CUSTOMER_ID FROM CUSTOMER";

        try (Connection connection = myDataSource.getConnection();
                PreparedStatement stmt = connection.prepareStatement(sql)) {
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                int customer_id = rs.getInt("CUSTOMER_ID");
                result.add(customer_id);
            }
        }

        return result;
    }

    // getProductDescription permet d'obtenir la liste des descriptions des produits du client
    public List<String> getProductDescription(int customer_id) throws SQLException {

        List<String> result = new LinkedList<>();
        String sql = "SELECT DESCRIPTION FROM product INNER JOIN purchase_order ON product.product_id = purchase_order.product_id WHERE customer_id = ?";

        try (Connection connection = myDataSource.getConnection();
                PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, customer_id);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                String nom = rs.getString("DESCRIPTION");
                result.add(nom);
            }
        }

        return result;
    }

    //getPurchaseCost permet d'obtenir les prix d'achat des produits achetés par le client
    public List<Double> getPurchaseCost(int customer_id) throws SQLException {

        List<Double> result = new LinkedList<>();
        String sql = "SELECT Purchase_Cost FROM product INNER JOIN purchase_order ON product.product_id = purchase_order.product_id WHERE customer_id = ?";

        try (Connection connection = myDataSource.getConnection();
                PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, customer_id);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                double price = rs.getInt("Purchase_Cost");
                result.add(price);
            }
        }

        return result;
    }

    //getDescriptions permet d'obtenir la liste des descriptions des produits
    public List<String> getDescriptions() throws SQLException {

        List<String> result = new LinkedList<>();
        String sql = "SELECT DESCRIPTION FROM PRODUCT";

        try (Connection connection = myDataSource.getConnection();
                PreparedStatement stmt = connection.prepareStatement(sql)) {
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                String description = rs.getString("DESCRIPTION");
                result.add(description);
            }
        }

        return result;
    }

    // getDates permet d'obtenir les dates de vente des produits achetés par le client
    public List<String> getDates(int customer_id) throws SQLException {

        List<String> result = new LinkedList<>();
        String sql = "SELECT SALES_DATE FROM purchase_order WHERE customer_id = ?";

        try (Connection connection = myDataSource.getConnection();
                PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, customer_id);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {

                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                Date date = (Date) rs.getObject("SALES_DATE");
                String dateS = sdf.format(date);

                result.add(dateS);
            }
        }

        return result;
    }

    // getCompanies permet d'obtenir les noms des entreprises dont les produits ont été achetés par le client
    public List<String> getCompanies(int customer_id) throws SQLException {

        List<String> result = new LinkedList<>();
        String sql = "SELECT FREIGHT_COMPANY FROM purchase_order WHERE customer_id = ?";

        try (Connection connection = myDataSource.getConnection();
                PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, customer_id);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                String comp = rs.getString("FREIGHT_COMPANY");
                result.add(comp);
            }
        }

        return result;
    }

    // getQuantity permet d'obtenir le nombre de produits achetés par le client
    public List<Integer> getQuantity(int customer_id) throws SQLException {

        List<Integer> result = new LinkedList<>();
        String sql = "SELECT QUANTITY FROM PURCHASE_ORDER WHERE customer_id = ?";

        try (Connection connection = myDataSource.getConnection();
                PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, customer_id);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                int quantity = rs.getInt("QUANTITY");
                result.add(quantity);
            }
        }

        return result;
    }

    // addBonCommande permet d'ajouter un bon de commande à un client
    public int addBonCommande(int order_num, int customer_id, int product_id, int quantity, double shipping_cost, String sales_date, String shipping_date, String company) throws SQLException {

        int result = 0;
        String sql = "INSERT INTO PURCHASE_ORDER VALUES (?,?,?,?,?,?,?,?)";

        try (Connection connection = myDataSource.getConnection();
                PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, order_num);
            stmt.setInt(2, customer_id);
            stmt.setInt(3, product_id);
            stmt.setInt(4, quantity);
            stmt.setDouble(5, shipping_cost);
            stmt.setString(6, sales_date);
            stmt.setString(7, shipping_date);
            stmt.setString(8, company);

            result = stmt.executeUpdate();
        }

        return result;
    }

    public int getMaxOrderNum() throws SQLException {
        int result = 0;
        String sql = "SELECT MAX(ORDER_NUM) AS NUMBER FROM PURCHASE_ORDER";

        try (Connection connection = myDataSource.getConnection();
                Statement stmt = connection.createStatement();
                ResultSet rs = stmt.executeQuery(sql)) {

            if (rs.next()) {
                result = rs.getInt("NUMBER");
            }
        }

        return result;
    }

    public List<Integer> getOrderNum(int customer_id) throws SQLException {

        List<Integer> result = new LinkedList<>();

        String sql = "SELECT ORDER_NUM AS NUMBER FROM PURCHASE_ORDER WHERE CUSTOMER_ID = ?";

        try (Connection connection = myDataSource.getConnection();
                PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, customer_id);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                int order_num = rs.getInt("NUMBER");
                result.add(order_num);
            }
        }

        return result;
    }

    // modifyBonCommande permet de mofidier un bon de commande d'un client
    public int modifyBonCommande(int num, int quantity) throws SQLException {

        int result = 0;
        String sql = "UPDATE PURCHASE_ORDER SET QUANTITY = ? WHERE ORDRE_NUM = ?";

        try (Connection connection = myDataSource.getConnection();
                PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, quantity);
            stmt.setInt(2, num);
            result = stmt.executeUpdate();
        }

        return result;
    }

    // deleteBonCommande permet de supprimer un bon de commande à un client
    public int deleteBonCommande(int num) throws SQLException {

        int result = 0;
        String sql = "DELETE FROM PURCHASE_ORDER WHERE ORDER_NUM = ?";

        try (Connection connection = myDataSource.getConnection();
                PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, num);
            result = stmt.executeUpdate();
        }

        return result;
    }

    public String getZip(int customer) throws SQLException {

        String result = "";
        String sql = "SELECT ZIP FROM CUSTOMER WHERE CUSTOMER_ID = ?";

        try (Connection connection = myDataSource.getConnection();
                PreparedStatement stmt = connection.prepareStatement(sql)) {

            stmt.setInt(1, customer);

            try (ResultSet rs = stmt.executeQuery()) {
                rs.next();
                result = rs.getString("ZIP");
            }
        }

        return result;
    }
    
    public String getName(int customer) throws SQLException {

        String result = "";
        String sql = "SELECT NAME FROM CUSTOMER WHERE CUSTOMER_ID = ?";

        try (Connection connection = myDataSource.getConnection();
                PreparedStatement stmt = connection.prepareStatement(sql)) {

            stmt.setInt(1, customer);

            try (ResultSet rs = stmt.executeQuery()) {
                rs.next();
                result = rs.getString("NAME");
            }
        }

        return result;
    }

    public String getAdress1(int customer) throws SQLException {

        String result = "";
        String sql = "SELECT ADDRESSLINE1 FROM CUSTOMER WHERE CUSTOMER_ID = ?";

        try (Connection connection = myDataSource.getConnection();
                PreparedStatement stmt = connection.prepareStatement(sql)) {

            stmt.setInt(1, customer);

            try (ResultSet rs = stmt.executeQuery()) {
                rs.next();
                result = rs.getString("ADDRESSLINE1");
            }
        }

        return result;
    }

    public String getAdress2(int customer) throws SQLException {

        String result = "";
        String sql = "SELECT ADDRESSLINE2 FROM CUSTOMER WHERE CUSTOMER_ID = ?";

        try (Connection connection = myDataSource.getConnection();
                PreparedStatement stmt = connection.prepareStatement(sql)) {

            stmt.setInt(1, customer);

            try (ResultSet rs = stmt.executeQuery()) {
                rs.next();
                result = rs.getString("ADDRESSLINE2");
            }
        }

        return result;
    }

    public String getCity(int customer) throws SQLException {

        String result = "";
        String sql = "SELECT CITY FROM CUSTOMER WHERE CUSTOMER_ID = ?";

        try (Connection connection = myDataSource.getConnection();
                PreparedStatement stmt = connection.prepareStatement(sql)) {

            stmt.setInt(1, customer);

            try (ResultSet rs = stmt.executeQuery()) {
                rs.next();
                result = rs.getString("CITY");
            }
        }

        return result;
    }

    public String getState(int customer) throws SQLException {

        String result = "";
        String sql = "SELECT STATE FROM CUSTOMER WHERE CUSTOMER_ID = ?";

        try (Connection connection = myDataSource.getConnection();
                PreparedStatement stmt = connection.prepareStatement(sql)) {

            stmt.setInt(1, customer);

            try (ResultSet rs = stmt.executeQuery()) {
                rs.next();
                result = rs.getString("STATE");
            }
        }

        return result;
    }

    public String getPhone(int customer) throws SQLException {

        String result = "";
        String sql = "SELECT PHONE FROM CUSTOMER WHERE CUSTOMER_ID = ?";

        try (Connection connection = myDataSource.getConnection();
                PreparedStatement stmt = connection.prepareStatement(sql)) {

            stmt.setInt(1, customer);

            try (ResultSet rs = stmt.executeQuery()) {
                rs.next();
                result = rs.getString("PHONE");
            }
        }

        return result;
    }

    public String getFax(int customer) throws SQLException {

        String result = "";
        String sql = "SELECT FAX FROM CUSTOMER WHERE CUSTOMER_ID = ?";

        try (Connection connection = myDataSource.getConnection();
                PreparedStatement stmt = connection.prepareStatement(sql)) {

            stmt.setInt(1, customer);

            try (ResultSet rs = stmt.executeQuery()) {
                rs.next();
                result = rs.getString("FAX");
            }
        }

        return result;
    }

    public String getEmail(int customer) throws SQLException {

        String result = "";
        String sql = "SELECT EMAIL FROM CUSTOMER WHERE CUSTOMER_ID = ?";

        try (Connection connection = myDataSource.getConnection();
                PreparedStatement stmt = connection.prepareStatement(sql)) {

            stmt.setInt(1, customer);

            try (ResultSet rs = stmt.executeQuery()) {
                rs.next();
                result = rs.getString("EMAIL");
            }
        }

        return result;
    }

    // modifyClient permet de modifier un client
    public int modifyClient(String nom, String adresse, String complement, String ville, String etat, String cp, String phone,
            String fax, String email, int id) throws SQLException {

        int result = 0;
        String sql = "UPDATE CUSTOMER SET NAME = ?, ADDRESSLINE1 = ?, ADDRESSLINE2 = ?, CITY = ?, STATE = ?, PHONE = ?"
                + ", FAX = ?, EMAIL = ?, ZIP = ? WHERE CUSTOMER_ID = ?";

        try (Connection connection = myDataSource.getConnection();
                PreparedStatement stmt = connection.prepareStatement(sql)) {
            
            stmt.setString(1, nom);
            stmt.setString(2, adresse);
            stmt.setString(3, complement);
            stmt.setString(4, ville);
            stmt.setString(5, etat);
            stmt.setString(6, phone);
            stmt.setString(7, fax);
            stmt.setString(8, email);
            stmt.setString(9, cp);
            
            stmt.setInt(10, id);

            result = stmt.executeUpdate();
        }
        return result;
    }

    // getProductId permet de récupérer le product_id de la description du produit donnée
    public int getProductId(String description) throws SQLException {

        int result = 0;
        String sql = "SELECT PRODUCT_ID AS ID FROM APP.PRODUCT WHERE DESCRIPTION = ?";

        try (Connection connection = myDataSource.getConnection();
                PreparedStatement stmt = connection.prepareStatement(sql)) {

            stmt.setString(1, description);

            try (ResultSet rs = stmt.executeQuery()) {
                rs.next();
                result = rs.getInt("ID");
            }
        }

        return result;
    }

    // getPurchaseCost permet de récupérer le purchase_cost de la description du produit donnée
    public double getPurchaseCost(String description) throws SQLException {

        double result = 0.;
        String sql = "SELECT PURCHASE_COST AS COST FROM APP.PRODUCT WHERE DESCRIPTION = ?";

        try (Connection connection = myDataSource.getConnection();
                PreparedStatement stmt = connection.prepareStatement(sql)) {

            stmt.setString(1, description);

            try (ResultSet rs = stmt.executeQuery()) {
                rs.next();
                result = rs.getDouble("COST");
            }
        }

        return result;
    }
}
