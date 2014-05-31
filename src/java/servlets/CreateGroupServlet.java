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
import java.rmi.ServerException;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
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
public class CreateGroupServlet extends HttpServlet {

    private DBManager manager;
    private String GROUPNAME_REGEX = "^[[a-zA-Z0-9àáâäèéêëìíîïòóôöùúûü_-]+(\\s[a-zA-Z0-9àáâäèéêëìíîïòóôöùúûü_-]+)*]{3,20}$";
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

        String groupName = request.getParameter("groupName");
        boolean is_private = "private".equals(request.getParameter("optionsRadios"));

        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date();

        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        int groupId = 0;

        //accenti
        System.out.println(groupName);

        groupName = groupName.replace("Ã ", "à");
        groupName = groupName.replace("Ã¡", "á");
        groupName = groupName.replace("Ã¢", "â");
        groupName = groupName.replace("Ã¤", "ä");
        groupName = groupName.replace("Ã¨", "è");
        groupName = groupName.replace("Ã©", "é");
        groupName = groupName.replace("Ãª", "ê");
        groupName = groupName.replace("Ã«", "ë");
        groupName = groupName.replace("Ã¬", "ì");
        groupName = groupName.replace("Ã­", "í");
        groupName = groupName.replace("Ã®", "î");
        groupName = groupName.replace("Ã¯", "ï");
        groupName = groupName.replace("Ã²", "ò");
        groupName = groupName.replace("Ã³", "ó");
        groupName = groupName.replace("Ã´", "ô");
        groupName = groupName.replace("Ã¶", "ö");
        groupName = groupName.replace("Ã¹", "ù");
        groupName = groupName.replace("Ãº", "ú");
        groupName = groupName.replace("Ã»", "û");
        groupName = groupName.replace("Ã¼", "ü");

        System.out.println(groupName);

        if ((groupName != null) && (groupName.matches(GROUPNAME_REGEX))) {
            try {
                groupId = manager.createGroup(user.getUserId(), groupName, dateFormat.format(date), is_private);
            } catch (SQLException ex) {
                log.error(ex.toString());
                throw new ServletException(ex);
            }
            if (groupId > 0) {
                try {
                    manager.subscribeAdmin(groupId, user.getUserId());
                    log.info("User " + user.getEmail() + " create a new group with id: " + groupId);
                } catch (SQLException ex) {
                    log.error(ex.toString());
                    throw new ServletException(ex);
                }
                response.sendRedirect(request.getContextPath() + "/GetOwnerGroups?email=" + user.getEmail());
            } else {
                System.out.println("no group_id");
                response.sendRedirect(request.getContextPath() + "/createGroup.jsp");
            }
        } else {
            System.out.println("no correct name");
            response.sendRedirect(request.getContextPath() + "/createGroup.jsp");
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
