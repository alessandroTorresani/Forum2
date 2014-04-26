/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package listeners;

import db.DBManager;
import java.io.File;
import java.sql.SQLException;
import java.util.logging.Logger;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import org.apache.log4j.PropertyConfigurator;


/**
 *
 * @author Alessandro
 */
public class AppContextListener implements ServletContextListener {
    
    static Logger log = Logger.getLogger(AppContextListener.class.getName());

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        
        
        ServletContext stx = sce.getServletContext();
        String dburl = stx.getInitParameter("DBURL"); //parametri connessione al database salvati in web.xml
        String user = stx.getInitParameter("DBUSER");
        String password = stx.getInitParameter("DBPWD");
        File propertiesFile = new File(sce.getServletContext().getRealPath("/"), "WEB-INF/log4j.properties");
        PropertyConfigurator.configure(propertiesFile.toString());

        try {
            DBManager manager = new DBManager(dburl, user, password); 
            sce.getServletContext().setAttribute("dbmanager", manager);     // pubblico la connessione al database sul servlet context
        } catch (SQLException ex) {
            log.severe(ex.toString());
            throw new RuntimeException(ex);
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        DBManager manager = (DBManager) sce.getServletContext().getAttribute("dbmanager");
        manager.shutdown();
        //DBManager.shutdown();      // chiudo il database
    } 
}
