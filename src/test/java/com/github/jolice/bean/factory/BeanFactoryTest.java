package com.github.jolice.bean.factory;

import com.github.jolice.bean.registry.ManagedBean;
import com.github.jolice.dependency.Dependency;
import com.github.jolice.loader.BeanRegistration;
import com.github.jolice.provider.InstanceProvider;
import com.github.jolice.bean.BeanDefinition;
import com.github.jolice.bean.lookup.BeanLookup;
import org.junit.jupiter.api.Test;

import java.util.*;

import static io.riguron.mocks.Mocks.*;
import static io.riguron.mocks.matcher.ArgumentMatchers.*;

public class BeanFactoryTest {

    @Test
    void createBeans() {

        Map<Class<?>, ManagedBean> simpleStorage = new HashMap<>();


        BeanDefinition b = new BeanDefinition(B.class, new InstanceProvider() {
            @Override
            public Object createBean(List<Object> dependencies) {
                return new B((A) dependencies.get(0));
            }

            @Override
            public List<Dependency> getDependencies() {
                return Collections.singletonList(new Dependency(
                        A.class,
                        A.class
                ));
            }
        }, B.class);


        BeanDefinition a = new BeanDefinition(A.class, new InstanceProvider() {
            @Override
            public Object createBean(List<Object> dependencies) {
                return new A(5);
            }

            @Override
            public List<Dependency> getDependencies() {
                return Collections.emptyList();
            }
        }, A.class);

        BeanLookup<ManagedBean> lookup = mock(BeanLookup.class);
        BeanRegistration<ManagedBean> registration = mock(BeanRegistration.class);

        BeanFactory beanFactory = new BeanFactory(
                lookup, registration, Arrays.asList(a, b)
        );


        when(lookup.lookup(any(), any(), any())).thenAnswer(
                invocationOnMock -> {
                    Class<?> type = invocationOnMock.getArgument(0);
                    return simpleStorage.get(type);
                });

        doAnswer(invocationOnMock -> {
            simpleStorage.put(invocationOnMock.getArgument(0),
                    invocationOnMock.getArgument(1));
            return null;
        }).when(registration).register(any(), any());


        beanFactory.createBeans();


        verify(registration).register(eq(A.class), argThat(o -> {
            A a1 = (A) o.getBeanInstance();
            return a1.x == 5;
        }));

        verify(registration).register(eq(B.class), argThat(o -> {
            B b1 = (B) o.getBeanInstance();
            return b1.a != null && b1.a.x == 5;
        }));

    }

    static class A {

        private int x;

        A(int x) {
            this.x = x;
        }
    }

    static class B {

        private final A a;

        B(A a) {
            this.a = a;
        }
    }
}