/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets;

import db.DBManager;
import db.User;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
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
public class AcceptBidsServlet extends HttpServlet {

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

        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");

        String[] acceptedBids = request.getParameterValues("accepted"); //checkbox params
        String[] refusedBids = request.getParameterValues("refused");

        List<String> accBids = null;//list of accepted bids
        List<String> refBids = null; //list of refused bids
        List<String> conflicts = null; //list of conflicts

        if (acceptedBids != null) { //fill accepting list
            accBids = new ArrayList(acceptedBids.length);
            for (int x = 0; x < acceptedBids.length; x++) {
                accBids.add(acceptedBids[x]);
            }
        }

        if (refusedBids != null) { // fill refused list
            refBids = new ArrayList(refusedBids.length);
            for (int x = 0; x < refusedBids.length; x++) {
                refBids.add(refusedBids[x]);
            }
        }

        if ((accBids != null) && (refBids != null)) { //check for conflicts
            conflicts = new ArrayList(acceptedBids.length + refusedBids.length);
            for (int x = 0; x < accBids.size(); x++) {
                for (int y = 0; y < refBids.size(); y++) {
                    if (accBids.get(x).equals(refBids.get(y))) {
                        conflicts.add(accBids.get(x));
                    }
                }
            }

            for (int x = 0; x < conflicts.size(); x++) { //remove conflicts
                accBids.remove(conflicts.get(x));
                refBids.remove(conflicts.get(x));
            }
        }

        if (accBids != null) { //check,accept and remove accepted bids

            for (int x = 0; x < accBids.size(); x++) {
                try {
                    if (manager.checkBids(user.getUserId(), Integer.parseInt(accBids.get(x))) == true) { //check if those bids are present and are referred to this user
                        accBids.remove(x);
                    }
                } catch(SQLException ex){
                    log.error(ex.toString());
                    throw new ServletException(ex);
                }
            }

            try {
                manager.AcceptBids(accBids, user.getUserId());
                manager.deleteBids(accBids);
            } catch (SQLException ex) {
                log.error(ex.toString());
                throw new ServletException(ex);
            }

        }

        if (refBids != null) {//check and remove refused bids
            
            for (int x = 0; x < refBids.size(); x++) {
                try {
                    if (manager.checkBids(user.getUserId(), Integer.parseInt(refBids.get(x))) == true) {
                        refBids.remove(x);
                    }
                } catch(SQLException ex){
                    log.error(ex.toString());
                    throw new ServletException(ex);
                }
            }
            
            try {
                manager.deleteBids(refBids);
            } catch (SQLException ex) {
                log.error(ex.toString());
                throw new ServletException(ex);
            }
        }
        response.sendRedirect(request.getContextPath() + "/");
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
