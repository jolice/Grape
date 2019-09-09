package me.riguron.grape;

import me.riguron.grape.bean.Configuration;

import javax.enterprise.inject.Produces;
import javax.inject.Named;

public class TestCfg implements Configuration {

    @Produces
    @Named("First B")
    public B firstB(@Named("Second B") B b) {
        return new B("FIRST");
    }

    @Produces
    @Named("Second B")
    public B secondB() {
        return new B("SECOND");
    }

    @Produces
    @Named("IMP1")
    public IFace iface() {
        return new Impl1(247);
    }

    @Produces
    @Named("IMP2")
    public IFace iface2() {
        return new Impl2();
    }

}
