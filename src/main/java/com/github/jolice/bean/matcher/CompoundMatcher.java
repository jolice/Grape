package com.github.jolice.bean.matcher;

import lombok.RequiredArgsConstructor;
import com.github.jolice.bean.BeanMeta;

@RequiredArgsConstructor
public class CompoundMatcher implements BeanMatcher {

    private final BeanMatcher first;
    private final BeanMatcher second;

    @Override
    public boolean matches(BeanMeta registeredBean) {
        return first.matches(registeredBean) || second.matches(registeredBean);
    }
}
