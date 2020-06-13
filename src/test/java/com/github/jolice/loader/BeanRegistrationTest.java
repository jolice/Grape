package com.github.jolice.loader;

import com.github.jolice.bean.registry.Registry;
import com.github.jolice.bean.BeanDefinition;
import org.junit.jupiter.api.Test;

import static io.riguron.mocks.Mocks.verify;
import static io.riguron.mocks.matcher.ArgumentMatchers.any;
import static io.riguron.mocks.matcher.ArgumentMatchers.eq;
import static io.riguron.mocks.Mocks.mock;

class BeanRegistrationTest {

    @Test
    void whenRegisterNonImplementorThenTypeRegistered() {


        Registry<BeanDefinition> registry = mock(Registry.class);

        BeanRegistration beanRegistration
                = new BeanRegistration(registry);


        beanRegistration.register(DoesNotImplement.class,
                mock(BeanDefinition.class));

        verify(registry).put(eq(DoesNotImplement.class), any());
    }

    @Test
    void whenRegisterInterfaceImplementationThenSupertypeRegistered() {

        Registry<BeanDefinition> registry = mock(Registry.class);

        BeanRegistration beanRegistration
                = new BeanRegistration(registry);


        beanRegistration.register(Impl.class, mock(BeanDefinition.class)
        );

        verify(registry).put(eq(Interface.class), any());

    }

    static class DoesNotImplement {
    }

    interface Interface {

    }

    static class Impl implements Interface {

    }

}