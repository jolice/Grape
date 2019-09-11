package me.riguron.grape.it.classes;

import lombok.Getter;
import me.riguron.grape.annotation.Component;

import javax.inject.Inject;
import javax.inject.Named;

@Getter
@Component
public class C {

    private final A a;
    private Interface anInterface;

    @Inject
    public C(A a, @Named("First B") B b) {
        this.a = a;
    }

    @Inject
    public void setInterface(@Named("ImplementationOne") Interface anInterface) {
        this.anInterface = anInterface;
    }
}
