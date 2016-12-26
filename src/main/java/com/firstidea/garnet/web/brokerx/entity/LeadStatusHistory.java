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
@Table(name = "LeadStatusHistory")
@NamedQueries({
    @NamedQuery(name = "LeadStatusHistory.findAll", query = "SELECT l FROM LeadStatusHistory l")})
public class LeadStatusHistory implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "LeadID")
    private Integer leadID;
    @Column(name = "CurrentStatus")
    private Integer currentStatus;
    @Column(name = "DealDoneDateTime")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dealDoneDateTime;
    @Column(name = "GoodsLiftedDateTime")
    @Temporal(TemporalType.TIMESTAMP)
    private Date goodsLiftedDateTime;
    @Size(max = 100)
    @Column(name = "InvoiceNumber")
    private String invoiceNumber;
    @Column(name = "DocumentsAttachedDateTime")
    @Temporal(TemporalType.TIMESTAMP)
    private Date documentsAttachedDateTime;
    @Column(name = "PaymentReceivedDateTime")
    @Temporal(TemporalType.TIMESTAMP)
    private Date paymentReceivedDateTime;
    @Column(name = "DocumentsreceivedDateTime")
    @Temporal(TemporalType.TIMESTAMP)
    private Date documentsreceivedDateTime;
    @Column(name = "DealClearedDateTime")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dealClearedDateTime;

    public LeadStatusHistory() {
    }

    public Integer getLeadID() {
        return leadID;
    }

    public void setLeadID(Integer leadID) {
        this.leadID = leadID;
    }

    public Integer getCurrentStatus() {
        return currentStatus;
    }

    public void setCurrentStatus(Integer currentStatus) {
        this.currentStatus = currentStatus;
    }

    public Date getDealDoneDateTime() {
        return dealDoneDateTime;
    }

    public void setDealDoneDateTime(Date dealDoneDateTime) {
        this.dealDoneDateTime = dealDoneDateTime;
    }

    public Date getGoodsLiftedDateTime() {
        return goodsLiftedDateTime;
    }

    public void setGoodsLiftedDateTime(Date goodsLiftedDateTime) {
        this.goodsLiftedDateTime = goodsLiftedDateTime;
    }

    public String getInvoiceNumber() {
        return invoiceNumber;
    }

    public void setInvoiceNumber(String invoiceNumber) {
        this.invoiceNumber = invoiceNumber;
    }

    public Date getDocumentsAttachedDateTime() {
        return documentsAttachedDateTime;
    }

    public void setDocumentsAttachedDateTime(Date documentsAttachedDateTime) {
        this.documentsAttachedDateTime = documentsAttachedDateTime;
    }

    public Date getPaymentReceivedDateTime() {
        return paymentReceivedDateTime;
    }

    public void setPaymentReceivedDateTime(Date paymentReceivedDateTime) {
        this.paymentReceivedDateTime = paymentReceivedDateTime;
    }

    public Date getDocumentsreceivedDateTime() {
        return documentsreceivedDateTime;
    }

    public void setDocumentsreceivedDateTime(Date documentsreceivedDateTime) {
        this.documentsreceivedDateTime = documentsreceivedDateTime;
    }

    public Date getDealClearedDateTime() {
        return dealClearedDateTime;
    }

    public void setDealClearedDateTime(Date dealClearedDateTime) {
        this.dealClearedDateTime = dealClearedDateTime;
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
        if (!(object instanceof LeadStatusHistory)) {
            return false;
        }
        LeadStatusHistory other = (LeadStatusHistory) object;
        if ((this.leadID == null && other.leadID != null) || (this.leadID != null && !this.leadID.equals(other.leadID))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.firstidea.garnet.web.brokerx.entity.LeadStatusHistory[ leadStatusHistoryID=" + leadID + " ]";
    }
    
}
