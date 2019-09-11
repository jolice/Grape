package me.riguron.grape.bean.scan;

import me.riguron.grape.annotation.Component;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ComponentScanTest {

    @Test
    void getAnnotatedBeans() {

        ComponentScan componentScan = new ComponentScan(getClass().getPackage().getName());

        assertEquals(new HashSet<>(
                Arrays.asList(A.class, B.class)
        ), new HashSet<>(componentScan.getAnnotatedBeans()));

    }

    @Component
    static class A {

    }

    @Component
    static class B {

    }


    static class C {

    }
}