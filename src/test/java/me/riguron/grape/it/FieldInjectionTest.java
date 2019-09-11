package me.riguron.grape.it;

import me.riguron.grape.bootstrap.Context;
import me.riguron.grape.bootstrap.Grape;
import me.riguron.grape.bootstrap.GrapeConfiguration;
import org.junit.jupiter.api.Test;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class FieldInjectionTest {

    @Test
    void injectWithMethod() {

        Grape grape = new Grape(
                new GrapeConfiguration()
                        .classes(Arrays.asList(A.class, B.class, C1.class, C2.class))

        );

        final Context context = grape.createContext();


        assertNotNull(context.getBean(A.class).c1);
        assertNotNull(context.getBean(A.class).b);
        assertNotNull(context.getBean(B.class).a);
        assertEquals(C2.class, context.getBean(B.class).c.getClass());

    }

    public static class A {

        @Inject
        private B b;

        @Inject
        private C1 c1;
    }

    public static class B {

        @Inject
        @Named("C2")
        private C c;

        @Inject
        private A a;
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
