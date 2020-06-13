package com.github.jolice.inject;

import com.github.jolice.bean.lookup.StandardLookupParams;
import com.github.jolice.bean.registry.ManagedBean;
import com.github.jolice.reflection.MethodInvoker;
import lombok.RequiredArgsConstructor;
import com.github.jolice.bean.lookup.BeanLookup;

import javax.inject.Inject;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.Parameter;

@RequiredArgsConstructor
public class MethodInjection implements Injection {

    private final BeanLookup<ManagedBean> beanRegistry;
    private final MethodInvoker methodInvoker;

    @Override
    public void inject(Object o) {
        for (Method method : o.getClass().getDeclaredMethods()) {
            if (Modifier.isPublic(method.getModifiers()) && method.isAnnotationPresent(Inject.class)) {
                Object[] targetBeanParams = new Object[method.getParameterCount()];
                Parameter[] parameters = method.getParameters();
                for (int i = 0; i < parameters.length; i++) {
                    targetBeanParams[i] = beanRegistry.lookup(parameters[i].getType(), o.getClass(), new StandardLookupParams(parameters[i])).getBeanInstance();
                }

                methodInvoker.invoke(o, method, targetBeanParams);
            }
        }
    }
}
