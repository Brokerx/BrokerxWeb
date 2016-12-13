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

    BUYER_POSTED("BP"), SELLER_POSTED("SP"),BUYER_REVERTED("BR"), SELLER_REVERTED("SR"),
    BROKER_REVERTED("RR"), BROKER_ACCEPTED("BA"), BUYER_BROKER_ACCEPTED("BRA"),;

    final String status;

    private LeadCurrentStatus(String status) {
        this.status = status;
    }
}
