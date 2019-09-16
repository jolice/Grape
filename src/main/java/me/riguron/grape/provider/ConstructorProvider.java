package me.riguron.grape.provider;


import me.riguron.grape.dependency.Dependency;
import me.riguron.grape.exception.reflection.InvocationException;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;
import java.lang.reflect.Parameter;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class ConstructorProvider implements InstanceProvider {

    private final Constructor<?> constructor;

    public ConstructorProvider(Constructor<?> constructor) {
        this.constructor = constructor;
    }

    @Override
    public Object createBean(List<Object> dependencies) {
        try {
            if (Modifier.isAbstract(constructor.getDeclaringClass().getModifiers())) {
                throw new InvocationException("Instantiable component can not be abstract");
            }
            return constructor.newInstance(dependencies.toArray());
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
            throw new InvocationException("Failed to create class instance - " + constructor.getDeclaringClass() + ", parameters - " + dependencies);
        }
    }

    @Override
    public List<Dependency> getDependencies() {
        return Arrays.stream(constructor.getParameters()).map(x -> new Dependency(
                x.getType(), x
        )).collect(Collectors.toList());
    }

    @Override
    public String toString() {
        return new StringBuilder()
                .append("Constructor")
                .append("\n")
                .append("Parameters: ")
                .append(Arrays.toString(constructor.getParameterTypes()))
                .append("\n")
                .append("Parameter annotations: ").append(Arrays.stream(constructor.getParameters()).filter(x -> x.getDeclaredAnnotations().length > 0).map(Parameter::getDeclaredAnnotations).collect(Collectors.toList()))
                .toString();
    }
}
