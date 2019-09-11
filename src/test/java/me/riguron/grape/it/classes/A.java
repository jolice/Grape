package me.riguron.grape.it.classes;

import lombok.Getter;
import me.riguron.grape.annotation.Component;

import javax.inject.Inject;
import javax.inject.Named;

@Component
public class A {

    @Getter
    private B b;

    @Getter
    private Interface implementationOne;

    @Inject
    public A(@Named("First B") B b, @Named("ImplementationOne") Interface implementationOne) {
        this.b = b;
        this.implementationOne = implementationOne;
    }


}
