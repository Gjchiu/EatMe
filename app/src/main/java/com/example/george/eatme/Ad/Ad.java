package com.example.george.eatme.Ad;

import java.io.Serializable;

/**
 * Created by Java on 2017/7/25.
 */

public class Ad implements Serializable {
    private String ad_id;
    private String store_id;
    private String ad_name;
    private String ad_content;
    private byte[] ad_image;
    private String ad_time;
    private String ad_state;
    private String ad_push_content;

    public String getAd_id() {
        return ad_id;
    }

    public void setAd_id(String ad_id) {
        this.ad_id = ad_id;
    }

    public String getStore_id() {
        return store_id;
    }

    public void setStore_id(String store_id) {
        this.store_id = store_id;
    }

    public String getAd_name() {
        return ad_name;
    }

    public void setAd_name(String ad_name) {
        this.ad_name = ad_name;
    }

    public String getAd_content() {
        return ad_content;
    }

    public void setAd_content(String ad_content) {
        this.ad_content = ad_content;
    }

    public byte[] getAd_image() {
        return ad_image;
    }

    public void setAd_image(byte[] ad_image) {
        this.ad_image = ad_image;
    }

    public String getAd_time() {
        return ad_time;
    }

    public void setAd_time(String ad_time) {
        this.ad_time = ad_time;
    }

    public String getAd_state() {
        return ad_state;
    }

    public void setAd_state(String ad_state) {
        this.ad_state = ad_state;
    }

    public String getAd_push_content() {
        return ad_push_content;
    }

    public void setAd_push_content(String ad_push_content) {
        this.ad_push_content = ad_push_content;
    }
}
