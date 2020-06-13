package com.github.jolice.bean.lookup;

import com.github.jolice.annotation.Name;
import com.github.jolice.bean.matcher.BeanMatcher;
import com.github.jolice.bean.matcher.NamedMatcher;
import com.github.jolice.bean.matcher.policy.BindingPolicy;
import com.github.jolice.bean.matcher.policy.ProvidedNamedPolicy;

import javax.inject.Named;

public class NamedLookup implements LookupParams {

    private final Named named;

    public NamedLookup(String beanName) {
        this.named = new Name(beanName);
    }

    @Override
    public BeanMatcher beanMatcher() {
        return new NamedMatcher(named);
    }

    @Override
    public BindingPolicy bindingPolicy() {
        return new ProvidedNamedPolicy(named);
    }
}
