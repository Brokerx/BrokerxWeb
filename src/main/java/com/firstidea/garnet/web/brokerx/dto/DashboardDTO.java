/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.firstidea.garnet.web.brokerx.dto;

import java.util.List;

/**
 *
 * @author Govind
 */
public class DashboardDTO {
    private String totalUsers;
    private String totalBrokers;
    private String totalAmount;
    private String totalNoOfdeals;
    private String currentMonthUser;
    private String currentMonthBrokers;
    private String currentMonthDeals;
    private String currentMonthRevenue;
    private List<DashboardItem> userSummary;
    private List<DashboardItem> dealsSummary;
    private List<DashboardItem> transactionSummary;

    public String getTotalUsers() {
        return totalUsers;
    }

    public void setTotalUsers(String totalUsers) {
        this.totalUsers = totalUsers;
    }

    public String getTotalBrokers() {
        return totalBrokers;
    }

    public void setTotalBrokers(String totalBrokers) {
        this.totalBrokers = totalBrokers;
    }

    public String getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(String totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getTotalNoOfdeals() {
        return totalNoOfdeals;
    }

    public void setTotalNoOfdeals(String totalNoOfdeals) {
        this.totalNoOfdeals = totalNoOfdeals;
    }

    public String getCurrentMonthUser() {
        return currentMonthUser;
    }

    public void setCurrentMonthUser(String currentMonthUser) {
        this.currentMonthUser = currentMonthUser;
    }

    public String getCurrentMonthBrokers() {
        return currentMonthBrokers;
    }

    public void setCurrentMonthBrokers(String currentMonthBrokers) {
        this.currentMonthBrokers = currentMonthBrokers;
    }

    public String getCurrentMonthDeals() {
        return currentMonthDeals;
    }

    public void setCurrentMonthDeals(String currentMonthDeals) {
        this.currentMonthDeals = currentMonthDeals;
    }

    public String getCurrentMonthRevenue() {
        return currentMonthRevenue;
    }

    public void setCurrentMonthRevenue(String currentMonthRevenue) {
        this.currentMonthRevenue = currentMonthRevenue;
    }

    public List<DashboardItem> getUserSummary() {
        return userSummary;
    }

    public void setUserSummary(List<DashboardItem> userSummary) {
        this.userSummary = userSummary;
    }

    public List<DashboardItem> getDealsSummary() {
        return dealsSummary;
    }

    public void setDealsSummary(List<DashboardItem> dealsSummary) {
        this.dealsSummary = dealsSummary;
    }

    public List<DashboardItem> getTransactionSummary() {
        return transactionSummary;
    }

    public void setTransactionSummary(List<DashboardItem> transactionSummary) {
        this.transactionSummary = transactionSummary;
    }
    
    
}
