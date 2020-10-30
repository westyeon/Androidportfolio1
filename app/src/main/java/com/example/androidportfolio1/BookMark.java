package com.example.androidportfolio1;

import java.util.Date;

public class BookMark {
    private String item;
    private String search_word;
    private String date;
    private String itemname;

    public BookMark() {
    }

    public String getItemname() {
        return itemname;
    }

    public void setItem(String item) {
        this.item = item;

        if(item.equals("item_seq_num")){
            itemname = "제품 일련번호";
        }else if(item.equals("item_name")){
            itemname = "제품 명";
        }else if(item.equals("entp_seq")){
            itemname = "업체 일련번호";
        }else if(item.equals("entp_name")){
            itemname = "업체 명";
        }else if(item.equals("edi_code")){
            itemname = "보험코드";
        }else{
            itemname = "선택항목 없음";
        }

    }
    public String getItem() {
        return this.item;
    }
    public void setSearch_word(String search_word) {
        this.search_word = search_word;
    }
    public String getSearch_word() {
        return this.search_word;
    }
    public void setDate(String date) {
        this.date = date;
    }
    public String getDate() {
        return this.date;
    }

    @Override
    public String toString() {
        return "BookMark{" +
                "item='" + item + '\'' +
                ", search_word='" + search_word + '\'' +
                ", date='" + date + '\'' +
                '}';
    }
}
