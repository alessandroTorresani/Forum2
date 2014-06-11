/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets;

import db.DBManager;
import db.Group;
import db.Post;
import db.User;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
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
public class LoadPostServlet extends HttpServlet {

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

        ServletContext sc = getServletContext();

        List<Post> posts = null; //list of posts
        int groupId = Integer.parseInt(request.getParameter("groupId"));

        Group groupPage;
        String imgUrl = "0.jpg";
        String imgUrlAdmin = "0.jpg";
        Boolean isSubscribed = false;

        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");

        try {
            posts = manager.getPosts(groupId); //get the posts
            groupPage = manager.getGroup(groupId);
        } catch (SQLException ex) {
            log.error(ex.toString());
            throw new ServletException(ex);
        }

        for (int x = 0; x < posts.size(); x++) {
            File tmp = new File(request.getServletContext().getRealPath("/") + File.separator + "Avatars" + File.separator + posts.get(x).getUserId() + ".jpg"); //imgUrl manage
            if (tmp.isFile()) { //if there is an avatar save the path
                posts.get(x).setImgUrl(posts.get(x).getUserId() + ".jpg");
            } else {
                posts.get(x).setImgUrl(imgUrl); //else use the default avatar
            }
            List<String> filePaths = null; //file paths for posts

            try {
                filePaths = manager.getAllFilesFromPostId(posts.get(x).getPostId());
            } catch (SQLException ex) {
                log.error(ex.toString());
                throw new ServletException(ex);
            }

            if (filePaths != null) { //if there are file paths link them in the post entity
                posts.get(x).setFileUrls(filePaths);
            } else {
                posts.get(x).setFileUrls(null);
            }
        }

        //img per admin  
        File tmp1 = new File(request.getServletContext().getRealPath("/") + File.separator + "Avatars" + File.separator + groupPage.getAdminId() + ".jpg");
        if (tmp1.isFile()) {
            imgUrlAdmin = groupPage.getAdminId() + ".jpg";
        }

        //is_subscibed for the current user and group
        if (user != null) { //check the user because in this servlet it can be null

            List<String> updatedGroups = (List<String>) session.getAttribute("updatedGroups"); //get the updated groups
            if (updatedGroups != null) {
                updatedGroups.remove("" + groupId); //remove this groups from updated groups
                session.setAttribute("updatedGroups", updatedGroups); //repost the updatedGroups session attribute
            }

            try {
                isSubscribed = manager.isSubscribed(user.getUserId(), groupId); //check if the user is subscribed
            } catch (SQLException ex) {
                log.error(ex.toString());
                throw new ServletException(ex);
            }

        }
        request.setAttribute("isSubscribed", isSubscribed);
        request.setAttribute("imgUrlAdmin", imgUrlAdmin);
        request.setAttribute("posts", posts);
        request.setAttribute("groupPage", groupPage);
        RequestDispatcher rd = sc.getRequestDispatcher("/groupPage.jsp");
        rd.forward(request, response);

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
