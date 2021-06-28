package com.mingduo.springboot.statemache.config;

import com.mingduo.springboot.statemache.domain.Events;
import com.mingduo.springboot.statemache.domain.States;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.statemachine.config.EnableStateMachineFactory;
import org.springframework.statemachine.config.EnumStateMachineConfigurerAdapter;
import org.springframework.statemachine.config.StateMachineFactory;
import org.springframework.statemachine.config.builders.StateMachineConfigurationConfigurer;
import org.springframework.statemachine.config.builders.StateMachineStateConfigurer;
import org.springframework.statemachine.config.builders.StateMachineTransitionConfigurer;
import org.springframework.statemachine.listener.StateMachineListener;
import org.springframework.statemachine.listener.StateMachineListenerAdapter;
import org.springframework.statemachine.persist.StateMachineRuntimePersister;
import org.springframework.statemachine.service.DefaultStateMachineService;
import org.springframework.statemachine.service.StateMachineService;
import org.springframework.statemachine.state.State;

import java.util.EnumSet;
import java.util.Optional;

@Slf4j
@EnableStateMachineFactory
@Configuration
public class StateMachineConfig
        extends EnumStateMachineConfigurerAdapter<States, Events> {

    @Autowired
    private StateMachineRuntimePersister<States, Events, String> stateMachineRuntimePersister;

    @Override
    public void configure(StateMachineConfigurationConfigurer<States, Events> config)
            throws Exception {
        config
                .withConfiguration()
                .machineId("my-machine2")
                .listener(listener())
                .autoStartup(true)
                .and()
                .withPersistence()
                .runtimePersister(stateMachineRuntimePersister)
        ;
    }

    @Override
    public void configure(StateMachineStateConfigurer<States, Events> states)
            throws Exception {
        states
                .withStates()
                .initial(States.SI)
                .end(States.SF)
                .states(EnumSet.allOf(States.class));
    }

    @Override
    public void configure(StateMachineTransitionConfigurer<States, Events> transitions)
            throws Exception {
        transitions
                .withExternal()
                .source(States.SI).target(States.S1).event(Events.E1).guardExpression("true")
                .and()
                .withExternal()
                .source(States.S1).target(States.S2).event(Events.E2)
                .and()
                .withExternal()
                .source(States.S2).target(States.SF).event(Events.E3);

    }


    @Bean
    public StateMachineListener<States, Events> listener() {
        return new StateMachineListenerAdapter<States, Events>() {
            @Override
            public void stateChanged(State<States, Events> from, State<States, Events> to) {
                log.warn("State change from:{} to:{} ", Optional.ofNullable(from).map(State::getId).orElse(null),
                        to.getId());

            }


        };
    }


    @Bean
    public StateMachineRuntimePersister<States, Events, String> stateMachineRuntimePersister() {
        return new MappedPersistingStateMachineInterceptor<>();
    }


    //service config
    @Bean
    public StateMachineService<States, Events> stateMachineService(
            StateMachineFactory<States, Events> stateMachineFactory,
            StateMachineRuntimePersister<States, Events, String> stateMachineRuntimePersister) {
        return new DefaultStateMachineService<>(stateMachineFactory, stateMachineRuntimePersister);
    }
}