package me.riguron.grape.bean;

import lombok.experimental.Delegate;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class BeanCollection<E> implements List<E> {

    @Delegate
    private List<E> bean = Collections.emptyList();

    public void put(E item) {
        if (bean.isEmpty()) {
            bean = Collections.singletonList(item);
        } else {
            bean = new ArrayList<>(bean);
            bean.add(item);
        }
    }
}
