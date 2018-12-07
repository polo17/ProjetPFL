package Controle;

import Modele.DAO;
import Modele.DataSourceFactory;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Ludovic
 */
@WebServlet(name = "Controle", urlPatterns = {"/Controle"})
public class Controle extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, SQLException {

        if (actionIs(request, "Connexion")) {
            newConnection(request, response);
        }
        else if (actionIs(request, "Déconnexion")) {
            showView("Connexion.jsp", request, response);
        }
        else if (actionIs(request, "Ajouter")){
            ajoutCommande(request, response);
        }
        else if(actionIs(request, "SupprimCommande")){
            supprCommande(request, response);
        }
        else if (actionIs(request, "Modif_cli")) {
            modifClient(request, response);
        }
        else {
            showView("Connexion.jsp", request, response);
	}
    }
        private void newConnection(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException {
            
            DAO dao = new DAO(DataSourceFactory.getDataSource());
            
            List<String> logins = dao.getEmails();
            List<Integer> mdps = dao.getCustomerIds();
             
            HttpSession session = request.getSession();
            
            String user = request.getParameter("email");
            int mdp = Integer.parseInt(request.getParameter("mdp"));
            
            session.setAttribute("custom_id", mdp);
            
            System.out.println("login "+user +" mot de passe  "+mdp);
            
            //jumboeagle@example.com
            //1
            
            
            if ( mdp == 1234 && user.equals("admin") ) pageAdmin(request, response);
   
            else if (mdps.contains(mdp) && logins.contains(user) &&
                    mdps.indexOf(mdp) == logins.indexOf(user)){

                pageClient(request, response);
            }
            
            else{
                String err = "Nom ou mot de passe incorrect";
                request.setAttribute("erreur", err);
                
                showView("Connexion.jsp", request, response);
            }
	}
    
    
    	private void pageClient(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException {
            HttpSession session = request.getSession();
            int id = (int) session.getAttribute("custom_id");
            
            DAO dao = new DAO(DataSourceFactory.getDataSource());
            
            List<Produit> produits = new LinkedList<>();
            
            List<String> descriptions = dao.getProductDescription(id);
            List<Integer> quantites = dao.getQuantity(id);            
            List<Double> prix = dao.getPurchaseCost(id);
            List<String> dates = dao.getDates(id);
            List<String> companies = dao.getCompanies(id);
            List<Integer> orders_num = dao.getOrderNum(id);

            List<Double> totaux = new LinkedList<>();
            for (int i = 0 ; i < quantites.size() ; i++){
                totaux.add(quantites.get(i)*prix.get(i));
            }
            
            for (int i = 0 ; i < descriptions.size() ; i++){
                Produit p = new Produit(descriptions.get(i),quantites.get(i),prix.get(i),dates.get(i),companies.get(i),totaux.get(i), orders_num.get(i));
                produits.add(p);
            }
            
            List<String> choixProd = dao.getDescriptions();
            request.setAttribute("choixProd", choixProd);
            
            request.setAttribute("produits",produits);      

            String name = dao.getName(id);
            request.setAttribute("nom", name);
            
            String address1 = dao.getAdress1(id);
            request.setAttribute("adresse", address1);
            
            String adress2 = dao.getAdress2(id);
            request.setAttribute("compadresse", adress2);
            
            String city = dao.getCity(id);
            request.setAttribute("ville", city);
            
            String zip = dao.getZip(id);
            request.setAttribute("cp", zip);
            
            String state = dao.getState(id);
            request.setAttribute("etat", state);
            
            String phone = dao.getPhone(id);
            request.setAttribute("telephone", phone);
            
            String fax = dao.getFax(id);
            request.setAttribute("fax", fax);
            
            String email = dao.getEmail(id);
            request.setAttribute("email", email);
            
            
            
            showView("Client.jsp", request, response);
	}        
        
        private void modifClient(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException {
            HttpSession session = request.getSession();
            
            DAO dao = new DAO(DataSourceFactory.getDataSource());
            
            int id = (Integer) session.getAttribute("custom_id");
            
            String nom = request.getParameter("nom");
            String adresse = request.getParameter("adresse");
            String complement = request.getParameter("compadresse");
            String ville = request.getParameter("ville");
            String cp = request.getParameter("cp");
            String etat = request.getParameter("etat");
            String telephone = request.getParameter("telephone");
            String fax = request.getParameter("fax");
            String email = request.getParameter("email");
            
            dao.modifyClient(nom,adresse,complement,ville,etat,cp,telephone,fax,email,id);
            
            pageClient(request, response);
	}
        
        private void ajoutCommande(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException {
            HttpSession session = request.getSession();
            
            DAO dao = new DAO(DataSourceFactory.getDataSource());
            
            //Description
            String nom = request.getParameter("nom");
            System.out.println(nom);
           //quantite
            int quantite = Integer.parseInt(request.getParameter("quantite"));
            
            //order_num int
            int order_num = dao.getMaxOrderNum() +1 ;
            //cistomer id int
            int customer_id = (Integer) session.getAttribute("custom_id");
            
            //product id int
            int product_id = dao.getProductId(nom);
            
            //prix double
            double prix = dao.getPurchaseCost(nom);
            
            //date string
            Date actuelle = new Date();
            DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
            
            String dat = dateFormat.format(actuelle);
            
            
            //companie string
            String companie = "Poney Express";
            
            
            
            dao.addBonCommande(order_num, customer_id, product_id, quantite, prix, dat, dat, companie);
            
            pageClient(request, response);
	}
        
        private void supprCommande(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException {
            HttpSession session = request.getSession();
            
            DAO dao = new DAO(DataSourceFactory.getDataSource());

            int order = Integer.parseInt(request.getParameter("order"));

            dao.deleteBonCommande(order);
            
            pageClient(request, response);
	}
        
        private void pageAdmin(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
            HttpSession session = request.getSession();
            
            showView("Admin.jsp", request, response);
	}
    
    
        private boolean actionIs(HttpServletRequest request, String action) {
            return action.equals(request.getParameter("action"));
	}
        
        private void showView(String jsp, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
            getServletConfig().getServletContext().getRequestDispatcher("/Vue/" + jsp).forward(request, response);
	}

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (SQLException ex) {
            Logger.getLogger(Controle.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (SQLException ex) {
            Logger.getLogger(Controle.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
