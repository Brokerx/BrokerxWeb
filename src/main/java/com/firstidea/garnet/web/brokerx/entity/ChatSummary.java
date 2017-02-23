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
import javax.validation.constraints.Size;

/**
 *
 * @author Govind
 */
@Entity
@Table(name = "ChatSummary")
@NamedQueries({
    @NamedQuery(name = "ChatSummary.findAll", query = "SELECT c FROM ChatSummary c")})
public class ChatSummary implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ChatSummaryID")
    private Integer chatSummaryID;
    @Column(name = "LeadID")
    private Integer leadID;
    @Size(max = 50)
    @Column(name = "ItemName")
    private String itemName;
    @Column(name = "FromUserID")
    private Integer fromUserID;
    @Size(max = 2)
    @Column(name = "FromUserType")
    private String fromUserType;
    @Column(name = "ToUserID")
    private Integer toUserID;
    @Size(max = 2)
    @Column(name = "ToUserType")
    private String toUserType;
    @Column(name = "LastMsg")
    private String lastMsg;
    @Column(name = "LastMsgType")
    private String lastMsgType;
    @Column(name = "LastMsgDateTime")
    @Temporal(TemporalType.TIMESTAMP)
    private Date lastMsgDateTime;
    
    @Transient
    private User toUser;

    public ChatSummary() {
    }

    public ChatSummary(Integer chatSummaryID) {
        this.chatSummaryID = chatSummaryID;
    }

    public Integer getChatSummaryID() {
        return chatSummaryID;
    }

    public void setChatSummaryID(Integer chatSummaryID) {
        this.chatSummaryID = chatSummaryID;
    }

    public Integer getLeadID() {
        return leadID;
    }

    public void setLeadID(Integer leadID) {
        this.leadID = leadID;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public Integer getFromUserID() {
        return fromUserID;
    }

    public void setFromUserID(Integer fromUserID) {
        this.fromUserID = fromUserID;
    }

    public String getFromUserType() {
        return fromUserType;
    }

    public void setFromUserType(String fromUserType) {
        this.fromUserType = fromUserType;
    }

    public Integer getToUserID() {
        return toUserID;
    }

    public void setToUserID(Integer toUserID) {
        this.toUserID = toUserID;
    }

    public String getToUserType() {
        return toUserType;
    }

    public void setToUserType(String toUserType) {
        this.toUserType = toUserType;
    }

    public String getLastMsg() {
        return lastMsg;
    }

    public void setLastMsg(String lastMsg) {
        this.lastMsg = lastMsg;
    }

    public String getLastMsgType() {
        return lastMsgType;
    }

    public void setLastMsgType(String lastMsgType) {
        this.lastMsgType = lastMsgType;
    }

    public Date getLastMsgDateTime() {
        return lastMsgDateTime;
    }

    public void setLastMsgDateTime(Date lastMsgDateTime) {
        this.lastMsgDateTime = lastMsgDateTime;
    }

    public User getToUser() {
        return toUser;
    }

    public void setToUser(User toUser) {
        this.toUser = toUser;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (chatSummaryID != null ? chatSummaryID.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ChatSummary)) {
            return false;
        }
        ChatSummary other = (ChatSummary) object;
        if ((this.chatSummaryID == null && other.chatSummaryID != null) || (this.chatSummaryID != null && !this.chatSummaryID.equals(other.chatSummaryID))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.firstidea.garnet.web.brokerx.entity.ChatSummary[ chatSummaryID=" + chatSummaryID + " ]";
    }
    
}
