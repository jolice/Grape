package me.riguron.grape.reflection;

import org.junit.jupiter.api.Test;

import javax.inject.Inject;
import java.lang.reflect.Constructor;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ConstructorLookupTest {

    @Test
    void whenInjectConstructorExists() {


        ConstructorLookup constructorLookup = new ConstructorLookup();
        Optional<Constructor<?>> injectConstructor = constructorLookup.getInjectConstructor(WithInjectionConstructor.class);


        assertTrue(injectConstructor.isPresent());

        Constructor<?> constructor = injectConstructor.get();
        assertEquals(1, constructor.getParameterCount());
        assertEquals(int.class, constructor.getParameterTypes()[0]);


    }

    @Test
    void whenNoInjectConstructor() {

        ConstructorLookup constructorLookup = new ConstructorLookup();

        assertFalse(constructorLookup.getInjectConstructor(WithJustEmptyConstructor.class).isPresent());


    }

    @Test
    void whenEmptyConstructorExists() {

        ConstructorLookup constructorLookup = new ConstructorLookup();
        assertTrue(constructorLookup.getEmptyConstructor(WithJustEmptyConstructor.class).isPresent());
    }

    @Test
    void whenEmptyConstructorDoesNotExist() {


        ConstructorLookup constructorLookup = new ConstructorLookup();

        assertFalse(constructorLookup.getEmptyConstructor(WithInjectionConstructor.class).isPresent());
    }

    static class WithInjectionConstructor {


        @Inject
        public WithInjectionConstructor(int x) {

        }
    }

    @SuppressWarnings("WeakerAccess")
    public static class WithJustEmptyConstructor {

    }
}