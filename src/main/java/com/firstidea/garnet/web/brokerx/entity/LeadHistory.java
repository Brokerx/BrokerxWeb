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
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author Govind
 */
@Entity
@Table(name = "LeadHistory")
@NamedQueries({
    @NamedQuery(name = "LeadHistory.findAll", query = "SELECT l FROM LeadHistory l"),
    @NamedQuery(name = "LeadHistory.getByLeadID", query = "SELECT l FROM LeadHistory l where l.leadID=:leadID")
})
public class LeadHistory implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "LeadHistoryID")
    private Integer leadHistoryID;
    @Basic(optional = false)
    @NotNull
    @Column(name = "LeadID")
    private int leadID;
    @Basic(optional = false)
    @NotNull
    @Column(name = "CreatedUserID")
    private int createdUserID;
    @Size(max = 10)
    @Column(name = "CreatedUserType")
    private String createdUserType;
    @Column(name = "AssignedToUserID")
    private Integer assignedToUserID;
    @Size(max = 2)
    @Column(name = "CurrentStatus")
    private String currentStatus;
    @Size(max = 50)
    @Column(name = "Make")
    private String make;
    @Column(name = "Qty")
    private BigDecimal qty;
    @Column(name = "QtyUnit")
    private Integer qtyUnit;
    @Column(name = "Packing")
    private Integer packing;
    @Size(max = 250)
    @Column(name = "Location")
    private String location;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "BasicPrice")
    private BigDecimal basicPrice;
    @Column(name = "ExciseDuty")
    private BigDecimal exciseDuty;
    @Column(name = "TransportCharges")
    private BigDecimal transportCharges;
    @Column(name = "MiscCharges")
    private BigDecimal miscCharges;
    @Column(name = "BrokerageAmt")
    private BigDecimal brokerageAmt;
    @Size(max = 250)
    @Column(name = "AgainstForm")
    private String againstForm;
    @Size(max = 45)
    @Column(name = "CreditPeriod")
    private String creditPeriod;
    @Size(max = 45)
    @Column(name = "FreeStoragePeriod")
    private String freeStoragePeriod;
    @Size(max = 150)
    @Column(name = "PreferredSellerName")
    private String preferredSellerName;
    @Size(max = 100)
    @Column(name = "Comments")
    private String comments;
    @Column(name = "CreatedDttm")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdDttm;
    @Size(max = 500)
    @Column(name = "FieldsAltered")
    private String fieldsAltered;

    public LeadHistory() {
    }

    public LeadHistory(Integer leadHistoryID) {
        this.leadHistoryID = leadHistoryID;
    }

    public LeadHistory(Integer leadHistoryID, int leadID, int createdUserID) {
        this.leadHistoryID = leadHistoryID;
        this.leadID = leadID;
        this.createdUserID = createdUserID;
    }

    public Integer getLeadHistoryID() {
        return leadHistoryID;
    }

    public void setLeadHistoryID(Integer leadHistoryID) {
        this.leadHistoryID = leadHistoryID;
    }

    public int getLeadID() {
        return leadID;
    }

    public void setLeadID(int leadID) {
        this.leadID = leadID;
    }

    public int getCreatedUserID() {
        return createdUserID;
    }

    public void setCreatedUserID(int createdUserID) {
        this.createdUserID = createdUserID;
    }

    public String getCreatedUserType() {
        return createdUserType;
    }

    public void setCreatedUserType(String createdUserType) {
        this.createdUserType = createdUserType;
    }

    public Integer getAssignedToUserID() {
        return assignedToUserID;
    }

    public void setAssignedToUserID(Integer assignedToUserID) {
        this.assignedToUserID = assignedToUserID;
    }

    public String getCurrentStatus() {
        return currentStatus;
    }

    public void setCurrentStatus(String currentStatus) {
        this.currentStatus = currentStatus;
    }

    public String getMake() {
        return make;
    }

    public void setMake(String make) {
        this.make = make;
    }

    public BigDecimal getQty() {
        return qty;
    }

    public void setQty(BigDecimal qty) {
        this.qty = qty;
    }

    public Integer getQtyUnit() {
        return qtyUnit;
    }

    public void setQtyUnit(Integer qtyUnit) {
        this.qtyUnit = qtyUnit;
    }

    public Integer getPacking() {
        return packing;
    }

    public void setPacking(Integer packing) {
        this.packing = packing;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public BigDecimal getBasicPrice() {
        return basicPrice;
    }

    public void setBasicPrice(BigDecimal basicPrice) {
        this.basicPrice = basicPrice;
    }

    public BigDecimal getExciseDuty() {
        return exciseDuty;
    }

    public void setExciseDuty(BigDecimal exciseDuty) {
        this.exciseDuty = exciseDuty;
    }

    public BigDecimal getTransportCharges() {
        return transportCharges;
    }

    public void setTransportCharges(BigDecimal transportCharges) {
        this.transportCharges = transportCharges;
    }

    public BigDecimal getMiscCharges() {
        return miscCharges;
    }

    public void setMiscCharges(BigDecimal miscCharges) {
        this.miscCharges = miscCharges;
    }

    public BigDecimal getBrokerageAmt() {
        return brokerageAmt;
    }

    public void setBrokerageAmt(BigDecimal brokerageAmt) {
        this.brokerageAmt = brokerageAmt;
    }

    public String getAgainstForm() {
        return againstForm;
    }

    public void setAgainstForm(String againstForm) {
        this.againstForm = againstForm;
    }

    public String getCreditPeriod() {
        return creditPeriod;
    }

    public void setCreditPeriod(String creditPeriod) {
        this.creditPeriod = creditPeriod;
    }

    public String getFreeStoragePeriod() {
        return freeStoragePeriod;
    }

    public void setFreeStoragePeriod(String freeStoragePeriod) {
        this.freeStoragePeriod = freeStoragePeriod;
    }

    public String getPreferredSellerName() {
        return preferredSellerName;
    }

    public void setPreferredSellerName(String preferredSellerName) {
        this.preferredSellerName = preferredSellerName;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public Date getCreatedDttm() {
        return createdDttm;
    }

    public void setCreatedDttm(Date createdDttm) {
        this.createdDttm = createdDttm;
    }

    public String getFieldsAltered() {
        return fieldsAltered;
    }

    public void setFieldsAltered(String fieldsAltered) {
        this.fieldsAltered = fieldsAltered;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (leadHistoryID != null ? leadHistoryID.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof LeadHistory)) {
            return false;
        }
        LeadHistory other = (LeadHistory) object;
        if ((this.leadHistoryID == null && other.leadHistoryID != null) || (this.leadHistoryID != null && !this.leadHistoryID.equals(other.leadHistoryID))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.firstidea.garnet.web.brokerx.entity.LeadHistory[ leadHistoryID=" + leadHistoryID + " ]";
    }
    
}
