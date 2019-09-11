package me.riguron.grape.bean.lookup;

import lombok.RequiredArgsConstructor;
import me.riguron.grape.bean.matcher.BeanMatcher;
import me.riguron.grape.bean.matcher.NamedMatcher;
import me.riguron.grape.bean.matcher.policy.AnnotatedNamedPolicy;
import me.riguron.grape.bean.matcher.policy.BindingPolicy;

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
