package com.github.jolice.it;

import com.github.jolice.bootstrap.Grape;
import com.github.jolice.bootstrap.GrapeConfiguration;
import com.github.jolice.exception.dependency.CircularDependencyException;
import com.github.jolice.exception.injection.InjectionPointError;
import org.junit.jupiter.api.Test;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.Arrays;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertThrows;

@SuppressWarnings("unused")
class CircularDependencyTest {

    @Test
    void whenCircularDependency() {

        Grape grape = new Grape(
                new GrapeConfiguration()
                        .classes(
                               Arrays.asList(Left.class, Right.class)
                        )
        );

        assertThrows(CircularDependencyException.class, grape::createContext);
    }

    @Test
    void whenPolymorphicCircularDependency() {

        Grape grape = new Grape(
                new GrapeConfiguration()
                        .classes(
                                Arrays.asList(One.class, Two.class)
                        )
        );

        assertThrows(CircularDependencyException.class, grape::createContext);

    }

    @Test
    void whenSelfCycle() {
        Grape grape = new Grape(
                new GrapeConfiguration()
                .classes(
                        Collections.singletonList(SelfCycle.class)
                )
        );

        assertThrows(InjectionPointError.class, grape::createContext);

    }

    private static class Left {

        private final Right right;

        @Inject
        public Left(Right right) {
            this.right = right;
        }
    }


    private static class Right {

        private final Left left;

        @Inject
        public Right(Left left) {
            this.left = left;
        }
    }


    private interface Interface {

    }

    @Named("One")
    private static class One implements Interface {

        private final Interface anInterface;

        @Inject
        public One(@Named("Two") Interface anInterface) {
            this.anInterface = anInterface;
        }
    }

    @Named("Two")
    private static class Two implements Interface {

        private final Interface anInterface;

        @Inject
        public Two(@Named("One") Interface anInterface) {
            this.anInterface = anInterface;
        }
    }

    public static class SelfCycle {

        private SelfCycle selfCycle;

        @Inject
        public SelfCycle(SelfCycle selfCycle) {
            this.selfCycle = selfCycle;
        }
    }
}

