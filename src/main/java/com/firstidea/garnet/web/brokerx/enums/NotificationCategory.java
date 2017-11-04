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
public enum NotificationCategory {

    BUYER("B"), SELLER("S"), HISTORY("H");

    private final String notificationCategory;

    private NotificationCategory(String category) {
        this.notificationCategory = category;
    }

    public String getNotificationCategory() {
        return notificationCategory;
    }

}
