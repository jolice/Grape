package com.github.jolice.provider;

import com.github.jolice.reflection.MethodInvoker;
import org.junit.jupiter.api.Test;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;

class MethodProviderTest {

    @Test
    void createBean() throws NoSuchMethodException {

        FactoryMethodHolder factoryMethodHolder

                = new FactoryMethodHolder();

        MethodProvider methodProvider =
                new MethodProvider(
                        FactoryMethodHolder.class.getDeclaredMethod("create", int.class),
                        factoryMethodHolder,
                        new MethodInvoker()
                );

        String createdBean = (String) methodProvider.createBean(Collections.singletonList(5));

        assertEquals("5val", createdBean);

    }

    @SuppressWarnings("WeakerAccess")
    public static class FactoryMethodHolder {

        public String create(int x) {
            return x + "val";
        }
    }

    @Test
    void getDependencies() {
    }


}