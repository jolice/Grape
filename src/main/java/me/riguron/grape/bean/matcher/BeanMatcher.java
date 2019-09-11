package me.riguron.grape.bean.matcher;

import me.riguron.grape.bean.BeanMeta;

/**
 * An IoC container may contain multiple beans of the same type. BeanMatcher
 * is intended for specifying the bean to be obtained from the container when
 * there are multiple candidates.
 */
public interface BeanMatcher {

    /**
     * Search criteria. This method is used by the part of IoC container that is immediately responsible for
     * bean storing and obtaining. It is a predicate method that is involved in the bean search.
     *
     * @param registeredBean bean compared with the queried
     * @return whether the requested bean matches the bean stored in the container.
     */
    boolean matches(BeanMeta registeredBean);


}
