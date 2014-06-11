/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package filters;

import db.DBManager;
import db.Group;
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

/**
 *
 * @author Alessandro
 */
public class GroupFilter implements Filter {

    private FilterConfig filterConfig;
    private DBManager manager;
    static Logger log = Logger.getLogger(ProfileFilter.class.getName());

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        this.filterConfig = filterConfig;
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

        HttpSession session = ((HttpServletRequest) request).getSession();
        this.manager = (DBManager) request.getServletContext().getAttribute("dbmanager");
        User user = (User) ((HttpServletRequest) request).getSession().getAttribute("user");

        int groupId;
        Group g = null;

        try {
            groupId = Integer.parseInt(request.getParameter("groupId"));
        } catch (NumberFormatException ex) {
            groupId = 0;
        }

        if (groupId > 0) {
            try {
                g = manager.getGroup(groupId);
            } catch (SQLException ex) {
                log.error(ex.toString());
                throw new ServletException(ex);
            }

            if (g != null) {
                
                if (g.isIsPrivate()) { //if the group is private check if the user is subscribed or is in moderator mode
                    try {
                        if ((user!=null)&&((manager.isSubscribed(user.getUserId(), groupId))||((user.getIsModerator())&&(user.isModeratorMode())))) {
                            chain.doFilter(request, response);
                        }
                        else{
                             ((HttpServletResponse) response).sendRedirect(((HttpServletRequest) request).getContextPath() + "/");}
                    } catch (SQLException ex) {
                        log.error(ex.toString());
                        throw new ServletException(ex);
                    }
                } else { //if the group is public do nothing
                    chain.doFilter(request, response);
                }
            
            } else {
                ((HttpServletResponse) response).sendRedirect(((HttpServletRequest) request).getContextPath() + "/");
            }
        } else {
            ((HttpServletResponse) response).sendRedirect(((HttpServletRequest) request).getContextPath() + "/");
        }

    }

    @Override
    public void destroy() {
    }

}
