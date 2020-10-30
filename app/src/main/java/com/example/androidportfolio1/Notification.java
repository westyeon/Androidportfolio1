package com.example.androidportfolio1;

public class Notification {
    public Number idx;
    public String drug_name;
    public String before_img;
    public String after_img;
    public String change_date;
    public String druginfo_url;
    public String idfyinfo_url;
    public String full_url;

    public Number getIdx() {
        return idx;
    }

    public void setIdx(Number idx) {
        this.idx = idx;
    }

    public String getDrug_name() {
        return drug_name;
    }

    public void setDrug_name(String drug_name) {
        this.drug_name = drug_name;
    }

    public String getBefore_img() {
        return before_img;
    }

    public void setBefore_img(String before_img) {
        this.before_img = before_img;
    }

    public String getAfter_img() {
        return after_img;
    }

    public void setAfter_img(String after_img) {
        this.after_img = after_img;
    }

    public String getChange_date() {
        return change_date;
    }

    public void setChange_date(String change_date) {
        this.change_date = change_date;
    }

    public String getDruginfo_url() {
        return druginfo_url;
    }

    public void setDruginfo_url(String druginfo_url) {
        this.druginfo_url = druginfo_url;
    }

    public String getIdfyinfo_url() {
        return idfyinfo_url;
    }

    public void setIdfyinfo_url(String idfyinfo_url) {
        this.idfyinfo_url = idfyinfo_url;
    }

    public String getFull_url() {
        return full_url;
    }

    public void setFull_url(String full_url) {
        this.full_url = full_url;
    }

    @Override
    public String toString() {
        return "Notification{" +
                "idx=" + idx +
                ", drug_name='" + drug_name + '\'' +
                ", before_img='" + before_img + '\'' +
                ", after_img='" + after_img + '\'' +
                ", change_date='" + change_date + '\'' +
                ", druginfo_url='" + druginfo_url + '\'' +
                ", idfyinfo_url='" + idfyinfo_url + '\'' +
                ", full_url='" + full_url + '\'' +
                '}';
    }
}
