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
        String sql ="SELECT DESCRIPTION FROM product INNER JOIN purchase_order ON product.product_id = purchase_order.product_id WHERE customer_id = ?";
        
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
        String sql ="SELECT Purchase_Cost FROM product INNER JOIN purchase_order ON product.product_id = purchase_order.product_id WHERE customer_id = ?";
        
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
    
    // getDates permet d'obtenir les dates de vente des produits achetés par le client
    public List<String> getDates(int customer_id) throws SQLException {
        
        List<String> result = new LinkedList<>();
        String sql ="SELECT SALES_DATE FROM purchase_order WHERE customer_id = ?";
        
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
        String sql ="SELECT FREIGHT_COMPANY FROM purchase_order WHERE customer_id = ?";
        
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
        String sql ="SELECT QUANTITY FROM PURCHASE_ORDER WHERE customer_id = ?";
        
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
    public int addBonCommande(int customer_id,int quantity) throws SQLException { // A MODIFIER
        
        int result = 0;
	String sql = "INSERT INTO PURCHASE_ORDER (ORDER_NUM,CUSTOMER_ID,PRODUCT_ID,QUANTITY,SHIPPING_COST,SALES_DATE,SHIPPING_DATE,FREIGHT_COMPANY) VALUES (?,?,?,?,?,?,?,?) WHERE CUSTOMER_ID = ? AND QUANTITY = ?";
        
	try (Connection connection = myDataSource.getConnection(); 
            PreparedStatement stmt = connection.prepareStatement(sql)) {
                stmt.setInt(1, customer_id);
                stmt.setInt(2, quantity);
                
                ResultSet rs = stmt.executeQuery();
                int order_num = rs.getInt("ORDER_NUM");
                String add1 = rs.getString("ADDRESSLINE1");
                String add2 = rs.getString("ADDRESSLINE2");
                String city = rs.getString("CITY");
                String zip = rs.getString("ZIP");
                String state = rs.getString("STATE");
                String phone = rs.getString("PHONE");
                String fax = rs.getString("FAX");
                
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
    
    // modifyBonCommande permet de mofidier un bon de commande d'un client
    public int modifyBonCommande(int num,int quantity) throws SQLException {
        
        int result = 0;
        String sql = "UPDATE PURCHASE_ORDER SET QUANTITY = ? WHERE ORDRE_NUM = ?";
        
        try (Connection connection = myDataSource.getConnection(); 
            PreparedStatement stmt = connection.prepareStatement(sql)) {
                stmt.setInt(1, quantity);
                stmt.setInt(2,num);
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
    public int modifyClient(int customer_id) throws SQLException {
        
        int result = 0;
        String sql = "UPDATE CUSTOMER SET NAME, ADDRESSLINE1, ADDRESSLINE2, CITY, ZIP, STATE, PHONE, FAX, EMAIL = VALUES(?,?,?,?,?,?,?,?,?) WHERE CUSTOMER_ID = ?";
        
        try(Connection connection = myDataSource.getConnection(); 
            PreparedStatement stmt = connection.prepareStatement(sql)) {
                stmt.setInt(1, customer_id);
                
                ResultSet rs = stmt.executeQuery();
                String nom = rs.getString("NAME");
                String add1 = rs.getString("ADDRESSLINE1");
                String add2 = rs.getString("ADDRESSLINE2");
                String city = rs.getString("CITY");
                String zip = rs.getString("ZIP");
                String state = rs.getString("STATE");
                String phone = rs.getString("PHONE");
                String fax = rs.getString("FAX");
                String email = rs.getString("EMAIL");
                
		result = stmt.executeUpdate();
            }
        return result;
    }
    
}
