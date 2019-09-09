package me.riguron.grape;

import me.riguron.grape.bean.matcher.AnyMatcher;
import me.riguron.grape.bean.matcher.policy.OptionalPolicy;
import me.riguron.grape.bean.registry.BeanQuery;
import me.riguron.grape.bootstrap.Grape;
import me.riguron.grape.bootstrap.GrapeConfiguration;

import java.util.Collections;
import java.util.HashSet;

public class Test {


    public static void main(String[] args) {

        Grape grape = new Grape(
                new GrapeConfiguration()
                        .scan("me.riguron")
                        .configurations(new HashSet<>(Collections.singletonList(new TestCfg()))));



        A a = grape.getBean(new BeanQuery(A.class, new AnyMatcher(), new OptionalPolicy()));
        B b = grape.getBean(new BeanQuery(B.class, new AnyMatcher(), new OptionalPolicy()));
        C c = grape.getBean(new BeanQuery(C.class, new AnyMatcher(), new OptionalPolicy()));

    }

}
