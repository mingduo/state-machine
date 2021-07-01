package com.mingduo.springboot.statemache.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.Message;
import org.springframework.statemachine.StateContext;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.listener.StateMachineListenerAdapter;
import org.springframework.statemachine.state.State;
import org.springframework.statemachine.transition.Transition;

import java.util.Optional;

/**
 * @author : weizc
 * @since 2021/6/29
 */
@Slf4j
public class MyStateMachineListenerAdapter<S, E> extends StateMachineListenerAdapter<S, E> {
    @Override
    public void stateChanged(State<S, E> from, State<S, E> to) {
        log.warn("State change from:{} to:{} ", Optional.ofNullable(from).map(State::getId)
                        .orElse(null),
                to.getId());

    }

    @Override
    public void stateContext(StateContext<S, E> stateContext) {
        print();

    }


    @Override
    public void stateEntered(State<S, E> state) {
        print();

    }

    @Override
    public void stateExited(State<S, E> state) {
        print();
    }

    @Override
    public void eventNotAccepted(Message<E> event) {
        print();
    }

    @Override
    public void transition(Transition<S, E> transition) {
        print();
    }

    @Override
    public void transitionStarted(Transition<S, E> transition) {
        print();
    }

    @Override
    public void transitionEnded(Transition<S, E> transition) {
        print();
    }

    @Override
    public void stateMachineStarted(StateMachine<S, E> stateMachine) {
        print();
    }

    @Override
    public void stateMachineStopped(StateMachine<S, E> stateMachine) {
        print();
    }

    @Override
    public void stateMachineError(StateMachine<S, E> stateMachine, Exception exception) {
        print();
    }

    @Override
    public void extendedStateChanged(Object key, Object value) {
        print();
    }

    private void print() {
        StackTraceElement[] stackTrace = new Throwable().getStackTrace();
        log.info(stackTrace[1].getMethodName());
    }
}
