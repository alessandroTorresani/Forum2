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

    public List<User> getAllInvitableUser(int groupId) throws SQLException {
        List<User> users = new ArrayList<User>();
        PreparedStatement stm = con.prepareStatement("SELECT * FROM users WHERE user_id NOT IN (SELECT user_id FROM users_groups WHERE group_id = ? )");
        try {
            stm.setInt(1, groupId);
            ResultSet rs = stm.executeQuery();
            try {
                while (rs.next()) {
                    User u = new User();
                    u.setUserId(rs.getInt("user_id"));
                    u.setUsername(rs.getString("username"));
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
        List<Group> publicGroups = new ArrayList<Group>();
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
                    publicGroups.add(g);
                }
            } finally {
                rs.close();
            }
        } finally {
            stm.close();
        }
        return publicGroups;
    }
    
    public List<Group> getPrivateGroups(int userId) throws SQLException{
        List<Group> privateGroups = new ArrayList<Group>();
        PreparedStatement stm = con.prepareStatement("SELECT * FROM users_groups JOIN groups ON users_groups.group_id = groups.group_id WHERE user_id = ? AND is_private=?");
        String adminUsername;
        try{
            stm.setInt(1, userId);
            stm.setBoolean(2, true);
            ResultSet rs = stm.executeQuery();
            try {
                while(rs.next()){
                    Group g = new Group();
                    int adminId = rs.getInt("administrator_id");
                    adminUsername = getUsernameByUserId(adminId);
                    g.setGroupId(rs.getInt("group_id"));
                    g.setAdminId(adminId);
                    g.setAdminUsername(adminUsername);
                    g.setCreationDate(rs.getString("creation_date"));
                    g.setGroupName(rs.getString("groupname"));
                    g.setIsClosed(rs.getBoolean("is_closed"));
                    g.setIsPrivate(true);
                    privateGroups.add(g);
                }
            } finally{
                rs.close();
            }
        } finally{
            stm.close();
        }
        return privateGroups;
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
        String adminUsername=null;
        PreparedStatement stm = con.prepareStatement("SELECT * FROM groups JOIN users ON users.user_id = groups.administrator_id WHERE group_id = ?");
        try {
            stm.setInt(1, groupId);
            ResultSet rs = stm.executeQuery();
            try {
                while (rs.next()) {
                    g = new Group();
                    g.setAdminId(rs.getInt("administrator_id"));
                    adminUsername = getUsernameByUserId(rs.getInt("administrator_id"));
                    g.setAdminUsername(adminUsername);
                    g.setGroupName(rs.getString("groupname"));
                    g.setIsPrivate(rs.getBoolean("is_private"));
                    g.setGroupId(rs.getInt("group_id"));
                    g.setCreationDate(rs.getString("creation_date"));
                }
            } finally {
                rs.close();
            }
        } finally {
            stm.close();
        }
        return g;
    }

    public String getGroupPage(int groupId) throws SQLException {
        String groupName = null;
        PreparedStatement stm = con.prepareStatement("SELECT groupname FROM groups WHERE group_id = ?");
        try {
            stm.setInt(1, groupId);
            ResultSet rs = stm.executeQuery();
            try {
                while (rs.next()) {
                    groupName = rs.getString("groupname");
                }
            } finally {
                rs.close();
            }
        } finally {
            stm.close();
        }
        return groupName;
    }
    
    public boolean isSubscribed(int userId, int groupId) throws SQLException{
        int res = 0;
        PreparedStatement stm = con.prepareStatement("SELECT group_id FROM users_groups WHERE user_id = ? AND group_id = ?");
        try {
            stm.setInt(1, userId);
            stm.setInt(2, groupId);
            ResultSet rs = stm.executeQuery();
            try {
                while(rs.next()){
                    res++;
                }
            } finally{
                rs.close();
            }
        } finally {
            stm.close();
        }
        return res == 1;
    }

    public boolean isAdmin(int userId, int groupId) throws SQLException {
        boolean isAdmin = false;
        PreparedStatement stm = con.prepareStatement("SELECT administrator_id FROM groups WHERE group_id = ?");
        try {
            stm.setInt(1, groupId);
            ResultSet rs = stm.executeQuery();
            try {
                while (rs.next()) {
                    isAdmin = rs.getInt("administrator_id") == userId;
                }
            } finally {
                rs.close();
            }
        } finally {
            stm.close();
        }
        return isAdmin;
    }

    public void sendBids(List<String> ids, int groupId, int adminId) throws SQLException {
        PreparedStatement stm = con.prepareStatement("INSERT INTO bids (user_id, group_id, administrator_id) VALUES (?,?,?)");
        try {
            stm.setInt(2, groupId);
            stm.setInt(3, adminId);
            for (int x = 0; x < ids.size(); x++) {
                stm.setInt(1, Integer.parseInt(ids.get(x)));
                stm.executeUpdate();
            }
        } finally {
            stm.close();
        }
    }

    public List<Bid> getBids(int userId) throws SQLException {
        List<Bid> bids = new ArrayList<Bid>();
        String groupName = null;
        String adminUsername = null;
        PreparedStatement stm = con.prepareStatement("SELECT * FROM bids WHERE user_id = ?");
        try {
            stm.setInt(1, userId);
            ResultSet rs = stm.executeQuery();
            try {
                while (rs.next()) {
                    Bid b = new Bid();
                    groupName = getGroupPage(rs.getInt("group_id"));
                    b.setGroupName(groupName);
                    b.setGroupId(rs.getInt("group_id"));
                    b.setAdminId(rs.getInt("administrator_id"));
                    adminUsername = getUsernameByUserId(rs.getInt("administrator_id"));
                    b.setAdminUsername(adminUsername);
                    b.setBidId(rs.getInt("bid_id"));
                    b.setUserId(rs.getInt("user_id"));
                    bids.add(b);
                }
            } finally {
                rs.close();
            }
        } finally {
            stm.close();
        }
        return bids;
    }
    
    public boolean checkBids(int userId, int bidId) throws SQLException{
        PreparedStatement stm = con.prepareStatement("SELECT user_id FROM bids WHERE bid_id = ? and user_id = ?");
        int numBids = 0;
        try {
            stm.setInt(1, bidId);
            stm.setInt(2, userId);
            ResultSet rs = stm.executeQuery();
            try {
                while(rs.next()){
                    numBids++;
                }
            } finally {
                rs.close();
            }
        } finally {
            stm.close();
        }
        return numBids == 0;
    }

    public void AcceptBids(List<String> bids, int userId) throws SQLException {
        PreparedStatement stm = con.prepareStatement("INSERT INTO users_groups (user_id, group_id, is_administrator) VALUES (?,?,?)");
        int groupId = 0;
        try {
            stm.setInt(1, userId);
            stm.setBoolean(3, false);
            for (int x = 0; x < bids.size(); x++) {
                PreparedStatement stm1 = con.prepareCall("SELECT group_id FROM bids WHERE bid_id = ?");
                try {
                    stm1.setInt(1, Integer.parseInt(bids.get(x)));
                    ResultSet rs = stm1.executeQuery();
                    try {
                        while (rs.next()) {
                            groupId = rs.getInt("group_id");
                            System.out.println("groupid= " + groupId);
                        }
                    } finally {
                        rs.close();
                    }
                } finally {
                    stm1.close();
                }
                stm.setInt(2, groupId);
                stm.executeUpdate();
            }
        } finally {
            stm.close();
        }
    }

    public void deleteBids(List<String> bids) throws SQLException {
        PreparedStatement stm = con.prepareStatement("DELETE FROM bids WHERE bid_id=?");
        try {
            for (int x = 0; x < bids.size(); x++) {
                stm.setInt(1, Integer.parseInt(bids.get(x)));
                stm.executeUpdate();
            }
        } finally {
            stm.close();
        }
    }
    
    public List<Post> getPosts(int groupId) throws SQLException{
        List<Post>posts = new ArrayList<Post>();
        PreparedStatement stm = con.prepareStatement("SELECT * FROM posts WHERE group_id = ?");
        try {
            stm.setInt(1, groupId);
            ResultSet rs = stm.executeQuery();
            try {
                while(rs.next()){
                    Post p = new Post();
                    String username = getUsernameByUserId(rs.getInt("user_id"));
                    p.setUsername(username);
                    p.setPostId(rs.getInt("post_id"));
                    p.setUserId(rs.getInt("user_id"));
                    p.setGroupId(rs.getInt("group_id"));
                    p.setMessage(rs.getString("message"));
                    p.setCreationDate(rs.getString("creation_date"));
                    posts.add(p);
                }
            } finally {
                rs.close();
            }
        } finally {
            stm.close();
        } 
        return posts;
    }
}
