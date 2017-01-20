/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.firstidea.garnet.web.brokerx.entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author Govind
 */
@Entity
@Table(name = "Chat")
@NamedQueries({
    @NamedQuery(name = "Chat.findAll", query = "SELECT c FROM Chat c")})
public class Chat implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ChatID")
    private Integer chatID;
    @Basic(optional = false)
    @NotNull
    @Column(name = "FromUserID")
    private Integer fromUserID;
    @Basic(optional = false)
    @NotNull
    @Column(name = "ToUserID")
    private Integer toUserID;
    @Column(name = "LeadID")
    private Integer leadID;
    @Size(max = 2000)
    @Column(name = "Message")
    private String message;
    @Size(max = 5)
    @Column(name = "Type")
    private String type;
    @Column(name = "CreatedDttm")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdDttm;
    @Transient
    private String fromUserName;
    @Transient
    private String fromUserPhoto;
    @Transient
    private String fromUserType;
    @Transient
    private String itemName;
    
    public Chat() {
    }

    public Chat(Integer chatID) {
        this.chatID = chatID;
    }

    public Chat(Integer chatID, int fromUserID, int toUserID) {
        this.chatID = chatID;
        this.fromUserID = fromUserID;
        this.toUserID = toUserID;
    }

    public Integer getChatID() {
        return chatID;
    }

    public void setChatID(Integer chatID) {
        this.chatID = chatID;
    }

    public int getFromUserID() {
        return fromUserID;
    }

    public void setFromUserID(int fromUserID) {
        this.fromUserID = fromUserID;
    }

    public int getToUserID() {
        return toUserID;
    }

    public void setToUserID(int toUserID) {
        this.toUserID = toUserID;
    }

    public Integer getLeadID() {
        return leadID;
    }

    public void setLeadID(Integer leadID) {
        this.leadID = leadID;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Date getCreatedDttm() {
        return createdDttm;
    }

    public void setCreatedDttm(Date createdDttm) {
        this.createdDttm = createdDttm;
    }

    public String getFromUserName() {
        return fromUserName;
    }

    public void setFromUserName(String fromUserName) {
        this.fromUserName = fromUserName;
    }

    public String getFromUserPhoto() {
        return fromUserPhoto;
    }

    public void setFromUserPhoto(String fromUserPhoto) {
        this.fromUserPhoto = fromUserPhoto;
    }

    public String getFromUserType() {
        return fromUserType;
    }

    public void setFromUserType(String fromUserType) {
        this.fromUserType = fromUserType;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (chatID != null ? chatID.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Chat)) {
            return false;
        }
        Chat other = (Chat) object;
        if ((this.chatID == null && other.chatID != null) || (this.chatID != null && !this.chatID.equals(other.chatID))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.firstidea.garnet.web.brokerx.entity.Chat[ chatID=" + chatID + " ]";
    }

}
