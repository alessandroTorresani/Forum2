/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package filters;

import db.DBManager;
import db.User;
import java.io.IOException;
import java.sql.SQLException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.log4j.Logger;
import servlets.RegistrationServlet;

/**
 *
 * @author Alessandro
 */
public class ProfileFilter implements Filter {

    private FilterConfig filterConfig;
    private DBManager manager;
    static Logger log = Logger.getLogger(ProfileFilter.class.getName());

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        this.filterConfig = filterConfig;
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        HttpSession session = ((HttpServletRequest) request).getSession();
        User user = (User) ((HttpServletRequest) request).getSession().getAttribute("user");
        this.manager = (DBManager) request.getServletContext().getAttribute("dbmanager");
        String email = request.getParameter("email");
        int userId = 0;

        if (user != null) {
            try {
                userId = manager.getUserIdByEmail(email);
            } catch (SQLException ex) {
                log.error(ex.toString());
            }
            if (user.getUserId() == userId) {
                chain.doFilter(request, response);
            } else {
                ((HttpServletResponse) response).sendRedirect(((HttpServletRequest) request).getContextPath() + "/Start");
            }
        } else {
            ((HttpServletResponse) response).sendRedirect(((HttpServletRequest) request).getContextPath() + "/Start");
        }
    }

    @Override
    public void destroy() {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
