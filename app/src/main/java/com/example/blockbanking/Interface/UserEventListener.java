package com.example.blockbanking.Interface;

import com.example.blockbanking.Models.User;

public interface UserEventListener {
    /**
     * call wen note clicked.
     *
     * @param user: note item
     */
    void onUserClick(User user);
    /**
     * call wen long Click to note.
     *
     * @param user : item
     */
    void onUserLongClick(User user);
}
