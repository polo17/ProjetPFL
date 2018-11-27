/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controle;

import Modele.DAO;
import Modele.DataSourceFactory;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author pedago
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
            throws ServletException, IOException {
             
        if (actionIs(request, "Connexion")) {
            newConnection(request, response);
        }
        else if (actionIs(request, "Déconnexion")) {
            showView("Connexion.jsp", request, response);
            //endGame(request, response);
        }
        else {
            showView("Connexion.jsp", request, response);
	}
    }
        private void newConnection(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
            
            DAO dao = new DAO(DataSourceFactory.getDataSource());
             
            HttpSession session = request.getSession();
            
            String user = request.getParameter("nomUtili");
            session.setAttribute("nomUtili", user);
            
            String mdp = request.getParameter("mdp");
            session.setAttribute("nomUtili", user);
            
            if ( mdp.equals("admin") && user.equals("admin") ) pageAdmin(request, response);
            //else if{
                
            //}
            else{
                String err = "Nom ou mot de passe incorrect";
                request.setAttribute("erreur", err);
            }
	}
    
    
    	private void pageClient(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
            HttpSession session = request.getSession();
            
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
        processRequest(request, response);
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
        processRequest(request, response);
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
