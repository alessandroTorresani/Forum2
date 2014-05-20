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
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Enumeration;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.log4j.Logger;
import static servlets.RegistrationServlet.log;

/**
 *
 * @author Alessandro
 */
public class ChangeAvatarServlet extends HttpServlet {
    
    static Logger log = Logger.getLogger(RegistrationServlet.class.getName());
    private DBManager manager;
    
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
       
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        MultipartRequest multi = null;
         
         try {
            multi = new MultipartRequest(request, request.getServletContext().getRealPath("/") + File.separator + "Avatars", 10 * 1024 * 1024, "ISO-8859-1", new DefaultFileRenamePolicy());
        } catch (Exception ex) {
            log.error(ex.toString());
        }
         
        if (multi != null){
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
                            File tmp = new File(request.getServletContext().getRealPath("/") + File.separator + "Avatars" + File.separator + user.getUserId()+".jpg");
                            if(tmp.isFile()){
                                tmp.delete();
                            }
                            log.info("User: " + user.getEmail() + " Changed his avatar");
                            Files.move(source, source.resolveSibling("" + user.getUserId() + ".jpg")); // copy the file with a new name
                            f.delete();  // delete source file
                            response.sendRedirect(request.getContextPath()+"/ViewProfile?email="+user.getEmail());
                        } else {
                            log.warn("User: " + user.getEmail() + " tried to change the avatar without uploading an image");
                            f.delete(); //if is not an image, it must be deleted
                            response.sendRedirect(request.getContextPath()+"/ViewProfile?email="+user.getEmail());
                        }
                    } else {
                         log.warn("User: " + user.getEmail() + " tried to change the avatar without uploading a file");
                         response.sendRedirect(request.getContextPath()+"/ViewProfile?email="+user.getEmail());
                    }   
                }
        } else {
            System.out.println("Errore non chiamato in modo legito");
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
