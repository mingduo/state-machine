package com.mingduo.springboot.statemache.config;

import com.mingduo.springboot.statemache.domain.Events;
import com.mingduo.springboot.statemache.domain.States;
import com.mingduo.springboot.statemache.domain.States2;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.statemachine.action.Action;
import org.springframework.statemachine.config.EnableStateMachine;
import org.springframework.statemachine.config.StateMachineConfigurerAdapter;
import org.springframework.statemachine.config.builders.StateMachineConfigurationConfigurer;
import org.springframework.statemachine.config.builders.StateMachineStateConfigurer;
import org.springframework.statemachine.config.builders.StateMachineTransitionConfigurer;

import java.util.EnumSet;
import java.util.Set;

import static java.util.stream.Collectors.toSet;

@Slf4j
@Configuration
public class StateMachineConfig {


    //@EnableStateMachine(name = "ma1")
    @Configuration
    public static class MachineConfig
            extends StateMachineConfigurerAdapter<String, Events> {


        @Override
        public void configure(StateMachineConfigurationConfigurer<String, Events> config)
                throws Exception {
            config
                    .withConfiguration()
                    .machineId("machine")
                    .autoStartup(true)
            ;


        }

        // si->S1->S2->SF
        @Override
        public void configure(StateMachineStateConfigurer<String, Events> states)
                throws Exception {
            states
                    .withStates()
                    .initial(States.SI.name())
                    .end(States.SF.name())
                    .states(EnumSet.allOf(States.class).stream().map(Enum::name).collect(toSet()));
        }

        @Override
        public void configure(StateMachineTransitionConfigurer<String, Events> transitions)
                throws Exception {
            transitions
                    .withExternal()
                    .source(States.SI.name()).target(States.S1.name()).event(Events.E1).guardExpression("true")
                    .and()
                    .withExternal()
                    .source(States.S1.name()).target(States.S2.name()).event(Events.E2)
                    .and()
                    .withExternal()
                    .source(States.S2.name()).target(States.SF.name()).event(Events.E3);
        }


    }

    //@EnableStateMachine(name = "ma2")
    @Configuration
    public static class MachineConfig2
            extends StateMachineConfigurerAdapter<String, Events> {


        @Override
        public void configure(StateMachineConfigurationConfigurer<String, Events> config)
                throws Exception {

            config
                    .withConfiguration()
                    .machineId("machine2")
                    .autoStartup(true)
                    .listener(new MyStateMachineListenerAdapter<>())
            ;


        }

        // S1->S2->SF
        //           |
        //         s21->s22
        @Override
        public void configure(StateMachineStateConfigurer<String, Events> states)
                throws Exception {
            Set<String> allSet = EnumSet.allOf(States2.class).stream().map(States2::name).collect(toSet());
            Action<String, Events> action = context -> System.out.println("finished");

            states
                    .withStates()
                    .initial(States2.S1.name())
                    .state(States2.SF.name(), action)
                    .states(allSet)
                    .and()
                    .withStates()
                    .parent(States2.S2.name())
                    .initial(States2.S21.name())
                    .state(States2.S22.name());
        }

        @Override
        public void configure(StateMachineTransitionConfigurer<String, Events> transitions)
                throws Exception {
            transitions
                    .withExternal()
                    .source(States2.S1.name()).target(States2.S2.name()).event(Events.E1).guardExpression("true")
                    .and()
                    .withExternal()
                    .source(States2.S2.name()).target(States2.SF.name()).event(Events.E3)
                    .and()
                    .withExternal()
                    .and()
                    .withExternal()
                    .source(States2.S21.name()).target(States2.S22.name()).event(Events.E2)
            ;
        }


    }


    // @EnableStateMachine(name = "ma3")
    @Configuration
    public static class MachineConfig3
            extends StateMachineConfigurerAdapter<String, Events> {


        @Override
        public void configure(StateMachineConfigurationConfigurer<String, Events> config)
                throws Exception {
            config
                    .withConfiguration()
                    .machineId("ma3")
                    .listener(new MyStateMachineListenerAdapter<>())
                    .autoStartup(true)
            ;


        }

        //              ->S21
        //  s1                   ->SF
        //              ->S22
        @Override
        public void configure(StateMachineStateConfigurer<String, Events> states)
                throws Exception {
            states
                    .withStates()
                    .initial(States2.S1.name())
                    .choice(States2.S2.name())
                    .end(States2.SF.name())
                    .states(EnumSet.allOf(States2.class)
                            .stream().map(Enum::name).collect(toSet()));
        }

        @Override
        public void configure(StateMachineTransitionConfigurer<String, Events> transitions)
                throws Exception {
            transitions
                    .withExternal()
                    .source(States2.S1.name()).target(States2.S2.name())
                    .event(Events.E1)
                    .and()
                    .withChoice()
                    .source(States2.S2.name())
                    .first(States2.S21.name(), new MyGuard())
                    //.then()
                    .last(States2.S22.name())
                    .and()
                    .withExternal()
                    .source(States2.S21.name()).target(States2.SF.name())
                    .event(Events.E2)
                    .and().withExternal()
                    .source(States2.S22.name()).target(States2.SF.name())
                    .event(Events.E3);

        }


    }


    @EnableStateMachine(name = "ma4")
    @Configuration
    public static class MachineConfig4
            extends StateMachineConfigurerAdapter<String, Events> {


        @Override
        public void configure(StateMachineConfigurationConfigurer<String, Events> config)
                throws Exception {
            config
                    .withConfiguration()
                    .machineId("ma4")
                    .listener(new MyStateMachineListenerAdapter<>())
                    .autoStartup(true)
            ;


        }

        //              ->S21
        //  s1                   ->SF
        //              ->S22
        @Override
        public void configure(StateMachineStateConfigurer<String, Events> states)
                throws Exception {

            Set<String> allSet = EnumSet.allOf(States2.class).stream().map(States2::name).collect(toSet());

            Action<String, Events> action = context -> System.out.println("action :" + context.getEvent());

            states
                    .withStates()
                    .initial(States2.S1.name())
                    .state(States2.S21.name(), action)
                    .states(allSet)
                    .and()
                    .withStates()
                    .parent(States2.S2.name())
                    .initial(States2.S21.name())
                    .state(States2.S22.name());
        }

        @Override
        public void configure(StateMachineTransitionConfigurer<String, Events> transitions)
                throws Exception {

            Action<String, Events> action = context -> System.out.println("action");


            transitions
                    .withLocal()
                    .source(States2.S1.name()).target(States2.S2.name())
                    .event(Events.E1).guardExpression("true")
                    .and()
                    .withInternal().
                    source(States2.S2.name())
                    .event(Events.E2)
                    .action(action)
                    .and()
                    .withExternal()
                    .source(States2.S2.name()).target(States2.SF.name())
                    .event(Events.E3)
                    .and()
                    .withExternal()
                    .and()
                    .withExternal()
                    .source(States2.S21.name()).target(States2.S22.name())
                    .event(Events.E4)
                    .and()
                    .withLocal()
                    .source(States2.S22.name()).target(States2.SF.name()).event(Events.E5)
            ;

            transitions
                    .withExternal()
                    .source(States.S1.name()).target(States.S2.name())
                    .event(Events.E1)
                    .and()
                    .withInternal()
                    .source(States.S2.name())
                    .event(Events.E2)
                    .and()
                    .withLocal()
                    .source(States.S2.name()).target(States2.SF.name())
                    .event(Events.E3);

        }


    }


}