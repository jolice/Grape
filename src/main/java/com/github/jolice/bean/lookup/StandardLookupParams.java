package com.github.jolice.bean.lookup;

import lombok.RequiredArgsConstructor;
import com.github.jolice.bean.matcher.BeanMatcher;
import com.github.jolice.bean.matcher.NamedMatcher;
import com.github.jolice.bean.matcher.policy.AnnotatedNamedPolicy;
import com.github.jolice.bean.matcher.policy.BindingPolicy;

import javax.inject.Named;
import java.lang.reflect.AnnotatedElement;

@RequiredArgsConstructor
public class StandardLookupParams implements LookupParams {

    private final AnnotatedElement annotatedElement;

    @Override
    public BeanMatcher beanMatcher() {
        return new NamedMatcher(annotatedElement.getAnnotation(Named.class));
    }

    @Override
    public BindingPolicy bindingPolicy() {
        return new AnnotatedNamedPolicy(annotatedElement);
    }
}
