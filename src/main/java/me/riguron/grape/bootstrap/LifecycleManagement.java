package me.riguron.grape.bootstrap;

import lombok.RequiredArgsConstructor;
import me.riguron.grape.bean.registry.ManagedBean;
import me.riguron.grape.bean.registry.Registry;
import me.riguron.grape.lifecycle.LifecycleCallback;
import me.riguron.grape.lifecycle.LifecycleCallbackFactory;

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
