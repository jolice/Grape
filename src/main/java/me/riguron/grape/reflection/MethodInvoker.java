package me.riguron.grape.reflection;

import me.riguron.grape.exception.reflection.InvocationException;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class MethodInvoker {

    public Object invoke(Object object, Method method, Object... parameters) {
        try {
            return method.invoke(object, parameters);
        } catch (IllegalAccessException e) {
            throw new IllegalArgumentException("Method must be public in order to be invoked");
        } catch (InvocationTargetException e) {
            throw new InvocationException(String.format("Failed to call method '%s' of class '%s'", method.getName(), object.getClass().getName()), e);
        }
    }
}
