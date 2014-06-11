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
import java.util.logging.Level;
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
public class ChangePasswordServlet extends HttpServlet {

    private DBManager manager;
    private String PASSWORD_REGEX = "((?=.*[a-z]).{3,20})";
    static Logger log = Logger.getLogger(RegistrationServlet.class.getName());
    
     public void init() throws ServletException {
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
            throws ServletException, IOException, SQLException {
        
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");

        String currentPassword = request.getParameter("currentPassword");
        String password1 = request.getParameter("password1");
        String password2 = request.getParameter("password2");
        
        if ((user.getEmail().equals(manager.checkUserPassword(user.getUserId(), currentPassword))) && (password1 != null) && (password2 != null) && (password1.matches(PASSWORD_REGEX)) && (password2.matches(PASSWORD_REGEX)) && (password1.equals(password2))){
            
            boolean res;
            try {
            manager.changeUserPassword(user.getUserId(), password1); //if the password inserted was the old one, change the password
            res = true;
            } catch (SQLException ex){
                res = false;
                log.error(ex.toString());
                throw new ServletException(ex);
            }
            if (res == true){
                log.info("User: " + user.getEmail() + " has changed the password successfully");
                response.sendRedirect(request.getContextPath() + "/ViewProfile?email="+user.getEmail()+"&changePassword=success");
            } else {
                log.info("User: " + user.getEmail() + " had an error changing his password");
                 response.sendRedirect(request.getContextPath() + "/ViewProfile?email="+user.getEmail()+"&changePassword=error");
            } 
        }
        else {
            response.sendRedirect(request.getContextPath() + "/ViewProfile?email="+user.getEmail()+"&changePassword=error");
        }
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
            java.util.logging.Logger.getLogger(ChangePasswordServlet.class.getName()).log(Level.SEVERE, null, ex);
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
            java.util.logging.Logger.getLogger(ChangePasswordServlet.class.getName()).log(Level.SEVERE, null, ex);
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
