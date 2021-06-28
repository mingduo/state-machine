package com.mingduo.springboot.statemache.controller;

import com.mingduo.springboot.statemache.domain.Events;
import com.mingduo.springboot.statemache.domain.States;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.statemachine.StateMachine;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author : weizc
 * @apiNode:
 * @since 2020/1/17
 */
@RequestMapping("/state")
@RestController
public class StateController {

    @Autowired
    StateMachine<States, Events> stateMachine;

    @GetMapping("/e1")
    public void changeState() {
        stateMachine.sendEvent(Events.E1);
    }

    @GetMapping("/e2")
    public void changeState2() {
        stateMachine.sendEvent(Events.E2);

    }

    @GetMapping("/e3")
    public void changeState3() {
        stateMachine.sendEvent(Events.E3);

    }


    @GetMapping("/state")
    public StateMachine getState(String id) {
        return stateMachine;
    }
}
