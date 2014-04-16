/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets;

import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;
import db.DBManager;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.Enumeration;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Alessandro
 */
public class RegistrationServlet extends HttpServlet {

    private DBManager manager;
    private String EMAIL_REGEX = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

    public void init() throws ServletException {
// inizializza il DBManager dagli attributi di Application
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
        PrintWriter out = response.getWriter();

        String email1 = null;
        String email2 = null;
        String password1 = null;
        String password2 = null;
        String username = null;
        int userID = 4;
        MultipartRequest multi = new MultipartRequest(request, request.getServletContext().getRealPath("/") + "\\Avatars", 10 * 1024 * 1024, "ISO-8859-1", new DefaultFileRenamePolicy());
        Enumeration params = multi.getParameterNames();
        while (params.hasMoreElements()) { // gestione parametri
            String name = (String) params.nextElement();
            String value = multi.getParameter(name);

            if (name.equals("username")) {
                username = value;
            } else if (name.equals("password2")) {
                password2 = value;
            } else if (name.equals("password1")) {
                password1 = value;
            } else if (name.equals("email2")) {
                email2 = value;
            } else if (name.equals("email1")) {
                email1 = value;
            }

        }

      /*  if ((password1.equals(password2)) && (email1.equals(email2)) && (email1.matches(EMAIL_REGEX))) {
            try {
                if (manager.checkNewEmail(email1)) {
                    userID = manager.registerUser(username, email1, password1);
                    System.out.println(userID);
                } else {
                    System.out.println("Email already used");
                }
            } catch (SQLException ex) {
                Logger.getLogger(RegistrationServlet.class.getName()).log(Level.SEVERE, ex.toString(), ex);
                throw new ServletException(ex);
            }
        } else {
            System.out.println("Errore inserimento dati");
        }*/

        Enumeration files = multi.getFileNames(); //gestione file
        while (files.hasMoreElements()) {
            String name = (String) files.nextElement();
            String filename = multi.getFilesystemName(name);
            String originalFilename = multi.getOriginalFileName(name);
            String type = multi.getContentType(name);
            File f = multi.getFile(name);
            out.println("name: " + name);
            out.println("filename: " + filename);
            out.println("originalFilename: " + originalFilename);
            out.println("type: " + type);
            if ((f!=null)&&(type.startsWith("image"))){
                System.out.println(f.toString());
                System.out.println(f.getName());
                File filetmp = new File(request.getServletContext().getRealPath("/")+userID);
                //f.renameTo(filetmp);
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
