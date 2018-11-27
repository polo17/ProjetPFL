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
