/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets;

import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;
import db.DBManager;
import db.User;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLEncoder;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Enumeration;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.log4j.Logger;
import static servlets.RegistrationServlet.log;
import utils.MyUtility;

/**
 *
 * @author Marco
 */
public class AddPostServlet extends HttpServlet {

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

        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");

        int groupId = Integer.parseInt(request.getParameter("groupId"));
        String message = request.getParameter("message");

        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date();
        MultipartRequest multi = null;

        Boolean isSubscribed = null;
        int postId = 0;

        if (request.getMethod() == "POST" && request.getContentType() != "multipart/form-data") {

            try {
                multi = new MultipartRequest(request, request.getServletContext().getRealPath("/") + File.separator + "Files", 10 * 1024 * 1024, "ISO-8859-1", new DefaultFileRenamePolicy());
            } catch (IOException ex) {
                log.error(ex.toString());
            }

            if (multi != null) {
                Enumeration params = multi.getParameterNames();
                while (params.hasMoreElements()) { // parameter management
                    String name = (String) params.nextElement();
                    String value = multi.getParameter(name);
                    if (name.equals("message")) {
                        message = value;
                    }
                }

                try {
                    isSubscribed = manager.isSubscribed(user.getUserId(), groupId);
                } catch (SQLException ex) {
                    log.error(ex.toString());
                    throw new ServletException(ex);
                }

                if (isSubscribed) {
                    try {
                        message = MyUtility.cleanHTMLTags(message);
                        message = MyUtility.checkMultiLink(message);
                        message = message.replaceAll("[\n\r]+", "<br>");

                        postId = manager.addPost(user.getUserId(), groupId, message, dateFormat.format(date));
                        File f = null;

                        if (postId > 0) {
                            Enumeration files = multi.getFileNames(); //file management

                            while (files.hasMoreElements()) {
                                String name = (String) files.nextElement();
                                String filename = multi.getFilesystemName(name);
                                String originalFilename = multi.getOriginalFileName(name);
                                f = multi.getFile(name);
                                if (f != null) {
                                    try {
                                        manager.addFileToPost(postId, filename);
                                    } catch (SQLException ex) {
                                        log.error(ex.toString());
                                        throw new ServletException(ex);
                                    }
                                }
                            }
                        } else if (f != null) {
                            //in caso of post failure, delete anyway the file uploaded
                            f.delete();
                        }
                        response.sendRedirect(request.getContextPath() + "/LoadPost?groupId=" + groupId);
                    } catch (SQLException ex) {
                        log.error(ex.toString());
                        throw new ServletException(ex);
                    }
                } else {
                    response.sendRedirect(request.getContextPath() + "/LoadPost?groupId=" + groupId);
                }
            } else {
                //rimandare alla groupPage con errore
                response.sendRedirect(request.getContextPath() + "/LoadPost?groupId=" + groupId + "&result="+URLEncoder.encode("fileSizeError", "UTF-8"));
            }
        } else {
            response.sendRedirect(request.getContextPath() + "/LoadPost?groupId=" + groupId);
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
