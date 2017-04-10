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
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author Govind
 */
@Entity
@Table(name = "Lead")
@NamedQueries({
    @NamedQuery(name = "Lead.findAll", query = "SELECT l FROM Lead l")})
public class Lead implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "LeadID")
    private Integer leadID;
    @Basic(optional = false)
    @NotNull
    @Column(name = "CreatedUserID")
    private int createdUserID;
    @Column(name = "BrokerID")
    private Integer brokerID;
    @Column(name = "AssignedToUserID")
    private Integer assignedToUserID;
    @Column(name = "ParentLeadID")
    private Integer parentLeadID;
    @Column(name = "ItemID")
    private Integer itemID;
    @Size(max = 100)
    @Column(name = "ItemName")
    private String itemName;
    @Size(max = 2)
    @Column(name = "Type")
    private String type;
    @Size(max = 2)
    @Column(name = "BuyerStatus")
    private String buyerStatus;
    @Size(max = 2)
    @Column(name = "SellerStatus")
    private String sellerStatus;
    @Size(max = 2)
    @Column(name = "BrokerStatus")
    private String brokerStatus;
    @Size(max = 50)
    @Column(name = "Make")
    private String make;
    @Column(name = "Qty")
    private BigDecimal qty;
    @Column(name = "QtyUnit")
    private Integer qtyUnit;
    @Column(name = "Packing")
    private Integer packing;
    @Column(name = "PackingType")
    private String packingType;
    @Size(max = 250)
    @Column(name = "Location")
    private String location;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "BasicPrice")
    private BigDecimal basicPrice;
    @Column(name = "BasicPriceUnit")
    private Integer basicPriceUnit;
    @Column(name = "Excisetype")
    private Integer excisetype;
    @Column(name = "Tax")
    private BigDecimal tax;
    @Column(name = "ExciseDuty")
    private BigDecimal exciseDuty;
    @Column(name = "ExciseUnit")
    private Integer exciseUnit;
    @Column(name = "AsPerAvailablity")
    private Boolean asPerAvailablity;
    @Column(name = "TransportCharges")
    private BigDecimal transportCharges;
    @Column(name = "MiscCharges")
    private BigDecimal miscCharges;
    @Column(name = "BrokerageAmt")
    private BigDecimal brokerageAmt;
    @Column(name = "BuyerBrokerage")
    private BigDecimal buyerBrokerage;
    @Column(name = "SellerBrokerage")
    private BigDecimal sellerBrokerage;
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
    @Column(name = "LastUpdUserID")
    private Integer LastUpdUserID;
    @Column(name = "LastUpdDateTime")
    @Temporal(TemporalType.TIMESTAMP)
    private Date lastUpdDateTime;

    @Transient
    User createdUser;
    @Transient
    User broker;
    @Transient
    User assignedToUser;
    @Transient
    private String fieldsAltered;
    @Transient
    private String createdUserType;
    @Transient
    private Boolean isMoveToPending;;

    public Lead() {
    }

    public Lead(Integer leadID) {
        this.leadID = leadID;
    }

    public Lead(Integer leadID, int createdUserID) {
        this.leadID = leadID;
        this.createdUserID = createdUserID;
    }

    public Integer getLeadID() {
        return leadID;
    }

    public void setLeadID(Integer leadID) {
        this.leadID = leadID;
    }

    public int getCreatedUserID() {
        return createdUserID;
    }

    public void setCreatedUserID(int createdUserID) {
        this.createdUserID = createdUserID;
    }

    public Integer getBrokerID() {
        return brokerID;
    }

    public void setBrokerID(Integer brokerID) {
        this.brokerID = brokerID;
    }

    public Integer getAssignedToUserID() {
        return assignedToUserID;
    }

    public void setAssignedToUserID(Integer assignedToUserID) {
        this.assignedToUserID = assignedToUserID;
    }

    public Integer getParentLeadID() {
        return parentLeadID;
    }

    public void setParentLeadID(Integer parentLeadID) {
        this.parentLeadID = parentLeadID;
    }

    public Integer getItemID() {
        return itemID;
    }

    public void setItemID(Integer itemID) {
        this.itemID = itemID;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getBuyerStatus() {
        return buyerStatus;
    }

    public void setBuyerStatus(String BuyerStatus) {
        this.buyerStatus = BuyerStatus;
    }

    public String getSellerStatus() {
        return sellerStatus;
    }

    public void setSellerStatus(String sellerStatus) {
        this.sellerStatus = sellerStatus;
    }

    public String getBrokerStatus() {
        return brokerStatus;
    }

    public void setBrokerStatus(String BrokerStatus) {
        this.brokerStatus = BrokerStatus;
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

    public String getPackingType() {
        return packingType;
    }

    public void setPackingType(String packingType) {
        this.packingType = packingType;
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

    public Integer getExcisetype() {
        return excisetype;
    }

    public void setExcisetype(Integer excisetype) {
        this.excisetype = excisetype;
    }

    public BigDecimal getTax() {
        return tax;
    }

    public void setTax(BigDecimal tax) {
        this.tax = tax;
    }

    public BigDecimal getExciseDuty() {
        return exciseDuty;
    }

    public void setExciseDuty(BigDecimal exciseDuty) {
        this.exciseDuty = exciseDuty;
    }

    public Integer getBasicPriceUnit() {
        return basicPriceUnit;
    }

    public void setBasicPriceUnit(Integer basicPriceUnit) {
        this.basicPriceUnit = basicPriceUnit;
    }

    public Integer getExciseUnit() {
        return exciseUnit;
    }

    public void setExciseUnit(Integer exciseUnit) {
        this.exciseUnit = exciseUnit;
    }

    public Boolean getAsPerAvailablity() {
        return asPerAvailablity;
    }

    public void setAsPerAvailablity(Boolean asPerAvailablity) {
        this.asPerAvailablity = asPerAvailablity;
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

    public BigDecimal getBuyerBrokerage() {
        return buyerBrokerage;
    }

    public void setBuyerBrokerage(BigDecimal buyerBrokerage) {
        this.buyerBrokerage = buyerBrokerage;
    }

    public BigDecimal getSellerBrokerage() {
        return sellerBrokerage;
    }

    public void setSellerBrokerage(BigDecimal sellerBrokerage) {
        this.sellerBrokerage = sellerBrokerage;
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

    public Integer getLastUpdUserID() {
        return LastUpdUserID;
    }

    public void setLastUpdUserID(Integer LastUpdUserID) {
        this.LastUpdUserID = LastUpdUserID;
    }

    public Date getLastUpdDateTime() {
        return lastUpdDateTime;
    }

    public void setLastUpdDateTime(Date lastUpdDateTime) {
        this.lastUpdDateTime = lastUpdDateTime;
    }

    public User getCreatedUser() {
        return createdUser;
    }

    public void setCreatedUser(User createdUser) {
        this.createdUser = createdUser;
    }

    public User getBroker() {
        return broker;
    }

    public void setBroker(User broker) {
        this.broker = broker;
    }

    public User getAssignedToUser() {
        return assignedToUser;
    }

    public void setAssignedToUser(User assignedToUser) {
        this.assignedToUser = assignedToUser;
    }

    public String getFieldsAltered() {
        return fieldsAltered;
    }

    public void setFieldsAltered(String fieldsAltered) {
        this.fieldsAltered = fieldsAltered;
    }

    public String getCreatedUserType() {
        return createdUserType;
    }

    public void setCreatedUserType(String createdUserType) {
        this.createdUserType = createdUserType;
    }

    public Boolean getIsMoveToPending() {
        return isMoveToPending;
    }

    public void setIsMoveToPending(Boolean isMoveToPending) {
        this.isMoveToPending = isMoveToPending;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (leadID != null ? leadID.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Lead)) {
            return false;
        }
        Lead other = (Lead) object;
        if ((this.leadID == null && other.leadID != null) || (this.leadID != null && !this.leadID.equals(other.leadID))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.firstidea.garnet.web.brokerx.entity.Lead[ leadID=" + leadID + " ]";
    }

}
