package me.riguron.grape.bean;

import me.riguron.grape.bean.lookup.BeanLookup;
import me.riguron.grape.bean.lookup.LookupParams;
import me.riguron.grape.bean.registry.Registry;
import me.riguron.grape.provider.InstanceProvider;
import org.junit.jupiter.api.Test;

import java.lang.reflect.AnnotatedElement;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class LookupParamsTest {

    @Test
    void whenNotPolymorphicType() {
        putAndLookup(Integer.class, Integer.class);
    }

    @Test
    void whenPolymorphicType() {
        putAndLookup(Implementation.class, Interface.class);
    }

    private void putAndLookup(Class<?> storeType, Class<?> queryType) {
        Registry<BeanDefinition> registry = mock(Registry.class);

        BeanDefinition beanDefinition = mock(BeanDefinition.class);
        when(beanDefinition.getBeanClass()).thenAnswer(invocationOnMock -> storeType);

        when(registry.get(
                any()
        )).thenReturn(
                new BeanDefinition(
                        storeType,
                        mock(InstanceProvider.class),
                        mock(AnnotatedElement.class)
                )
        );

        BeanLookup beanLookup = new BeanLookup(registry);

        assertEquals(storeType, beanLookup.lookup(
                queryType, String.class, mock(LookupParams.class)
        ).getBeanClass());
    }

    interface Interface {
    }

    private class Implementation implements Interface {
    }


}