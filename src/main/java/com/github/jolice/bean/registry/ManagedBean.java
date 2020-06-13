package com.github.jolice.bean.registry;

import com.github.jolice.bean.BeanMeta;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;

import java.lang.reflect.AnnotatedElement;

@Data
@Setter(AccessLevel.NONE)
public class ManagedBean implements BeanMeta {

    private final Object beanInstance;
    private final AnnotatedElement annotationData;

    @Override
    public Class<?> getBeanClass() {
        return beanInstance.getClass();
    }
}
