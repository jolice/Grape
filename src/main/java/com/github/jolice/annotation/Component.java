package com.github.jolice.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Indicates that an annotated class is managed by IoC container.
 * Annotated classes are revealed during the component scanning and
 * automatically added to the container, so that they become managed
 * and may be used for the further injection.
 * <p>
 * This is an alternative of programmatic bean declaration (i.e in Java
 * configuration). It can be used with any bean annotations (e.g Named).
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface Component {


}