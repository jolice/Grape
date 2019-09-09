package me.riguron.grape.bootstrap;

import lombok.RequiredArgsConstructor;
import me.riguron.grape.bean.BeanLookup;
import me.riguron.grape.bean.registry.ManagedBean;
import me.riguron.grape.inject.BeanInjection;
import me.riguron.grape.inject.FieldInjection;
import me.riguron.grape.inject.SetterInjection;
import me.riguron.grape.reflection.MethodInvoker;

@RequiredArgsConstructor
public class OptionalInjections {

    private final BeanLookup<ManagedBean> beanLookup;
    private final MethodInvoker methodInvoker;

    public void doInject() {
        BeanInjection beanInjection = new BeanInjection(
                new FieldInjection(beanLookup),
                new SetterInjection(beanLookup, methodInvoker)
        );
        beanLookup.getAll().forEach(registeredBean -> beanInjection.inject(registeredBean.getBean()));
    }
}
