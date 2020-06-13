package com.github.jolice.reflection;

import com.github.jolice.exception.reflection.InvocationException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class MethodInvokerTest {

    @Test
    void invoke() throws NoSuchMethodException {

        Instance instance = new Instance();
        MethodInvoker methodInvoker = new MethodInvoker();

        methodInvoker.invoke(
                instance, instance.getClass().getDeclaredMethod(
                        "increment"
                )
        );

        assertEquals(6, instance.v);

    }

    @Test
    void callPrivate() {
        Instance instance = new Instance();
        MethodInvoker methodInvoker = new MethodInvoker();

        assertThrows(InvocationException.class, () -> methodInvoker.invoke(
                instance, instance.getClass().getDeclaredMethod(
                        "privateMethod"
                )
        ));

    }

    static class Instance {

        private int v = 5;

        public void increment() {
            v++;
        }

        private void privateMethod() {
        }

    }
}