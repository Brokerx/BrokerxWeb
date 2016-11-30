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
@Table(name = "User")
@NamedQueries({
    @NamedQuery(name = "User.findAll", query = "SELECT u FROM User u")})
public class User implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "UserID")
    private Integer userID;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 150)
    @Column(name = "FullName")
    private String fullName;
    @Size(max = 11)
    @Column(name = "Mobile")
    private String mobile;
    // @Pattern(regexp="[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?", message="Invalid email")//if the field contains email address consider using this annotation to enforce field validation
    @Size(max = 250)
    @Column(name = "Email")
    private String email;
    @Size(max = 45)
    @Column(name = "Password")
    private String password;
    @Size(max = 45)
    @Column(name = "City")
    private String city;
    @Size(max = 6)
    @Column(name = "VerificationCode")
    private String verificationCode;
    @Column(name = "VerificationExpiry")
    @Temporal(TemporalType.TIMESTAMP)
    private Date verificationExpiry;
    @Column(name = "IsBroker")
    private Boolean isBroker;
    @Size(max = 100)
    @Column(name = "Address")
    private String address;
    @Size(max = 250)
    @Column(name = "ImageURL")
    private String imageURL;
    @Size(max = 250)
    @Column(name = "GCMKey")
    private String gcmKey;
    @Size(max = 500)
    @Column(name = "About")
    private String about;
    @Size(max = 1000)
    @Column(name = "BrokerDealsInItems")
    private String brokerDealsInItems;
    @Column(name = "SignupDttm")
    @Temporal(TemporalType.TIMESTAMP)
    private Date signupDttm;
    @Column(name = "LastUpdDttm")
    @Temporal(TemporalType.TIMESTAMP)
    private Date lastUpdDttm;
    @Transient
    private Float rating;
    @Transient
    private String status;
    @Transient
    private boolean isMyRequest;
    @Transient
    private Integer userConnectionID;
    
    public User() {
    }

    public User(Integer userID) {
        this.userID = userID;
    }

    public User(Integer userID, String fullName) {
        this.userID = userID;
        this.fullName = fullName;
    }

    public Integer getUserID() {
        return userID;
    }

    public void setUserID(Integer userID) {
        this.userID = userID;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getVerificationCode() {
        return verificationCode;
    }

    public void setVerificationCode(String verificationCode) {
        this.verificationCode = verificationCode;
    }

    public Date getVerificationExpiry() {
        return verificationExpiry;
    }

    public void setVerificationExpiry(Date verificationExpiry) {
        this.verificationExpiry = verificationExpiry;
    }

    public Boolean getIsBroker() {
        return isBroker;
    }

    public void setIsBroker(Boolean isBroker) {
        this.isBroker = isBroker;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public String getGcmKey() {
        return gcmKey;
    }

    public void setGcmKey(String gcmKey) {
        this.gcmKey = gcmKey;
    }

    public String getAbout() {
        return about;
    }

    public void setAbout(String about) {
        this.about = about;
    }

    public String getBrokerDealsInItems() {
        return brokerDealsInItems;
    }

    public void setBrokerDealsInItems(String brokerDealsInItems) {
        this.brokerDealsInItems = brokerDealsInItems;
    }

    public Date getSignupDttm() {
        return signupDttm;
    }

    public void setSignupDttm(Date signupDttm) {
        this.signupDttm = signupDttm;
    }

    public Date getLastUpdDttm() {
        return lastUpdDttm;
    }

    public void setLastUpdDttm(Date lastUpdDttm) {
        this.lastUpdDttm = lastUpdDttm;
    }

    public Float getRating() {
        return rating;
    }

    public void setRating(Float rating) {
        this.rating = rating;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getUserConnectionID() {
        return userConnectionID;
    }

    public void setUserConnectionID(Integer userConnectionID) {
        this.userConnectionID = userConnectionID;
    }

    public boolean isIsMyRequest() {
        return isMyRequest;
    }

    public void setIsMyRequest(boolean isMyRequest) {
        this.isMyRequest = isMyRequest;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (userID != null ? userID.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof User)) {
            return false;
        }
        User other = (User) object;
        if ((this.userID == null && other.userID != null) || (this.userID != null && !this.userID.equals(other.userID))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.firstidea.garnet.web.brokerx.entity.User[ userID=" + userID + " ]";
    }
    
}
