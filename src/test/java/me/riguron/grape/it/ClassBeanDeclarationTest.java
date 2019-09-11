package me.riguron.grape.it;

import lombok.Getter;
import me.riguron.grape.bootstrap.Context;
import me.riguron.grape.bootstrap.Grape;
import me.riguron.grape.bootstrap.GrapeConfiguration;
import org.junit.jupiter.api.Test;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.Arrays;
import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class ClassBeanDeclarationTest {

    @Test
    void whenNamedOnClass() {

        Grape grape = new Grape(
                new GrapeConfiguration()
                        .classes(new HashSet<>(Arrays.asList(A.class, First.class, Second.class)))
        );

        final Context context = grape.createContext();

        assertNotNull(context.getBean(A.class).a);
        assertNotNull(context.getBean(A.class).b);


    }

    @Getter
    public static class A {


        private Interface a;
        private Interface b;

        @Inject
        public A(@Named("First") Interface a, @Named("Second") Interface b) {
            this.a = a;
            this.b = b;
        }
    }

    interface Interface {

    }


    @Named("First")
    public static class First implements Interface {

    }

    @Named("Second")
    public static class Second implements Interface {

    }
}
