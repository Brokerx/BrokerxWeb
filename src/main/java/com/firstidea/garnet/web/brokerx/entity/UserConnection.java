/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.firstidea.garnet.web.brokerx.entity;

import java.io.Serializable;
import java.math.BigDecimal;
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
import javax.validation.constraints.Size;

/**
 *
 * @author Govind
 */
@Entity
@Table(name = "UserConnection")
@NamedQueries({
    @NamedQuery(name = "UserConnection.findAll", query = "SELECT u FROM UserConnection u")})
public class UserConnection implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "UserConnectionID")
    private Integer userConnectionID;
    @Column(name = "FromUserID")
    private Integer fromUserID;
    @Column(name = "ToUserID")
    private Integer toUserID;
    @Size(max = 1)
    @Column(name = "Status")
    private String status;
    @Column(name = "StatusDttm")
    @Temporal(TemporalType.TIMESTAMP)
    private Date statusDttm;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "FromUserRating")
    private Float fromUserRating;
    @Column(name = "ToUserrating")
    private Float toUserrating;
    @Column(name = "CreatedDttm")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdDttm;

    public UserConnection() {
    }

    public UserConnection(Integer userConnectionID) {
        this.userConnectionID = userConnectionID;
    }

    public Integer getUserConnectionID() {
        return userConnectionID;
    }

    public void setUserConnectionID(Integer userConnectionID) {
        this.userConnectionID = userConnectionID;
    }

    public Integer getFromUserID() {
        return fromUserID;
    }

    public void setFromUserID(Integer fromUserID) {
        this.fromUserID = fromUserID;
    }

    public Integer getToUserID() {
        return toUserID;
    }

    public void setToUserID(Integer toUserID) {
        this.toUserID = toUserID;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getStatusDttm() {
        return statusDttm;
    }

    public void setStatusDttm(Date statusDttm) {
        this.statusDttm = statusDttm;
    }

    public Float getFromUserRating() {
        return fromUserRating;
    }

    public void setFromUserRating(Float fromUserRating) {
        this.fromUserRating = fromUserRating;
    }

    public Float getToUserrating() {
        return toUserrating;
    }

    public void setToUserrating(Float toUserrating) {
        this.toUserrating = toUserrating;
    }

    public Date getCreatedDttm() {
        return createdDttm;
    }

    public void setCreatedDttm(Date createdDttm) {
        this.createdDttm = createdDttm;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (userConnectionID != null ? userConnectionID.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof UserConnection)) {
            return false;
        }
        UserConnection other = (UserConnection) object;
        if ((this.userConnectionID == null && other.userConnectionID != null) || (this.userConnectionID != null && !this.userConnectionID.equals(other.userConnectionID))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.firstidea.garnet.web.brokerx.entity.UserConnection[ userConnectionID=" + userConnectionID + " ]";
    }
    
}
