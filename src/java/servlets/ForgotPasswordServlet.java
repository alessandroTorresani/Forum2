/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets;

import db.DBManager;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;
import java.util.zip.DataFormatException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;
import static servlets.RegistrationServlet.log;

/**
 *
 * @author Alessandro
 */
public class ForgotPasswordServlet extends HttpServlet {

    private DBManager manager;
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
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String email = request.getParameter("email");
        int userId;
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date();

        try {
            userId = manager.getUserIdByEmail(email);
            if (userId > 0) { //if user which that email exits
                String requestId = UUID.randomUUID().toString();
               System.out.println(requestId);
                try {
                    if (manager.checkPasswordRequest(userId)) { //if the user had already done a request
                        // query update
                        manager.updatePasswordRequest(userId, dateFormat.format(date), ""+requestId); //we update the request
                    } else {
                        //insert query
                        manager.insertPasswordRequest(userId, dateFormat.format(date), ""+requestId); //else we create a new change password request
                    }
                } catch (SQLException ex) {
                    log.error(ex.toString());
                    throw new ServletException(ex);
                }
                //invio email
                 response.sendRedirect(request.getContextPath() + "/");
            } else {
                System.out.println("Email non esistente"); // gestione errore
            }
        } catch (SQLException ex) {
            log.error(ex.toString());
            throw new ServletException(ex);
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
