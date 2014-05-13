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
import javax.mail.MessagingException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;
import utils.Mailer;

/**
 *
 * @author Alessandro
 */
public class ForgotPasswordServlet extends HttpServlet {

    private DBManager manager;
    static Logger log = Logger.getLogger(ForgotPasswordServlet.class.getName());
    private String PASSWORD_REGEX = "((?=.*[a-z]).{3,20})";

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
        String password1 = request.getParameter("password1");
        String password2 = request.getParameter("password2");

        int userId;
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date();

        //invio email (bisogna inviare anche la password inserita??) evitare false richieste
        try {
            userId = manager.getUserIdByEmail(email);
            if (userId > 0) { //if user which that email exits
                if ((password1 != null) && (password2 != null) && (password1.matches(PASSWORD_REGEX)) && (password2.matches(PASSWORD_REGEX)) && (password1.equals(password2))) {
                    String requestId = UUID.randomUUID().toString(); // generate random requestId
                    try {
                        if (manager.checkPasswordRequest(userId)) { //if the user had already done a request
                            // query update
                            manager.updatePasswordRequest(userId, dateFormat.format(date), requestId, password1); //we update the request
                        } else {
                            //insert query
                            manager.insertPasswordRequest(userId, dateFormat.format(date), requestId, password1); //else we create a new change password request
                        }
                    } catch (SQLException ex) {
                        log.error(ex.toString());
                        throw new ServletException(ex);
                    }
                    
                    String recoveryLink = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath() + "/RestorePassword?requestId=" + requestId;
                    String mailBody = "<div style='font-size: 16px; font-family:\\\"Helvetica Neue\\\", Helvetica, Arial, \\\"Lucida Grande\\\", sans-serif;'>"+
                            "Hi " + manager.getUsernameByUserId(userId)+", <br/><br/>" +
                            "It seems that you requested a password change, using this password: <b>" + password1 + "</b>. <br/><br/>" +
                            "if you didn't requested this change simply ignore this message <br/><br/>" +
                            "Otherwise check the inserted password and click the link to validate the changes <br/><br/> "+
                             recoveryLink + "<br/><br/>" +
                            "Best regards </div>";
                    
                    Mailer mail = new Mailer();
                    try {
                        mail.sendEmail(email, "Password recovery", mailBody);
                    } catch (MessagingException mex) {
                        log.error(mex.toString());
                        throw new ServletException(mex);
                    }

                    response.sendRedirect(request.getContextPath() + "/");
                } else {
                    System.out.println("password non corrette");
                }

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
