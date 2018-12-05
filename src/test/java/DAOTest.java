/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import Modele.DAO;
import static Modele.DataSourceFactory.getDataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import javax.sql.DataSource;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author pedago
 */
public class DAOTest {
    
    private DAO myDAO; // L'objet à tester
    private DataSource myDataSource; // La source de données à utiliser
    private static Connection myConnection ; // La connection à la BD de test
    
    public DAOTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() throws SQLException {
        myDataSource = getDataSource();
	myConnection = myDataSource.getConnection();
	//executeSQLScript(myConnection, "schema.sql");
	//executeSQLScript(myConnection, "bigtestdata.sql");		
        myDAO = new DAO(myDataSource);
    }
    
    @After
    public void tearDown() throws SQLException {
        myConnection.close(); // La base de données de test est détruite ici
    }

    // TESTS //
    
    // testGetEmails() teste la méthode getEmails() du DAO
    @Test
    public void testGetEmails() throws SQLException {
        List<String> l = myDAO.getEmails();
        assertEquals(13,l.size());
        assertEquals("jumboeagle@example.com",l.get(0));
    }
    
    // testGetCustomerIds() teste la méthode getCustomerIds() du DAO
    @Test
    public void testGetCustomerIds() throws SQLException {
        List<Integer> l = myDAO.getCustomerIds();
        assertEquals(13,l.size());
        assertEquals(1,(long)l.get(0));
    }
    
    // testGetProductDescription() teste la méthode getProductDescription()
    @Test
    public void testGetProductDescription() throws SQLException {
        List<String> l = myDAO.getProductDescription(1); //pour le customer_id 1
        assertEquals(2,l.size());
        assertEquals("Identity Server",l.get(0));
    }
    
    // testGetPurchaseCost() teste la méthode getPurchaseCost()
    @Test
    public void testGetPurchaseCost() throws SQLException {
        List<Double> l = myDAO.getPurchaseCost(1); //pour le customer_id 1
        assertEquals(2,l.size());
        assertEquals(1095,00,l.get(0));
    }
    
    // testGetDates() teste la méthode getDates()
    @Test
    public void testGetDates() throws SQLException {
        List<String> l = myDAO.getDates(1); //pour le customer_id 1
        assertEquals(2,l.size());
        assertEquals("2011-05-24",l.get(1));
    }
    
    // testGetCompanies() teste la méthode getCompanies()
    @Test
    public void testGetCompanies() throws SQLException {
        List<String> l = myDAO.getCompanies(2); //pour le customer_id 2
        assertEquals(2,l.size());
        assertEquals("Poney Express",l.get(1));
    }
    
    // testGetQuantity() teste la méthode getQuantity()
    @Test
    public void testGetQuantity() throws SQLException {
        List<Integer> l = myDAO.getQuantity(2); //pour le customer_id 2
        assertEquals(2,l.size());
        assertEquals(25,(long)l.get(1));
    }
    
    // testaddBonCommande() teste la méthode addBonCommande()
    @Test
    public void testaddBonCommande() throws SQLException {
        int r = myDAO.addBonCommande(10,1,980001,10,10.0,"2010-10-10","2010-10-10","10"); 
        assertEquals(2,r);
        //assertEquals(25,(long)l.get(1));
    }
    
    // testGetMaxOrderNum() teste la méthode getMaxOrderNum()
    @Test
    public void testGetMaxOrderNum() throws SQLException {
        int r = myDAO.getMaxOrderNum();
        assertEquals(30298004,r);
    }
    
    
}
