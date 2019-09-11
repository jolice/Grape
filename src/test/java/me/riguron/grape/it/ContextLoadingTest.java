package me.riguron.grape.it;

import me.riguron.grape.bean.lookup.NamedLookup;
import me.riguron.grape.bootstrap.Context;
import me.riguron.grape.bootstrap.Grape;
import me.riguron.grape.bootstrap.GrapeConfiguration;
import me.riguron.grape.it.classes.*;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class ContextLoadingTest {

    private Context context;

    @BeforeAll
    void setupContext() {
        Grape grape = new Grape(
                new GrapeConfiguration()
                        .scan("me.riguron.grape.it.classes")
                        .configurations(Collections.singletonList(new TestConfiguration()))
        );
        this.context = grape.createContext();
    }

    @Test
    void nonPolymorphicBeans() {
        assertNotNull(context.getBean(A.class).getB());
        assertNotNull(context.getBean(A.class).getImplementationOne());
        assertNotNull(context.getBean(C.class).getA());
        assertEquals(247, context.getBean(C.class).getAnInterface().getValue());
    }

    @Test
    void concreteImplementations() {
        assertEquals(247, context.getBean(ImplementationA.class).getValue());
        assertEquals(227, context.getBean(ImplementationB.class).getValue());
    }

    @Test
    void polymorphicLookups() {
        assertEquals(247, context.getBean(Interface.class, new NamedLookup("ImplementationOne")).getValue());
        assertEquals(227, context.getBean(Interface.class, new NamedLookup("ImplementationTwo")).getValue());
    }

    @Test
    void multipleBeansOfSameType() {
        assertEquals("First", context.getBean(B.class, new NamedLookup("First B")).getDescription());
        assertEquals("Second", context.getBean(B.class, new NamedLookup("Second B")).getDescription());
    }
}
