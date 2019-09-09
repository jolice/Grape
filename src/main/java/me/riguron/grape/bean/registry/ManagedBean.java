package me.riguron.grape.bean.registry;

import lombok.Value;
import me.riguron.grape.bean.BeanMeta;

import java.lang.reflect.AnnotatedElement;

@Value
public class ManagedBean implements BeanMeta {

    private Object bean;
    private AnnotatedElement annotationData;

    @Override
    public Class<?> getBeanClass() {
        return bean.getClass();
    }
}
