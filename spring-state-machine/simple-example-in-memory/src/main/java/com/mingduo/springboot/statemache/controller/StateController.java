package com.mingduo.springboot.statemache.controller;

import com.mingduo.springboot.statemache.domain.Events;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.statemachine.StateMachine;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author : weizc
 * @apiNode:
 * @since 2020/1/17
 */
@Slf4j
@RequestMapping("/state")
@RestController
public class StateController {

    @Autowired
    StateMachine<String, Events> stateMachine;

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

    @GetMapping("/e4")
    public void changeState4() {
        stateMachine.sendEvent(Events.E4);
    }

    @GetMapping("/e5")
    public void changeState5() {
        Message<Events> message = MessageBuilder.withPayload(Events.E5).build();
        stateMachine.sendEvent(message);
    }


    @GetMapping("/state")
    public String getState(String id) {
        log.info("stateMachine: {} ", stateMachine.toString());
        return stateMachine.toString();
    }
}
