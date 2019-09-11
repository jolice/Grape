package me.riguron.grape.inject;

import me.riguron.grape.bean.lookup.BeanLookup;
import me.riguron.grape.bean.lookup.StandardLookupParams;
import me.riguron.grape.bean.registry.ManagedBean;
import org.junit.jupiter.api.Test;

import javax.inject.Inject;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class FieldInjectionTest {

    private final BeanLookup<ManagedBean> lookup = mock(BeanLookup.class);

    @Test
    void inject() throws NoSuchFieldException {


        putBean("str val", "a");
        putBean(1, "b");
        putBean(1.5, "d");


        Sample sample = new Sample();
        FieldInjection fieldInjection = new FieldInjection(lookup);

        fieldInjection.inject(sample);



        assertEquals("str val", sample.a);
        assertEquals(1, sample.b);
        assertEquals(1.5, sample.d);
    }

    private void putBean(Object o, String name) throws NoSuchFieldException {

        when(lookup.lookup(eq(o.getClass()), any(), eq(new StandardLookupParams(Sample.class.getDeclaredField(name)))))
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