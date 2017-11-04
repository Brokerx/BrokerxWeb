/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.firstidea.garnet.web.brokerx.dto;

import com.firstidea.garnet.web.brokerx.entity.User;
import java.math.BigDecimal;

/**
 *
 * @author Govind
 */
public class UserDTO {

    private User userInfo;
    Integer buyerActiveDealsCount;
    Integer buyerPendingDealsCount;
    Integer buyerDoneDealsCount;
    Integer buyerRejectedDealsCount;
    Integer sellerActiveDealsCount;
    Integer sellerPendingDealsCount;
    Integer sellerDoneDealsCount;
    Integer sellerRejectedDealsCount;
    BigDecimal brokerEarnings;
    BigDecimal sellerEarnings;
    BigDecimal buyerspendings;

    public User getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(User userInfo) {
        this.userInfo = userInfo;
    }

    public Integer getBuyerActiveDealsCount() {
        return buyerActiveDealsCount;
    }

    public void setBuyerActiveDealsCount(Integer buyerActiveDealsCount) {
        this.buyerActiveDealsCount = buyerActiveDealsCount;
    }

    public Integer getBuyerPendingDealsCount() {
        return buyerPendingDealsCount;
    }

    public void setBuyerPendingDealsCount(Integer buyerPendingDealsCount) {
        this.buyerPendingDealsCount = buyerPendingDealsCount;
    }

    public Integer getBuyerDoneDealsCount() {
        return buyerDoneDealsCount;
    }

    public void setBuyerDoneDealsCount(Integer buyerDoneDealsCount) {
        this.buyerDoneDealsCount = buyerDoneDealsCount;
    }

    public Integer getSellerActiveDealsCount() {
        return sellerActiveDealsCount;
    }

    public void setSellerActiveDealsCount(Integer sellerActiveDealsCount) {
        this.sellerActiveDealsCount = sellerActiveDealsCount;
    }

    public Integer getSellerPendingDealsCount() {
        return sellerPendingDealsCount;
    }

    public void setSellerPendingDealsCount(Integer sellerPendingDealsCount) {
        this.sellerPendingDealsCount = sellerPendingDealsCount;
    }

    public Integer getSellerDoneDealsCount() {
        return sellerDoneDealsCount;
    }

    public void setSellerDoneDealsCount(Integer sellerDoneDealsCount) {
        this.sellerDoneDealsCount = sellerDoneDealsCount;
    }

    public Integer getBuyerRejectedDealsCount() {
        return buyerRejectedDealsCount;
    }

    public void setBuyerRejectedDealsCount(Integer buyerRejectedDealsCount) {
        this.buyerRejectedDealsCount = buyerRejectedDealsCount;
    }

    public Integer getSellerRejectedDealsCount() {
        return sellerRejectedDealsCount;
    }

    public void setSellerRejectedDealsCount(Integer sellerRejectedDealsCount) {
        this.sellerRejectedDealsCount = sellerRejectedDealsCount;
    }

    public BigDecimal getBrokerEarnings() {
        return brokerEarnings;
    }

    public void setBrokerEarnings(BigDecimal brokerEarnings) {
        this.brokerEarnings = brokerEarnings;
    }

    public BigDecimal getSellerEarnings() {
        return sellerEarnings;
    }

    public void setSellerEarnings(BigDecimal sellerEarnings) {
        this.sellerEarnings = sellerEarnings;
    }

    public BigDecimal getBuyerspendings() {
        return buyerspendings;
    }

    public void setBuyerspendings(BigDecimal buyerspendings) {
        this.buyerspendings = buyerspendings;
    }

}
