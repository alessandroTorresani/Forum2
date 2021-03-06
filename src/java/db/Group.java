/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package db;

import java.io.Serializable;

/**
 *
 * @author Alessandro
 */
public class Group  implements Serializable {
    private int groupId;
    private int adminId;
    private String groupName;
    private String creationDate;
    private boolean isClosed;
    private boolean isPrivate;
    private String adminUsername;
    private int subscribers;
    private int nrPosts;
    private boolean updated;

    /**
     * @return the groupId
     */
    public int getGroupId() {
        return groupId;
    }

    /**
     * @param groupId the groupId to set
     */
    public void setGroupId(int groupId) {
        this.groupId = groupId;
    }

    /**
     * @return the adminId
     */
    public int getAdminId() {
        return adminId;
    }

    /**
     * @param adminId the adminId to set
     */
    public void setAdminId(int adminId) {
        this.adminId = adminId;
    }

    /**
     * @return the groupName
     */
    public String getGroupName() {
        return groupName;
    }

    /**
     * @param groupName the groupName to set
     */
    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    /**
     * @return the creationDate
     */
    public String getCreationDate() {
        return creationDate;
    }

    /**
     * @param creationDate the creationDate to set
     */
    public void setCreationDate(String creationDate) {
        this.creationDate = creationDate;
    }

    /**
     * @return the isClosed
     */
    public boolean isIsClosed() {
        return isClosed;
    }

    /**
     * @param isClosed the isClosed to set
     */
    public void setIsClosed(boolean isClosed) {
        this.isClosed = isClosed;
    }

    /**
     * @return the isPrivate
     */
    public boolean isIsPrivate() {
        return isPrivate;
    }

    /**
     * @param isPrivate the isPrivate to set
     */
    public void setIsPrivate(boolean isPrivate) {
        this.isPrivate = isPrivate;
    }

    /**
     * @return the adminUsername
     */
    public String getAdminUsername() {
        return adminUsername;
    }

    /**
     * @param adminUsername the adminUsername to set
     */
    public void setAdminUsername(String adminUsername) {
        this.adminUsername = adminUsername;
    }

    /**
     * @return the subscribers
     */
    public int getSubscribers() {
        return subscribers;
    }

    /**
     * @param subscribers the subscribers to set
     */
    public void setSubscribers(int subscribers) {
        this.subscribers = subscribers;
    }

    /**
     * @return the nrPosts
     */
    public int getNrPosts() {
        return nrPosts;
    }

    /**
     * @param nrPosts the nrPosts to set
     */
    public void setNrPosts(int nrPosts) {
        this.nrPosts = nrPosts;
    }

    /**
     * @return the updated
     */
    public boolean isUpdated() {
        return updated;
    }

    /**
     * @param updated the updated to set
     */
    public void setUpdated(boolean updated) {
        this.updated = updated;
    }
}
