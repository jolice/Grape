package me.riguron.grape.bean.registry;

import me.riguron.grape.annotation.Primary;
import me.riguron.grape.bean.matcher.policy.BindingPolicy;
import me.riguron.grape.bean.matcher.policy.OptionalPolicy;
import me.riguron.grape.exception.ExceptionProvider;
import me.riguron.grape.exception.dependency.AmbiguousDependencyException;
import me.riguron.grape.exception.dependency.UnsatisfiedDependencyException;
import org.junit.jupiter.api.Test;

import java.lang.reflect.AnnotatedElement;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class RegistryTest {

    @Test
    void plainPutAndGet() {

        Registry<ManagedBean> registry =
                new Registry<>();

        registry.put(
                Integer.class,
                new ManagedBean(
                        3,
                        mock(AnnotatedElement.class)
                )
        );

        ManagedBean managedBean = registry.get(
                new BeanQuery(
                        Integer.class, registeredBean -> true, new OptionalPolicy()
                ));

        assertNotNull(managedBean);

        assertEquals(3, managedBean.getBeanInstance());

    }

    @Test
    void whenMultipleItemsFound() {
        Registry<ManagedBean> registry =
                new Registry<>();

        registry.put(Integer.class, new ManagedBean(3, mock(AnnotatedElement.class)));
        AnnotatedElement element = mock(AnnotatedElement.class);

        when(element.isAnnotationPresent(Primary.class)).thenReturn(true);
        registry.put(Integer.class, new ManagedBean(5, element));


        ManagedBean managedBean = registry.get(new BeanQuery(Integer.class, bean -> true, new BindingPolicy() {
            @Override
            public boolean isMandatory() {
                return true;
            }

            @Override
            public ExceptionProvider unsatisfiedError() {
                throw new UnsupportedOperationException();
            }
        }));

        assertEquals(5, managedBean.getBeanInstance());

    }

    @Test

    void whenNoData() {
        Registry<ManagedBean> registry =
                new Registry<>();

        assertThrows(UnsatisfiedDependencyException.class, () -> registry.get(
                new BeanQuery(
                        Integer.class, registeredBean -> true, new OptionalPolicy()
                )));
    }

    @Test
    void whenNoDataAndPolicyIsMandatory() {
        Registry<ManagedBean> registry =
                new Registry<>();

        assertThrows(SampleError.class, () -> registry.get(
                new BeanQuery(
                        Integer.class, registeredBean -> true, new BindingPolicy() {
                    @Override
                    public boolean isMandatory() {
                        return true;
                    }

                    @Override
                    public ExceptionProvider unsatisfiedError() {
                        return SampleError::new;
                    }
                }
                )));
    }

    @Test
    void whenNotFoundAndBindingIsMandatory() {
        Registry<ManagedBean> registry =
                new Registry<>();

        registry.put(Integer.class, new ManagedBean(3, mock(AnnotatedElement.class)));

        assertThrows(SampleError.class,
                () -> registry.get(
                        new BeanQuery(Integer.class, registeredBean -> false, new BindingPolicy() {
                            @Override
                            public boolean isMandatory() {
                                return true;
                            }

                            @Override
                            public ExceptionProvider unsatisfiedError() {
                                return SampleError::new;
                            }
                        })
                ));
    }

    @Test
    void whenAmbiguity() {

        Registry<ManagedBean> registry =
                new Registry<>();


        registry.put(Integer.class, new ManagedBean(3, mock(AnnotatedElement.class)));
        registry.put(Integer.class, new ManagedBean(3, mock(AnnotatedElement.class)));

        assertThrows(AmbiguousDependencyException.class,
                () -> registry.get(
                        new BeanQuery(
                                Integer.class,
                                registeredBean -> false,
                                new BindingPolicy() {
                                    @Override
                                    public boolean isMandatory() {
                                        return false;
                                    }

                                    @Override
                                    public ExceptionProvider unsatisfiedError() {
                                        throw new UnsupportedOperationException();
                                    }
                                }
                        )
                ));


    }

    @Test
    void whenAmbiguityButPrimaryExists() {

        Registry<ManagedBean> registry =
                new Registry<>();

        AnnotatedElement element = mock(AnnotatedElement.class);

        when(element.isAnnotationPresent(Primary.class)).thenReturn(true);

        registry.put(Integer.class, new ManagedBean(3, element));
        registry.put(Integer.class, new ManagedBean(9, mock(AnnotatedElement.class)));

        final ManagedBean managedBean = registry.get(
                new BeanQuery(
                        Integer.class,
                        registeredBean -> false,
                        new BindingPolicy() {
                            @Override
                            public boolean isMandatory() {
                                return false;
                            }

                            @Override
                            public ExceptionProvider unsatisfiedError() {
                                throw new UnsupportedOperationException();
                            }
                        }
                )
        );

        assertEquals(3,
                managedBean.getBeanInstance());

    }


    @Test
    void whenPolicyIsOptionalAndOnlyOneExists() {

        Registry<ManagedBean> registry =
                new Registry<>();


        registry.put(Integer.class, new ManagedBean(3, mock(AnnotatedElement.class)));

        final ManagedBean managedBean = registry.get(
                new BeanQuery(
                        Integer.class,
                        registeredBean -> false,
                        new BindingPolicy() {
                            @Override
                            public boolean isMandatory() {
                                return false;
                            }

                            @Override
                            public ExceptionProvider unsatisfiedError() {
                                throw new UnsupportedOperationException();
                            }
                        }
                )
        );

        assertEquals(3,
                managedBean.getBeanInstance());
    }

    private static class SampleError extends RuntimeException {

    }

}