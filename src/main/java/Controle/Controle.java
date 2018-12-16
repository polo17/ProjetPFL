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
        } else if (actionIs(request, "Déconnexion")) {
            showView("Connexion.jsp", request, response);
        } else if (actionIs(request, "Ajouter")) {
            ajoutCommande(request, response);
        } else if (actionIs(request, "AjouterProd")) {
            ajoutProduit(request, response);
        } else if (actionIs(request, "SupprimCommande")) {
            supprCommande(request, response);
        } else if (actionIs(request, "modifier")) {
            modifCommande(request, response);
        } else if (actionIs(request, "Modif_cli")) {
            modifClient(request, response);
        } else {
            showView("Connexion.jsp", request, response);
        }
    }
    
    // <editor-fold defaultstate="collapsed" desc="Méthode pour l'initialisation d'une nouvelle connexion">

    /**
     * Sauvegarde le client connecté dans la session pour y afficher son espace client
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     * @throws SQLException 
     */
    private void newConnection(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException {

        DAO dao = new DAO(DataSourceFactory.getDataSource());

        List<String> logins = dao.getEmails();
        List<Integer> mdps = dao.getCustomerIds();

        HttpSession session = request.getSession();

        String user = request.getParameter("email");
        int mdp = Integer.parseInt(request.getParameter("mdp"));

        session.setAttribute("custom_id", mdp);

        System.out.print("");
        System.out.println("Connexion sur " + user + " Mot de passe  " + mdp);
        System.out.print("");
        
        //jumboeagle@example.com
        //1
        if (mdp == 1234 && user.equals("admin")) {
            pageAdmin(request, response);
        } else if (mdps.contains(mdp) && logins.contains(user)
                && mdps.indexOf(mdp) == logins.indexOf(user)) {

            pageClient(request, response);
        } else {
            String err = "Nom ou mot de passe incorrect";
            request.setAttribute("erreur", err);

            showView("Connexion.jsp", request, response);
        }
    }
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Méthodes pour l'ajout et modification de produits dans la base de donnée">
    
    /***
     * Fonction d'ajout d'un produit dans la base de données
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     * @throws SQLException 
     */
    private void ajoutProduit(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException {
        HttpSession session = request.getSession();

        DAO dao = new DAO(DataSourceFactory.getDataSource());

        int prod_id = dao.getMaxProdId() + 1;
        
        String nom = request.getParameter("nom");
        int quantite = Integer.parseInt(request.getParameter("quantite"));
        int prix = Integer.parseInt(request.getParameter("prix"));
        int markup = Integer.parseInt(request.getParameter("markup"));
        String code = request.getParameter("code");
        String fabricant = request.getParameter("manu");
        int manu_id = dao.getManuIdWithName(fabricant);
        
        String avaible = "TRUE";

        dao.addProduit(prod_id, manu_id, code, prix, quantite, markup, avaible, nom);

        pageAdmin(request, response);
    }
    
        /***
     * Fonction de modification d'un produit dans la base de données
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     * @throws SQLException 
     */
    private void modifierProduit(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException {
        HttpSession session = request.getSession();

        DAO dao = new DAO(DataSourceFactory.getDataSource());

        int prod_id = dao.getMaxProdId() + 1;
        
        String nom = request.getParameter("nom");
        int quantite = Integer.parseInt(request.getParameter("quantite"));
        int prix = Integer.parseInt(request.getParameter("prix"));
        int markup = Integer.parseInt(request.getParameter("markup"));
        String code = request.getParameter("code");
        String fabricant = request.getParameter("manu");
        int manu_id = dao.getManuIdWithName(fabricant);
        
        String avaible = "TRUE";

        dao.addProduit(prod_id, manu_id, code, prix, quantite, markup, avaible, nom);

        pageAdmin(request, response);
    }
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Méthode de modification d'un client">
    
    /***
     * Fonction de modification d'un client
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     * @throws SQLException 
     */
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
        
        int credit = dao.getCreditLimit(id);
        String discount = dao.getDiscountCode(id);

        dao.modifyClient(nom, adresse, complement, ville, etat, cp, telephone, fax, email,credit,discount, id);

        pageClient(request, response);
    }
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Méthodes d'ajout, modification et supression commande">

    /***
     * Fonction d'ajout d'une commande dans la base de donnée
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     * @throws SQLException 
     */
    private void ajoutCommande(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException {
        HttpSession session = request.getSession();

        DAO dao = new DAO(DataSourceFactory.getDataSource());

        String nom = request.getParameter("nom");
        int quantite = Integer.parseInt(request.getParameter("quantite"));
        int order_num = dao.getMaxOrderNum() + 1;
        int customer_id = (Integer) session.getAttribute("custom_id");
        int product_id = dao.getProductId(nom);
        double prix = dao.getPurchaseCostWithDescription(nom);
        
        java.util.Date d1 = new java.util.Date();
        java.sql.Date d2 = new java.sql.Date(d1.getTime());
        

        String companie = "Poney Express";

        dao.addBonCommande(order_num, customer_id, product_id, quantite, prix*0.004, d2, d2, companie);

        pageClient(request, response);
    }

    /***
     * Fonction de supression d'une commande
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     * @throws SQLException 
     */
    private void supprCommande(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException {
        
        DAO dao = new DAO(DataSourceFactory.getDataSource());

        int order = Integer.parseInt(request.getParameter("order"));

        dao.deleteBonCommande(order);

        pageClient(request, response);
    }

    private void modifCommande(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException {
        HttpSession session = request.getSession();

        DAO dao = new DAO(DataSourceFactory.getDataSource());
        
        int quant = Integer.parseInt(request.getParameter("quantite"));
        int order = Integer.parseInt(request.getParameter("orderm"));
        
        java.util.Date d1 = new java.util.Date();
        java.sql.Date d2 = new java.sql.Date(d1.getTime());
        
        dao.modifyBonCommande(order, quant, d2);

        pageClient(request, response);
    }
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Méthodes d'affichage pageClient et pageAdmin">
    
    /***
     * Réaffichage page client prenant en compte les mises à jour de la base de donnée
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     * @throws SQLException 
     */
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
        for (int i = 0; i < quantites.size(); i++) {
            totaux.add(quantites.get(i) * prix.get(i));
        }

        for (int i = 0; i < descriptions.size(); i++) {
            Produit p = new Produit(descriptions.get(i), quantites.get(i), prix.get(i), dates.get(i), companies.get(i), totaux.get(i), orders_num.get(i));
            produits.add(p);
        }

        List<String> choixProd = dao.getDescriptions();
        request.setAttribute("choixProd", choixProd);

        request.setAttribute("produits", produits);

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

    /***
     * Réaffichage page admin prenant en compte les mises à jour de la base de donnée
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     * @throws SQLException 
     */
    private void pageAdmin(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException {
        HttpSession session = request.getSession();

        DAO dao = new DAO(DataSourceFactory.getDataSource());

        String minDate = dao.getMinDate();
        String maxDate = dao.getMaxDate();

        request.setAttribute("minDate", minDate);
        request.setAttribute("maxDate", maxDate);

        double total_tout = 0;

        List<Integer> customers_ids = dao.getCustomerIds();
        List<ChiffreAffaire> chiffres = new LinkedList<>();

        List<String> states = dao.getStates();
        List<ChiffreAffaire> chiffres_etat = new LinkedList<>();
        
        List<Integer> pids = dao.getProducts_id();
        List<ChiffreAffaire> chiffres_prod = new LinkedList<>();
        


        for (int c = 0; c < customers_ids.size(); c++) { //Calcul du total entier
            List<Double> prix = dao.getPurchaseCost(1);
            List<Integer> quantites = dao.getQuantity(1);

            double total = 0;
            for (int i = 0; i < quantites.size(); i++) {
                total = total + quantites.get(i) * prix.get(i);
            }
            total_tout = total_tout + total;
        }
        
        for (int c = 0; c < pids.size(); c++) { //Calcul du chiffre par produit
            int pid = pids.get(c);
            
            int quantite = dao.getQuantity_pid(pid); //quantite de commande
            
            String nom = dao.getDescription_pid(pid); //nom du produit
            
            double prix = dao.getPurchaseCostWithDescription(nom); //prix du produit

            double total = 0;
            total = total + quantite * prix;

            ChiffreAffaire ca = new ChiffreAffaire(nom, ((total * 100) / total_tout) / 100);
            chiffres_prod.add(ca);
        }

        for (int c = 0; c < customers_ids.size(); c++) { //Calcul du chiffre par client
            int id = customers_ids.get(c);
            String nom = dao.getName(id);

            List<Double> prix = dao.getPurchaseCost(id);
            List<Integer> quantites = dao.getQuantity(id);

            double total = 0;
            for (int i = 0; i < quantites.size(); i++) {
                total = total + quantites.get(i) * prix.get(i);
            }

            ChiffreAffaire ca = new ChiffreAffaire(nom, ((total * 100) / total_tout) / 100);
            chiffres.add(ca);

        }
        for (int s = 0; s < states.size(); s++) { //Calcul du chiffre par etat
            List<Integer> custom = dao.getCustomer_s(states.get(s));
            for (int c = 0; c < custom.size(); c++) { //Calcul du chiffre par etat (en parcourant les habitants des états)
                int id = customers_ids.get(c);
                String nom = dao.getName(id);

                List<Double> prix = dao.getPurchaseCost(id);
                List<Integer> quantites = dao.getQuantity(id);

                double total = 0;
                for (int i = 0; i < quantites.size(); i++) {
                    total = total + quantites.get(i) * prix.get(i);
                }
                
                ChiffreAffaire ca = new ChiffreAffaire("US-"+states.get(s), total);
                chiffres_etat.add(ca);

            }
        }

        List<String> codes = dao.getProductCodes();
        
        List<String> noms = dao.getManuNames();
        
        request.setAttribute("choixCode", codes);
        
        request.setAttribute("choixManu", noms);
        
        request.setAttribute("chiffres", chiffres);
        request.setAttribute("chiffres_etat", chiffres_etat);
        request.setAttribute("chiffres_prod", chiffres_prod);

        showView("Admin.jsp", request, response);
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Méthodes de fonctionnement pour affichage et récupération action">
    
    private boolean actionIs(HttpServletRequest request, String action) {
        return action.equals(request.getParameter("action"));
    }

    private void showView(String jsp, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        getServletConfig().getServletContext().getRequestDispatcher("/Vue/" + jsp).forward(request, response);
    }
    // </editor-fold>
    

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
