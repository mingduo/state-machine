package demo.datajpamultipersist;

import org.springframework.statemachine.StateMachineContext;
import org.springframework.statemachine.data.jpa.JpaRepositoryStateMachine;
import org.springframework.statemachine.data.jpa.JpaRepositoryStateMachinePersist;
import org.springframework.statemachine.data.jpa.JpaStateMachineRepository;
import org.springframework.statemachine.service.StateMachineSerialisationService;

/**
 * @author : weizc
 * @since 2021/6/23
 */
public class MyJpaRepositoryStateMachinePersist<S, E> extends JpaRepositoryStateMachinePersist<S, E> {
    public MyJpaRepositoryStateMachinePersist(JpaStateMachineRepository jpaStateMachineRepository) {
        super(jpaStateMachineRepository);
    }

    public MyJpaRepositoryStateMachinePersist(JpaStateMachineRepository jpaStateMachineRepository, StateMachineSerialisationService<S, E> serialisationService) {
        super(jpaStateMachineRepository, serialisationService);
    }

    @Override
    protected JpaRepositoryStateMachine build(StateMachineContext<S, E> context, Object contextObj, byte[] serialisedContext) {
        JpaRepositoryStateMachine build = super.build(context, contextObj, serialisedContext);
        String id = (String) context.getExtendedState().getVariables().get("id");
        String newMachineId = build.getMachineId() + "_" + id;
        build.setMachineId(newMachineId);
        return build;
    }
}
