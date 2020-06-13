package com.github.jolice.inject;

import com.github.jolice.bean.registry.ManagedBean;
import com.github.jolice.bean.lookup.BeanLookup;
import org.junit.jupiter.api.Test;

import javax.inject.Inject;

import static io.riguron.mocks.Mocks.when;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static io.riguron.mocks.matcher.ArgumentMatchers.any;
import static io.riguron.mocks.matcher.ArgumentMatchers.eq;
import static io.riguron.mocks.Mocks.mock;

class FieldInjectionTest {

    private final BeanLookup<ManagedBean> lookup = mock(BeanLookup.class);

    @Test
    void inject() throws NoSuchFieldException {


        putBean("str val");
        putBean(1);
        putBean(1.5);


        Sample sample = new Sample();
        FieldInjection fieldInjection = new FieldInjection(lookup);

        fieldInjection.inject(sample);



        assertEquals("str val", sample.a);
        assertEquals(1, sample.b.intValue());
        assertEquals(1.5, sample.d.doubleValue());
    }

    private void putBean(Object o) {


        when(lookup.lookup(eq(o.getClass()), any(), any()))
                .thenReturn(
                        createBean(o)
                );

    }

    private ManagedBean createBean(Object value) {
        return new ManagedBean(value, null);
    }

    static class Sample {

        @Inject
        private String a;

        @Inject
        private Integer b;

        @Inject
        private Double d;

    }
}