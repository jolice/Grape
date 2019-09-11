package me.riguron.grape.bean.matcher;

import lombok.RequiredArgsConstructor;
import me.riguron.grape.bean.BeanMeta;

@RequiredArgsConstructor
public class ExactClassMatcher implements BeanMatcher {

    private final Class<?> originalType;

    @Override
    public boolean matches(BeanMeta registeredBean) {
        return registeredBean.getBeanClass().equals(originalType);
    }
}
