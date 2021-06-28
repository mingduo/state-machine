package com.mingduo.springboot.statemache.controller;

import com.mingduo.springboot.statemache.domain.Events;
import com.mingduo.springboot.statemache.domain.States;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.StateMachineContext;
import org.springframework.statemachine.StateMachinePersist;
import org.springframework.statemachine.service.StateMachineService;
import org.springframework.ui.Model;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.EnumSet;
import java.util.List;

/**
 * @author : weizc
 * @apiNode:
 * @since 2020/1/17
 */
@RequestMapping("/state")
@RestController
public class StateController {

    public final static String MACHINE_ID_1 = "datajpapersist1";
    public final static String MACHINE_ID_2 = "datajpapersist2";

    private final static String[] MACHINES = new String[]{MACHINE_ID_1, MACHINE_ID_2};

    private StateMachine<States, Events> currentStateMachine;

    @Autowired
    private StateMachineService<States, Events> stateMachineService;


    @Autowired
    private StateMachinePersist<States, Events, String> stateMachinePersist;

    @GetMapping("/e1")
    public void changeState(@RequestParam(value = "machine", defaultValue = MACHINE_ID_1) String machineId) {
        getState(machineId).sendEvent(Events.E1);
    }

    @GetMapping("/e2")
    public void changeState2(@RequestParam(value = "machine", defaultValue = MACHINE_ID_1) String machineId) {
        getState(machineId).sendEvent(Events.E2);

    }

    @GetMapping("/e3")
    public void changeState3(@RequestParam(value = "machine", defaultValue = MACHINE_ID_1) String machineId) {
        getState(machineId).sendEvent(Events.E3);

    }


    @GetMapping("/state")
    public StateMachine getState(@RequestParam(value = "machine", defaultValue = MACHINE_ID_1) String machineId) {
        try {
            return getStateMachine(machineId);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    private synchronized StateMachine<States, Events> getStateMachine(String machineId) throws Exception {
        if (currentStateMachine == null) {
            currentStateMachine = stateMachineService.acquireStateMachine(machineId);
            currentStateMachine.start();
        } else if (!ObjectUtils.nullSafeEquals(currentStateMachine.getId(), machineId)) {
            stateMachineService.releaseStateMachine(currentStateMachine.getId());
            currentStateMachine.stop();
            currentStateMachine = stateMachineService.acquireStateMachine(machineId);
            currentStateMachine.start();
        }
        return currentStateMachine;
    }


    @RequestMapping("/state")
    public StateMachineContext feedAndGetStates(
            @RequestParam(value = "events", required = false) List<Events> events,
            @RequestParam(value = "machine", required = false, defaultValue = MACHINE_ID_1) String machine,
            Model model) throws Exception {
        StateMachine<States, Events> stateMachine = getStateMachine(machine);
        if (events != null) {
            for (Events event : events) {
                stateMachine.sendEvent(MessageBuilder.withPayload(event).build());
            }
        }
        StateMachineContext<States, Events> stateMachineContext = stateMachinePersist.read(machine);
        model.addAttribute("allMachines", MACHINES);
        model.addAttribute("machine", machine);
        model.addAttribute("allEvents", getEvents());
        model.addAttribute("context", stateMachineContext != null ? stateMachineContext.toString() : "");
        return stateMachineContext;
    }

    private String createMessages(List<String> messages) {
        StringBuilder buf = new StringBuilder();
        for (String message : messages) {
            buf.append(message);
            buf.append("\n");
        }
        return buf.toString();
    }

    private Events[] getEvents() {
        return EnumSet.allOf(Events.class).toArray(new Events[0]);
    }
}
