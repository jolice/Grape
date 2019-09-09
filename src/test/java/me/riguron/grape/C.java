package me.riguron.grape;

import me.riguron.grape.annotation.Component;

import javax.inject.Inject;
import javax.inject.Named;

@Component
public class C {

    @Inject
    public C(A a, @Named("First B") B b) {
        System.out.println("A init - " + a);
        System.out.println("C init - " + b);
    }

    @Inject
    public void setIFace(@Named("IMP1") IFace iFace) {
        System.out.println("inject");
        System.out.println(iFace + " >> " + iFace);
    }
}
