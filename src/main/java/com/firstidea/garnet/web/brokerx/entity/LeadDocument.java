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
import javax.validation.constraints.Size;

/**
 *
 * @author Govind
 */
@Entity
@Table(name = "LeadDocument")
@NamedQueries({
    @NamedQuery(name = "LeadDocument.findAll", query = "SELECT l FROM LeadDocument l")})
public class LeadDocument implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "LeadDocumentID")
    private Integer leadDocumentID;
    @Column(name = "LeadID")
    private Integer leadID;
    @Size(max = 250)
    @Column(name = "DocumentURL")
    private String documentURL;
    @Column(name = "UploadedByUserID")
    private Integer uploadedByUserID;
    @Column(name = "UploadedDttm")
    @Temporal(TemporalType.TIMESTAMP)
    private Date uploadedDttm;
    @Column(name = "IsDeleted")
    private Boolean isDeleted;
    @Size(max = 50)
    @Column(name = "type")
    private String type;

    public LeadDocument() {
    }

    public LeadDocument(Integer leadDocumentID) {
        this.leadDocumentID = leadDocumentID;
    }

    public Integer getLeadDocumentID() {
        return leadDocumentID;
    }

    public void setLeadDocumentID(Integer leadDocumentID) {
        this.leadDocumentID = leadDocumentID;
    }

    public Integer getLeadID() {
        return leadID;
    }

    public void setLeadID(Integer leadID) {
        this.leadID = leadID;
    }

    public String getDocumentURL() {
        return documentURL;
    }

    public void setDocumentURL(String documentURL) {
        this.documentURL = documentURL;
    }

    public Integer getUploadedByUserID() {
        return uploadedByUserID;
    }

    public void setUploadedByUserID(Integer uploadedByUserID) {
        this.uploadedByUserID = uploadedByUserID;
    }

    public Date getUploadedDttm() {
        return uploadedDttm;
    }

    public void setUploadedDttm(Date uploadedDttm) {
        this.uploadedDttm = uploadedDttm;
    }

    public Boolean getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(Boolean isDeleted) {
        this.isDeleted = isDeleted;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (leadDocumentID != null ? leadDocumentID.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof LeadDocument)) {
            return false;
        }
        LeadDocument other = (LeadDocument) object;
        if ((this.leadDocumentID == null && other.leadDocumentID != null) || (this.leadDocumentID != null && !this.leadDocumentID.equals(other.leadDocumentID))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.firstidea.garnet.web.brokerx.entity.LeadDocument[ leadDocumentID=" + leadDocumentID + " ]";
    }
    
}
