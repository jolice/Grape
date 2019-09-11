package me.riguron.grape.inject;

import lombok.RequiredArgsConstructor;
import me.riguron.grape.bean.lookup.BeanLookup;
import me.riguron.grape.bean.lookup.StandardLookupParams;
import me.riguron.grape.bean.registry.ManagedBean;
import me.riguron.grape.reflection.MethodInvoker;

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
