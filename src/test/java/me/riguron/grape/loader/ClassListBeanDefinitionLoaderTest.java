package me.riguron.grape.loader;

import me.riguron.grape.bean.BeanDefinition;
import me.riguron.grape.reflection.ConstructorLookup;
import org.junit.jupiter.api.Test;

import javax.inject.Inject;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class ClassListBeanDefinitionLoaderTest {


    @Test
    void load() {


        ClassListBeanDefinitionLoader classListBeanDefinitionLoader =
                new ClassListBeanDefinitionLoader(
                        new ConstructorLookup(),
                        new HashSet<>(Arrays.asList(A.class, B.class, C.class))
                );


        Map<Class<?>, BeanDefinition> definitions = classListBeanDefinitionLoader.load().stream().collect(Collectors.toMap(BeanDefinition::getBeanClass, Function.identity()));

        assertEquals(3, definitions.size());

        final BeanDefinition first = definitions.get(B.class);

        assertNotNull(first);
        assertEquals(B.class, first.getBeanClass());
        assertEquals(1, first.getConstructor().getDependencies().size());


        final BeanDefinition second = definitions.get(C.class);

        assertNotNull(second);
        assertEquals(C.class, second.getBeanClass());
        assertEquals(2, second.getConstructor().getDependencies().size());

        final BeanDefinition third = definitions.get(A.class);

        assertNotNull(third);
        assertEquals(A.class, third.getBeanClass());
        assertEquals(0, third.getConstructor().getDependencies().size());

    }



    public static class A {

    }

    public static class B {

        private final A a;

        @Inject
        public B(A a) {
            this.a = a;
        }
    }

    public static class C {

        private final A a;
        private final B b;

        @Inject
        public C(A a, B b) {
            this.a = a;
            this.b = b;
        }
    }
}