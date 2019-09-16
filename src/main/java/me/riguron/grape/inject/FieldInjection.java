package me.riguron.grape.inject;

import lombok.RequiredArgsConstructor;
import me.riguron.grape.bean.lookup.BeanLookup;
import me.riguron.grape.bean.lookup.StandardLookupParams;
import me.riguron.grape.bean.registry.ManagedBean;

import javax.inject.Inject;
import java.lang.reflect.Field;

@RequiredArgsConstructor
public class FieldInjection implements Injection {

    private final BeanLookup<ManagedBean> beanRegistry;

    @Override
    public void inject(Object o) {
        for (Field field : o.getClass().getDeclaredFields()) {
            if (field.isAnnotationPresent(Inject.class)) {
                if (!field.isAccessible()) {
                    field.setAccessible(true);
                }
                Object fieldValue = beanRegistry.lookup(field.getType(), o.getClass(), new StandardLookupParams(field)).getBeanInstance();
                try {
                    field.set(o, fieldValue);
                } catch (IllegalAccessException e) {
                    throw new IllegalStateException("Failed to inject dependency through the field", e);
                }
            }
        }
    }
}
