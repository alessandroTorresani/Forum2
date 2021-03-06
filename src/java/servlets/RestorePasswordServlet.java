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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;

/**
 *
 * @author Alessandro
 */
public class RestorePasswordServlet extends HttpServlet {

    private DBManager manager;
    static Logger log = Logger.getLogger(RestorePasswordServlet.class.getName());

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

        ServletContext sc = getServletContext();
        String requestId = request.getParameter("requestId");
        
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date actualDate = new Date();
        Date requestDate = null;
        int userId = 0;

        try {
            String requestTime = manager.getPasswordRequestTimebyRequestId(requestId); //get request time by requestId
            if (requestTime != null){  //if was done a request using the specified userId, and exists a request date
                try {
                    requestDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(requestTime); //parsing request date
                } catch (ParseException pex) {
                    log.error(pex.toString());
                    throw new ServletException(pex);
                }
                long secondsAfterRequest = (actualDate.getTime() - requestDate.getTime()) / 1000; // get the second between the change password request and the visit of the link to change the password

                if (secondsAfterRequest <= 90) { //if the gap is less than 90 seconds process the request
                    userId = manager.getUserIdByRequestId(requestId);
                    if (userId > 0) {
                        String tempPassword = manager.getTempPasswordByUserId(userId);
                        manager.changeUserPassword(userId, tempPassword);
                        manager.deletePasswordRequest(requestId);
                         log.warn("Recovery password request clicked in 90 second, password changed successfully");
                        request.setAttribute("correctRequest", true);
                        RequestDispatcher rd = sc.getRequestDispatcher("/changePasswordResult.jsp?requestId=" + requestId); //display the result of the operation
                        rd.forward(request, response);
                    }
                } else {
                    log.warn("Recovery password request clicked after 90 second");
                    request.setAttribute("errorMessage", "Request out of time");
                    manager.deletePasswordRequest(requestId);
                    RequestDispatcher rd = sc.getRequestDispatcher("/changePasswordResult.jsp"); // display error message in the page
                    rd.forward(request, response);
                }
            } else {
                log.warn("Illegal call of the recovery servlet");
                request.setAttribute("correctRequest", false);
                RequestDispatcher rd = sc.getRequestDispatcher("/changePasswordResult.jsp"); //illegal call of the servlet
                rd.forward(request, response);
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
