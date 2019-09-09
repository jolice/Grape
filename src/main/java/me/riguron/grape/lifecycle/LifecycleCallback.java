package me.riguron.grape.lifecycle;

import lombok.RequiredArgsConstructor;
import me.riguron.grape.lifecycle.type.LifecycleAction;
import me.riguron.grape.reflection.MethodInvoker;

import java.util.Arrays;

@RequiredArgsConstructor
public class LifecycleCallback {

    private final MethodInvoker methodInvoker;
    private final LifecycleAction action;

    public void trigger(Object bean) {
        Arrays.stream(bean.getClass().getDeclaredMethods())
                .filter(m -> m.isAnnotationPresent(action.getAnnotation()))
                .filter(m -> m.getParameterCount() == 0)
                .peek(m -> m.setAccessible(true))
                .forEach(m -> methodInvoker.invoke(bean, m));
    }

}
