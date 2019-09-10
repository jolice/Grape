package me.riguron.grape.provider;

import lombok.RequiredArgsConstructor;
import me.riguron.grape.dependency.Dependency;
import me.riguron.grape.reflection.MethodInvoker;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class MethodProvider implements InstanceProvider {

    private final Method beanDeclaration;
    private final Object configuration;
    private final MethodInvoker methodInvoker;

    @Override
    public Object createBean(List<Object> dependencies) {
        return methodInvoker.invoke(configuration, beanDeclaration, dependencies.toArray());
    }

    @Override
    public List<Dependency> getDependencies() {
        return Arrays.stream(beanDeclaration.getParameters())
                .map(x -> new Dependency(x.getType(), x))
                .collect(Collectors.toList());
    }

    @Override
    public String toString() {
        return new StringBuilder()
                .append("Method, ")
                .append("parameters: ")
                .append(Arrays.toString(beanDeclaration.getParameterTypes()))
                .append("\n")
                .append("parameter annotations: ").append(Arrays.stream(beanDeclaration.getParameters()).filter(x -> x.getDeclaredAnnotations().length > 0).map(Parameter::getDeclaredAnnotations).collect(Collectors.toList()))
                .toString();
    }

}
