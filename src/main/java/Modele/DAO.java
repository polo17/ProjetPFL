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
    
    public List<Integer> getPurchaseCost(int customer_id) throws SQLException {
        
        List<Integer> result = new LinkedList<>();
        String sql ="SELECT Purchase_Cost FROM product INNER JOIN purchase_order ON product.product_id = purchase_order.product_id WHERE customer_id = ?";
        
        try (Connection connection = myDataSource.getConnection(); 
            PreparedStatement stmt = connection.prepareStatement(sql)) {
                stmt.setInt(1, customer_id);
                ResultSet rs = stmt.executeQuery();
		while (rs.next()) {
                    int price = rs.getInt("Purchase_Cost");
                    result.add(price);
		}
	}
        
	return result;
    }
    
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
    public int addBonCommande(int quantity) throws SQLException {
        
        int result = 0;
	String sql = "INSERT INTO PURCHASE_ORDER ?";
        
	try (Connection connection = myDataSource.getConnection(); 
            PreparedStatement stmt = connection.prepareStatement(sql)) {
                stmt.setInt(1, quantity);
		result = stmt.executeUpdate();
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
}
