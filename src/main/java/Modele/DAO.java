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

    // getProductDescription permet d'obtenir la liste des descriptions des produits du client entré en paramètre
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

    // getPurchaseCost permet d'obtenir la liste des prix d'achat des produits achetés par le client entré en paramètre
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

    // getDescriptions permet d'obtenir la liste des descriptions des produits
    public List<String> getDescriptions() throws SQLException {

        List<String> result = new LinkedList<>();
        String sql = "SELECT DESCRIPTION FROM PRODUCT WHERE AVAILABLE = 'TRUE'";

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

    // getDates permet d'obtenir la liste des dates de vente des produits achetés par le client entré en paramètre
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

    // getCompanies permet d'obtenir la liste des noms des entreprises dont les produits ont été achetés par le client entré en paramètre
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

    // getQuantity permet d'obtenir la liste des nombres de produits achetés par le client entré en paramètre
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
    
    // getQuantity_pid permet d'obtenir la quantité achetée du produit entré en paramètre
    public int getQuantity_pid(int pid) throws SQLException {

        int result = 0;
        String sql = "SELECT QUANTITY FROM PURCHASE_ORDER WHERE PRODUCT_ID = ?";

        try (Connection connection = myDataSource.getConnection();
                PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, pid);
            
            try (ResultSet rs = stmt.executeQuery()) {
                rs.next();
                result = rs.getInt("QUANTITY");
            }
          
        }

        return result;
    }
    
    // getQuantity_p permet d'obtenir la quantité achetée du produit correspondant au bon de commande entré en paramètre
    public int getQuantity_p(int order) throws SQLException {

        int result = 0;
        String sql = "SELECT QUANTITY FROM PURCHASE_ORDER WHERE ORDER_NUM = ?";

        try (Connection connection = myDataSource.getConnection();
                PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, order);
            
            try (ResultSet rs = stmt.executeQuery()) {
                rs.next();
                result = rs.getInt("QUANTITY");
            }
          
        }

        return result;
    }
    
    // getCompanies_p permet d'obtenir le nom de l'entreprise correspondant au bon de commande entré en paramètre
    public String getCompanies_p(int order) throws SQLException {

        String result = "";
        String sql = "SELECT FREIGHT_COMPANY FROM purchase_order WHERE ORDER_NUM = ?";

        try (Connection connection = myDataSource.getConnection();
                PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, order);
            try (ResultSet rs = stmt.executeQuery()) {
                rs.next();
                result = rs.getString("FREIGHT_COMPANY");
            }
        }

        return result;
    }
    
    // getPurchaseCost_p permet d'obtenir le prix d'achat du produit correspondant au bon de commande entré en paramètre
    public double getPurchaseCost_p(int order) throws SQLException {

        double result = 0;
        String sql = "SELECT Purchase_Cost FROM product INNER JOIN purchase_order ON product.product_id = purchase_order.product_id WHERE ORDER_NUM = ?";

        try (Connection connection = myDataSource.getConnection();
                PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, order);
            
            try (ResultSet rs = stmt.executeQuery()) {
                rs.next();
                result = rs.getInt("Purchase_Cost");
            }
            
        }
        return result;
    }
    
    // getDates_p permet d'obtenir la date de vente du produit correspondant au bon de commande entré en paramètre
    public String getDates_p(int order) throws SQLException {

        String result = "";
        String sql = "SELECT SALES_DATE FROM purchase_order WHERE ORDER_NUM = ?";

        try (Connection connection = myDataSource.getConnection();
                PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, order);
            try (ResultSet rs = stmt.executeQuery()) {
                rs.next();
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                Date date = (Date) rs.getObject("SALES_DATE");
                result = sdf.format(date);
            }


        }

        return result;
    }
    
    // getDescription_p permet d'obtenir la description du produit correspondant au bon de commande entré en paramètre
    public String getDescription_p(int order) throws SQLException {

        String result = "";
        String sql = "SELECT DESCRIPTION FROM product INNER JOIN purchase_order ON product.product_id = purchase_order.product_id WHERE ORDER_NUM = ?";

        try (Connection connection = myDataSource.getConnection();
                PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, order);
            try (ResultSet rs = stmt.executeQuery()) {
                rs.next();
                result = rs.getString("DESCRIPTION");
            }  
        }

        return result;
    }
    
    // getDescription_pid permet d'obtenir la description du produit correspondant au produit entré en paramètre
    public String getDescription_pid(int pid) throws SQLException {

        String result = "";
        String sql = "SELECT DESCRIPTION FROM product WHERE PRODUCT_ID = ?";

        try (Connection connection = myDataSource.getConnection();
                PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, pid);
            try (ResultSet rs = stmt.executeQuery()) {
                rs.next();
                result = rs.getString("DESCRIPTION");
            }  
        }

        return result;
    }

    // getMaxOrderNum permet d'obtenir le numéro de bon commande maximal dans la table des bons de commandes
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
    
    // getProducts_id permet d'obtenir la liste des product_ids de la table purchase_order 
    public List<Integer> getProducts_id() throws SQLException {

        List<Integer> result = new LinkedList<>();
        String sql = "SELECT PRODUCT_ID FROM PURCHASE_ORDER";

        try (Connection connection = myDataSource.getConnection();
            PreparedStatement stmt = connection.prepareStatement(sql)) {
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                int pid = rs.getInt("PRODUCT_ID");
                result.add(pid);
            }
        }
        return result;
    }
    
    // getStates permet d'obtenir la liste des états des clients 
    public List<String> getStates() throws SQLException {

        List<String> result = new LinkedList<>();
        String sql = "SELECT STATE FROM CUSTOMER";

        try (Connection connection = myDataSource.getConnection();
                PreparedStatement stmt = connection.prepareStatement(sql)) {
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                String state = rs.getString("STATE");
                result.add(state);
            }
        }

        return result;
    }
    
    // getCustomer_s permet d'obtenir la liste des customer_ids vivant dans l'état entré en paramètre
    public List<Integer> getCustomer_s(String state) throws SQLException {

        List<Integer> result = new LinkedList<>();
        String sql = "SELECT CUSTOMER_ID FROM CUSTOMER WHERE STATE = ?";

        try (Connection connection = myDataSource.getConnection();
                PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, state);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                int cid = rs.getInt("CUSTOMER_ID");
                result.add(cid);
            }
        }

        return result;
    }
    
    // getMaxDate permet d'obtenir la date d'envoi la plus récente d'un produit
    public String getMaxDate() throws SQLException {
        String result = "";
        String sql = "SELECT MAX(SHIPPING_DATE) AS DATE FROM PURCHASE_ORDER";

        try (Connection connection = myDataSource.getConnection();
                Statement stmt = connection.createStatement();
                ResultSet rs = stmt.executeQuery(sql)) {

            if (rs.next()) {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                Date date = (Date) rs.getObject("DATE");
                result = sdf.format(date);
            }
        }

        return result;
    }
    
    // getMinDate permet d'obtenir la date d'envoi la moins récente d'un produit
    public String getMinDate() throws SQLException {
        String result = "";
        String sql = "SELECT MIN(SHIPPING_DATE) AS DATE FROM PURCHASE_ORDER";

        try (Connection connection = myDataSource.getConnection();
                Statement stmt = connection.createStatement();
                ResultSet rs = stmt.executeQuery(sql)) {

            if (rs.next()) {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                Date date = (Date) rs.getObject("DATE");
                result = sdf.format(date);
            }
        }

        return result;
    }

    // getOrderNum permet d'obtenir la liste des numéros de bons de commande du client entré en paramètre
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

    // getZip permet d'obtenir le code postal du client entré en paramètre
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
    
    // getZip permet d'obtenir le nom du client entré en paramètre
    public String getName(int customer) throws SQLException {

        String result = "";
        String sql = "SELECT NAME AS NOM FROM CUSTOMER WHERE CUSTOMER_ID = ?";

        try (Connection connection = myDataSource.getConnection();
                PreparedStatement stmt = connection.prepareStatement(sql)) {

            stmt.setInt(1, customer);

            try (ResultSet rs = stmt.executeQuery()) {
                rs.next();
                result = rs.getString("NOM");
            }
        }

        return result;
    }

    // getAdress1 permet d'obtenir l'addresse1 du client entré en paramètre
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

    // getAdress2 permet d'obtenir l'addresse2 du client entré en paramètre
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

    // getCity permet d'obtenir la ville du client entré en paramètre
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

    // getState permet d'obtenir l'état dans lequel vit le client entré en paramètre
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

    // getPhone permet d'obtenir le numéro de téléphone du client entré en paramètre
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

    // getFax permet d'obtenir le fax du client entré en paramètre
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

    // getEmail permet d'obtenir l'email du client entré en paramètre
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

    // getProductId permet de récupérer le product_id correspondant à la description du produit donnée en paramètre
    public int getProductId(String description) throws SQLException {

        int result = 0;
        String sql = "SELECT PRODUCT_ID AS ID FROM PRODUCT WHERE DESCRIPTION = ?";

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
    public double getPurchaseCostWithDescription(String description) throws SQLException {

        double result = 0.;
        String sql = "SELECT PURCHASE_COST AS COST FROM PRODUCT WHERE DESCRIPTION = ?";

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
    
    // modifyBonCommande permet de mofidier un bon de commande d'un client
    public int modifyBonCommande(int num, int quantity, String date) throws SQLException {

        int result = 0;
        String sql = "UPDATE PURCHASE_ORDER SET QUANTITY = ?, SET SALES_DATE = ? WHERE ORDER_NUM = ?";

        try (Connection connection = myDataSource.getConnection();
                PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, quantity);
            stmt.setString(2, date);
            stmt.setInt(3, num);
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
    
    
}
