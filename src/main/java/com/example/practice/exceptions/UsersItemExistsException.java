package com.example.practice.exceptions;

import com.example.practice.entity.Possession;

public class UsersItemExistsException extends RuntimeException{

    public UsersItemExistsException(Possession p) {
        super("the user id=" + p.getUser().getId() + " already owns the item id=" + p.getItem().getId());
    }
}
