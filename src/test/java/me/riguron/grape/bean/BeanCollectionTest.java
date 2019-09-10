package me.riguron.grape.bean;

import org.junit.jupiter.api.Test;

import java.util.Iterator;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class BeanCollectionTest {

    @Test
    void put() {

        BeanCollection<String> beanCollection = new BeanCollection<>();

        beanCollection.add("1");
        beanCollection.add("2");
        beanCollection.add("3");

        Iterator<String> iterator = beanCollection.iterator();
        assertNextElement("1", iterator);
        assertNextElement("2", iterator);
        assertNextElement("3", iterator);

    }

    private void assertNextElement(String expected, Iterator<String> iterator) {
        assertTrue(iterator.hasNext());
        assertEquals(expected, iterator.next());
    }
}