package com.github.jolice.bean.matcher;

import lombok.RequiredArgsConstructor;
import com.github.jolice.bean.BeanMeta;

@RequiredArgsConstructor
public class ExactClassMatcher implements BeanMatcher {

    private final Class<?> originalType;

    @Override
    public boolean matches(BeanMeta registeredBean) {
        return registeredBean.getBeanClass().equals(originalType);
    }
}
