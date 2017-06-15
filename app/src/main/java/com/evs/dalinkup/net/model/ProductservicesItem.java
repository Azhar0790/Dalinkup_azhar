package com.evs.dalinkup.net.model;

/**
 * Created by Sarps on 2/28/2017.
 */
public class ProductservicesItem {

    String s_id,s_name,s_img,s_price,event_id,qty;

    public ProductservicesItem(String s_id, String s_name, String s_price, String event_id, String qty, String s_img) {
        this.s_id = s_id;
        this.s_name = s_name;
        this.s_price = s_price;
        this.event_id = event_id;
        this.qty = qty;
        this.s_img = s_img;
    }

    public String getS_img() {
        return s_img;
    }

    public void setS_img(String s_img) {
        this.s_img = s_img;
    }

    public String getQty() {
        return qty;
    }

    public void setQty(String qty) {
        this.qty = qty;
    }

    public String getS_id() {
        return s_id;
    }

    public void setS_id(String s_id) {
        this.s_id = s_id;
    }

    public String getS_name() {
        return s_name;
    }

    public void setS_name(String s_name) {
        this.s_name = s_name;
    }

    public String getS_price() {
        return s_price;
    }

    public void setS_price(String s_price) {
        this.s_price = s_price;
    }

    public String getEvent_id() {
        return event_id;
    }

    public void setEvent_id(String event_id) {
        this.event_id = event_id;
    }
}
