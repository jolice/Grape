package me.riguron.grape;

import me.riguron.grape.annotation.Component;

import javax.inject.Inject;
import javax.inject.Named;

@Component
public class A {

    private B b;

    @Inject
    public A(@Named("First B")B b) {
        this.b = b;
    }

    @Override
    public String toString() {
        return "A (B - " + b + ")";
    }

}
