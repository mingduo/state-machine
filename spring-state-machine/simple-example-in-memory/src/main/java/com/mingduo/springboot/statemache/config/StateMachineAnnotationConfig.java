package com.mingduo.springboot.statemache.config;

import org.springframework.messaging.Message;
import org.springframework.statemachine.ExtendedState;
import org.springframework.statemachine.StateContext;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.annotation.*;

import java.util.Map;

/**
 * @author : weizc
 * @apiNode:
 * @since 2020/1/19
 */
@WithStateMachine(name = "xxx")
public class StateMachineAnnotationConfig {


    @OnTransition
    public void anyTransition(
            @EventHeaders Map<String, Object> headers,
            StateContext<String, String> stateContext,
            // @EventHeader("myheader1") Object myheader1,
            //  @EventHeader(name = "myheader2", required = false) String myheader2,
            ExtendedState extendedState,
            StateMachine<String, String> stateMachine,
            Message<String> message,
            Exception e) {
        System.out.println("anyTransition with annotation");
    }

    @OnStateChanged(source = "S1", target = "S2")
    public void fromS1ToS2() {
        System.out.println("from s1 to s2");
    }


    @OnEventNotAccepted(event = "E1")
    public void e1EventNotAccepted() {
        System.out.println("e1EventNotAccepted");
    }


    @OnExtendedStateChanged()
    public void key1Changed() {
        System.out.println("key1Changed");

    }
}


