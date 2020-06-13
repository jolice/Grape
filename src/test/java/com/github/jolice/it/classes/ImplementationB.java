package com.github.jolice.it.classes;

import lombok.Getter;

public class ImplementationB implements Interface {

    @Getter
    private int value;

    public ImplementationB(int value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "ImplementationB";
    }

}


