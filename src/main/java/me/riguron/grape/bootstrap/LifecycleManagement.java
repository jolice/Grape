package me.riguron.grape.bootstrap;

import lombok.RequiredArgsConstructor;
import me.riguron.grape.bean.registry.ManagedBean;
import me.riguron.grape.bean.registry.Registry;
import me.riguron.grape.lifecycle.LifecycleCallback;
import me.riguron.grape.lifecycle.type.PostConstructAction;
import me.riguron.grape.lifecycle.type.PreDestroyAction;
import me.riguron.grape.reflection.MethodInvoker;

@RequiredArgsConstructor
public class LifecycleManagement {

    private final MethodInvoker methodInvoker;
    private final Registry<ManagedBean> beanRegistry;

    public void triggerBeanInitialization() {
        LifecycleCallback postConstruct = new LifecycleCallback(methodInvoker, new PostConstructAction());
        beanRegistry.getAll().forEach(postConstruct::trigger);
    }

    public void registerShutdownCallbacks() {
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            LifecycleCallback preDestroy = new LifecycleCallback(methodInvoker, new PreDestroyAction());
            beanRegistry.getAll().forEach(preDestroy::trigger);
        }));
    }
}
