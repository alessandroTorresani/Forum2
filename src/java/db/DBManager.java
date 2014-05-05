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
public class DBManager implements Serializable {

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

    public User authenticate(String email, String password, String loginDate) throws SQLException {

        PreparedStatement stm = con.prepareStatement("SELECT * FROM users WHERE email = ? AND password = ?");
        try {
            stm.setString(1, email);
            stm.setString(2, password);
            ResultSet rs = stm.executeQuery();
            try {
                if (rs.next()) {
                    User user = new User();
                    user.setUserId(rs.getInt("user_id"));
                    user.setUsername(rs.getString("username"));
                    user.setEmail(rs.getString("email"));
                    user.setIsModerator(rs.getBoolean("is_moderator"));
                    user.setLastLogin(rs.getString("last_login"));
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

    public void setLoginDate(int userId, String loginDate) throws SQLException {

        PreparedStatement stm = con.prepareStatement("UPDATE users SET last_login = ?  WHERE user_id = ?");
        try {
            stm.setString(1, loginDate);
            stm.setInt(2, userId);
            stm.executeUpdate();
        } finally {
            stm.close();
        }
    }

    public boolean checkEmail(String email) throws SQLException {
        int resultLenght = 0;
        PreparedStatement stm = con.prepareStatement("SELECT email  FROM users WHERE email= ?");
        try {
            stm.setString(1, email);
            ResultSet rs = stm.executeQuery();
            try {
                while (rs.next()) {
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

    public int getUserIdByEmail(String email) throws SQLException {
        int userId = 0;
        PreparedStatement stm = con.prepareStatement("SELECT user_id FROM users WHERE email=?");
        try {
            stm.setString(1, email);
            ResultSet rs = stm.executeQuery();
            try {
                while (rs.next()) {
                    userId = rs.getInt("user_id");
                }
            } finally {
                rs.close();
            }
        } finally {
            stm.close();
        }
        return userId;
    }

    public int registerUser(String username, String email, String password) throws SQLException {
        PreparedStatement stm = con.prepareStatement("INSERT INTO users ( username,password,email) VALUES (?,?,?)", Statement.RETURN_GENERATED_KEYS);
        int userID = 0;
        try {
            stm.setString(1, username);
            stm.setString(2, password);
            stm.setString(3, email);
            stm.executeUpdate();
            ResultSet rs = stm.getGeneratedKeys();
            try {
                while (rs.next()) {
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

    public boolean checkPasswordRequest(int userId) throws SQLException {
        int resultLenght = 0;
        PreparedStatement stm = con.prepareStatement("SELECT user_id  FROM forgotten_passwords WHERE user_id= ?");
        try {
            stm.setInt(1, userId);
            ResultSet rs = stm.executeQuery();
            try {
                while (rs.next()) {
                    resultLenght++;
                }
            } finally {
                rs.close();
            }
        } finally {
            stm.close();
        }
        return resultLenght > 0;
    }

    public void updatePasswordRequest(int userId, String requestTime) throws SQLException {
        PreparedStatement stm = con.prepareStatement("UPDATE forgotten_passwords  SET request_time = ?  WHERE user_id = ?");
        try {
            stm.setString(1, requestTime);
            stm.setInt(2, userId);
            stm.executeUpdate();
        } finally {
            stm.close();
        }
    }

    public void insertPasswordRequest(int userId, String requestTime) throws SQLException {
        PreparedStatement stm = con.prepareStatement("INSERT INTO forgotten_passwords (user_id, request_time) VALUES(?,?)");
        try {
            stm.setInt(1, userId);
            stm.setString(2, requestTime);
            stm.executeUpdate();
        } finally {
            stm.close();
        }
    }

    public String getPasswordRequestTimebyUserId(int userId) throws SQLException {
        String requestTime = null;
        PreparedStatement stm = con.prepareCall("SELECT request_time FROM forgotten_passwords WHERE user_id = ?");
        try {
            stm.setInt(1, userId);
            ResultSet rs = stm.executeQuery();
            try {
                while (rs.next()) {
                    requestTime = rs.getString("request_time");
                }
            } finally {
                rs.close();
            }
        } finally {
            stm.close();
        }
        return requestTime;
    }

    public List<Group> getPublicGroups() throws SQLException {
        List<Group> groups = new ArrayList<Group>();
        PreparedStatement stm = con.prepareStatement("SELECT * FROM groups WHERE is_private = ?");
        try {
            stm.setBoolean(1, false);
            ResultSet rs = stm.executeQuery();
            try {
                while (rs.next()) {
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
