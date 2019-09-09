package me.riguron.grape.reflection;

import javax.inject.Inject;
import java.lang.reflect.Constructor;
import java.util.Optional;

public class ConstructorLookup {

    public Optional<Constructor<?>> getInjectConstructor(Class<?> type) {
        for (Constructor<?> constructor : type.getDeclaredConstructors()) {
            if (constructor.isAnnotationPresent(Inject.class)) {
                return Optional.of(constructor);
            }
        }
        return Optional.empty();
    }

    public Optional<Constructor<?>> getEmptyConstructor(Class<?> type) {
        try {
            return Optional.ofNullable(type.getConstructor());
        } catch (NoSuchMethodException e) {
            return Optional.empty();
        }
    }


}
