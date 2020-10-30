package com.example.androidportfolio1;

public class Medicine {
    //접근 지정자를 public으로 선언해서 사용하기 편리하게 만듦
    public String item_seq_num;
    public String item_name;
    public String entp_seq;
    public String entp_name;
    public String chart;
    public String item_image;
    public String print_front;
    public String print_back;
    public String drug_shape;
    public String color_class1;
    public String color_class2;
    public String line_front;
    public String line_back ;
    public String leng_long;
    public String leng_short ;
    public String thick ;
    public String img_regist_ts ;
    public String class_no ;
    public String class_name;
    public String etc_otc_name;
    public String item_permit_date;
    public String form_code_name;
    public String mark_code_front_anal;
    public String mark_code_back_anal;
    public String mark_code_front_img;
    public String mark_code_back_img;
    public String change_date ;
    public String mark_code_front;
    public String mark_code_back;
    public String item_eng_name;
    public String edi_code;

    public String getEdi_code() {
        return edi_code;
    }

    public void setEdi_code(String edi_code) {
        this.edi_code = edi_code;
    }

    public String getItem_seq_num() {
        return item_seq_num;
    }

    public void setItem_seq_num(String item_seq_num) {
        this.item_seq_num = item_seq_num;
    }

    public String getItem_name() {
        return item_name;
    }

    public void setItem_name(String item_name) {
        this.item_name = item_name;
    }

    public String getEntp_seq() {
        return entp_seq;
    }

    public void setEntp_seq(String entp_seq) {
        this.entp_seq = entp_seq;
    }

    public String getEntp_name() {
        return entp_name;
    }

    public void setEntp_name(String entp_name) {
        this.entp_name = entp_name;
    }

    public String getItem_image() {
        return item_image;
    }

    public void setItem_image(String item_image) {
        this.item_image = item_image;
    }

    @Override
    public String toString() {
        return "Medicine{" +
                "item_seq_num='" + item_seq_num + '\'' +
                ", item_name='" + item_name + '\'' +
                ", entp_seq='" + entp_seq + '\'' +
                ", entp_name='" + entp_name + '\'' +
                ", chart='" + chart + '\'' +
                ", item_image='" + item_image + '\'' +
                ", print_front='" + print_front + '\'' +
                ", print_back='" + print_back + '\'' +
                ", drug_shape='" + drug_shape + '\'' +
                ", color_class1='" + color_class1 + '\'' +
                ", color_class2='" + color_class2 + '\'' +
                ", line_front='" + line_front + '\'' +
                ", line_back='" + line_back + '\'' +
                ", leng_long='" + leng_long + '\'' +
                ", leng_short='" + leng_short + '\'' +
                ", thick='" + thick + '\'' +
                ", img_regist_ts='" + img_regist_ts + '\'' +
                ", class_no='" + class_no + '\'' +
                ", class_name='" + class_name + '\'' +
                ", etc_otc_name='" + etc_otc_name + '\'' +
                ", item_permit_date='" + item_permit_date + '\'' +
                ", form_code_name='" + form_code_name + '\'' +
                ", mark_code_front_anal='" + mark_code_front_anal + '\'' +
                ", mark_code_back_anal='" + mark_code_back_anal + '\'' +
                ", mark_code_front_img='" + mark_code_front_img + '\'' +
                ", mark_code_back_img='" + mark_code_back_img + '\'' +
                ", change_date='" + change_date + '\'' +
                ", mark_code_front='" + mark_code_front + '\'' +
                ", mark_code_back='" + mark_code_back + '\'' +
                ", item_eng_name='" + item_eng_name + '\'' +
                ", edi_code='" + edi_code + '\'' +
                '}';
    }
}
