package com.github.jolice.bootstrap;

import com.github.jolice.bean.registry.ManagedBean;
import com.github.jolice.inject.BeanInjection;
import com.github.jolice.inject.MethodInjection;
import com.github.jolice.reflection.MethodInvoker;
import lombok.RequiredArgsConstructor;
import com.github.jolice.bean.lookup.BeanLookup;
import com.github.jolice.inject.FieldInjection;

@RequiredArgsConstructor
public class OptionalInjections {

    private final BeanLookup<ManagedBean> beanLookup;
    private final MethodInvoker methodInvoker;

    public void doInject() {
        BeanInjection beanInjection = new BeanInjection(
                new FieldInjection(beanLookup),
                new MethodInjection(beanLookup, methodInvoker)
        );
        beanLookup.getAll().forEach(registeredBean -> beanInjection.inject(registeredBean.getBeanInstance()));
    }
}
