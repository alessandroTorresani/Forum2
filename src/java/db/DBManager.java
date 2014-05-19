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

    public String checkUserPassword(int userId, String password) throws SQLException {
        PreparedStatement stm = con.prepareStatement("SELECT email FROM users WHERE user_id = ? AND password = ?");
        String email = null;
        try {
            stm.setInt(1, userId);
            stm.setString(2, password);
            ResultSet rs = stm.executeQuery();
            try {
                if (rs.next()) {
                    email = rs.getString("email");
                }
            } finally {
                rs.close();
            }
        } finally {
            stm.close();
        }
        return email;
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

    public String getUsernameByUserId(int userId) throws SQLException {
        String username = null;
        PreparedStatement stm = con.prepareCall("SELECT username FROM users WHERE user_id=?");
        try {
            stm.setInt(1, userId);
            ResultSet rs = stm.executeQuery();
            try {
                while (rs.next()) {
                    username = rs.getString("username");
                }
            } finally {
                rs.close();
            }
        } finally {
            stm.close();
        }
        return username;
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

    public void updatePasswordRequest(int userId, String requestTime, String requestId, String tempPassword) throws SQLException {
        PreparedStatement stm = con.prepareStatement("UPDATE forgotten_passwords  SET request_time = ?, request_id = ?, temp_password = ?  WHERE user_id = ?");
        try {
            stm.setString(1, requestTime);
            stm.setString(2, requestId);
            stm.setString(3, tempPassword);
            stm.setInt(4, userId);

            stm.executeUpdate();
        } finally {
            stm.close();
        }
    }

    public void insertPasswordRequest(int userId, String requestTime, String requestId, String tempPassword) throws SQLException {
        PreparedStatement stm = con.prepareStatement("INSERT INTO forgotten_passwords (user_id, request_time, request_id, temp_password) VALUES(?,?,?,?)");
        try {
            stm.setInt(1, userId);
            stm.setString(2, requestTime);
            stm.setString(3, requestId);
            stm.setString(4, tempPassword);
            stm.executeUpdate();
        } finally {
            stm.close();
        }
    }

    public String getPasswordRequestTimebyRequestId(String requestId) throws SQLException {
        String requestTime = null;
        PreparedStatement stm = con.prepareCall("SELECT request_time FROM forgotten_passwords WHERE request_id = ?");
        try {
            stm.setString(1, requestId);
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

    public String getPasswordRequestIdbyUserId(int userId) throws SQLException {
        String requestId = null;
        PreparedStatement stm = con.prepareCall("SELECT request_id FROM forgotten_passwords WHERE user_id = ?");
        try {
            stm.setInt(1, userId);
            ResultSet rs = stm.executeQuery();
            try {
                while (rs.next()) {
                    requestId = rs.getString("request_id");
                }
            } finally {
                rs.close();
            }
        } finally {
            stm.close();
        }
        return requestId;
    }

    public String getTempPasswordByUserId(int userId) throws SQLException {
        String tempPassword = null;
        PreparedStatement stm = con.prepareCall("SELECT temp_password FROM forgotten_passwords WHERE user_id = ?");
        try {
            stm.setInt(1, userId);
            ResultSet rs = stm.executeQuery();
            try {
                while (rs.next()) {
                    tempPassword = rs.getString("temp_password");
                }
            } finally {
                rs.close();
            }
        } finally {
            stm.close();
        }
        return tempPassword;
    }

    public void changeUserPassword(int userId, String password) throws SQLException {
        PreparedStatement stm = con.prepareCall("UPDATE users SET password = ? WHERE user_id = ?");
        try {
            stm.setString(1, password);
            stm.setInt(2, userId);
            stm.executeUpdate();
        } finally {
            stm.close();
        }
    }

    public void deletePasswordRequest(String requestId) throws SQLException {
        PreparedStatement stm = con.prepareCall("DELETE  FROM forgotten_passwords WHERE request_id = ?");
        try {
            stm.setString(1, requestId);
            stm.executeUpdate();
        } finally {
            stm.close();
        }
    }

    public int getUserIdByRequestId(String requestId) throws SQLException {
        int userId = 0;
        PreparedStatement stm = con.prepareCall("SELECT user_id FROM forgotten_passwords WHERE request_id = ?");
        try {
            stm.setString(1, requestId);
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

    public void subscribeAdmin(int groupId, int adminId) throws SQLException {
        PreparedStatement stm = con.prepareStatement("INSERT INTO users_groups (user_id, group_id, is_administrator) VALUES (?,?,?)");
        try {
            stm.setInt(1, adminId);
            stm.setInt(2, groupId);
            stm.setBoolean(3, true);
            stm.executeUpdate();
        } finally {
            stm.close();
        }
    }

    public List<User> getAllUser(int adminId, int groupId) throws SQLException {
        List<User> users = new ArrayList<User>();
        PreparedStatement stm = con.prepareStatement("SELECT * FROM users EXCEPT (SELECT * FROM users WHERE user_id = ? )");
        try {
            stm.setInt(1, adminId);
            ResultSet rs = stm.executeQuery();
            try {
                while (rs.next()) {
                    User u = new User();
                    u.setUserId(rs.getInt("user_id"));
                    u.setUsername(rs.getString("username"));
                    u.setEmail(rs.getString("email"));
                    users.add(u);
                }
            } finally {
                rs.close();
            }
        } finally {
            stm.close();
        }
        return users;
    }

    public List<Group> getPublicGroups() throws SQLException {
        List<Group> groups = new ArrayList<Group>();
        String adminUsername;
        PreparedStatement stm = con.prepareStatement("SELECT * FROM groups WHERE is_private = ?");
        try {
            stm.setBoolean(1, false);
            ResultSet rs = stm.executeQuery();
            try {
                while (rs.next()) {
                    Group g = new Group();
                    int adminId = rs.getInt("administrator_id");
                    adminUsername = getUsernameByUserId(adminId);
                    g.setGroupId(rs.getInt("group_id"));
                    g.setAdminId(adminId);
                    g.setAdminUsername(adminUsername);
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

    public int createGroup(int userId, String groupName, String creationDate, boolean isPrivate) throws SQLException {

        int groupId = 0;
        System.out.println(userId + groupName + creationDate + isPrivate);
        PreparedStatement stm = con.prepareStatement("INSERT INTO groups (administrator_id, groupname,creation_date,is_private, is_closed) VALUES (?,?,?,?,?)", Statement.RETURN_GENERATED_KEYS);
        try {
            stm.setInt(1, userId);
            stm.setString(2, groupName);
            stm.setString(3, creationDate);
            stm.setBoolean(4, isPrivate);
            stm.setBoolean(5, false);
            stm.executeUpdate();
            ResultSet rs = stm.getGeneratedKeys();
            try {
                while (rs.next()) {
                    groupId = rs.getInt(1);
                }
            } finally {
                rs.close();
            }
        } finally {
            stm.close();
        }
        return groupId;
    }

    public void editGroup(int groupId, String groupName, boolean status) throws SQLException {
        PreparedStatement stm = con.prepareStatement("UPDATE groups SET groupname = ?, is_private = ? WHERE group_id = ? ");
        try {
            stm.setString(1, groupName);
            stm.setBoolean(2, status);
            stm.setInt(3, groupId);
            stm.executeUpdate();
        } finally {
            stm.close();
        }
    }

    public List<Group> getOwnerGroups(int userId) throws SQLException {
        List<Group> groups = new ArrayList<Group>();
        PreparedStatement stm = con.prepareStatement("SELECT * FROM groups WHERE administrator_id = ?");
        try {
            stm.setInt(1, userId);
            ResultSet rs = stm.executeQuery();
            try {
                while (rs.next()) {
                    Group g = new Group();
                    g.setGroupId(rs.getInt("group_id"));
                    g.setCreationDate(rs.getString("creation_date"));
                    g.setGroupName(rs.getString("groupname"));
                    g.setIsClosed(rs.getBoolean("is_closed"));
                    g.setIsPrivate(rs.getBoolean("is_private"));
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

    public Group getGroup(int groupId) throws SQLException {
        Group g = null;
        PreparedStatement stm = con.prepareStatement("SELECT * FROM groups WHERE group_id = ?");
        try {
            stm.setInt(1, groupId);
            ResultSet rs = stm.executeQuery();
            try {
                while (rs.next()) {
                    g = new Group();
                    g.setGroupName(rs.getString("groupname"));
                    g.setIsPrivate(rs.getBoolean("is_private"));
                    g.setGroupId(rs.getInt("group_id"));
                }
            } finally {
                rs.close();
            }
        } finally {
            stm.close();
        }
        return g;
    }
}
