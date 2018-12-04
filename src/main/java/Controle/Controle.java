package Controle;

import Modele.DAO;
import Modele.DataSourceFactory;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
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
        else if (actionIs(request, "Modif_cli")) {
            System.out.println("modif");
            Modif_cli(request, response);
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
            List<Integer> prix = dao.getPurchaseCost(id);
            List<String> dates = dao.getDates(id);
            List<String> companies = dao.getCompanies(id);

            List<Integer> totaux = new LinkedList<>();
            for (int i = 0 ; i < quantites.size() ; i++){
                totaux.add(quantites.get(i)*prix.get(i));
            }
            
            for (int i = 0 ; i < descriptions.size() ; i++){
                Produit p = new Produit(descriptions.get(i),quantites.get(i),prix.get(i),dates.get(i),companies.get(i),totaux.get(i));
                produits.add(p);
            }
            
            request.setAttribute("produits",produits);
            /*request.setAttribute("descriptions",descriptions);
            request.setAttribute("quantites",quantites);
            request.setAttribute("prix",prix);
            request.setAttribute("totaux",totaux);
            request.setAttribute("dates",dates);
            request.setAttribute("companies",companies);*/
            
            showView("Client.jsp", request, response);
	}
        
        private void Modif_cli(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
            HttpSession session = request.getSession();
            
            System.out.println("modif");
            
            DAO dao = new DAO(DataSourceFactory.getDataSource());
            
            String customer = (String) session.getAttribute("custom_id");
            
            String nom = request.getParameter("nom");
            String adresse = request.getParameter("adresse");
            String complément = request.getParameter("compadresse");
            String ville = request.getParameter("ville");
            String cp = request.getParameter("cp");
            String etat = request.getParameter("etat");
            String telephone = request.getParameter("télephone");
            String fax = request.getParameter("fax");
            String email = request.getParameter("email");
            
            //dao.modifCli(...,custom_id)
            
            showView("Client.jsp", request, response);
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
