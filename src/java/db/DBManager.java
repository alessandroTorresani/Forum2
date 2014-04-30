/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package db;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

/**
 *
 * @author Alessandro
 */
public class DBManager  implements Serializable {

    private transient Connection con;
    private String url;
    private String user;
    private String password;

    public DBManager(String url, String user, String password) throws SQLException {
        try {
            this.url = url;
            this.user = user;
            this.password = password;

            Class.forName("org.apache.derby.jdbc.ClientDriver", true, getClass().getClassLoader()); // carico driver

        } catch (Exception e) {
            throw new RuntimeException(e.toString(), e);
        }
        Connection con = DriverManager.getConnection(url, user, password); // connessione al database
        this.con = con;
    }

    public static void shutdown() {
        try {
            DriverManager.getConnection("jdbc:derby:;shutdown=true"); // chiudo la connessione
        } catch (SQLException ex) {
            Logger.getLogger(DBManager.class.getName()).info(ex.getMessage());
        }
    }
    
    public User authenticate(String email, String password) throws SQLException {

        PreparedStatement stm = con.prepareStatement("SELECT * FROM users WHERE email = ? AND password = ?");
        try {
            stm.setString(1, email);
            stm.setString(2, password);
            ResultSet rs = stm.executeQuery();
            try {
                if (rs.next()) {
                    User user = new User(); // se la query va a buon fine creo un'istanza della classe user
                    user.setUsername(rs.getString("username"));
                    user.setUserId(rs.getInt("user_id"));
                    user.setEmail(rs.getString("email"));
                    return user;

                } else {
                    return null;
                }

            } finally {
                rs.close();
            }

        } finally {
            stm.close();
        }
    }
    
    public boolean checkNewEmail(String email) throws SQLException{
        int resultLenght = 0;
        PreparedStatement stm = con.prepareStatement("SELECT email  FROM users WHERE email= ?");
        try{
            stm.setString(1, email);
            ResultSet rs = stm.executeQuery();
            try{
                while(rs.next()){
                    resultLenght++;
                }
            } finally {
                rs.close();
            }
        } finally {
            stm.close();
        }
        return resultLenght == 0;
    }
    
    public int registerUser(String username, String email,String password) throws SQLException{
         PreparedStatement stm = con.prepareStatement("INSERT INTO users ( username,password,email) VALUES (?,?,?)",Statement.RETURN_GENERATED_KEYS) ;
         int userID = 0;
         try{
             stm.setString(1, username);
             stm.setString(2, password);
             stm.setString(3, email);
             stm.executeUpdate();
             ResultSet rs = stm.getGeneratedKeys();
             try{
                 while(rs.next()){
                     userID = rs.getInt(1);                 
                 }
             } finally {
                 rs.close();
             }
         } finally {
             stm.close();
         }
         return userID;
    }
    
    public List<Group> getPublicGroups() throws SQLException{
         List<Group> groups = new ArrayList<Group>();
         PreparedStatement stm = con.prepareStatement("SELECT * FROM groups WHERE is_private = ?");
         try {
             stm.setBoolean(1, false);
             ResultSet rs = stm.executeQuery();
             try {
                 while(rs.next()){
                     Group g = new Group();
                     g.setGroupId(rs.getInt("group_id"));
                     g.setAdminId(rs.getInt("administrator_id"));
                     g.setCreationDate(rs.getString("creation_date"));
                     g.setGroupName(rs.getString("groupname"));
                     g.setIsClosed(rs.getBoolean("is_closed"));
                     g.setIsPrivate(false);
                     groups.add(g);
                 }
             } finally {
                 rs.close();
             }
         } finally {
             stm.close();
         }
         return groups;
    }
}
