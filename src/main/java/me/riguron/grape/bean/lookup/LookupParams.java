package me.riguron.grape.bean.lookup;

import me.riguron.grape.bean.matcher.BeanMatcher;
import me.riguron.grape.bean.matcher.policy.BindingPolicy;

public interface LookupParams {

    BeanMatcher beanMatcher();

    BindingPolicy bindingPolicy();


}
