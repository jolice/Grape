package me.riguron.grape.bean.registry;

import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;
import me.riguron.grape.bean.BeanMeta;

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
