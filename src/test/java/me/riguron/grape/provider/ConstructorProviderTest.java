package me.riguron.grape.provider;

import org.junit.jupiter.api.Test;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ConstructorProviderTest {

    @Test
    void createBean() {

        ConstructorProvider constructorProvider =
                new ConstructorProvider(
                        X.class.getDeclaredConstructors()[0]
                );

        X x = (X) constructorProvider.createBean(
                Collections.singletonList(5)
        );

        assertEquals(5, x.v);
    }

    static class X {

        private int v;

        public X(int v) {
            this.v = v;
        }
    }
}