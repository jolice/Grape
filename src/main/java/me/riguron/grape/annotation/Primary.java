package me.riguron.grape.annotation;

import java.lang.annotation.*;

/**
 * This annotation is intended for resolving ambiguous dependency issues.
 * If there are multiple beans of some type, the one annotated will be chosen.
 */
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Primary {

}