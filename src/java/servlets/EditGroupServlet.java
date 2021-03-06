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
public class EditGroupServlet extends HttpServlet {

    private DBManager manager;
    static Logger log = Logger.getLogger(StartServlet.class.getName());
    private String GROUPNAME_REGEX = "^[a-zA-Z0-9_-]{3,20}$";
    private String GROUPNAME_REGEX1 = "^[[a-zA-Z0-9_-]+(\\s[a-zA-Z0-9_-]+)*]{3,20}$"; 

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

        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");

        String[] checkbox_params = request.getParameterValues("users");
        List<String> bids = new ArrayList();

        int groupId = Integer.parseInt(request.getParameter("groupId"));
        String groupName = request.getParameter("groupName");
        boolean is_private = "private".equals(request.getParameter("optionsRadios"));
        boolean res = false;

        if ((groupId > 0) && (groupName != null) && (groupName.matches(GROUPNAME_REGEX1))) {
            try {
                manager.editGroup(groupId, groupName, is_private);
                res = true;
            } catch (SQLException ex) {
                res = false;
                log.error(ex.toString());
                throw new ServletException(ex);
            }
            if (res == true) {
                response.sendRedirect(request.getContextPath() + "/GetOwnerGroups?email=" + user.getEmail());
            }
        } else {
            System.out.println("Name or id of the group incorrect");
            response.sendRedirect(request.getContextPath() + "/GetOwnerGroups?email=" + user.getEmail()+"&groupName=error");
        }

        if ((res) && (checkbox_params != null)) {
            for (int x = 0; x < checkbox_params.length; x++) { // fill the list
                bids.add(checkbox_params[x]);
            }
            try {
                manager.sendBids(bids, groupId, user.getUserId()); //send bids
            } catch (SQLException ex) {
                log.error(ex.toString());
                throw new ServletException();
            }
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
