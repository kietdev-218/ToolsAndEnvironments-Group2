package com.example.blockbanking.Models;

public class Data {
    private String nameSend, nameTake, moneySend, messenger;

    public Data() {
    }

    public Data(String nameSend, String nameTake, String moneySend, String messenger) {
        this.nameSend = nameSend;
        this.nameTake = nameTake;
        this.moneySend = moneySend;
        this.messenger = messenger;
    }

    public String getNameSend() {
        return nameSend;
    }

    public void setNameSend(String nameSend) {
        this.nameSend = nameSend;
    }

    public String getNameTake() {
        return nameTake;
    }

    public void setNameTake(String nameTake) {
        this.nameTake = nameTake;
    }

    public String getMoneySend() {
        return moneySend;
    }

    public void setMoneySend(String moneySend) {
        this.moneySend = moneySend;
    }

    public String getMessenger() {
        return messenger;
    }

    public void setMessenger(String messenger) {
        this.messenger = messenger;
    }
}
