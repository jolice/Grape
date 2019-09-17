package me.riguron.grape.bean;

import org.junit.jupiter.api.Test;

import java.util.Iterator;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class BeanCollectionTest {

    @Test
    void put() {

        BeanCollection<String> beanCollection = new BeanCollection<>();

        beanCollection.put("1");
        beanCollection.put("2");
        beanCollection.put("3");

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