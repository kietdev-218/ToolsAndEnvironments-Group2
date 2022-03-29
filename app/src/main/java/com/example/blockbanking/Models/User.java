package com.example.blockbanking.Models;

import java.util.Random;

public class User {
    private String email, name, birthday, phoneNum, numberCard, uid;
    private long money;

    public User() {
    }

    public User(String email, String name, String birthday, String phoneNum, String uid) {
        this.email = email;
        this.name = name;
        this.birthday = birthday;
        this.phoneNum = phoneNum;
        this.money = 0;
        this.uid = uid;
        CreateNumCard();

    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }

    public String getNumberCard() {
        return numberCard;
    }

    public void setNumberCard(String numberCard) {
        this.numberCard = numberCard;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public long getMoney() {
        return money;
    }

    public void setMoney(long money) {
        this.money = money;
    }

    private void CreateNumCard() {
        Random rd = new Random();
        int card = 8426;
        int x, y;
        do {
            x = rd.nextInt(9999);
        } while ((x + "").length() != 4);
        do {
            y = rd.nextInt(9999);
        } while ((y + "").length() != 4);
        setNumberCard("" + card + x + y);
    }

    public String MoneyToString(long mo) {
        return mo + " VNĐ";
    }

}
