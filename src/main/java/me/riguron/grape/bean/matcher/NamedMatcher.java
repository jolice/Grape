package me.riguron.grape.bean.matcher;

import me.riguron.grape.bean.BeanMeta;

import javax.inject.Named;
import java.lang.reflect.AnnotatedElement;

/**
 * Represents a name search criteria. This matcher is used
 * for the named injection.
 */
public class NamedMatcher implements BeanMatcher {

    private Named injectionPoint;

    public NamedMatcher(Named injectionPoint) {
        this.injectionPoint = injectionPoint;
    }

    @Override
    public boolean matches(BeanMeta registeredBean) {
        return injectionPoint != null && registeredBean.getAnnotationData().isAnnotationPresent(Named.class) && getNameValue(registeredBean.getAnnotationData()).equalsIgnoreCase(injectionPoint.value());
    }

    private String getNameValue(AnnotatedElement element) {
        return element.getDeclaredAnnotation(Named.class).value();
    }

    @Override
    public String toString() {
        return String.format("Named matcher [%s]", injectionPoint != null ? "value = " + injectionPoint.value() : "");
    }
}
