package me.riguron.grape.bean;

import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;
import me.riguron.grape.provider.InstanceProvider;

import java.lang.reflect.AnnotatedElement;
import java.util.Arrays;

@Data
@Setter(AccessLevel.NONE)
public class BeanDefinition implements BeanMeta {

    private final Class<?> beanClass;
    private final InstanceProvider constructor;
    private final AnnotatedElement annotationData;

    @Override
    public String toString() {
        return String.format("[BeanDefinition/%s] annotations = %s, provider = \n%s", beanClass, Arrays.toString(annotationData.getDeclaredAnnotations()), constructor);
    }

    @Override
    public Class<?> getBeanClass() {
        return beanClass;
    }
}