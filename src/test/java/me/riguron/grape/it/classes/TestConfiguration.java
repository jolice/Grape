package me.riguron.grape.it.classes;

import me.riguron.grape.bean.Configuration;

import javax.enterprise.inject.Produces;
import javax.inject.Named;

public class TestConfiguration implements Configuration {

    @Produces
    @Named("First B")
    public B firstB(@Named("Second B") B b) {
        return new B("First");
    }

    @Produces
    @Named("Second B")
    public B secondB() {
        return new B("Second");
    }

    @Produces
    @Named("ImplementationOne")
    public Interface interface1() {
        return new ImplementationA(247);
    }

    @Produces
    @Named("ImplementationTwo")
    public Interface interface2() {
        return new ImplementationB(227);
    }


}
