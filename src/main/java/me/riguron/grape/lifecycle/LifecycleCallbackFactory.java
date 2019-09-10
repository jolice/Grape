package me.riguron.grape.lifecycle;

import lombok.RequiredArgsConstructor;
import me.riguron.grape.lifecycle.type.PostConstructAction;
import me.riguron.grape.lifecycle.type.PreDestroyAction;
import me.riguron.grape.reflection.MethodInvoker;

@RequiredArgsConstructor
public class LifecycleCallbackFactory {

    private final MethodInvoker methodInvoker;

    public LifecycleCallback postConstructCallback() {
        return new LifecycleCallback(methodInvoker, new PostConstructAction());
    }

    public LifecycleCallback preDestroyCallback() {
        return new LifecycleCallback(methodInvoker, new PreDestroyAction());
    }
}
