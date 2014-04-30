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
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.SQLException;
import java.util.Enumeration;
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
public class RegistrationServlet extends HttpServlet {

    private DBManager manager;
    private String EMAIL_REGEX = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
    private String PASSWORD_REGEX = "((?=.*[a-z]).{3,20})";
    private String USERNAME_REGEX = "^[a-z0-9_-]{3,15}$";
    static Logger log = Logger.getLogger(RegistrationServlet.class.getName());

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
        int userID = 0;
        MultipartRequest multi = null;
        ServletContext sc = getServletContext();

        try {
            multi = new MultipartRequest(request, request.getServletContext().getRealPath("/") + File.separator + "Avatars", 10 * 1024 * 1024, "ISO-8859-1", new DefaultFileRenamePolicy());
        } catch (Exception ex) {
            log.error(ex.toString());
        }
        if (multi != null) {
            Enumeration params = multi.getParameterNames();
            while (params.hasMoreElements()) { // parameter management
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

            boolean usernameCheck = false,
                    pass1Check = false,
                    pass2Check = false,
                    email1Check = false,
                    email2Check = false,
                    equalPass = false,
                    equalEmail = false,
                    isAllRight = false;

            if ((username != null) && (password1 != null) && (password2 != null) && (email1 != null) && (email2 != null)) {
                if (usernameCheck = username.matches(USERNAME_REGEX)) {
                    request.setAttribute("usernameCheck", true);
                } else {
                    request.setAttribute("usernameCheck", false);
                }
                if (pass1Check = password1.matches(PASSWORD_REGEX)) {
                    request.setAttribute("pass1Check", true);
                } else {
                    request.setAttribute("pass1Check", false);
                }
                if (pass2Check = password2.matches(PASSWORD_REGEX)) {
                    request.setAttribute("pass2Check", true);
                } else {
                    request.setAttribute("pass2Check", false);
                }
                if (email1Check = email1.matches(EMAIL_REGEX)) {
                    request.setAttribute("email1Check", true);
                } else {
                    request.setAttribute("email1Check", false);
                }
                if (email2Check = email2.matches(EMAIL_REGEX)) {
                    request.setAttribute("email2Check", true);
                } else {
                    request.setAttribute("email2Check", false);
                }
                if ((pass1Check == true) && (pass2Check == true) && (equalPass = password1.equals(password2))) {
                    request.setAttribute("equalPass", true);
                } else {
                    request.setAttribute("equalPass", false);
                }
                if ((email1Check == true) && (email2Check == true) && (equalEmail = email1.equals(email2))) {
                    request.setAttribute("equalEmail", true);
                } else {
                    request.setAttribute("equalEmail", false);
                }
                isAllRight = usernameCheck && pass1Check && pass2Check && email1Check && email2Check && equalEmail && equalPass;
                System.out.println("prova "+("ciao".equals("oaic")));
            }

            /*if ((isAllRight)&&(username != null) && (password1 != null) && (password2 != null) && (username.matches(USERNAME_REGEX))
             && (password1.matches(PASSWORD_REGEX)) && (password2.matches(PASSWORD_REGEX)) && (password1.equals(password2))
             && (email1 != null) && (email2 != null) && (email1.matches(EMAIL_REGEX)) && (email2.matches(EMAIL_REGEX)) && (email1.equals(email2)))*/
            if (isAllRight) {
                try {
                    if (manager.checkNewEmail(email1)) {
                        userID = manager.registerUser(username, email1, password1);
                    } else {
                        log.warn("Email already used");
                        Enumeration files = multi.getFileNames();
                        while (files.hasMoreElements()) {
                            String name = (String) files.nextElement();
                            File f = multi.getFile(name);
                            if (f != null) {
                                f.delete(); //multi has aleady uploaded the file, so it must be deleted
                            }
                        }
                        request.setAttribute("Result", "Sorry, email already used");
                        RequestDispatcher rd = sc.getRequestDispatcher("/registrationResult.jsp");
                        rd.forward(request, response);
                    }
                } catch (SQLException ex) {
                    log.error(ex.toString());
                    throw new ServletException(ex);
                }
            } else {
                log.warn("Inserted data is wrong, something missing or passwords/emails do not coincide");
                request.setAttribute("Result", "There are some errors in the inserted data, please retry");
                RequestDispatcher rd = sc.getRequestDispatcher("/registrationResult.jsp");
                rd.forward(request, response);
            }

            if (userID > 0) {
                Enumeration files = multi.getFileNames(); //file management
                while (files.hasMoreElements()) {
                    String name = (String) files.nextElement();
                    String filename = multi.getFilesystemName(name);
                    String originalFilename = multi.getOriginalFileName(name);
                    String type = multi.getContentType(name);
                    File f = multi.getFile(name);
                    if (f != null) {
                        if (type.startsWith("image")) {
                            Path source = f.toPath(); //path to the uploaded file
                            Files.move(source, source.resolveSibling("" + userID + ".jpg")); // copy the file with a new name
                            f.delete();  // delete source file

                            request.setAttribute("Result", "Your registration was successful");
                            RequestDispatcher rd = sc.getRequestDispatcher("/registrationResult.jsp");
                            rd.forward(request, response);
                        } else {
                            f.delete(); //if is not an image, it must be deleted

                            request.setAttribute("Result", "Your registration was successful, but you upload an invalid image for avatar");
                            RequestDispatcher rd = sc.getRequestDispatcher("/registrationResult.jsp");
                            rd.forward(request, response);
                        }
                    } else {
                        request.setAttribute("Result", "Your registration was successful");
                        RequestDispatcher rd = sc.getRequestDispatcher("/registrationResult.jsp");
                        rd.forward(request, response);
                    }
                }
            }
        } else {
            out.println("No information inserted, please retry");
            request.setAttribute("Result", "You inserted wrong or empty data, pleasy retry");

            RequestDispatcher rd = sc.getRequestDispatcher("/registrationResult.jsp");
            rd.forward(request, response);
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
