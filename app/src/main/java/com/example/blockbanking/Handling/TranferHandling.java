package com.example.blockbanking.Handling;

import com.example.blockbanking.Models.Block;
import com.example.blockbanking.Models.User;

import java.util.List;

public class TranferHandling {
    public TranferHandling() {
    }

    public boolean CheckMoney(User user, String money){
        if (user.getMoney() >= Long.valueOf(money)){
            return true;
        }
        return false;
    }

    public boolean CheckZeroMoney(String money){
        if (Long.valueOf(money) != 0){
            return true;
        }
        return false;
    }

    public Block GetLastBlock(List<Block> blocks){
        return blocks.get(blocks.size()-1);
    }

    public User FindUserSend(List<User> users, String uid){
        for (User user : users){
            if (user.getUid().equals(uid)){
                return user;
            }
        }
        return null;
    }

    public User FindUserTake(List<User> users, String numberCard){
        for (User user : users){
            if (user.getNumberCard().equals(numberCard)){
                return user;
            }
        }
        return null;
    }
}
