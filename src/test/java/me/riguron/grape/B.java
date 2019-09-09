package me.riguron.grape;

import me.riguron.grape.annotation.Component;

@Component
public class B {

    private String desc;

    public B() {

    }

    public B(String desc) {
        this.desc = desc;
    }

    @Override
    public String toString() {
        return "B - " + (desc==null ? "NOT SET" : desc);
    }

}
