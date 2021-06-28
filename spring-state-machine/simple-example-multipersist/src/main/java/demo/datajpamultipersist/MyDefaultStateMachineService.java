package demo.datajpamultipersist;

import lombok.extern.slf4j.Slf4j;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.StateMachineContext;
import org.springframework.statemachine.StateMachineException;
import org.springframework.statemachine.StateMachinePersist;
import org.springframework.statemachine.config.StateMachineFactory;
import org.springframework.statemachine.service.DefaultStateMachineService;

import java.util.HashMap;
import java.util.Map;

/**
 * @author : weizc
 * @since 2021/6/23
 */
@Slf4j
public class MyDefaultStateMachineService<S, E> extends DefaultStateMachineService<S, E> {

    private final StateMachineFactory<S, E> stateMachineFactory;
    private final Map<String, StateMachine<S, E>> machines = new HashMap<String, StateMachine<S, E>>();
    private StateMachinePersist<S, E, String> stateMachinePersist;

    public MyDefaultStateMachineService(StateMachineFactory stateMachineFactory) {
        super(stateMachineFactory);
        this.stateMachineFactory = stateMachineFactory;
    }

    public MyDefaultStateMachineService(StateMachineFactory<S, E> stateMachineFactory, StateMachinePersist<S, E, String> stateMachinePersist) {
        super(stateMachineFactory, stateMachinePersist);
        this.stateMachineFactory = stateMachineFactory;
        this.stateMachinePersist = stateMachinePersist;
    }


    @Override
    public StateMachine<S, E> acquireStateMachine(String machineId, boolean start) {
        log.info("Acquiring machine with id " + machineId);
        StateMachine<S, E> stateMachine = null;
        synchronized (machines) {

            log.info("Getting new machine from factory with id " + machineId);
            String readMachineId = machineId.substring(0, machineId.contains("_") ? machineId.indexOf("_") : machineId.length());
            //获取配置的状态机
            stateMachine = stateMachineFactory.getStateMachine(readMachineId);
            if (stateMachinePersist != null) {
                try {
                    //获取context
                    StateMachineContext<S, E> stateMachineContext = stateMachinePersist.read(machineId);
                    stateMachine = restoreStateMachine(stateMachine, stateMachineContext);
                } catch (Exception e) {
                    log.error("Error handling context", e);
                    throw new StateMachineException("Unable to read context from store", e);
                }
            }
        }
        return handleStart(stateMachine, start);
    }

}
