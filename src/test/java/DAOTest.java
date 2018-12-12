/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import Modele.DAO;
import static Modele.DataSourceFactory.getDataSource;
import java.io.File;
import java.io.IOException;
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
import org.junit.Ignore;

import org.hsqldb.cmdline.SqlFile;
import org.hsqldb.cmdline.SqlToolError;

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
    public void setUp() throws SQLException, IOException, SqlToolError {
        myDataSource = getDataSource();
	myConnection = myDataSource.getConnection();
        String sqlFilePath = DAOTest.class.getResource("testdata.sql").getFile();
	SqlFile sqlFile = new SqlFile(new File(sqlFilePath));
	sqlFile.setConnection(myConnection);
	sqlFile.execute();
	sqlFile.closeReader();
        myDAO = new DAO(myDataSource);
    }
    
    @After
    public void tearDown() throws SQLException {
        myConnection.close(); // La base de données de test est détruite ici
        myDAO = null;
    }

    // TESTS //
    
    // testGetEmails() teste la méthode getEmails() du DAO
    @Test
    public void testGetEmails() throws SQLException {
        List<String> l = myDAO.getEmails();
        assertEquals(2,l.size());
        assertEquals("jumboeagle@example.com",l.get(0));
    }
    
    // testGetCustomerIds() teste la méthode getCustomerIds() du DAO
    @Test
    public void testGetCustomerIds() throws SQLException {
        List<Integer> l = myDAO.getCustomerIds();
        assertEquals(2,l.size());
        assertEquals(1,(long)l.get(0));
    }
    
    // testGetProductDescription() teste la méthode getProductDescription() du DAO
    @Test
    public void testGetProductDescription() throws SQLException {
        List<String> l = myDAO.getProductDescription(1); // pour le customer_id 1
        assertEquals(1,l.size());
        assertEquals("Identity Server",l.get(0));
    }
    
    // testGetPurchaseCost() teste la méthode getPurchaseCost() du DAO
    @Test
    public void testGetPurchaseCost() throws SQLException {
        List<Double> l = myDAO.getPurchaseCost(1); // pour le customer_id 1
        assertEquals(1,l.size());
        assertEquals(1095,00,l.get(0));
    }
    
    // testGetDescriptions() teste la méthode getDescriptions() du DAO
    @Test
    public void testGetDescriptions() throws SQLException {
        List<String> l = myDAO.getDescriptions();
        assertEquals(3,l.size());
    }
    
    // testGetDates() teste la méthode getDates() du DAO
    @Test
    public void testGetDates() throws SQLException {
        List<String> l = myDAO.getDates(1); // pour le customer_id 1
        assertEquals(1,l.size());
        assertEquals("2011-05-24",l.get(0));
    }
    
    // testGetCompanies() teste la méthode getCompanies() du DAO
    @Test
    public void testGetCompanies() throws SQLException {
        List<String> l = myDAO.getCompanies(2); // pour le customer_id 2
        assertEquals(2,l.size());
        assertEquals("Poney Express",l.get(1));
    }
    
    // testGetQuantity() teste la méthode getQuantity() du DAO
    @Test
    public void testGetQuantity() throws SQLException {
        List<Integer> l = myDAO.getQuantity(2); // pour le customer_id 2
        assertEquals(2,l.size());
        assertEquals(25,(long)l.get(1));
    }
    
    // testGetQuantity_pid() teste la méthode getQuantity_pid() du DAO
    @Test
    public void testGetQuantity_pid() throws SQLException {
        int r = myDAO.getQuantity_pid(980001); // pour le produit 980001
        assertEquals(10,r);
    }
    
    // testGetQuantity_p() teste la méthode getQuantity_p() du DAO
    @Test
    public void testGetQuantity_p() throws SQLException {
        int r = myDAO.getQuantity_p(10398003); // pour le bon de commande 10398003
        assertEquals(25,r);
    }
    
    // testGetCompanies_p() teste la méthode getCompanies_p() du DAO
    @Test
    public void testGetCompanies_p() throws SQLException {
        String s = myDAO.getCompanies_p(10398002); // pour le bon de commande 10398002
        assertEquals("Poney Express",s);
    }
    
    // testGetPurchaseCost_p() teste la méthode getPurchaseCost_p() du DAO
    @Test
    public void testGetPurchaseCost_p() throws SQLException {
        Double d = myDAO.getPurchaseCost_p(10398001); // pour le bon de commande 10398001
        assertEquals(1095,00,d);
    }
    
    // testGetDates_p() teste la méthode getDates_p() du DAO
    @Test
    public void testGetDates_p() throws SQLException {
        String s = myDAO.getDates_p(10398001); // pour le bon de commande 10398001
        assertEquals("2011-05-24",s);
    }
       
    // testGetDescription_p() teste la méthode getDescription_p() du DAO
    @Test
    public void testGetDescription_p() throws SQLException {
        String s = myDAO.getDescription_p(10398001); // pour le bon de commande 10398001
        assertEquals("Identity Server",s);
    }
    
    // testGetDescription_pid() teste la méthode getDescription_pid() du DAO
    @Test
    public void testGetDescription_pid() throws SQLException {
        String s = myDAO.getDescription_pid(980005); // pour le produit 980005
        assertEquals("Accounting Application",s);
    }
    
    // testGetMaxOrderNum() teste la méthode getMaxOrderNum()
    @Test
    public void testGetMaxOrderNum() throws SQLException {
        int r = myDAO.getMaxOrderNum();
        assertEquals(10398003,r);
    }
    
    // testGetProducts_id() teste la méthode getProducts_id() du DAO
    @Test
    public void testGetProducts_id() throws SQLException {
        List<Integer> l = myDAO.getProducts_id();
        assertEquals(3,l.size());
        assertEquals(980005,(long)l.get(1));
    }
    
    // testGetStates() teste la méthode getStates() du DAO
    @Test
    public void testGetStates() throws SQLException {
        List<String> l = myDAO.getStates();
        assertEquals(2,l.size());
    }
    
    // testGetCustomer_s() teste la méthode getCustomer_s() du DAO
    @Test
    public void testGetCustomer_s() throws SQLException {
        List<Integer> l = myDAO.getCustomer_s("FL"); // pour l'état de Floride
        assertEquals(2,l.size());
    }
    
    // testGetMaxDate() teste la méthode getMaxDate() du DAO
    @Test
    public void testGetMaxDate() throws SQLException {
        String s = myDAO.getMaxDate();
        assertEquals("2012-05-24",s);
    }   
    
    // testGetMinDate() teste la méthode getMinDate() du DAO
    @Test
    public void testGetMinDate() throws SQLException {
        String s = myDAO.getMinDate();
        assertEquals("2011-01-24",s);
    }   
    
    // testGetOrderNum() teste la méthode getOrderNum() du DAO
    @Test
    public void testGetOrderNum() throws SQLException {
        List<Integer> l = myDAO.getOrderNum(2); // pour le customer_id 2
        assertEquals(2,l.size());
    }
            
    // testGetZip() teste la méthode getZip() du DAO
    @Test
    public void testGetZip() throws SQLException {
        String s = myDAO.getZip(1); // pour le customer_id 1
        assertEquals("95117",s);
    }

    // testGetName() teste la méthode getName() du DAO
    @Test
    public void testGetName() throws SQLException {
        String s = myDAO.getName(1); // pour le customer_id 1
        assertEquals("Jumbo Eagle Corp",s);
    }
    
    // testGetAdress1() teste la méthode getAdress1() du DAO
    @Test
    public void testGetAdress1() throws SQLException {
        String s = myDAO.getAdress1(1); // pour le customer_id 1
        assertEquals("111 E. Las Olivas Blvd",s);
    }
            
    // testGetAdress2() teste la méthode getAdress2() du DAO
    @Test
    public void testGetAdress2() throws SQLException {
        String s = myDAO.getAdress2(2); // pour le customer_id 2
        assertEquals("P.O. Box 567",s);
    }
    
    // testGetCity() teste la méthode getCity() du DAO
    @Test
    public void testGetCity() throws SQLException {
        String s = myDAO.getCity(1); // pour le customer_id 1
        assertEquals("Fort Lauderdale",s);
    }
    
    // testGetState() teste la méthode getState() du DAO
    @Test
    public void testGetState() throws SQLException {
        String s = myDAO.getState(1); // pour le customer_id 1
        assertEquals("FL",s);
    }
            
    // testGetPhone() teste la méthode getPhone() du DAO
    @Test
    public void testGetPhone() throws SQLException {
        String s = myDAO.getPhone(1); // pour le customer_id 1
        assertEquals("305-555-0188",s);
    }
    
    // testGetFax() teste la méthode getFax() du DAO
    @Test
    public void testGetFax() throws SQLException {
        String s = myDAO.getFax(1); // pour le customer_id 1
        assertEquals("305-555-0189",s);
    }
    
    // testGetEmail() teste la méthode getEmail() du DAO
    @Test
    public void testGetEmail() throws SQLException {
        String s = myDAO.getEmail(2); // pour le customer_id 2
        assertEquals("www.new.example.com",s);
    }
    
    // testGetProductId() teste la méthode getProductId() du DAO
    @Test
    public void testGetProductId() throws SQLException {
        int r = myDAO.getProductId("Identity Server");
        assertEquals(980001,r);
    }
    
    // testGetPurchaseCostWithDescription() teste la méthode getPurchaseCost() du DAO
    @Test
    public void testGetPurchaseCostWithDescription() throws SQLException {
        double r = myDAO.getPurchaseCostWithDescription("Identity Server");
        assertEquals(1095,00,r);
    }
    
    // testaddBonCommande() teste la méthode addBonCommande() du DAO
    @Test
    public void testaddBonCommande() throws SQLException {
        List<Integer> l = myDAO.getQuantity(2); // liste des commandes passées par le customer_id 2 avant ajout
        assertEquals(2,l.size());
        int r = myDAO.addBonCommande(10,2,980001,10,10.0,"2010-10-10","2010-10-10","10");
        List<Integer> l2 = myDAO.getQuantity(2); // liste des commandes passées par le customer_id 2 après ajout
        assertEquals(3,l2.size());
    }
    
    // testModifyBonCommande() teste la méthode modifyBonCommande() du DAO
    @Test
    public void testModifyBonCommande() throws SQLException {
        int a = myDAO.getQuantity_p(10398001); // quantité de produits achetés dans le bon de commande 10398001 avant modification
        String b = myDAO.getDates_p(10398001); // date d'achat correspondant au bon de commande 10398001 avant modification
        assertEquals(10,a);
        assertEquals("2011-05-24",b);
        int r = myDAO.modifyBonCommande(10398001,20,"2011-01-01");
        int a2 = myDAO.getQuantity_p(10398001); // quantité de produits achetés dans le bon de commande 10398001 après modification
        String b2 = myDAO.getDates_p(10398001); // date d'achat correspondant au bon de commande 10398001 après modification
        assertEquals(20,a2);
        assertEquals("2011-01-01",b2);
    }  
    
    
    // testDeleteBonCommande() teste la méthode deleteBonCommande()
    @Test 
    public void testDeleteBonCommande() throws SQLException {
        List<Integer> l = myDAO.getQuantity(2); // liste des commandes passées par le customer_id 2 avant suppression
        assertEquals(2,l.size());
        int r1 = myDAO.addBonCommande(10,2,980001,10,10.0,"2010-10-10","2010-10-10","10");
        List<Integer> l2 = myDAO.getQuantity(2); // liste des commandes passées par le customer_id 2 avant suppression et après ajout
        assertEquals(3,l2.size());
        int r2 = myDAO.deleteBonCommande(10);
        List<Integer> l3 = myDAO.getQuantity(2); // liste des commandes passées par le customer_id 2 après suppression
        assertEquals(2,l3.size());
    }
    
    //ModifyClient à faire
    
    
    public static DataSource getDataSource() throws SQLException {
		org.hsqldb.jdbc.JDBCDataSource ds = new org.hsqldb.jdbc.JDBCDataSource();
		ds.setDatabase("jdbc:hsqldb:mem:testcase;shutdown=true");
		ds.setUser("sa");
		ds.setPassword("sa");
		return ds;
	}
}
