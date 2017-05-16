/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.firstidea.garnet.web.brokerx.dto;

import com.firstidea.garnet.web.brokerx.entity.User;
import java.util.List;

/**
 *
 * @author Govind
 */
public class DropDownValuesDTO {

    private List<String> itemsDealWith;
    private List<User> buyers;
    private List<User> sellers;

    public List<String> getItemsDealWith() {
        return itemsDealWith;
    }

    public void setItemsDealWith(List<String> itemsDealWith) {
        this.itemsDealWith = itemsDealWith;
    }

    public List<User> getBuyers() {
        return buyers;
    }

    public void setBuyers(List<User> buyers) {
        this.buyers = buyers;
    }

    public List<User> getSellers() {
        return sellers;
    }

    public void setSellers(List<User> sellers) {
        this.sellers = sellers;
    }

}
