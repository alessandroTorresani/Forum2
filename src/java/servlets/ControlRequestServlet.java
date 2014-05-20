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
import static servlets.ForgotPasswordServlet.log;

/**
 *
 * @author Alessandro
 */
public class ControlRequestServlet extends HttpServlet {

    private DBManager manager;
    static Logger log = Logger.getLogger(ControlRequestServlet.class.getName());

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
        String RequestId = request.getParameter("requestId");
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date actualDate = new Date();
        Date requestDate = null;

        try {
            String requestTime = manager.getPasswordRequestTimebyRequestId(RequestId);
            System.out.println(requestTime);
            if (requestTime != null) { //if was done a request using the specified userId, and exists a request date
                try {
                    requestDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(requestTime);
                } catch (ParseException pex) {
                    log.error(pex.toString());
                    throw new ServletException(pex);
                }

                long secondsAfterRequest = (actualDate.getTime() - requestDate.getTime()) / 1000; // get the second between the change password request and the visit of the link to change the password
                System.out.println("gap:" + secondsAfterRequest);

                if (secondsAfterRequest <= 1000) {
                    request.setAttribute("correctRequest", true);
                    RequestDispatcher rd = sc.getRequestDispatcher("/changePassword.jsp?requestId=" + RequestId); // change password ??is really necessary the userId here?
                    rd.forward(request, response);
                } else {
                    request.setAttribute("errorMessage", "Request out of time");
                    manager.deletePasswordRequest(RequestId);
                    RequestDispatcher rd = sc.getRequestDispatcher("/changePassword.jsp"); // change password ??is really necessary the userId here?
                    rd.forward(request, response);
                }
            } else {
                request.setAttribute("correctRequest", false);
                RequestDispatcher rd = sc.getRequestDispatcher("/changePassword.jsp");
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
