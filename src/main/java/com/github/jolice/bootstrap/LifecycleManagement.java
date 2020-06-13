package com.github.jolice.bootstrap;

import com.github.jolice.bean.registry.ManagedBean;
import com.github.jolice.bean.registry.Registry;
import com.github.jolice.lifecycle.LifecycleCallback;
import lombok.RequiredArgsConstructor;
import com.github.jolice.lifecycle.LifecycleCallbackFactory;

@RequiredArgsConstructor
public class LifecycleManagement {

    private final Registry<ManagedBean> beanRegistry;
    private final Runtime runtime;
    private final LifecycleCallbackFactory lifecycleCallbackFactory;

    public void triggerBeanInitialization() {
        LifecycleCallback postConstruct = lifecycleCallbackFactory.postConstructCallback();
        beanRegistry.getAll().stream().map(ManagedBean::getBeanInstance).forEach(postConstruct::trigger);
    }

    public void registerShutdownCallbacks() {
        runtime.addShutdownHook(new Thread(() -> {
            LifecycleCallback preDestroy = lifecycleCallbackFactory.preDestroyCallback();
            beanRegistry.getAll().stream().map(ManagedBean::getBeanInstance).forEach(preDestroy::trigger);
        }));
    }
}
