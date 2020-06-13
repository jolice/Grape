package com.github.jolice.inject;

import com.github.jolice.bean.lookup.BeanLookup;
import com.github.jolice.bean.lookup.StandardLookupParams;
import com.github.jolice.bean.registry.ManagedBean;
import lombok.RequiredArgsConstructor;

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
