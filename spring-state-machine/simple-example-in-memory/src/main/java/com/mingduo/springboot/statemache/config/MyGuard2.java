package com.mingduo.springboot.statemache.config;

import com.mingduo.springboot.statemache.domain.Events;
import org.springframework.statemachine.StateContext;
import org.springframework.statemachine.guard.Guard;

/**
 * @author : weizc
 * @since 2021/6/29
 */
public class MyGuard2 implements Guard<String, Events> {


    @Override
    public boolean evaluate(StateContext<String, Events> context) {
        return false;
    }
}
