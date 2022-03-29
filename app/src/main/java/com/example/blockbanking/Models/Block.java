package com.example.blockbanking.Models;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.Base64;
import java.util.Date;

public class Block {
    private String timeStamp, hash, previousHash, uids, uidt;
    private Data data;
    private int x;

    public Block() {
    }

    public Block(Data data, String uids, String uidt,String prehash) {
        this.setTimeStamp(new Date().toString());
        this.data = data;
        this.setHash(calculateBlockHash());
        this.uids = uids;
        this.uidt = uidt;
        this.previousHash = prehash;
    }

    public String getUids() {
        return uids;
    }

    public void setUids(String uids) {
        this.uids = uids;
    }

    public String getUidt() {
        return uidt;
    }

    public void setUidt(String uidt) {
        this.uidt = uidt;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public String getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }

    public String getHash() {
        return hash;
    }


    public void setHash(String hash) {
        this.hash = hash;
    }

    public String getPreviousHash() {
        return previousHash;
    }

    public void setPreviousHash(String previousHash) {
        this.previousHash = previousHash;
    }

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public String calculateBlockHash() {
        String dataToHash = getTimeStamp() + getPreviousHash() + getData().getMessenger()
                + getData().getMoneySend() + getData().getNameSend() + getData().getNameTake();
        MessageDigest digest;
        String encoded = null;
        try {
            digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(dataToHash.getBytes(StandardCharsets.UTF_8));
            encoded = Base64.getEncoder().encodeToString(hash);
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return encoded;
    }
}
