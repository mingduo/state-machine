package com.mingduo.springboot.statemache.config;

import org.springframework.statemachine.StateMachineContext;
import org.springframework.statemachine.StateMachinePersist;
import org.springframework.statemachine.persist.AbstractPersistingStateMachineInterceptor;
import org.springframework.statemachine.persist.StateMachineRuntimePersister;
import org.springframework.statemachine.support.StateMachineInterceptor;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * @author : weizc
 * @since 2021/6/21
 */
public class MappedPersistingStateMachineInterceptor<S, E, T> extends AbstractPersistingStateMachineInterceptor<S, E, T>
        implements StateMachineRuntimePersister<S, E, T> {

    private ConcurrentMap<T, StateMachineContext<S, E>> concurrentMap = new ConcurrentHashMap<>();

    private StateMachinePersist<S, E, T> persist = new StateMachinePersist<S, E, T>() {
        @Override
        public void write(StateMachineContext<S, E> context, T contextObj) throws Exception {
            concurrentMap.put(contextObj, context);

        }

        @Override
        public StateMachineContext<S, E> read(T contextObj) throws Exception {
            return concurrentMap.get(contextObj);
        }
    };

    /**
     * Instantiates a new abstract state machine persister.
     */
    public MappedPersistingStateMachineInterceptor() {
        super();
    }


    @Override
    public StateMachineInterceptor<S, E> getInterceptor() {
        return this;
    }

    @Override
    public void write(StateMachineContext<S, E> context, T contextObj) throws Exception {
        concurrentMap.put(contextObj, context);

    }

    @Override
    public StateMachineContext<S, E> read(T contextObj) throws Exception {
        return concurrentMap.get(contextObj);
    }
}
