package com.github.jolice.it;

import com.github.jolice.bootstrap.Context;
import com.github.jolice.bootstrap.Grape;
import com.github.jolice.bootstrap.GrapeConfiguration;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class MethodInjectionTest {

    @Test
    void injectWithMethod() {

        Grape grape = new Grape(
                new GrapeConfiguration()
                        .classes(Arrays.asList(A.class, B.class, C1.class, C2.class))

        );

        final Context context = grape.createContext();

        assertNotNull(context.getBean(A.class).b);
        assertEquals(C2.class, context.getBean(B.class).c.getClass());
        assertNotNull(context.getBean(B.class).a);
        assertNotNull(context.getBean(A.class).c1);

    }


    public static class A {

        private B b;
        private C1 c1;

        @Inject
        public void setC1(C1 c1) {
            this.c1 = c1;
        }

        @Inject
        public void setB(B b) {
            this.b = b;
        }
    }

    public static class B {

        private C c;
        private A a;

        @Inject
        public void configure(@Named("C2") C c, A a) {
            this.c = c;
            this.a = a;
        }
    }

    public interface C {

    }

    @Named("C1")
    public static class C1 implements C {

    }

    @Named("C2")
    public static class C2 implements C {

    }
}
