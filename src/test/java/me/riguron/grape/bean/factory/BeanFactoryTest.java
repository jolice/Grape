package me.riguron.grape.bean.factory;

import me.riguron.grape.bean.BeanDefinition;
import me.riguron.grape.bean.lookup.BeanLookup;
import me.riguron.grape.bean.registry.ManagedBean;
import me.riguron.grape.dependency.Dependency;
import me.riguron.grape.loader.BeanRegistration;
import me.riguron.grape.provider.InstanceProvider;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatcher;

import java.util.*;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;

class BeanFactoryTest {

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
                invocationOnMock -> simpleStorage.get(invocationOnMock.getArgumentAt(0, Class.class)));

        doAnswer(invocationOnMock -> {
            simpleStorage.put(invocationOnMock.getArgumentAt(0, Class.class),
                    invocationOnMock.getArgumentAt(1, ManagedBean.class));
            return null;
        }).when(registration).register(any(), any());


        beanFactory.createBeans();


        verify(registration).register(eq(A.class), argThat(new ArgumentMatcher<ManagedBean>() {
            @Override
            public boolean matches(Object o) {
                ManagedBean managedBean = (ManagedBean) o;
                A a = (A) managedBean.getBeanInstance();
                return a.x == 5;
            }
        }));

        verify(registration).register(eq(B.class), argThat(new ArgumentMatcher<ManagedBean>() {
            @Override
            public boolean matches(Object o) {
                ManagedBean managedBean = (ManagedBean) o;
                B b = (B) managedBean.getBeanInstance();
                return b.a != null && b.a.x == 5;
            }
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