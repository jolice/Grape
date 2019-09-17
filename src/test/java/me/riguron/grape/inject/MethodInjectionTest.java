package me.riguron.grape.inject;

import me.riguron.grape.bean.lookup.BeanLookup;
import me.riguron.grape.bean.registry.ManagedBean;
import me.riguron.grape.reflection.MethodInvoker;
import org.junit.jupiter.api.Test;

import javax.inject.Inject;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class MethodInjectionTest {

    private final BeanLookup<ManagedBean> lookup = mock(BeanLookup.class);

    @Test
    void inject() throws NoSuchMethodException {

        putBean("x");
        putBean(1);
        putBean(1.5D);

        Sample sample = new Sample();
        MethodInjection methodInjection =  new MethodInjection(lookup, new MethodInvoker());
        methodInjection.inject(sample);


        assertEquals("x", sample.x);
        assertEquals(1, sample.i);
        assertEquals(1.5D, sample.d);
    }

    public static class Sample {

        private String x;
        private Integer i;
        private Double d;

        @Inject
        public void inject(String x, Integer i) {
            this.x = x;
            this.i = i;
        }

        @Inject
        public void injMethod(Double d) {
            this.d = d;
        }

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

}