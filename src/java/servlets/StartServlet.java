/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets;

import db.Bid;
import db.DBManager;
import db.Group;
import db.User;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.log4j.Logger;

/**
 *
 * @author Alessandro
 */
public class StartServlet extends HttpServlet {

    private DBManager manager;
    static Logger log = Logger.getLogger(StartServlet.class.getName());

    public void init() throws ServletException {    // inizializza il DBManager dagli attributi di Application
        this.manager = (DBManager) super.getServletContext().getAttribute("dbmanager");
    }

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

        ServletContext sc = getServletContext();
        List<Group> publicGroups = null; //list of public groups
        List<Group> privateGroups = null; //list of private groups
        List<Bid> bids = null; //list of bids
        List<String> updatedGroups = new ArrayList<String>(); //list of updated groups

        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        updatedGroups = (List<String>) session.getAttribute("updatedGroups");

        try {
            publicGroups = manager.getPublicGroups(); // get all public groups

            if (user != null) {
                privateGroups = manager.getPrivateGroups(user.getUserId()); //get all private groups
                bids = manager.getBids(user.getUserId()); //get the bids
                request.setAttribute("bids", bids);

               for (int x =0; x < publicGroups.size(); x++){ //check for updated public groups
                   if (updatedGroups.contains(""+publicGroups.get(x).getGroupId())){
                       publicGroups.get(x).setUpdated(true);
                   }
                   else {
                       publicGroups.get(x).setUpdated(false);
                   }
               }
               
               for (int x =0; x < privateGroups.size(); x++){ //check for updated private groups
                   if (updatedGroups.contains(""+privateGroups.get(x).getGroupId())){
                       privateGroups.get(x).setUpdated(true);
                   }
                    else {
                       privateGroups.get(x).setUpdated(false);
                   }
               }
            }
        } catch (Exception ex) {
            log.error(ex.toString());
        }

        request.setAttribute("publicGroups", publicGroups);
        request.setAttribute("privateGroups", privateGroups);
        RequestDispatcher rd = sc.getRequestDispatcher("/index.jsp");
        rd.forward(request, response);
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
