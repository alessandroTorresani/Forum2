/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets;

import db.DBManager;
import db.Group;
import db.User;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.log4j.Logger;

/**
 *
 * @author Alessandro
 */
@WebServlet(name = "PreEditGroup", urlPatterns = {"/PreEditGroup"})
public class PreEditGroupServlet extends HttpServlet {

    private DBManager manager;
    static Logger log = Logger.getLogger(StartServlet.class.getName());

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
            throws ServletException, IOException { //get the data to pass to the editgroup.jsps

        HttpSession session = request.getSession();
        ServletContext sc = getServletContext();

        int groupId = Integer.parseInt(request.getParameter("groupId"));
        User user = (User) session.getAttribute("user");
        Group g = null;
        List<User> invitableUser = null;

        try {
            g = manager.getGroup(groupId);
        } catch (SQLException ex) {
            log.error(ex.toString());
            throw new ServletException(ex);
        }

        if (g.isIsPrivate()) { // if the group is private allow to invite users
            try {
                invitableUser = manager.getAllInvitableUser(groupId); //invitable users
            } catch (SQLException ex) {
                log.error(ex.toString());
                throw new ServletException(ex);
            }
            request.setAttribute("invitableUsers", invitableUser);
        }

        if (g != null) {
            request.setAttribute("group", g);
            RequestDispatcher rd = sc.getRequestDispatcher("/editGroup.jsp?groupId=" + g.getGroupId());
            rd.forward(request, response);
        } else {
            response.sendRedirect(request.getContextPath() + "/");
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
