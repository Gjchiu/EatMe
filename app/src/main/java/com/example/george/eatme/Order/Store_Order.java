package com.example.george.eatme.Order;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * Created by George on 2017/6/21.
 */

public class Store_Order implements Serializable{
    private String order_id;
    private String order_time;
    private String mem_id;
    private String store_id;
    private String order_state;
    private Integer totalprice;
    private String order_way;
    private String receive_address;
    private String order_note;
    private String order_taketime;

    public String getStore_name() {
        return store_name;
    }

    public void setStore_name(String store_name) {
        this.store_name = store_name;
    }

    private String store_name;

    public String getOrder_id() {
        return order_id;
    }

    public void setOrder_id(String order_id) {
        this.order_id = order_id;
    }

    public String getOrder_time() {
        return order_time;
    }

    public void setOrder_time(String order_time) {
        this.order_time = order_time;
    }

    public String getMem_id() {
        return mem_id;
    }

    public void setMem_id(String mem_id) {
        this.mem_id = mem_id;
    }

    public String getStore_id() {
        return store_id;
    }

    public void setStore_id(String store_id) {
        this.store_id = store_id;
    }

    public String getOrder_state() {
        return order_state;
    }

    public void setOrder_state(String order_state) {
        this.order_state = order_state;
    }

    public String getOrder_way() {
        return order_way;
    }

    public void setOrder_way(String order_way) {
        this.order_way = order_way;
    }

    public Integer getTotalprice() {
        return totalprice;
    }

    public void setTotalprice(Integer totalprice) {
        this.totalprice = totalprice;
    }



    public String getReceive_address() {
        return receive_address;
    }

    public void setReceive_address(String receive_address) {
        this.receive_address = receive_address;
    }

    public String getOrder_note() {
        return order_note;
    }

    public void setOrder_note(String order_note) {
        this.order_note = order_note;
    }

    public String getOrder_taketime() {
        return order_taketime;
    }

    public void setOrder_taketime(String order_taketime) {
        this.order_taketime = order_taketime;
    }


}
