package me.riguron.grape.bean;

import lombok.experimental.Delegate;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class BeanCollection<E> implements List<E> {

    @Delegate
    private List<E> bean = Collections.emptyList();

    public synchronized void put(E item) {
        if (bean.isEmpty()) {
            bean = Collections.singletonList(item);
            return;
        } else if (bean.size() == 1) {
            bean = new CopyOnWriteArrayList<>(bean);
        }
        bean.add(item);
    }
}

