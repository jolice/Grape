package com.github.jolice.it.classes;

import com.github.jolice.annotation.Component;
import lombok.Getter;

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
