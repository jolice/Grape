package me.riguron.grape.it.classes;

import lombok.Getter;

public class B {

    @Getter
    private String description;

    B(String description) {
        this.description = description;
    }


}
