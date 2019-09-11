package me.riguron.grape.it.classes;

import lombok.Getter;

public class ImplementationA implements Interface {

    @Getter
    private int value;

    public ImplementationA(int value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "ImplementationA - " + value;
    }
}
