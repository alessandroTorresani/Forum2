/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets;

import com.sun.mail.iap.ParsingException;
import db.DBManager;
import db.Post;
import db.User;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
public class LoginServlet extends HttpServlet {

    private DBManager manager;
    static Logger log = Logger.getLogger(LoginServlet.class.getName());

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

        PrintWriter out = response.getWriter();
        HttpSession session = request.getSession();
        User user = null;
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String lastLogin = null;
        List<Post> lastPosts = null;
        List<String> updatedGroups = null;

        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date loginDate = new Date();
        Date lastLoginDate;
        Date lastPostDate;
        String result;

        try {
            user = manager.authenticate(email, password); //authenticate the user
            if (user != null) {
                lastLogin = manager.setLoginDate(user.getUserId(), dateFormat.format(loginDate)); //if the user exits set the the login date and get the last login date
                String requestId = manager.getPasswordRequestIdbyUserId(user.getUserId()); // if there is a request to restore password, get it
                manager.deletePasswordRequest(requestId); // and remove it
            }
        } catch (SQLException ex) {
            log.error(ex.toString());
        }

        if (user != null) {

            if (lastLogin != null) {    // if the user has a last login info in database
                lastPosts = new ArrayList<Post>(); 

                try {
                    lastPosts = manager.getLastPosts(user.getUserId(), lastLogin); //get the last post of every groups where the user is subscribed
                } catch (SQLException ex) {
                    log.error(ex.toString());
                    throw new ServletException(ex);
                }

                updatedGroups = new ArrayList<String>(); //list of the updated groups respect to the last login of the user

                try {
                    lastLoginDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(lastLogin); //parsing of the last login date
                } catch (ParseException ex) {
                    log.error(ex.toString());
                    throw new ServletException(ex);
                }
                for (int x = 0; x < lastPosts.size(); x++) {
                    try {
                        lastPostDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(lastPosts.get(x).getCreationDate()); // parsing of each last post creation date
                    } catch (ParseException ex) {
                        log.error(ex.toString());
                        throw new ServletException(ex);
                    }
                    if ((lastLoginDate != null)&&(lastPostDate != null)){ //check if the post was created after the last login date
                        long differanceDate = (lastPostDate.getTime() - lastLoginDate.getTime())/1000;
                        if (differanceDate > 0){
                            updatedGroups.add(""+lastPosts.get(x).getGroupId()); // if it's true, add the group containing  this post to the updated groups
                        }
                    }
                }
                session.setAttribute("updatedGroups", updatedGroups); //public the list of updated groups in the session
            }
            
            session.setAttribute("user", user); 
            result = "success";
            log.info("login corretto, user:" + user.getEmail());
        } else {
            result = "failure";
        }
        response.sendRedirect(request.getContextPath() + "/?login=" + result);
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
