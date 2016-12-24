/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.firstidea.garnet.web.brokerx.enums;

/**
 *
 * @author Govind
 */
public enum LeadCurrentStatus {

    Accepted("A"), Rejected("X"), Reverted("R"), Pending("P"),Done("D"), Waiting("W"), Deleted("L");

    final String status;

    private LeadCurrentStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return this.status;
    }
}
