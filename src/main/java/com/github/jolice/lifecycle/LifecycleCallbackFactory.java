package com.github.jolice.lifecycle;

import com.github.jolice.reflection.MethodInvoker;
import lombok.RequiredArgsConstructor;
import com.github.jolice.lifecycle.type.PostConstructAction;
import com.github.jolice.lifecycle.type.PreDestroyAction;

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
